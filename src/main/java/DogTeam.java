package main.java;

import java.awt.Point;

public class DogTeam extends SearchTeam {
	
	public DogTeam(Point startLocation, TopographicMap map) {
		super(200, startLocation, map, "../resources/DogIcon.png");
	}
	
	@Override
	public Point getHypothesizedLocation() {
		return this.getCenter();
	}
	
	@Override
	public void notifyUser(Point p) {
		this.move(p);
	}
}
