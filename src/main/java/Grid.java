package main.java;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Grid extends JPanel implements MouseListener, Runnable {
	private ArrayList<GridCell> cells;
	private ArrayList<GridObject> objects;
	private Dimension dimension;
	private int cellPixelSize;

	private Image background;

	public interface Clickable {
		public void notifyUser();
	}

	public Grid() {
		super();
		this.addMouseListener(this);
		this.cells = new ArrayList<GridCell>();
		this.objects = new ArrayList<GridObject>();
		this.dimension = null;
		this.background = null;
	}

	public Grid(int cellPixelSize, String imageURL) {
		this();
		this.cellPixelSize = cellPixelSize;
		this.setBackgroundImage(imageURL);
		this.setGridDimensions(this.background.getHeight(null) / cellPixelSize, this.background.getWidth(null) / cellPixelSize);
		this.setLocation(0, 0);
		this.setBounds(0, 0, this.dimension.width * this.cellPixelSize, this.dimension.height * this.cellPixelSize);
	}

	public Grid(int rows, int columns, int cellPixelSize) {
		this();
		this.setGridDimensions(rows, columns);
		this.cellPixelSize = cellPixelSize;
		this.setLocation(0, 0);
		this.setBounds(0, 0, this.dimension.width * this.cellPixelSize, this.dimension.height * this.cellPixelSize);
	}

	public Grid(int rows, int columns, String imageURL) {
		this();
		this.setGridDimensions(rows, columns);
		this.setBackgroundImage(imageURL);
		this.setLocation(0, 0);
		this.setBounds(0, 0, this.dimension.width * this.cellPixelSize, this.dimension.height * this.cellPixelSize);
	}

	private void setGridDimensions(int rows, int columns) {
		if(rows > 0 && columns > 0) {
			this.dimension = new Dimension(columns, rows);
			this.cells.ensureCapacity(rows * columns);
			for(int r = 1; r <= this.dimension.height; r++) {
				for(int c = 1; c <= this.dimension.width; c++) {
					this.cells.add(new GridCell(r, c));
				}
			}
		} else {
			this.cells = null;
			this.dimension = null;
			throw new RuntimeException("Invalid 2D Grid Dimension: [Rows: " + rows + ", Columns: " + columns + "]");
		}
	}
	// Management

	public GridCell getGridCell(int row, int column) {
		if(this.isValid(row, column)) return(this.cells.get(this.getIndex(row,  column)));
		else return null;
	}

	public GridCell getGridCell(Point p) {
		return this.getGridCell((p.y / this.cellPixelSize) + 1, (p.x / this.cellPixelSize) + 1);
	}

	public GridObject getGridObject(Point p) {
		for(GridObject go : this.objects) {
			if(go.contains(p)) return go;
		}
		return null;
	}

	public Clickable getClicked(Point p) {
		if(this.getGridObject(p) != null) return this.getGridObject(p);
		else return this.getGridCell(p);
	}

	public Dimension getGridDimension() {
		return this.dimension;
	}

	public ArrayList<GridCell> getGridCells() {
		return this.cells;
	}

	public ArrayList<GridObject> getGridObjects() {
		return this.objects;
	}

	public boolean isValid(int row, int column) {
		return (row > 0) && (column > 0) && (column <= this.dimension.width) && (row <= this.dimension.height);
	}

	public Integer getIndex(int row, int column) {
		if(isValid(row, column)) return ((row - 1) * this.dimension.width + (column - 1));
		else return null;
	}

	public Dimension getGridSize() {
		return this.dimension;
	}
	// Graphics Customization and Specifications

	public void setBackgroundImage(String imageURL) {
		this.background = new ImageIcon(this.getClass().getResource(imageURL)).getImage();

		if(this.background != null && this.dimension != null) {
			int height = this.background.getHeight(null);
			int width = this.background.getWidth(null);

			if(width > height) this.cellPixelSize = (int) (height / this.dimension.getHeight());
			else this.cellPixelSize = (int) (width / this.dimension.getWidth());
		}
	}

	public void addGridObject(GridObject go) {
		this.objects.add(go);
		go.notifyUser();
		this.repaint();
	}

	//Inherited Methods and Implementations

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(this.background, 0, 0, this.dimension.width * this.cellPixelSize, this.dimension.height * this.cellPixelSize,  null);

		for(GridCell gpc : this.getGridCells()) {
			g.setColor(gpc.getColor());
			g.fillRect((gpc.getColumn() - 1) * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, this.cellPixelSize, this.cellPixelSize);
			if(gpc.getBorders()[0]) g.drawLine((gpc.getColumn() - 1) * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, gpc.getColumn() * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize);
			if(gpc.getBorders()[1]) g.drawLine((gpc.getColumn() - 1) * this.cellPixelSize, gpc.getRow() * this.cellPixelSize, gpc.getColumn() * this.cellPixelSize, gpc.getRow() * this.cellPixelSize);
			if(gpc.getBorders()[2]) g.drawLine((gpc.getColumn() - 1) * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, (gpc.getColumn() - 1) * this.cellPixelSize, gpc.getRow() * this.cellPixelSize);
			if(gpc.getBorders()[3]) g.drawLine(gpc.getColumn() * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, gpc.getColumn() * this.cellPixelSize, gpc.getRow() * this.cellPixelSize);
		}

		for(GridObject go : this.getGridObjects()) {
			g.drawImage(go.getScaledImage(), go.getBounds().x, go.getBounds().y, null);
			g.drawRect(go.getBounds().x,go.getBounds().y, go.getBounds().width, go.getBounds().height);   //Draws a border around the object.
			if(go instanceof SearchTeam) {
				ArrayList<Line2D> path = ((SearchTeam) go).getPath();
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				g2.setColor(((SearchTeam) go).getColor());
				for(Line2D line : path)
					g2.draw(line);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			if(this.getClicked(arg0.getPoint()) instanceof SearchTeam) {
				int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					this.getGridObjects().remove(this.getClicked(arg0.getPoint()));
					this.repaint();
				}
			} else if(this.getClicked(arg0.getPoint()) instanceof GridCell) {
				Object[] objects = {"Dog Team", "Hiker Team", "Helicopter Team"};
				String s = (String)JOptionPane.showInputDialog(this, "Please choose seach team type:", "New Search Team", JOptionPane.PLAIN_MESSAGE, null, objects, "Dog Team");
				switch (s) {
				case "Dog Team":
					this.addGridObject(new DogTeam(arg0.getPoint()));
					break;
				case "Hiker Team":
					this.addGridObject(new HikerTeam(arg0.getPoint()));
					break;
				case "Helicopter Team":
					this.addGridObject(new HelicopterTeam(arg0.getPoint()));
					break;
				default:
					break;
				}
			}
		}
		else if(SwingUtilities.isLeftMouseButton(arg0)) {
			this.getClicked(arg0.getPoint()).notifyUser();
			this.repaint();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
