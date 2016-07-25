package edu.xidian;

import org.junit.Test;

import de.invation.code.toval.graphic.component.DisplayFrame;
import de.uni.freiburg.iig.telematik.jagal.graph.Graph;
import de.uni.freiburg.iig.telematik.jagal.graph.exception.VertexNotFoundException;
import de.uni.freiburg.iig.telematik.jagal.graph.weighted.WeightedGraph;
import de.uni.freiburg.iig.telematik.jagal.visualization.flexible.GraphComponent;
import de.uni.freiburg.iig.telematik.jagal.visualization.flexible.WeightedGraphComponent;

public class DisplayTest {

	public static void main(String[] args) throws VertexNotFoundException {
			// Graph<U> where U is the type of the vertex elements
			Graph<Integer> g = new Graph<Integer>();
			// Add some vertices
			g.addVertex("A", 4);
			g.addVertex("B", 3);
			g.addVertex("C", 1);
			g.addVertex("D");
			g.getVertex("D").setElement(2);
			g.addVertex("E", 8);
			// Add some edges
			g.addEdge("A", "B");
			g.addEdge("B", "C");
			g.addEdge("B", "D");
			g.addEdge("C", "A");
			g.addEdge("C", "D");
			g.addEdge("D", "E");
			g.addEdge("E", "B");
			// Let's take a look at the result
			System.out.println(g);
			System.out.println(g.getVertex("E").getElement());
			
			// WeightedGraph<U> where U is the vertex element type
			WeightedGraph<Integer> gw = new WeightedGraph<Integer>();
			// Add some vertices
			gw.addVertex("A");
			gw.addVertex("B");
			gw.addVertex("C");
			gw.addVertex("D");
			gw.addVertex("E");
			// Add some edges
			gw.addEdge("A", "B");
			gw.addEdge("B", "C");
			gw.addEdge("B", "D", 2.0);
			gw.addEdge("C", "A", 3.4);
			gw.addEdge("C", "D");
			gw.addEdge("D", "E");
			gw.addEdge("E", "B");
			// Let's take a look at the result
			System.out.println(gw);
			
			// display
			GraphComponent jpanle;
			try {
				jpanle = new GraphComponent(g);
				jpanle.initialize();
				new DisplayFrame(jpanle, true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			WeightedGraphComponent jpanle1;
			try {
				jpanle1 = new WeightedGraphComponent(gw);
				jpanle1.initialize();
				new DisplayFrame(jpanle1, true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				//new DisplayFrame(new GraphComponent(g), true);
				//new DisplayFrame(new WeightedGraphComponent(gw), true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
