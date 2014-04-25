package main.java;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ControlWindow extends JFrame {
	private final static int WINDOW_BORDER_THICKNESS = 39;
	private final static int WINDOW_EDGE_THICKNESS = 17;
	private final static String RESOURCE_PATH = "../resources/";
	
	private Grid grid;
	
	public static void main(String[] args){
		ControlWindow cw = new ControlWindow();
	}
	
	public ControlWindow() throws HeadlessException {
		super();
		
		JOptionPane.showMessageDialog(this,
			    "To add a new Search Team right click on their starting Location.",
			    "Begin Similulation",
			    JOptionPane.PLAIN_MESSAGE);
		
		this.setTitle("Alpine Rescuess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		
		this.grid = new Grid(20, RESOURCE_PATH + "Pike_Peak_Topo.png");
		
		this.add(grid, BorderLayout.CENTER);
		this.setSize(this.grid.getWidth() + ControlWindow.WINDOW_EDGE_THICKNESS, this.grid.getHeight() + ControlWindow.WINDOW_BORDER_THICKNESS);
	}

}
