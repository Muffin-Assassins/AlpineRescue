package main.java;

import java.awt.Point;

public class HikerTeam extends SearchTeam {

	public HikerTeam(Point startLocation, TopographicMap map) {
		super(100, startLocation, map, "../resources/HikerIcon.png");
	}
}
