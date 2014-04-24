package main.java;

public class Velocity {
	private double speed;
	private Direction direction;
	
	
	public Velocity() {
		super();
		this.speed = 0.0;
	}


	public Velocity(double speed, Direction direction) { 
		super();
		this.setSpeed(speed);
		this.direction = direction;
	}
	//write limits on speed and direction not to run off the screen
	
	public void setSpeed(double speed) {
		if(speed < 0.0 || speed > 100.0) throw new NumberFormatException("Invalid speed: " + Double.toString(speed) + "\nValue must be between 0.0 and 1.0, inclusive.");
		else this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}


	@Override
	public String toString() {
		return "Velocity [speed=" + speed + ", direction=" + direction + "]";
	}
}
