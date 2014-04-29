package main.java;

import java.awt.Point;

public class HelicopterTeam extends SearchTeam {
	

	public HelicopterTeam(Point startLocation, TopographicMap map) { //create object with picture as icon
		super(1000, startLocation, map, "../resources/HelicopterIcon.png");
	}

}
