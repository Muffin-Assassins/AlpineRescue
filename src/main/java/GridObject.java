package main.java;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class GridObject implements Grid.Clickable{
	private Point center;
	private int width;
	private int height;
	private BufferedImage bufferedImage;

	public GridObject(int x, int y, int width, int height, String imageURL) {
		Image image = new ImageIcon(this.getClass().getResource(imageURL)).getImage();
	    this.bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    bufferedImage.createGraphics().drawImage(image, 0, 0, null);
		this.center = new Point(x, y);
		this.width = width;
		this.height = height;
	}

	public boolean contains(Point p) {
		return (p.x > (this.center.x - this.width / 2)) && (p.x < (this.center.x + this.width / 2)) && (p.y > (this.center.y - this.height / 2)) && (p.y > (this.center.y - this.height / 2)); 
	}

	public Rectangle getBounds() {
		return new Rectangle(this.center.x - this.width / 2, this.center.y - this.height / 2, this.width, this.height);
	}

	public Image getImage() {
		return this.bufferedImage;
	}

	public Image getScaledImage() {
		return this.bufferedImage.getScaledInstance(this.width, this.height, Image.SCALE_FAST);
	}

	@Override
	public void notifyUser() {
		System.out.println("You clicked on an object");
	}
}
