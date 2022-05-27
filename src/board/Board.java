package board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.Splutch;
import core.Nave;

public class Board extends JPanel implements ActionListener, KeyListener, MouseListener {

    @Serial
    private static final long serialVersionUID = 490905409104883233L;

    public static final int TITLE_SIZE = 50;

    public static final int ROWS = 10;
    public static final int COLUMNS = 10;
    public static final int NUM_NAVI = 10;
    protected static ArrayList<Nave> navi;
    protected static ArrayList<Splutch> splutch;

    public Board() {

        setPreferredSize(new Dimension(COLUMNS * TITLE_SIZE, ROWS * TITLE_SIZE));
        setBackground(new Color(46, 91, 255));

        navi = new ArrayList<>();
        splutch = new ArrayList<>();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

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

        drawBackground(g, new Color(46, 140, 255));

        Toolkit.getDefaultToolkit().sync(); //?
    }

    public void drawBackground(Graphics g, Color color) {

        g.setColor(color);

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if ((row + column) % 2 == 1) {
                    g.fillRect(column * TITLE_SIZE, row * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE);
                }
            }
        }
    }

    public static void shot(Point pos) {

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
