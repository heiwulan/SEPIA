package edu.xidian;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.hamcrest.core.IsInstanceOf;

import de.uni.freiburg.iig.telematik.sepia.petrinet.pt.PTNet;

/**
 * PTNet Graph and It's marking graph 
 */
public class PTNetGraph implements ActionListener, ItemListener {
	/** 输出状态信息 */
    private JTextArea output;
    private static final String newline = "\n";
    /** PTNet Graph */
    private PTNetGraphComponent ptnetGraph = null;
    
    /** 图的朝向 */
    private String[] orientationStr = {"NORTH","WEST","SOUTH","EAST"};
    
    /** 表示图的朝向的单选按钮，key: orientationStr */
    private Map<String,JRadioButtonMenuItem> orientationRadioBtn = new HashMap<>();
   
    public PTNetGraph(PTNetGraphComponent ptnetGraph) {
		this.ptnetGraph = ptnetGraph;
	}

	private JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        // Create the menu bar.
        menuBar = new JMenuBar();

        // Build the first menu.
        menu = new JMenu("Graph");
        menu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(menu);

        // JMenuItems for first menu
        menuItem = new JMenuItem("PTNet");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("MrkingGraph");
        menuItem.setMnemonic(KeyEvent.VK_M);
        menuItem.addActionListener(this);
        menu.add(menuItem);


        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
        	rbMenuItem = new JRadioButtonMenuItem(orientationStr[i]);
            group.add(rbMenuItem);
            rbMenuItem.addActionListener(this);
            menu.add(rbMenuItem);
            orientationRadioBtn.put(orientationStr[i],rbMenuItem);
        }
        // default selected
        orientationRadioBtn.get("NORTH").setSelected(true);
        // Sets the keyboard mnemonic 
        orientationRadioBtn.get("NORTH").setMnemonic(KeyEvent.VK_N);
        orientationRadioBtn.get("WEST").setMnemonic(KeyEvent.VK_W);
        orientationRadioBtn.get("SOUTH").setMnemonic(KeyEvent.VK_S);
        orientationRadioBtn.get("EAST").setMnemonic(KeyEvent.VK_E);
        
        //a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("PTNet");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        cbMenuItem.addItemListener(this);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("MarkingGraph");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        cbMenuItem.addItemListener(this);
        menu.add(cbMenuItem);
        
        return menuBar;
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource()); 
        
        System.out.println(source instanceof JRadioButtonMenuItem);
        
        String s = "Action event detected."
                   + newline
                   + " (an instance of " + getClassName(source) + ")"
                   + newline
                   + "    Event source: " + source.getText();
        status(s);
    }

    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                   + newline
                   + " (an instance of " + getClassName(source) + ")"
                   + newline
                   + "    Event source: " + source.getText()
                   + newline
                   + "    New state: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
        status(s);
    }
    
    /**
     * 显示状态
     * @param status 状态信息
     */
    public void status(String status) {
    	 output.append(status + newline);
         output.setCaretPosition(output.getDocument().getLength());
    }
    
	private Container addComponentsToPane( ) {

		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);
		
		// Add the ptnetGraph to the content pane.
		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(ptnetGraph);
//		contentPane.add(scroll, BorderLayout.CENTER);
//		contentPane.add(scroll, BorderLayout.PAGE_START);
		contentPane.add(scroll, BorderLayout.LINE_START);
		
		// add the marking graph
		//JButton button = new JButton("Line end Button (LINE_END)");
		//contentPane.add(button, BorderLayout.LINE_END);
		
		//Create a scrolled status text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        JScrollPane statusPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(statusPane, BorderLayout.PAGE_END);
        
        return contentPane;
	}
	
    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /**
     * Create the GUI and show it.  For thread safety, this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI(PTNetGraphComponent ptnetGraph) {		
        //Create and set up the window.
        JFrame frame = new JFrame("PTNet and MarkingGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        PTNetGraph demo = new PTNetGraph(ptnetGraph);
        frame.setJMenuBar(demo.createMenuBar());
       
		frame.setContentPane(demo.addComponentsToPane());

        //Display the window.
        //frame.setSize(450, 260);
		frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
    	PTNet ptnet = CreatePetriNet.createPTnet1();
    	PTNetGraphComponent ptnetGraph = new PTNetGraphComponent(ptnet);
 		try {
 			ptnetGraph.initialize();
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
    	//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(ptnetGraph);
            }
        });
    }
}
