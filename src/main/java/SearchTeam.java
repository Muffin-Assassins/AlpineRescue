package main.java;

import java.awt.Color;
import java.awt.Point;

public abstract class SearchTeam implements GridPanel.Drawable {
	private final Color color;
	private final int radius;
	private final Point startLocation;
	
	private Point lastKnownPosition;
	
	public SearchTeam(Color color, int radius, Point startLocation) {
		super();
		this.color = color;
		this.radius = radius;
		this.startLocation = startLocation;
		this.lastKnownPosition = lastKnownPosition;
	}
}
