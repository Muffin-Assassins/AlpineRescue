package main.java;

import java.awt.Point;

public class DogTeam extends SearchTeam {
	
	public DogTeam(Point startLocation, TopographicMap map) {
		super(200, startLocation, map, "../resources/DogIcon.png"); //create object with picture as icon
	}
	
	@Override
	public Point getHypothesizedLocation() { //dog teams have erratic behavior and therefore the program does not attempt to hypothesize their behavior 
		return this.getCenter();
	}
	
	@Override
	public void notifyUser(Point p) {
		this.move(p);
	}
}
