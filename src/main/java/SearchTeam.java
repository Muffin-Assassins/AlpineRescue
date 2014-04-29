package main.java;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class SearchTeam extends SearchGridObject {
	private int radius;
	private long checkInTime;
	private String name;
	private Point lastKnownLocation;
	private ArrayList<Point> path;
	private TopographicMap topographicMap;
	private Velocity velocity;
	
	public SearchTeam() {
		super();
		this.lastKnownLocation = null;
	}
	
	public SearchTeam(int radius, Point startLocation, TopographicMap topographicMap, String imageUrl) {
		super(startLocation.x, startLocation.y, 40, 40, imageUrl);
		this.path = new ArrayList<Point>();
		
		JPanel box = new JPanel(new GridLayout(1,2));
		
		JTextField nameDialog = new JTextField("");
		
		nameDialog.setEditable(true);
		
		box.add(new JLabel("Name"));
		box.add(nameDialog);
		
		final JComponent[] inputs = new JComponent[] { box };
		Object[] options = {"Submit", "Cancel"};
		JOptionPane.showOptionDialog(null, inputs, "New Search Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		
		this.name = nameDialog.getText();
		this.topographicMap = topographicMap;
		this.radius = radius;
		this.checkInTime = System.currentTimeMillis();
		this.move(startLocation);
		velocity = new Velocity(0.0, 0.0);
	}
	
	public void setVelocity(Velocity v){
		velocity = v;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public String getName() {
		return this.name;
	}
	
	public GeoCoordinate getGeoCoordinate() {
		return this.topographicMap.getGeoCoordinate(this.lastKnownLocation);
	}
	
	public Color getColor() {
		return new Color(0, 255, 0, 127);
	}
	
	public Point getLocation() {
		return this.lastKnownLocation;
	}
	
	public Point getHypothesizedLocation() {
		double time = ((double)(System.currentTimeMillis() - this.checkInTime)) / 3600000.0;
		int dx = (int)((this.velocity.getSpeed() * Math.sin(this.velocity.getDirection() * Math.PI / 180.0)) * this.topographicMap.getPixelsPerMile() * time);
		int dy = (int)(this.velocity.getSpeed() * Math.cos(this.velocity.getDirection() * Math.PI / 180.0) * this.topographicMap.getPixelsPerMile() * time);
		System.out.println(dx + ", " + dy);
		return new Point(this.lastKnownLocation.x + dx, this.lastKnownLocation.y - dy);
	}
	
	public ArrayList<Line2D> getPath() {
		ArrayList<Line2D> linePath = new ArrayList<Line2D>();
		for(int i = 0; i < (this.path.size() - 1); i++) linePath.add(new Line2D.Double(this.path.get(i), this.path.get(i + 1)));
		return linePath;
	}
	
	public void move(Point p) {
		super.move(p);
		this.checkInTime = System.currentTimeMillis();
		this.path.add(p);
		this.lastKnownLocation = p;
	}
	
	@Override
	public void notifyUser(Point p) {
		JPanel box = new JPanel(new GridLayout(2,2));
		
		JTextField directionDialog = new JTextField(Double.toString(this.velocity.getDirection()));
		JTextField speedDialog = new JTextField(Double.toString(this.velocity.getSpeed()));
		
		speedDialog.setEditable(true);
		directionDialog.setEditable(true);
		
		box.add(new JLabel("Direction"));
		box.add(directionDialog);
		box.add(new JLabel("Speed"));
		box.add(speedDialog);
		
		final JComponent[] inputs = new JComponent[] { box };
		Object[] options = {"Submit", "Cancel"};
		JOptionPane.showOptionDialog(null, inputs, "Velocity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		this.setVelocity(new Velocity(Double.parseDouble(speedDialog.getText()), Double.parseDouble(directionDialog.getText())));
		this.move(p);
	}
	
	@Override
	public void notifyUser() {
		
		JPanel box = new JPanel(new GridLayout(3, 2));
		String[] latChoice = {"N", "S"};
		String[] lonChoice = {"W", "E"};
		
		JComboBox<String> lat = new JComboBox<String>(latChoice);
		JComboBox<String> lon = new JComboBox<String>(lonChoice);
		
		JPanel latitudeInput = new JPanel(new GridLayout(1, 4));
		JPanel longitudeInput = new JPanel(new GridLayout(1, 4));
		JTextField[] latitudeValue = new JTextField[3];
		JTextField[] longitudeValue = new JTextField[3];
		
		box.add(new JLabel("Geo-Coordinate"));
		box.add(new JLabel("                  °            \'            \""));
		
		latitudeInput.add(lat);
		latitudeInput.add(latitudeValue[0] = new JTextField(3));
		latitudeInput.add(latitudeValue[1] = new JTextField(2));
		latitudeInput.add(latitudeValue[2] = new JTextField(2));
		
		longitudeInput.add(lon);
		longitudeInput.add(longitudeValue[0] = new JTextField(3));
		longitudeInput.add(longitudeValue[1] = new JTextField(2));
		longitudeInput.add(longitudeValue[2] = new JTextField(2));
		
		box.add(new JLabel("Latitude:"));
		box.add(latitudeInput);
		box.add(new JLabel("Longitude:"));
		box.add(longitudeInput);
		
		final JComponent[] inputs = new JComponent[] { box };
		Object[] options = {"Submit", "Cancel"};
		int choice = JOptionPane.showOptionDialog(null, inputs, "Move Search Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
			    options,
			    options[1]);
		
		double latitude = GeoCoordinate.DMStoDecimal((String) lat.getSelectedItem(), Integer.parseInt(latitudeValue[0].getText()), Integer.parseInt(latitudeValue[1].getText()), Integer.parseInt(latitudeValue[2].getText()));
		double longitude = GeoCoordinate.DMStoDecimal((String) lon.getSelectedItem(), Integer.parseInt(longitudeValue[0].getText()), Integer.parseInt(longitudeValue[1].getText()), Integer.parseInt(longitudeValue[2].getText()));
		if(choice == JOptionPane.OK_OPTION) this.notifyUser(this.topographicMap.getPoint(new GeoCoordinate(latitude, longitude)));
	}
}
