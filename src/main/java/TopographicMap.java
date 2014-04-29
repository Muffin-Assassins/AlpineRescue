package main.java;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TopographicMap {
	private GeoCoordinate geoCoordinate;
	private int metersPerPixel;
	private String size;
	private String info = null;
	BufferedImage topographicMap;
	
	public interface Mappable {
		public abstract Point getLocation(GeoCoordinate gc, TopographicMap map);
		public abstract GeoCoordinate getLocation(Point gc, TopographicMap map);
	}
	
	public TopographicMap() {
		super();
		this.geoCoordinate = new GeoCoordinate(39.75751, -105.22240);
		this.metersPerPixel = 16;
		this.size = "l";
	}
	
	public TopographicMap(GeoCoordinate gc, int scale, String size) {
		super();
		this.loadMapImage(gc, scale, size);
	}
	
	public BufferedImage getMapImage() {
		return this.topographicMap;
	}
	
	public void loadMapImage() {
		this.loadMapImage(this.geoCoordinate, this.metersPerPixel, this.size);
	}
	
	public void loadMapImage(GeoCoordinate gc) {
		this.geoCoordinate = gc;
		this.loadMapImage(this.geoCoordinate, this.metersPerPixel, this.size);
	}
	
	public void loadMapImage(GeoCoordinate gc, int scale, String size) {
		this.geoCoordinate = gc;
		
		String line = "";
		if(scale != 4 && scale != 8 && scale != 16 && scale != 32 && scale != 64 && scale != 128) scale = 16;
		this.metersPerPixel = scale;
		if(!size.equals("s") && !size.equals("m") && !size.equals("l") && !size.equals("xl")) size = "m";
		this.size = size;
		try {
			System.out.println(TopographicMap.getURL(gc, size, scale));
			URL image = TopographicMap.getURL(gc, size, scale);
			URLConnection imageConnection = image.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(imageConnection.getInputStream()));

			while ((line = br.readLine()) != null) {
				if(line.contains("<title>") && this.info == null) {
					this.info = line.substring(line.indexOf("<title>") + 7, line.indexOf("-"));
				}
	            if(line.contains("/tmp/")) {
	            	line = line.substring(line.indexOf("/tmp/"), line.indexOf(".png"));
	            	System.out.println("http://www.topoquest.com" + line + ".png");
	            	topographicMap = ImageIO.read(new URL("http://www.topoquest.com" + line + ".png"));
	            	br.close();
	            	break;
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(this.info == null) System.exit(0);
	}
	
	public void shiftByPixel(int dx, int dy) {
		this.geoCoordinate = this.getGeoCoordinate(new Point(this.getCenterPoint().y + dx, this.getCenterPoint().y + dy));
		this.loadMapImage();
	}
	
	public void loadMapDialog() {
		String[] sizeChoice = {"Small", "Medium", "Large", "Extra Large"};
		String[] scaleChoice = {"4", "8", "16", "32", "64", "128"};
		String[] latChoice = {"N", "S"};
		String[] lonChoice = {"W", "E"};
		
		JComboBox<String> size = new JComboBox<String>(sizeChoice);
		JComboBox<String> scale = new JComboBox<String>(scaleChoice);
		JComboBox<String> lat = new JComboBox<String>(latChoice);
		JComboBox<String> lon = new JComboBox<String>(lonChoice);
		
		lat.setSelectedIndex(0);
		lon.setSelectedIndex(0);
		size.setSelectedIndex(2);
		scale.setSelectedIndex(2);
		
		JPanel box = new JPanel(new GridLayout(5, 2));
		JPanel latitudeInput = new JPanel(new GridLayout(1, 4));
		JPanel longitudeInput = new JPanel(new GridLayout(1, 4));
		JTextField[] latitudeValue = new JTextField[3];
		JTextField[] longitudeValue = new JTextField[3];
		
		box.add(new JLabel("Geo-Coordinate"));
		box.add(new JLabel("                  °            \'            \""));
		
		latitudeInput.add(lat);
		latitudeInput.add(latitudeValue[0] = new JTextField("038"));
		latitudeInput.add(latitudeValue[1] = new JTextField("00"));
		latitudeInput.add(latitudeValue[2] = new JTextField("49"));
		
		longitudeInput.add(lon);
		longitudeInput.add(longitudeValue[0] = new JTextField("104"));
		longitudeInput.add(longitudeValue[1] = new JTextField("58"));
		longitudeInput.add(longitudeValue[2] = new JTextField("59"));
		
		box.add(new JLabel("Latitude:"));
		box.add(latitudeInput);
		box.add(new JLabel("Longitude:"));
		box.add(longitudeInput);
		box.add(new JLabel("Image Size"));
		box.add(new JLabel("Scale (Meters Per Pixel)"));
		box.add(size);
		box.add(scale);
		
		final JComponent[] inputs = new JComponent[] { box };
		Object[] options = {"Submit",
        "Cancel"};
		JOptionPane.showOptionDialog(null, inputs, "Load Topographic Image", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
			    options,
			    options[1]);
		
		double latitude = GeoCoordinate.DMStoDecimal((String) lat.getSelectedItem(), Integer.parseInt(latitudeValue[0].getText()), Integer.parseInt(latitudeValue[1].getText()), Integer.parseInt(latitudeValue[2].getText()));
		double longitude = GeoCoordinate.DMStoDecimal((String) lon.getSelectedItem(), Integer.parseInt(longitudeValue[0].getText()), Integer.parseInt(longitudeValue[1].getText()), Integer.parseInt(longitudeValue[2].getText()));
		this.geoCoordinate = new GeoCoordinate(latitude, longitude);
		this.metersPerPixel = Integer.parseInt((String) scale.getSelectedItem());
		switch(size.getSelectedIndex()) {
		case 0:
			this.size = "s";
			break;
		case 1:
			this.size = "m";
			break;
		case 2:
			this.size = "l";
			break;
		case 3:
			this.size = "xl";
			break;
		}
		this.loadMapImage();
		if(JOptionPane.showConfirmDialog(null, "Load the topographic map for this Geo-Coordinate?\n" + this.geoCoordinate.toString() + "\n" + this.info, null, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION);
		else this.topographicMap = null;
	}
	
	private static URL getURL(GeoCoordinate gc, String size, int scale) {
		URL url = null;
		try {
			url = new URL("http://www.topoquest.com/map.php?lat=" + gc.getLatitude().toString() + "&lon=" + gc.getLongitude().toString() + "&datum=nad27&zoom=" + scale + "&map=auto&coord=d&mode=zoomin&size=" + size);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public double getPixelsPerMile() {
		return (0.305 * 5280) / (double)this.metersPerPixel;
	}
	
	public Point getCenterPoint() {
		return new Point(this.topographicMap.getWidth() / 2, this.topographicMap.getHeight() / 2);
	}
	
	public GeoCoordinate getCenterCoordinate() {
		return this.geoCoordinate;
	}
	
	public GeoCoordinate getGeoCoordinate(Point p) {
		double radius = 6378137.00;
		double dx = (double)(((double)this.topographicMap.getWidth() / 2.0) - ((double)p.x)) * ((double)this.metersPerPixel);
		double dy = (double)(((double)p.y) - ((double)this.topographicMap.getHeight()) / 2.0) * ((double)this.metersPerPixel);
		double dLat = dy / radius;
		double dLon = dx / (radius * Math.cos(Math.PI * this.geoCoordinate.getLatitude().getDecimal() / 180.0));
		
		return new GeoCoordinate(this.geoCoordinate.getLatitude().getDecimal() + dLat * 180.0 / Math.PI, this.geoCoordinate.getLongitude().getDecimal() + dLon * 180.0 / Math.PI);
	}
	
	
	public Point getPoint(GeoCoordinate gc) {
		double radius = 6378137.00;
		double dLat = gc.getLatitude().getDecimal() - this.geoCoordinate.getLatitude().getDecimal();
		double dLon = gc.getLongitude().getDecimal() - this.geoCoordinate.getLongitude().getDecimal();
		double dx = (Math.PI / 180.0) * (radius * Math.cos(Math.PI * this.geoCoordinate.getLatitude().getDecimal() / 180.0)) * dLat / (double)this.metersPerPixel;
		double dy = (Math.PI / 180.0) * (radius / (double)this.metersPerPixel) * dLon;
		return new Point((int)(this.getCenterPoint().y - dy), (int)(this.getCenterPoint().x + dx));
	}
	
	public int getScale() {
		return this.metersPerPixel;
	}

	public String getSize() {
		return this.size;
	}
	
	public String getInfo() {
		return this.info;
	}
}
