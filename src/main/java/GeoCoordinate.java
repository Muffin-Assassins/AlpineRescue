package main.java;

import java.text.DecimalFormat;

public class GeoCoordinate { //implemented as 0 to 360 with 0 degrees as North instead of the standard 8 compass directions
	//to allow more flexibility in terms of tracking team movement
	private Latitude latitude;
	private Longitude longitude;
	
	public GeoCoordinate(double lat, double lon) {
		this.latitude = new GeoCoordinate.Latitude(lat);
		this.longitude = new GeoCoordinate.Longitude(lon);
	}
	
	public Latitude getLatitude() {
		return new Latitude(this.latitude.getDecimal());
	}
	
	public Longitude getLongitude() {
		return new Longitude(this.longitude.getDecimal());
	}
	
	private enum Direction {
		NORTH, SOUTH, EAST, WEST, NONE;
		
		public static Direction get(String dir) {
			switch(dir.toUpperCase().charAt(0)) {
				case 'N':	return Direction.NORTH;
				case 'S':	return Direction.SOUTH;
				case 'E':	return Direction.EAST;
				case 'W':	return Direction.WEST;
				default: return Direction.NONE;
			}
		}
		
		@Override
		public String toString() {
			switch(this) {
				case NORTH:	return("N");
				case SOUTH: return("S");
				case EAST: return("E");
				case WEST: return("W");
				default: return(" ");
			}
		}
	}
	
	public static double DMStoDecimal(String dir, int d, int m, int s) {
		GeoCoordinate.Direction direction = GeoCoordinate.Direction.get(dir);
		if(direction == GeoCoordinate.Direction.SOUTH || direction == GeoCoordinate.Direction.WEST) d *= -1;
		if(direction == GeoCoordinate.Direction.SOUTH || direction == GeoCoordinate.Direction.NORTH) if(d > 90 || d < -90) d = 0;
		else if(direction == GeoCoordinate.Direction.EAST || direction == GeoCoordinate.Direction.WEST) if(d > 180 || d < -180) d = 0;
		if(m > 59 || m < 0) m = 0;
		if(s > 59 || s < 0) s = 0;
		if(d < 0) return (double)d - (double)m / 60.0 - (double)m / 3600.0;
		else return (double)d + (double)m / 60.0 + (double)m / 3600.0;
	}
	
	private static int[] DecimaltoDMS(double deg) {
		int[] dms = new int[4];
		double d = Math.abs(deg);
		dms[0] = (int)d;
		double t = (d - (double)dms[0]) * 60.0;
		dms[1] = (int)t;
		dms[2] = (int)((t - (double)dms[1]) * 60.0);
		if(deg < 0) dms[3] = -1;
		else dms[3] = 1;
		return dms;
	}
	
	public class Latitude {
		private double latitude;
		public Latitude(double latitude) { this.setlatitude(latitude); }
		public Latitude(String direction, int d, int m, int s) { this.setlatitude(GeoCoordinate.DMStoDecimal(direction, d, m, s)); }
		public double getDecimal() { return this.latitude; }
		public String getDMS() { 
			int[] dms = GeoCoordinate.DecimaltoDMS(this.latitude);
			GeoCoordinate.Direction dir;
			if(this.latitude >= 0) dir = GeoCoordinate.Direction.NORTH;
			else dir = GeoCoordinate.Direction.NORTH;
			return dms[0] + "° "+ dms[1] + "\' " + dms[2] + "\" " + dir.toString();
		}
		public void setlatitude(double latitude) {
			if(latitude >= -90.0 && latitude <= 90.0) this.latitude = latitude;
			else this.latitude = 0.0;
		}
		
		@Override
		public String toString() { return new DecimalFormat("00.00000").format(this.latitude); }
	}
	
	public class Longitude {
		private double longitude;
		public Longitude(double longitude) { this.setLongitude(longitude); }
		public Longitude(String direction, int d, int m, int s) { this.setLongitude(GeoCoordinate.DMStoDecimal(direction, d, m, s)); }
		public double getDecimal() { return this.longitude; }
		public String getDMS() { 
			int[] dms = GeoCoordinate.DecimaltoDMS(this.longitude);
			GeoCoordinate.Direction dir;
			if(this.longitude >= 0) dir = GeoCoordinate.Direction.EAST;
			else dir = GeoCoordinate.Direction.WEST;
			return dms[0] + "° "+ dms[1] + "\' " + dms[2] + "\" " + dir.toString(); 
		}
		public void setLongitude(double longitude) {
			if(longitude >= -180.0 && longitude <= 180.0) this.longitude = longitude;
			else this.longitude = 0.0;
		}
		
		@Override
		public String toString() { return new DecimalFormat("000.00000").format(this.longitude); }
	}
	
	@Override
	public String toString() {
		return this.latitude.getDMS() + "\n" + this.longitude.getDMS();
	}
	
	@Override
	public GeoCoordinate clone() {
		return new GeoCoordinate(this.getLatitude().getDecimal(), this.getLongitude().getDecimal());
	}
}
