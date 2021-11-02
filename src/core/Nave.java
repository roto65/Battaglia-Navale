package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screen.Board;

public class Nave {
	
	private ArrayList <BufferedImage> sprites;
	
	private Point pos;
	private int length;
	private int facing;
	
	/* 
	0 = punta in alto e culo verso il basso, 
	1 = punta a dx e culo verso sx;
	2 = punta in basso e culo verso l'alto
	3 = punta a sx e culo verso dx
	*/
	public Nave(int x, int y, int length, int facing) {
		
		this.length = length;
		this.facing = facing;
		this.pos = new Point(x, y);
		
		sprites = new ArrayList <BufferedImage> ();
		sprites = loadSprites();
		
	}

	private ArrayList <BufferedImage> loadSprites() {
		
		try {
			if (length == 1) {
				sprites.add(ImageIO.read(new File("drawable/naveta.png")));
			} else {
				for (int i = 0; i < length; i++) {
					if(i == 0 || i == length - 1) {
						sprites.add(ImageIO.read(new File("drawable/sedere.png")));
					} else {
						sprites.add(ImageIO.read(new File("drawable/dritto.png")));
					}
				}
			}
		} catch (IOException e) {
			System.out.print("Error opening image file: " + e.getMessage());
		}
		return sprites;
	}
	
	public void draw(Graphics g, ImageObserver observer) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform backup = g2d.getTransform();
		
		if (length == 1) {
			
			g.drawImage(sprites.get(0), pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE,observer);
			
		} else {
			
			double rotation = findRotation();
			double locationX, locationY;
			int [] position = {0,0};
			
			for (int i = 0; i < length; i++) {
				
				position = findPosition(i);
				locationX = (pos.x + position[0]) * Board.TITLE_SIZE + (Board.TITLE_SIZE / 2);
				locationY = (pos.y + position[1]) * Board.TITLE_SIZE + (Board.TITLE_SIZE / 2);
				g2d.rotate(rotation, locationX, locationY);
				
				if(i != length - 1) {			
					
					g2d.drawImage(sprites.get(i), (pos.x + position[0]) * Board.TITLE_SIZE,
							(pos.y + position[1]) * Board.TITLE_SIZE, Board.TITLE_SIZE, 
							Board.TITLE_SIZE,observer);
					
				} else {
					
					rotation += Math.PI;
					g2d.rotate(rotation, locationX, locationY);
					
					g2d.drawImage(sprites.get(i), (pos.x + position[0]) * Board.TITLE_SIZE,
							(pos.y + position[1]) * Board.TITLE_SIZE, Board.TITLE_SIZE, 
							Board.TITLE_SIZE,observer);
				}
			}
		}
		
		g2d.setTransform(backup);
	}
	
	private double findRotation() {
		double rotation = 0;
		switch (facing) {
		case 0:
			rotation = Math.toRadians(0);
			break;
		case 1:
			rotation = Math.toRadians(90);
			break;
		case 2:
			rotation = Math.toRadians(180);
			break;
		case 3:
			rotation = Math.toRadians(270);
			break;
		}
		return rotation;
	}
	
	private int [] findPosition (int i) {
		
		int [] position = {0,0};
		
		switch (facing) {
		case 0:
			position[1] = i;
			break;
		case 1:
			position[0] = -i;
			break;
		case 2:
			position[1] = -i;
			break;
		case 3:
			position[0] = i;
			break;
		}
		
		return position;
		
	}
	
	public Point getPos() {
		return pos;
	}

	public int getLength() {
		return length;
	}

	public int getFacing() {
		return facing;
	}
	
}
