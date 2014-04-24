package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class HikerTeam extends SearchTeam {

	public HikerTeam(Color color, int radius, Point startLocation) {
		super(color, radius, startLocation);
		// TODO Auto-generated constructor stub
	}

	public void manualUpdate(Point location){
		super.lastKnownPosition=location;
	}

}
