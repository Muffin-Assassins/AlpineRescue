package main.java;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public abstract class SearchTeam extends GridObject{
	private final int radius;
	private final Point startLocation;
	
	protected Velocity velocity;
	protected Point lastKnownPosition;
	protected ArrayList<Point> hypothesizedLocations;
	
	public SearchTeam(int radius, Point startLocation, String imageUrl) {
		super(startLocation.x,startLocation.y, 40,40, imageUrl);
		this.radius = radius;
		this.startLocation = startLocation;
		this.lastKnownPosition = startLocation;
		this.hypothesizedLocations = new ArrayList<Point>(); //drawing from hypothesizedLocations
		//clear hypoLoc during every manual update because past hypotheses no longer matter
		velocity= new Velocity(0.0, Direction.NORTH);
		this.hypothesizedLocations.add(lastKnownPosition);
	}
	
	public void setVelocity(Velocity v){
		velocity=v;
	}

	public ArrayList<Point> getHypotheses() {
		return hypothesizedLocations;
	}

	public void hypothesizeLocation(){
		switch(velocity.getDirection()){
		case NORTH:	
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()-1).x, hypothesizedLocations.get(hypothesizedLocations.size()-1).y - ((int)this.velocity.getSpeed())));
			break;
		case SOUTH: 
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()-1).x, hypothesizedLocations.get(hypothesizedLocations.size()-1).y + ((int)this.velocity.getSpeed())));
			break;
		case EAST: 
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()-1).x + ((int)this.velocity.getSpeed()), hypothesizedLocations.get(hypothesizedLocations.size()-1).y));
			break;
		case WEST: 
			this.hypothesizedLocations.add(new Point(hypothesizedLocations.get(hypothesizedLocations.size()-1).x - ((int)this.velocity.getSpeed()), hypothesizedLocations.get(hypothesizedLocations.size() -1).y));
			break;
		case NORTHEAST:
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).x + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).y - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		case NORTHWEST: 
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).x - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).y - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		case SOUTHEAST: 
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).x + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).y + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		case SOUTHWEST:
			this.hypothesizedLocations.add(new Point((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).x - ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)), ((int)((hypothesizedLocations.get(hypothesizedLocations.size()-1).y + ((int)this.velocity.getSpeed() ))/ Math.sqrt(2)))));
			break;
		default:
				break;
		}
	}
	
	public void manualUpdate(Point location){
		this.lastKnownPosition=location;
		super.manualUpdate(this.lastKnownPosition);
	}
	
	@Override
	public void notifyUser() {
		JComboBox directionDialog = new JComboBox(Direction.values());
		directionDialog.setSelectedIndex(0);
		JTextField speedDialog = new JTextField(Double.toString(velocity.getSpeed()));
		speedDialog.setEditable(true);
		JTextField locationxDialog = new JTextField(Integer.toString(this.lastKnownPosition.x));
		locationxDialog.setEditable(true);
		JTextField locationyDialog = new JTextField(Integer.toString(this.lastKnownPosition.y));
		locationyDialog.setEditable(true);
		JPanel box = new JPanel(new GridLayout(4,2));
		box.add(new JLabel("Direction"));
		box.add(directionDialog);
		box.add(new JLabel("Speed"));
		box.add(speedDialog);
		box.add(new JLabel("Update X Location"));
		box.add(locationxDialog);
		box.add(new JLabel("Update Y Location"));
		box.add(locationyDialog);
		final JComponent[] inputs = new JComponent[] {
				box
		};
		Object[] options = {"Submit",
        "Cancel"};
		int choice=JOptionPane.showOptionDialog(null, inputs, "New Velocity", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,
			    options,
			    options[1]);
		double newSpeed=Double.parseDouble(speedDialog.getText());
		this.manualUpdate(new Point(Integer.parseInt(locationxDialog.getText()),Integer.parseInt(locationyDialog.getText())));
		if(newSpeed >100){
			newSpeed = 100;
			JOptionPane.showMessageDialog(null,
			    "The max speed is 100.0. Your value has been set to 100.0.",
			    "Speed too large.",
			    JOptionPane.PLAIN_MESSAGE);
		}
		else if(newSpeed <0){
			newSpeed =0;
			JOptionPane.showMessageDialog(null,
			    "The minimum speed is 0.0. Your value has been set to 0.0.",
			    "Speed too large.",
			    JOptionPane.PLAIN_MESSAGE);
		}
		this.setVelocity(new Velocity(newSpeed,(Direction)directionDialog.getSelectedItem()));
		System.out.println(this.lastKnownPosition);
	}
}
