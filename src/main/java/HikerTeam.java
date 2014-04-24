package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class HikerTeam extends SearchTeam {

	public HikerTeam(Point startLocation) {
		super(5, startLocation, "../resources/HikerIcon.png");
		// TODO Auto-generated constructor stub
	}

	public void manualUpdate(Point location){
		super.lastKnownPosition=location;
	}

}
