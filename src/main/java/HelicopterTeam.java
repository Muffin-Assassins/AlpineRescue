package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class HelicopterTeam extends SearchTeam {
	

	public HelicopterTeam(Color color, int radius, Point startLocation) {
		super(color, radius, startLocation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Point getGridLocation() {
		// TODO Auto-generated method stub
		return super.lastKnownPosition;
	}

	@Override
	public void draw(Graphics graphics) {
		// TODO Auto-generated method stub
		
	}
	public void manualUpdate(Point location){
		super.lastKnownPosition=location;
	}
}
