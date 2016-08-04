package edu.xidian;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxEdgeLabelLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxPartitionLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import de.invation.code.toval.graphic.component.DisplayFrame;
import de.invation.code.toval.validate.ParameterException;
import de.invation.code.toval.validate.Validate;
import de.uni.freiburg.iig.telematik.jagal.graph.Edge;
import de.uni.freiburg.iig.telematik.jagal.graph.Graph;
import de.uni.freiburg.iig.telematik.jagal.graph.Vertex;
import de.uni.freiburg.iig.telematik.jagal.graph.abstr.AbstractGraph;
import de.uni.freiburg.iig.telematik.jagal.graph.exception.VertexNotFoundException;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractFlowRelation;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractMarking;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPNNode;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPetriNet;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractPlace;
import de.uni.freiburg.iig.telematik.sepia.petrinet.abstr.AbstractTransition;
import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTNet;

public class AbstractPetriGraphComponent<P extends AbstractPlace<F,S>, 
                                         T extends AbstractTransition<F,S>, 
                                         F extends AbstractFlowRelation<P,T,S>, 
                                         M extends AbstractMarking<S>, 
                                         S extends Object>  
                                       extends JPanel {
	
	private static final long serialVersionUID = -6795020261692373388L;
	
	protected AbstractPetriNet<P,T,F,M,S> petriNet = null;

	private JComponent graphPanel = null;

	protected mxGraph visualGraph = null;
	protected Graph<Object> graph = new Graph<Object>();
	protected Map<String, Object> vertices = new HashMap<String, Object>(); 
	
	protected boolean highlightSources = true;
	protected boolean highlightDrains = false;
	
	protected static final String sourceNodeColor = "#f6f5b1";
	protected static final String drainNodeColor = "#f2ddf8";

	public AbstractPetriGraphComponent(AbstractPetriNet<P,T,F,M,S> petriNet) throws ParameterException  {
		Validate.notNull(petriNet);
		this.petriNet = petriNet;
	}
	
	public void initialize() throws Exception{
		setupGraph();
		setupVisualGraph();
		setLayout(new BorderLayout(20, 0));
		add(getGraphPanel(), BorderLayout.CENTER);
		setPreferredSize(getGraphPanel().getPreferredSize());
	}
	
	private void setupGraph() throws VertexNotFoundException {
		for(AbstractPNNode<F> node: petriNet.getNodes()) {
			graph.addVertex(node.getName());
		}
		
		for(F flow: petriNet.getFlowRelations()) {
			graph.addEdge(flow.getSource().getName(),flow.getTarget().getName());
		}
	}

	/**
	 * 子类重写此方法，insertVextex，isertEdge
	 * @param graph
	 * @throws Exception
	 */
	protected void setupVisualGraph() throws Exception{
		mxStylesheet style = new mxStylesheet();
		System.out.println(style.getDefaultVertexStyle());
		System.out.println(style.getDefaultVertexStyle());
		System.out.println(style.getStyles());
		
		
		visualGraph = new mxGraph();
		Object parent = visualGraph.getDefaultParent();

		visualGraph.getModel().beginUpdate();
		try{
			for (Vertex graphVertex : graph.getVertices()) {
				String vertexName = graphVertex.getName();
				Object vertex = visualGraph.insertVertex(parent, vertexName, vertexName, 0, 0, 40, 40, getShapeForVertex(vertexName));
				//Object vertex = visualGraph.insertVertex(parent, vertexName, vertexName, 0, 0, 40, 40);
				vertices.put(vertexName, vertex);
				Object[] cells = new Object[1];
				cells[0] = vertex;
				visualGraph.setCellStyles(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_RIGHT,cells);
				//visualGraph.setCellStyles(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_LEFT,cells);
				//visualGraph.setCellStyles(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_TOP,cells);
			}
			for (String vertexName : graph.getVertexNames()) {
				for (Edge outgoingEdge : graph.getOutgoingEdgesFor(vertexName)) {
					String followerVertexName = outgoingEdge.getTarget().getName();
//					mxCell insertedEdge = (mxCell) visualGraph.insertEdge(parent, vertexName + "-" + followerVertexName, getEdgeLabel(outgoingEdge), vertices.get(vertexName), vertices.get(followerVertexName));
					mxCell insertedEdge = (mxCell) visualGraph.insertEdge(parent, vertexName + "-" + followerVertexName, "1", vertices.get(vertexName), vertices.get(followerVertexName));
					Object[] cells = new Object[1];
					cells[0] = insertedEdge;
					visualGraph.setCellStyles(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff", cells);
					//visualGraph.setCellStyles(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_LEFT,cells);
					//visualGraph.setCellStyles(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_RIGHT,cells);
				}
			}
		}
		finally{
			visualGraph.getModel().endUpdate();
		}
		
//		if (highlightSources) {
//			for (V sourceVertex : graph.getSources()) {
//				setNodeColor(sourceNodeColor, sourceVertex.getName());
//			}
//		}
//		
//		if (highlightDrains) {
//			for (V drainVertex : graph.getDrains()) {
//				setNodeColor(drainNodeColor, drainVertex.getName());
//			}
//		}
		
		mxHierarchicalLayout layout = new mxHierarchicalLayout(visualGraph);
		//mxCompactTreeLayout layout = new mxCompactTreeLayout(visualGraph);
		//mxFastOrganicLayout layout = new mxFastOrganicLayout(visualGraph);
		//mxOrganicLayout layout = new mxOrganicLayout(visualGraph);
		
		//layout.setDisableEdgeStyle(false);
		layout.setDisableEdgeStyle(true); // ok
		layout.execute(visualGraph.getDefaultParent());
	}
	
	/**
	 * 顶点的shape,缺省是"shape=ellipse",
	 * 子类可以重写该方法，定义顶点的形状，如"shape=doubleEllipse";
	 * @param graphVertex
	 * @return
	 */
	protected String getShapeForVertex(String nodeName) {
		// Place
		if (petriNet.getPlace(nodeName) != null) return mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ELLIPSE;  //"shape=ellipse";
		//Transition
		return mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_RECTANGLE; //"shap=rectangle";
	}

	/**
	 * 边的Label，这里是"",子类可以重写此方法，显示边的Label
	 * @param edge
	 * @return
	 */
	protected String getEdgeLabel(F edge){
		return "";
	}
	
	private JComponent getGraphPanel() {
		if(graphPanel == null){
			graphPanel = new mxGraphComponent(visualGraph);
		}
		return graphPanel;
	}
	
	public void setNodeColor(String color, String... nodeNames){
		Object[] cells = new Object[nodeNames.length];
		for(int i=0; i<nodeNames.length; i++)
			cells[i] = vertices.get(nodeNames[i]);
		visualGraph.setCellStyles(mxConstants.STYLE_FILLCOLOR, color, cells);
	}
	
	public static void main(String[] args) {
		PTNet ptnet = CreatePetriNet.createPTnet1();
		AbstractPetriGraphComponent<?, ?, ?, ?, ?> component = new AbstractPetriGraphComponent<>(ptnet);
		try {
			component.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    new DisplayFrame(component,true);
	}

}
