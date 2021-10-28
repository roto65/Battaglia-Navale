package core;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import screen.Board;

public class Player {

	private BufferedImage image;
	
	private Point pos;

	public Player() {
		 
		loadImage();
		
		pos = new Point(0, 0);
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File("drawable/pg.png"));
		} catch (IOException e) {
			System.out.print("Error opening image file: " + e.getMessage());
		}
	}
	
	public void draw(Graphics g, ImageObserver observer) {
		
		g.drawImage(image, pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, observer);
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			pos.translate(0, -1);
		}
		if (key == KeyEvent.VK_RIGHT) {
			pos.translate(1, 0);
		}
		if (key == KeyEvent.VK_DOWN) {
			pos.translate(0, 1);
		}
		if (key == KeyEvent.VK_LEFT) {
			pos.translate(-1, 0);
		}
		
	}
	
	public void tick() {
		
		if (pos.x < 0) {
			pos.x = 0;
		} else if (pos.x >= Board.COLUMNS) {
			pos.x = Board.COLUMNS - 1;
		}
		
		if (pos.y < 0) {
			pos.y = 0;
		} else if (pos.y >= Board.ROWS) {
			pos.y = Board.ROWS - 1;
		}
		
	}

	public Point getPos() {
		return pos;
	}
	
}
