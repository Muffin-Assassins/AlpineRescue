package main.java;

public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST,
	NORTHEAST,
	NORTHWEST,
	SOUTHEAST,
	SOUTHWEST;
	
	@Override
	public String toString() {
		switch(this) {
			case NORTH:	return("N");
			case SOUTH: return("S");
			case EAST: return("E");
			case WEST: return("W");
			case NORTHEAST: return("NE");
			case NORTHWEST: return("NW");
			case SOUTHEAST: return("SE");
			case SOUTHWEST: return("SW");
			default: return(" ");
		}
	}
}
