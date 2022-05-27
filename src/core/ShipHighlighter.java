package core;

import board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShipHighlighter {

    private BufferedImage corner;

    private Point pos;

    private final ArrayList<Nave> navi;

    private boolean onShip;
    private boolean isVisible;

    private static final int TITLE_SIZE = Board.TITLE_SIZE;

    public ShipHighlighter(ArrayList<Nave> navi) {

        pos = new Point(0, 0);

        this.navi = navi;
        this.onShip = false;
        this.isVisible = true;

        loadImages();

    }

    private void loadImages() {
        try {
            corner = ImageIO.read(new File("drawable/highlighter.png"));
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {

        if(!isVisible) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform backup = g2d.getTransform();

        int[] offset = findOffset();

        double locationX, locationY;

        locationX = pos.x * TITLE_SIZE + ((float) TITLE_SIZE / 2);
        locationY = pos.y * TITLE_SIZE + ((float) TITLE_SIZE / 2);

        //tl marker
        g2d.drawImage(corner, (pos.x + offset[2]) * TITLE_SIZE, (pos.y + offset[0]) * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
        g2d.rotate(Math.toRadians(90), locationX, locationY);

        //tr marker
        g2d.drawImage(corner, (pos.x + offset[0]) * TITLE_SIZE, (pos.y - offset[3]) * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
        g2d.rotate(Math.toRadians(90), locationX, locationY);

        //br marker
        g2d.drawImage(corner, (pos.x - offset[3]) * TITLE_SIZE, (pos.y - offset[1]) * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
        g2d.rotate(Math.toRadians(90), locationX, locationY);

        //bl marker
        g2d.drawImage(corner, (pos.x - offset[1]) * TITLE_SIZE, (pos.y + offset[2]) * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
        g2d.rotate(Math.toRadians(90), locationX, locationY);

        g2d.setTransform(backup);
    }

    /*
     * tl e tr hanno lo stesso offset su y --> topY
     * bl e br hanno lo stesso offset su y --> botY
     *
     * tl e bl hanno lo stesso offset su x --> leftX
     * tr e br hanno lo stesso offset su x --> rightX
     *
     * [topY, botY, leftX, rightX]
     */

    private int[] findOffset() {

        int[] offset = new int[4];

        for (Nave nave : navi) {
            if(!nave.isVisible()){
                continue;
            }

            for (int i = 0; i < nave.getLength(); i++) {

                int[] blockRelativePosition = nave.findPosition(i);
                Point blockPosition = new Point(nave.getPos().x + blockRelativePosition[0],
                        nave.getPos().y + blockRelativePosition[1]);

                if (pos.equals(blockPosition)) {
                    switch (nave.getFacing()) {
                        case 0 -> {
                            offset[0] = nave.getPos().y - pos.y;
                            offset[1] = offset[0] + nave.getLength() - 1;
                            offset[2] = 0;
                            offset[3] = 0;
                        }
                        case 1 -> {
                            offset[0] = 0;
                            offset[1] = 0;
                            offset[3] = nave.getPos().x - pos.x;
                            offset[2] = offset[3] - nave.getLength() + 1;
                        }
                        case 2 -> {
                            offset[1] = nave.getPos().y - pos.y;
                            offset[0] = offset[1] - nave.getLength() + 1;
                            offset[2] = 0;
                            offset[3] = 0;
                        }
                        case 3 -> {
                            offset[0] = 0;
                            offset[1] = 0;
                            offset[2] = nave.getPos().x - pos.x;
                            offset[3] = offset[2] + nave.getLength() - 1;
                        }
                    }
                }
            }
        }
        return offset;
    }

    public boolean isOnShip() {

        for (Nave nave : navi) {
            if (nave.isVisible()){
                for (int i = 0; i < nave.getLength(); i++) {
                    int[] blockRelativePosition = nave.findPosition(i);
                    Point blockPosition = new Point(nave.getPos().x + blockRelativePosition[0],
                            nave.getPos().y + blockRelativePosition[1]);

                    if (pos.equals(blockPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void keyPressed(int key) {

        onShip = isOnShip();

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
        if (isOnShip() && onShip) {
            keyPressed(key);
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

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public ArrayList<Nave> getNavi() {
        return navi;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
