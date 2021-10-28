package core;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screen.Board;

public class Nave {
	
	private ArrayList <BufferedImage> sprites;
	private BufferedImage image;
	
	private Point pos;
	private int length;
	private int facing;
	
	public Nave(int x, int y, int length, int facing) {
		
		loadSprites(length);
		
		pos = new Point(x, y);
	}

	private void loadSprites(int length) {
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
	}
	
	public void draw(Graphics g, ImageObserver observer) {
		
		if (length == 1) {
			g.drawImage(sprites.get(0), pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE,observer);
		} else {
			for (int i = 0; i < length; i++) {
				
				
			}
		}
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
