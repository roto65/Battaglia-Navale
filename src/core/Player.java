package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import board.Board;

public class Player {

	private BufferedImage image;
	
	private Point pos;

	public Player() {
		 
		loadImage();
		
		pos = new Point(0, 0);
	}

	private void loadImage() {
		try {
			image = ImageIO.read(new File("drawable/mirino.png"));
		} catch (IOException e) {
			System.out.print("Error opening image file: " + e.getMessage());
		}
	}
	
	public void draw(Graphics g, ImageObserver observer) {
		
		//g.drawImage(image, pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE, observer);
		
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform backup = g2d.getTransform();
		
		double rotation = Math.toRadians(0);
		double locationX = pos.x * Board.TITLE_SIZE + (Board.TITLE_SIZE /2);
		double locationY = pos.y * Board.TITLE_SIZE + (Board.TITLE_SIZE /2);
		
		g2d.rotate(rotation, locationX, locationY);
		
		g2d.drawImage(image, pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE, observer);
		
		g2d.setTransform(backup);
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			pos.translate(0, -1);
		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			pos.translate(1, 0);
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			pos.translate(0, 1);
		}
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			pos.translate(-1, 0);
		}
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (key == KeyEvent.VK_SPACE) {
			Board.shot(pos);
		}
		
	}

	public void mousePressed(MouseEvent e){

		int button = e.getButton();

		if (button == MouseEvent.BUTTON1){
			Board.shot(pos);
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

	public void tick (Point mousePos) {

		pos.x = (mousePos.x - (mousePos.x % Board.TITLE_SIZE)) / Board.TITLE_SIZE;
		pos.y = (mousePos.y - (mousePos.y % Board.TITLE_SIZE)) / Board.TITLE_SIZE;

		tick();
	}

	public Point getPos() {
		return pos;
	}
	
}
