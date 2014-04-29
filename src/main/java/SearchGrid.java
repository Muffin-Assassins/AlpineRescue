package main.java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SearchGrid extends JPanel implements MouseListener, ActionListener {
	private ArrayList<SearchGridCell> cells;
	private ArrayList<SearchGridObject> objects;
	private Dimension dimension;
	private int cellPixelSize;
	private Clickable focus;
	private Image background;
	private double pixelsPerMile;
	private TopographicMap topographicMap;
	private Timer timer;

	public interface Clickable {
		public abstract void notifyUser(Point p); //notifyUser gui when clicked
		public abstract void notifyUser();
	}

	private void initialize() {
		this.addMouseListener(this);
		this.cells = new ArrayList<SearchGridCell>();
		this.objects = new ArrayList<SearchGridObject>();
		this.dimension = null;
		this.background = null;
		this.focus = null;
	}

	public SearchGrid() {
		super();
		this.initialize();
		this.dimension = new Dimension(1, 1);
		topographicMap = new TopographicMap();
		topographicMap.loadMapDialog();
		this.setBackgroundImage(topographicMap.getMapImage());
		this.timer = new Timer(1000, this);
		this.timer.start();
	}

	private void setGridDimensions(int rows, int columns) {
		if(rows > 0 && columns > 0) {
			this.dimension = new Dimension(columns, rows);
			this.cells.ensureCapacity(rows * columns);
			for(int r = 1; r <= this.dimension.height; r++) {
				for(int c = 1; c <= this.dimension.width; c++) {
					this.cells.add(new SearchGridCell(r, c));
				}
			}
		} else { //if impossible size, throw exception
			this.cells = null;
			this.dimension = null;
			throw new RuntimeException("Invalid 2D Grid Dimension: [Rows: " + rows + ", Columns: " + columns + "]");
		}
	}
	// Management

	public SearchGridCell getGridCell(int row, int column) {
		if(this.isValid(row, column)) return(this.cells.get(this.getIndex(row,  column)));
		else return null;
	}

	public SearchGridCell getGridCell(Point p) {
		return this.getGridCell((p.y / this.cellPixelSize) + 1, (p.x / this.cellPixelSize) + 1);
	}

	public SearchGridObject getGridObject(Point p) {
		for(SearchGridObject go : this.objects) {
			if(go.contains(p)) return go;
		}
		return null;
	}

	public Clickable getClicked(Point p) { //what was clicked?
		if(this.getGridObject(p) != null) return this.getGridObject(p);
		else return this.getGridCell(p);
	}

	public Dimension getGridDimension() {
		return this.dimension;
	}

	public ArrayList<SearchGridCell> getGridCells() {
		return this.cells;
	}

	public ArrayList<SearchGridObject> getGridObjects() {
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

	public void setBackgroundImage(BufferedImage image) {
		this.background = image;
		this.cells = new ArrayList<SearchGridCell>();
		if(this.background != null) {
			int height = this.background.getHeight(null);
			int width = this.background.getWidth(null);
			this.pixelsPerMile = topographicMap.getPixelsPerMile();

			this.setGridDimensions(width / (int)this.pixelsPerMile, height / (int)this.pixelsPerMile);
			if(width > height) this.cellPixelSize = (int) (height / this.dimension.getHeight());
			else this.cellPixelSize = (int) (width / this.dimension.getWidth());
			this.setLocation(0, 0);
			this.setBounds(0, 0, this.dimension.width * this.cellPixelSize, this.dimension.height * this.cellPixelSize);
		}
	}

	public void addGridObject(SearchGridObject go) {
		this.objects.add(go);
		go.notifyUser(go.getCenter());
		this.repaint();
	}
	//Inherited Methods and Implementations

	@Override
	public void paintComponent(Graphics g) { //draw grid and everything on it
		g.drawImage(this.background, 0, 0, this.dimension.width * this.cellPixelSize, this.dimension.height * this.cellPixelSize,  null);

		for(SearchGridCell gpc : this.getGridCells()) {
			g.setColor(gpc.getColor());
			//drawing grid lines for every cell at specified size
			if(gpc.getBorders()[0]) g.drawLine((gpc.getColumn() - 1) * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, gpc.getColumn() * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize);
			if(gpc.getBorders()[1]) g.drawLine((gpc.getColumn() - 1) * this.cellPixelSize, gpc.getRow() * this.cellPixelSize, gpc.getColumn() * this.cellPixelSize, gpc.getRow() * this.cellPixelSize);
			if(gpc.getBorders()[2]) g.drawLine((gpc.getColumn() - 1) * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, (gpc.getColumn() - 1) * this.cellPixelSize, gpc.getRow() * this.cellPixelSize);
			if(gpc.getBorders()[3]) g.drawLine(gpc.getColumn() * this.cellPixelSize, (gpc.getRow() - 1) * this.cellPixelSize, gpc.getColumn() * this.cellPixelSize, gpc.getRow() * this.cellPixelSize);
		}

		for(SearchGridObject go : this.getGridObjects()) {
			if(go instanceof SearchTeam) {
				SearchTeam st = ((SearchTeam) go);
				ArrayList<Line2D> path = ((SearchTeam) go).getPath();
				Graphics2D g2 = (Graphics2D) g;
				//set conversion of miles, etc. to pixels for gui
				int stroke = (int) (((double)st.getRadius() / 5280.0) * this.topographicMap.getPixelsPerMile());
				g2.setStroke(new BasicStroke(stroke));
				g.setColor(((SearchTeam) go).getColor());
				for(Line2D line : path)
					g2.draw(line);
				g2.setColor(new Color(200, 0, 0, 200));
				g2.draw(new Line2D.Double(go.getCenter(), ((SearchTeam) go).getHypothesizedLocation()));
				g.drawImage(go.getScaledImage(), go.getBounds().x, go.getBounds().y, null);
				g.setColor(Color.BLACK);
				g.drawString(st.getName(), st.getLocation().x - 20, st.getLocation().y - 25);
				g.drawString(st.getGeoCoordinate().getLatitude().getDMS(), st.getLocation().x - 20, st.getLocation().y + 30);
				g.drawString(st.getGeoCoordinate().getLongitude().getDMS(), st.getLocation().x - 20, st.getLocation().y + 40);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			if(this.getClicked(arg0.getPoint()) instanceof SearchTeam) { //if a searchTeam object is clicked, provide opportunity to delete
				int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirmation Dialog", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					this.getGridObjects().remove(this.getClicked(arg0.getPoint()));
					this.repaint();
				}
			} else if(this.getClicked(arg0.getPoint()) instanceof SearchGridCell) { //if right click, create team
				Object[] objects = {"Dog Team", "Hiker Team", "Helicopter Team"};
				String s = (String)JOptionPane.showInputDialog(this, "Please choose seach team type:", "New Search Team", JOptionPane.PLAIN_MESSAGE, null, objects, "Dog Team");
				if(s != null) {
					switch (s) {
					case "Dog Team":
						this.addGridObject(new DogTeam(arg0.getPoint(), this.topographicMap));
						break;
					case "Hiker Team":
						this.addGridObject(new HikerTeam(arg0.getPoint(), this.topographicMap));
						break;
					case "Helicopter Team":
						this.addGridObject(new HelicopterTeam(arg0.getPoint(), this.topographicMap));
						break;
					default:
						break;
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) { //drag and drop implementation
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			if(this.getClicked(arg0.getPoint()) instanceof SearchTeam) {
				this.focus = (SearchTeam) this.getClicked(arg0.getPoint());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			if(this.focus instanceof SearchTeam) {
				if(Math.abs(((SearchTeam) this.focus).getCenter().x - arg0.getPoint().x) > 10 || Math.abs(((SearchTeam) this.focus).getCenter().y - arg0.getPoint().y) > 10) {
					this.focus.notifyUser(arg0.getPoint());
				}
				else {
					System.out.println("Searchteam not moved yet");
					this.focus.notifyUser();
				}
				this.focus = null;
			}
			this.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.timer){
			this.repaint();
		}
	}
}
