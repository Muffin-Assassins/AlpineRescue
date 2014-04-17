package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class GridPanel extends JPanel {
	private Dimension dimension;
	private int cellSize;
	
	public interface Drawable {
		public Point getGridLocation();
		public void draw(Graphics graphics);
	}
	
	public GridPanel() {
		this.dimension = new Dimension(0, 0);
		this.cellSize = 1;
	}
	
	public GridPanel(int rows, int columns, int size) {
		this();
		this.setGridSize(rows, columns);
		this.setCellSize(size);
	}
	
	public void setGridSize(int rows, int columns) {
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		if(rows > 0 && columns > 0 &&  columns * this.dimension.width < screenSize.width && rows * this.dimension.height < screenSize.height)
			this.dimension = new Dimension(columns, rows);
		else
			throw new RuntimeException("Invalid Grid Dimension: [Rows: " + Integer.toString(rows) + ", Columns: " + Integer.toString(columns)+ "]");
	}
	
	public Dimension getGridSize() {
		return this.dimension;
	}
	
	public void setCellSize(int size) {
		Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		if(size > 0 && size * this.dimension.width < screenSize.width && size * this.dimension.height < screenSize.height)
			this.cellSize = size;
		else
			throw new RuntimeException("Invalid Grid Cell Size: [Size: " + Integer.toString(size));
	}
	
	public int getCellSize() {
		return this.cellSize;
	}
	
	public Dimension getGridPixelDimension() {
		return new Dimension(this.getGridPixelWidth(), this.getGridPixelHeight());
	}
	public int getGridPixelWidth() {
		return this.cellSize * this.dimension.width;
	}
	
	public int getGridPixelHeight() {
		return this.cellSize * this.dimension.height;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		
		for(int row = 0; row < (this.dimension.height + 1); row++)
			g.drawLine(0, row * cellSize, this.dimension.width * cellSize, row * cellSize);
		for(int column = 0; column < (this.dimension.width + 1); column++)
			g.drawLine(column * cellSize, 0,  column * cellSize, this.dimension.height * cellSize);
	}
}
