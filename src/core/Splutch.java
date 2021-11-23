package core;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import screen.Board;

public class Splutch {
	
	private BufferedImage image;
	
	private Point pos;

	public Splutch(int x, int y) {
		
		pos = new Point(x,y);
		
		loadImage();		
	}
	
	private void loadImage () {
		
		try {
			if ((pos.x + pos.y) % 2 == 0) {
				image = ImageIO.read(new File ("drawable/splutch.png"));
			} else {
				image = ImageIO.read(new File ("drawable/splutch2.png"));
			}
		} catch (IOException e) {
			System.out.print("Error opening image file: " + e.getMessage());
		}
	}
	
	public void draw (Graphics g, ImageObserver observer) {
		
		g.drawImage(image, pos.x * Board.TITLE_SIZE, pos.y * Board.TITLE_SIZE, Board.TITLE_SIZE, Board.TITLE_SIZE, observer);
		
	}
	
	public static void addSplutch(Point pos) {
		
		boolean nuovoSplutch = true;
		
		for (Splutch splutch : Board.getSplutch()) {
			if (splutch.getPos().equals(pos))
				nuovoSplutch = false;
		}
		
		if(nuovoSplutch) {
			Board.getSplutch().add(new Splutch(pos.x, pos.y));
		}
	}
	
	public static void addSplutch(int x, int y) {

		addSplutch(new Point (x, y));
	}
	
	public static void surroundNave(Nave nave) {
		
		Point posNave = nave.getPos();
		
		switch (nave.getFacing()) {
		case 0:
			for(int i = -1; i < 2; i++) {
				addSplutch(posNave.x + i, posNave.y - 1);
				addSplutch(posNave.x + i, posNave.y + nave.getLength());
			}
			
			for (int i = 0; i < nave.getLength(); i++) {
				addSplutch(posNave.x - 1, posNave.y + i);
				addSplutch(posNave.x + 1, posNave.y + i);
			}
			
			break;
		case 1:
			for (int i = -1; i < 2; i++) {
				addSplutch(posNave.x + 1, posNave.y + i);
				addSplutch(posNave.x - nave.getLength(), posNave.y + i);
			}

			for (int i = 0; i < nave.getLength(); i++) {
				addSplutch(posNave.x - i, posNave.y - 1);
				addSplutch(posNave.x - i, posNave.y + 1);
			}
			break;
		case 2:
			for (int i = -1; i < 2; i++) {
				addSplutch(posNave.x + i, posNave.y + 1);
				addSplutch(posNave.x + i, posNave.y - nave.getLength());
			}

			for (int i = 0; i < nave.getLength(); i++) {
				addSplutch(posNave.x - 1, posNave.y - i);
				addSplutch(posNave.x + 1, posNave.y - i);
			}

			break;
		case 3:
			for (int i = -1; i < 2; i++) {
				addSplutch(posNave.x - 1, posNave.y + i);
				addSplutch(posNave.x + nave.getLength(), posNave.y + i);
			}

			for (int i = 0; i < nave.getLength(); i++){
				addSplutch(posNave.x + i, posNave.y - 1);
				addSplutch(posNave.x + i, posNave.y + 1);
			}

			break;
		}
		
	}

	public Point getPos() {
		return pos;
	}
	
	
}
