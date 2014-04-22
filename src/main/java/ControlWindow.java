package main.java;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class ControlWindow extends JFrame {
	private final static int WINDOW_BORDER_THICKNESS = 39;
	private final static int WINDOW_EDGE_THICKNESS = 17;
	
	private TopographicGridPanel topographicGridPanel;
	private SearchGridPanel searchGridPanel;
	private JLayeredPane mapPane;
	
	public ControlWindow() throws HeadlessException {
		super();
		this.setTitle("Alpine Rescuess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		mapPane = new JLayeredPane();
		this.topographicGridPanel = new TopographicGridPanel(1, 20 ,"../resources/Pike_Peak_Topo.png");
		this.searchGridPanel = new SearchGridPanel();
		this.searchGridPanel.setBounds(0, 0, this.topographicGridPanel.getGridPixelWidth(), this.topographicGridPanel.getGridPixelHeight());
		mapPane.setPreferredSize(this.topographicGridPanel.getGridPixelDimension());
		
		mapPane.add(this.searchGridPanel);
		mapPane.add(this.topographicGridPanel);
		
		this.add(mapPane, BorderLayout.CENTER);
		this.pack();
		this.setSize(this.topographicGridPanel.getGridPixelWidth() + this.WINDOW_EDGE_THICKNESS, this.topographicGridPanel.getGridPixelHeight() + this.WINDOW_BORDER_THICKNESS);
	}
	
	public static void main(String[] args) { // turn this into a test instead of a main
		ControlWindow gui = new ControlWindow();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
