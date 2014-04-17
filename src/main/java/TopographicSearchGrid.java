package main.java;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class TopographicSearchGrid extends GridPanel {
	private int cellsPerUnitDistance;
	private int pixelsPerUnitDistance;
	private Image background;
	
	public TopographicSearchGrid(int cellsPerUnitDistance, int pixelsPerUnitDistance, String imageURL) {
		super();
		this.background = new ImageIcon(this.getClass().getResource(imageURL)).getImage();
		this.setPixelsPerUnitDistance(pixelsPerUnitDistance);
		this.setCellsPerUnitDistance(cellsPerUnitDistance);
		this.cropImageToGrid();
		this.setPreferredSize(this.getGridPixelDimension());
	}
	
	private void cropImageToGrid() {
		this.setGridSize((int)(((1.0 * background.getHeight(null)) / (1.0 * pixelsPerUnitDistance)) * (1.0 * cellsPerUnitDistance)), (int)(((1.0 * background.getWidth(null)) / (1.0 * pixelsPerUnitDistance)) * (1.0 * cellsPerUnitDistance)));
		this.setCellSize(background.getHeight(null) / this.getGridSize().height);
	}
	
	public int getCellsPerUnitDistance() {
		return cellsPerUnitDistance;
	}

	public void setCellsPerUnitDistance(int cellsPerUnitDistance) {
		this.cellsPerUnitDistance = cellsPerUnitDistance;
	}

	public int getPixelsPerUnitDistance() {
		return pixelsPerUnitDistance;
	}

	public void setPixelsPerUnitDistance(int pixelsPerUnitDistance) {
		this.pixelsPerUnitDistance = pixelsPerUnitDistance;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.background, 0, 0, this.getGridPixelWidth(), this.getGridPixelHeight(),  null);
		super.paintComponent(g);
	}
}
