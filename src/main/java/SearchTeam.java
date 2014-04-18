package main.java;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public abstract class SearchTeam implements GridPanel.Drawable {
	private final Color color;
	private final int radius;
	private final Point startLocation;
	
	protected Velocity velocity;
	protected Point lastKnownPosition;
	protected ArrayList<Point> hypothesizedLocations;
	
	public SearchTeam(Color color, int radius, Point startLocation) {
		super();
		this.color = color;
		this.radius = radius;
		this.startLocation = startLocation;
		this.lastKnownPosition = startLocation;
		this.hypothesizedLocations = new ArrayList<Point>();
		velocity= new Velocity(0.0, Direction.NORTH);
		this.hypothesizedLocations.add(lastKnownPosition);
	}
	public void setVelocity(Velocity v){
		velocity=v;
	}
	public void hypothesizeLocation(){
		switch(velocity.getDirection()){
		case NORTH:	
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()).x, hypothesizedLocations.get(hypothesizedLocations.size()).y - ((int)this.velocity.getSpeed())));
			break;
		case SOUTH: 
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()).x, hypothesizedLocations.get(hypothesizedLocations.size()).y + ((int)this.velocity.getSpeed())));
			break;
		case EAST: 
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()).x + ((int)this.velocity.getSpeed()), hypothesizedLocations.get(hypothesizedLocations.size()).y));
			break;
		case WEST: 
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()).x - ((int)this.velocity.getSpeed()), hypothesizedLocations.get(hypothesizedLocations.size()).y));
			break;
		case NORTHEAST:
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()).x + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()).y - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		case NORTHWEST: 
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()).x - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()).y - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		case SOUTHEAST: 
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()).x + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()).y + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		case SOUTHWEST:
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()).x - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()).y + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		default:
				break;
		}
	}
	public abstract void manualUpdate(Point location);
}
