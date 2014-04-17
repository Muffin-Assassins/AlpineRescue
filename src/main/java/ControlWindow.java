package main.java;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class ControlWindow extends JFrame {
	private final static int WINDOW_BORDER_THICKNESS = 39;
	private final static int WINDOW_EDGE_THICKNESS = 17;
	private TopographicGridPanel topographicSearchGrid;
	
	public ControlWindow() throws HeadlessException {
		super();
		this.setTitle("Alpine Rescuess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.add(this.topographicSearchGrid = new TopographicGridPanel(1, 20 ,"../resources/Pike_Peak_Topo.png"), BorderLayout.CENTER);
		this.setSize(this.topographicSearchGrid.getGridPixelWidth() + this.WINDOW_EDGE_THICKNESS, this.topographicSearchGrid.getGridPixelHeight() + this.WINDOW_BORDER_THICKNESS);
	}
	
	public static void main(String[] args) {
		ControlWindow gui = new ControlWindow();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
