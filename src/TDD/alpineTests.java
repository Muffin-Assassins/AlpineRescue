package TDD;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import main.java.ControlWindow;
import main.java.DogTeam;
import main.java.HelicopterTeam;
import main.java.HikerTeam;

import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

public class alpineTests {
	private ControlWindow testGUI;
	private HikerTeam hike0 = new HikerTeam(Color.blue, 1, new Point(0, 0)); //should we create constants for the radius and put it in the constructor
	private DogTeam dog0 = new DogTeam(Color.BLACK, 3, new Point(100, 20)); // but not as a parameter?;
	private HelicopterTeam heli0 = new HelicopterTeam(Color.gray, 5,new Point(500, 300));;
	
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
		ArrayList<Point> actual = dog0.getHypotheses();
		//ArrayList<Point> expected = [0];
		System.out.println("hike: " + hike0.getHypotheses() + "dog: " + dog0.getHypotheses());
		Assert.assertEquals(null, actual);
	}
	
	//create control panel gui
}
