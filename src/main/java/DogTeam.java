package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class DogTeam extends SearchTeam {
	
	public DogTeam(Point startLocation) {
		super(7,startLocation,"../resources/DogIcon.png");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void hypothesizeLocation(){
		//DO NOTHING
	}

}
