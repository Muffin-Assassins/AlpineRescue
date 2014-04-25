package main.java;

import java.awt.Color;

public class GridCell implements Grid.Clickable {
	private Color color;
	private int row;
	private int column;
	private boolean[] borders;
	
	public GridCell(int row, int column) {
		this.color = new Color(0, 0, 0, 0);		//Color(red,blue,green,opacity)
		this.borders = new boolean[4];
		this.borders[0]=true;					//Create borders on cells
		this.borders[1]=true;					//Create borders on cells
		this.borders[2]=true;					//Create borders on cells
		this.borders[3]=true;					//Create borders on cells
		if(row > 0) this.row = row;
		if(column > 0) this.column = column;
	}
	
	
	public void setColor(int r, int g, int b, int o) {
		this.color = new Color(r, g, b, o);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setBounds(boolean top, boolean bottom, boolean left, boolean right) {
		this.borders = new boolean[]{top, bottom, left, right};
	}
	
	public boolean[] getBorders() {
		return this.borders;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}

	@Override
	public void notifyUser() {
		System.out.println(row + ", " + column);
	}
}
