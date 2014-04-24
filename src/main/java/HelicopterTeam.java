package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class HelicopterTeam extends SearchTeam {
	

	public HelicopterTeam(Point startLocation) {
		super(10, startLocation,"../resources/HelicopterIcon.png");
		// TODO Auto-generated constructor stub
	}

	public void manualUpdate(Point location){
		super.lastKnownPosition=location;
	}
}
