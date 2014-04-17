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
	
	public ControlWindow() throws HeadlessException {
		super();
		this.setTitle("Alpine Rescuess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.topographicGridPanel = new TopographicGridPanel(1, 20 ,"../resources/Pike_Peak_Topo.png");
		this.searchGridPanel = new SearchGridPanel();
		
		this.add(this.topographicGridPanel = new TopographicGridPanel(1, 20 ,"../resources/Pike_Peak_Topo.png"), BorderLayout.CENTER);
		this.add(this.searchGridPanel, BorderLayout.CENTER);
		this.setSize(this.topographicGridPanel.getGridPixelWidth() + this.WINDOW_EDGE_THICKNESS, this.topographicGridPanel.getGridPixelHeight() + this.WINDOW_BORDER_THICKNESS);
	}
	
	public static void main(String[] args) {
		ControlWindow gui = new ControlWindow();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
