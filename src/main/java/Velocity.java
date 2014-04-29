package main.java;

public class Velocity {
	private double speed;
	private double direction;
	
	
	public Velocity() {
		super();
		this.speed = 0.0;
		this.direction = 0.0;
	}


	public Velocity(double speed, double direction) { 
		super();
		this.setSpeed(speed);
		this.setDirection(direction);
	}
	
	public void setSpeed(double speed) {
		if(speed < 0.0 || speed > 100.0) throw new RuntimeException("Invalid speed: " + Double.toString(speed) + "\nValue must be between 0.0 and 100.0, inclusive.");
		else this.speed = speed;
	}

	public void setDirection(double direction) {
		if(direction < 0.0 || direction > 360.0) throw new RuntimeException("Invalid direction: " + Double.toString(speed) + "\nValue must be between 0.0 and 360.0, inclusive.");
		else this.direction = direction;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public double getDirection() {
		return this.direction;
	}


	@Override
	public String toString() {
		return "Velocity [speed=" + speed + ", direction=" + direction + "]";
	}
}
