package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class SearchGridPanel extends GridPanel {
	ArrayList<SearchTeam> searchTeams;
	
	public SearchGridPanel() {
		this.setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(255, 0, 0, 100));
		g.fillRect(100,  100, 200, 200);
	}

}
