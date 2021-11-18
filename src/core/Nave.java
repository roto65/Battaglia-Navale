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

	private BufferedImage spriteX = null;
	
	private boolean [] hit;
	
	private Point pos;
	
	private int length;
	private int facing;
	private boolean affondata;
	
	/* 
	0 = punta in alto e culo verso il basso, 
	1 = punta a dx e culo verso sx;
	2 = punta in basso e culo verso l'alto
	3 = punta a sx e culo verso dx
	*/

	public Nave(int x, int y, int length, int facing) {
		
		this.length = length;
		this.facing = facing;
		this.pos = new Point(x - 1, y - 1);
		
		sprites = new ArrayList <BufferedImage> ();
		sprites = loadSprites();
		
		hit = new boolean [length];
		for (int i = 0; i < length; i++) {
			hit[i] = false;
		}

		affondata = false;

	}

	private ArrayList <BufferedImage> loadSprites() {
		
		try {
			if (length == 1) {

				sprites.add(ImageIO.read(new File("drawable/navetta.png")));
				sprites.add(ImageIO.read(new File("drawable/navetta2.png")));

			} else {

				for (int i = 0; i < length; i++) {
					if(i == 0 || i == length - 1) {
						sprites.add(ImageIO.read(new File("drawable/sedere.png")));
					} else {
						sprites.add(ImageIO.read(new File("drawable/dritto.png")));
					}
				}

				for (int i = 0; i < length; i++) {
					if(i == 0 || i == length - 1) {
						sprites.add(ImageIO.read(new File("drawable/sedere2.png")));
					} else {
						sprites.add(ImageIO.read(new File("drawable/dritto2.png")));
					}
				}
			}

		} catch (IOException e) {
			System.out.print("Error opening image file: " + e.getMessage());
		}
		return sprites;
	}

	private void loadX() {

		try {
			spriteX = ImageIO.read(new File("drawable/x.png"));
		} catch (IOException e) {
			System.out.print("Error opening image file: " + e.getMessage());
		}
	}

	public void draw(Graphics g, ImageObserver observer) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform backup = g2d.getTransform();
		
		int j = 0;
		
		if (length == 1) {

			if (hit[0]) {
				g.drawImage(sprites.get(1), pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE, observer);
			} else {
				g.drawImage(sprites.get(0), pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE, observer);
			}

		} else {
			
			double rotation = findRotation();
			double locationX, locationY;
			int [] position = {0,0};
			
			for (int i = 0; i < length; i++) {
				
				g2d.setTransform(backup);
				
				position = findPosition(i);
				locationX = (pos.x + position[0]) * Board.TITLE_SIZE + (Board.TITLE_SIZE / 2);
				locationY = (pos.y + position[1]) * Board.TITLE_SIZE + (Board.TITLE_SIZE / 2);
				
				if (hit[i]) {
					j = i + length;
				} else {
					j = i;
				}
				
				if(i != length - 1) {
					
					g2d.rotate(rotation, locationX, locationY);
					g2d.drawImage(sprites.get(j), (pos.x + position[0]) * Board.TITLE_SIZE,
							(pos.y + position[1]) * Board.TITLE_SIZE, Board.TITLE_SIZE, 
							Board.TITLE_SIZE,observer);
					
				} else {
					
					rotation += Math.PI;
					g2d.rotate(rotation, locationX, locationY);
					
					g2d.drawImage(sprites.get(j), (pos.x + position[0]) * Board.TITLE_SIZE,
							(pos.y + position[1]) * Board.TITLE_SIZE, Board.TITLE_SIZE, 
							Board.TITLE_SIZE,observer);
				}
			}
		}
		
		g2d.setTransform(backup);
	}

	public void drawX(Graphics g, ImageObserver observer) {

		if (spriteX == null) {
			loadX();
		}

		int [] position = {0,0};

		for (int i = 0; i < length; i++){

			position = findPosition(i);

			if(hit[i]){
				g.drawImage(spriteX, (pos.x + position[0]) * Board.TITLE_SIZE,
						(pos.y + position[1]) * Board.TITLE_SIZE, Board.TITLE_SIZE,
						Board.TITLE_SIZE, observer );
			}
		}
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
	
	public void setHit(int blockNumber) {
		hit [blockNumber] = true;
				
		boolean naveCompleta = true;
		
		for (int i = 0; i < length; i++) {
			if(!hit[i]) {
				naveCompleta = false;
			}
		}
		
		affondata = naveCompleta;
		
		if (affondata) {
			Splutch.surroundNave(this);
		}
		
		affondata = naveCompleta;
		
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

	public boolean isAffondata() {
		return affondata;
	}
}
