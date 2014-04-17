package main.java;

import java.awt.Color;
import java.awt.Graphics;

public class SearchGridPanel extends GridPanel {
	
	public SearchGridPanel() {
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(255, 0, 0, 20));
		g.fillRect(100,  100, 200, 200);
	}

}
