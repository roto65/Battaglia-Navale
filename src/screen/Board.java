package screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
//import java.util.Timer;

import javax.swing.JPanel;

import core.Player;
import core.Nave;

public class Board extends JPanel implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 490905409104883233L;
	
	//private final int DELAY = 25; //useless?
	
	public static final int TITLE_SIZE = 50;
	public static final int ROWS = 10;
	public static final int COLUMNS = 10;
	
	// private Timer timer; //useless?
	
	private Player player;
	private Nave nave;
	
	private ArrayList <Nave> navi;
	
	public Board () {
		
		setPreferredSize(new Dimension(COLUMNS * TITLE_SIZE, ROWS * TITLE_SIZE));
		setBackground(new Color(46, 91, 255));
		
		player = new Player();
		
		nave = new Nave(5,5,3,0);
		
		navi = new ArrayList <Nave> ();
		navi.add(nave);
		
		//timer = new Timer(DELAY, this); //useless?
		//timer.start(); //useless?
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		
		player.tick();
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
				
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
	
		for (Nave nave : navi) {
			nave.draw(g,this);
			
		}
		
		player.draw(g, this);
		

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
}
