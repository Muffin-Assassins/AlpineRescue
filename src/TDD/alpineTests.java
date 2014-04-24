package TDD;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import main.java.ControlWindow;
import main.java.Direction;
import main.java.DogTeam;
import main.java.HelicopterTeam;
import main.java.HikerTeam;
import main.java.SearchTeam;
import main.java.Velocity;

import org.junit.Assert;
import org.junit.Before;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

public class alpineTests {
	private ControlWindow testGUI;
	private SearchTeam hike0 = new HikerTeam(Color.blue, 1, new Point(0, 0)); //should we create constants for the radius and put it in the constructor
	private SearchTeam dog0 = new DogTeam(Color.BLACK, 3, new Point(100, 20)); // but not as a parameter?;
	private SearchTeam heli0 = new HelicopterTeam(Color.gray, 5,new Point(500, 300));
	
	@Before
	public void resetVelocity() {
		hike0.setVelocity(new Velocity (20, Direction.SOUTH));
		heli0.setVelocity(new Velocity(35, Direction.WEST));
		
	}
	
	@Test
	public void seeGUI() {
		testGUI = new ControlWindow(); //how do we make this stay open to inspect it?
	}
	
	@Test
	public void hypotheses() {
		for(int i=0; i < 5; i++){
			hike0.hypothesizeLocation();
			dog0.hypothesizeLocation();
			heli0.hypothesizeLocation();
		}
		Point expected = new Point(100,20);
		Point actual = dog0.getHypotheses().get(dog0.getHypotheses().size() -1);
		Assert.assertEquals(expected, actual);
		actual = hike0.getHypotheses().get(hike0.getHypotheses().size() -1);
		expected = new Point(0,100);
		Assert.assertEquals(expected, actual);
		actual = heli0.getHypotheses().get(hike0.getHypotheses().size() -1);
		expected = new Point(325, 300);
		Assert.assertEquals(expected, actual);
	}
	
	//create control panel gui
}
