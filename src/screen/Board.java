package screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Player;
import core.Splutch;
import core.Nave;

public class Board extends JPanel implements ActionListener, KeyListener, BoardConstants{
	
	private static final long serialVersionUID = 490905409104883233L;
		
	public static final int TITLE_SIZE = 50;
	public static final int ROWS = 10;
	public static final int COLUMNS = 10;
		
	private Player player;

	private int type;

	private static ArrayList <Nave> navi;
	private static ArrayList <Splutch> splutch;

	public Board (int type) { //update

		this.type = type;
		
		setPreferredSize(new Dimension(COLUMNS * TITLE_SIZE, ROWS * TITLE_SIZE));
		setBackground(new Color(46, 91, 255));
		
		if (type == BoardConstants.PLAYER_BOARD) {
			player = new Player();
		}
		
		navi = new ArrayList <Nave> ();
		splutch = new ArrayList <Splutch> ();
		
		navi.add( new Nave(5,5,4,3));

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (type == BoardConstants.PLAYER_BOARD) {
			player.keyPressed(e);

			player.tick();
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
				
	}
	
	@Override
	public void paintComponent(Graphics g) { //update
		super.paintComponent(g);
		
		drawBackground(g);
	
		for (Nave nave : navi) {

			if(nave.isAffondata() || type == BoardConstants.MAP_BOARD) {
				nave.draw(g, this);
			} else {
				nave.drawX(g, this);
			}
		}
		
		for (Splutch splutch : splutch) {			
			splutch.draw(g, this);
		}

		if(type == BoardConstants.PLAYER_BOARD) {
			player.draw(g, this);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawBackground(Graphics g) {
		
		g.setColor(new Color(46, 140, 255));
		
		for (int row = 0; row < ROWS; row++) {
			for (int column = 0; column < COLUMNS; column++) {
				if ((row + column) % 2 == 1) {
					g.fillRect(column * TITLE_SIZE, row * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE);
				}
			}
		}
	}
	
	public static void shot (Point pos) {
				
		boolean naveColpita = false;
		
		for (Nave nave : navi) {
			for (int i = 0; i < nave.getLength(); i++) {
				
				switch (nave.getFacing()) {
				
				case 0:
					if (pos.x == nave.getPos().x && pos.y == nave.getPos().y + i) {
						nave.setHit(i);
						naveColpita = true;
					}
					break;
				case 1:
					if (pos.x == nave.getPos().x - i && pos.y == nave.getPos().y) {
						nave.setHit(i);
						naveColpita = true;
					}
					break;
				case 2:
					if (pos.x == nave.getPos().x && pos.y == nave.getPos().y - i) {
						nave.setHit(i);
						naveColpita = true;
					}
					break;
				case 3:
					if (pos.x == nave.getPos().x + i && pos.y == nave.getPos().y) {
						nave.setHit(i);
						naveColpita = true;
					}
					break;
				}
			}
		}
		
		if (!naveColpita) {
			
			Splutch.addSplutch(pos);
		}
	}

	public static ArrayList<Splutch> getSplutch() {
		return splutch;
	}

}
