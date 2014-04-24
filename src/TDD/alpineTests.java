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
	private SearchTeam hike0 = new HikerTeam(new Point(0, 0)); //should we create constants for the radius and put it in the constructor
	private SearchTeam dog0 = new DogTeam(new Point(100, 20)); // but not as a parameter?;
	private SearchTeam heli0 = new HelicopterTeam(new Point(500, 300));
	
	@Before
	public void resetVelocity() {
		hike0.setVelocity(new Velocity (20, Direction.SOUTH));
		heli0.setVelocity(new Velocity(35, Direction.WEST)); 
		dog0.setVelocity(new Velocity(15, Direction.SOUTHEAST));//Even though these are currently only used in one test,
		//keeping them in @Before in case more hypotheses tests come up that require it
		
		
	}
	
	@Test
	public void seeGUI() {
		testGUI = new ControlWindow(); //a lot of GUI tests consist of visual confirmation; this covers most of that
	}
	
	@Test
	public void hypotheses() { //normal hypothesis situation
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
	
	@Test
	public void diagonalHypothesis() {
		heli0.setVelocity(new Velocity(10, Direction.SOUTHWEST)); 
		for (int i = 0; i < 7; i++) {
			heli0.hypothesizeLocation();
		}
		Point expected = new Point(451, 349);
		Point actual = heli0.getHypotheses().get(heli0.getHypotheses().size() -1);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void offTheGrid() { //hypothesis situation that would take them off the grid,
		//but restrictions should handle it and keep SearchTeams on the map and stop hypotheses 
		//until next manual update of velocity
		hike0.setVelocity(new Velocity (20, Direction.NORTH));
		
		for(int i=0; i < 5; i++){
			hike0.hypothesizeLocation();
			dog0.hypothesizeLocation();
			heli0.hypothesizeLocation();
		}
		Point expected = new Point(100,20);
		Point actual = dog0.getHypotheses().get(dog0.getHypotheses().size() -1);
		Assert.assertEquals(expected, actual);
		actual = hike0.getHypotheses().get(hike0.getHypotheses().size() -1);
		expected = new Point(0,0);
		Assert.assertEquals(expected, actual);
		actual = heli0.getHypotheses().get(hike0.getHypotheses().size() -1);
		expected = new Point(325, 300);
		Assert.assertEquals(expected, actual);
	}

}
