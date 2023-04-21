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

import board.Board;

public class Nave {

    //transient = not serializable

    private transient final int TITLE_SIZE = Board.TITLE_SIZE;

    private transient ArrayList<BufferedImage> sprites;

    private transient BufferedImage spriteX = null;

    public boolean[] hit;

    private Point pos;

    private int length;
    private int facing;
    private boolean affondata;
    private boolean isVisible;

    /*
     * 0 = punta in alto e culo verso il basso,
     * 1 = punta a dx e culo verso sx;
     * 2 = punta in basso e culo verso l'alto
     * 3 = punta a sx e culo verso dx
     */

    public Nave(int x, int y, int length, int facing) {

        this.length = length;
        this.facing = facing;
        this.pos = new Point(x - 1, y - 1);

        sprites = new ArrayList<>();
        sprites = loadSprites();

        hit = new boolean[length];
        for (int i = 0; i < length; i++) {
            hit[i] = false;
        }

        affondata = false;
        isVisible = true;

    }

    public Nave(Point pos, int length, int facing) {
        this(pos.x, pos.y, length, facing);
    }

    private ArrayList<BufferedImage> loadSprites() {

        try {
            if (length == 1) {

                sprites.add(ImageIO.read(new File("drawable/navetta.png")));
                sprites.add(ImageIO.read(new File("drawable/navetta2.png")));

            } else {

                for (int i = 0; i < length; i++) {
                    if (i == 0 || i == length - 1) {
                        sprites.add(ImageIO.read(new File("drawable/sedere.png")));
                    } else {
                        sprites.add(ImageIO.read(new File("drawable/dritto.png")));
                    }
                }

                for (int i = 0; i < length; i++) {
                    if (i == 0 || i == length - 1) {
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

        if(!isVisible) {
            return;
        }

        if(sprites == null){
            sprites = new ArrayList<>();
            sprites = loadSprites();
        }

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform backup = g2d.getTransform();

        int j = 0;

        if (length == 1) {

            if (hit[0]) {
                g.drawImage(sprites.get(1), pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
            } else {
                g.drawImage(sprites.get(0), pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
            }

        } else {

            double rotation = findRotation();
            double locationX, locationY;
            int[] position = {0, 0};

            for (int i = 0; i < length; i++) {

                g2d.setTransform(backup);

                position = findPosition(i);
                locationX = (pos.x + position[0]) * TITLE_SIZE + ((float) TITLE_SIZE / 2);
                locationY = (pos.y + position[1]) * TITLE_SIZE + ((float) TITLE_SIZE / 2);

                if (hit[i]) {
                    j = i + length;
                } else {
                    j = i;
                }

                if (i == length - 1) {

                    rotation += Math.PI;

                }
                g2d.rotate(rotation, locationX, locationY);
                g2d.drawImage(sprites.get(j), (pos.x + position[0]) * TITLE_SIZE,
                        (pos.y + position[1]) * TITLE_SIZE, TITLE_SIZE,
                        TITLE_SIZE, observer);
            }
        }

        g2d.setTransform(backup);
    }

    public void drawX(Graphics g, ImageObserver observer) {

        if (spriteX == null) {
            loadX();
        }

        int[] position;

        for (int i = 0; i < length; i++) {

            position = findPosition(i);

            if (hit[i]) {
                g.drawImage(spriteX, (pos.x + position[0]) * TITLE_SIZE,
                        (pos.y + position[1]) * TITLE_SIZE, TITLE_SIZE,
                        TITLE_SIZE, observer);
            }
        }
    }

    private double findRotation() {
        return switch (facing) {
            case 0 -> Math.toRadians(0);
            case 1 -> Math.toRadians(90);
            case 2 -> Math.toRadians(180);
            case 3 -> Math.toRadians(270);
            default -> 0;
        };
    }

    public int[] findPosition(int i) {

        int[] position = {0, 0};

        switch (facing) {
            case 0 -> position[1] = i;
            case 1 -> position[0] = -i;
            case 2 -> position[1] = -i;
            case 3 -> position[0] = i;
        }

        return position;
    }

    public Point findBlockPosition(int blockNumber) {

        Point blockPosition = (Point) pos.clone();

        switch (facing) {
            case 0 -> blockPosition.y += blockNumber;
            case 1 -> blockPosition.x -= blockNumber;
            case 2 -> blockPosition.y -= blockNumber;
            case 3 -> blockPosition.x += blockNumber;
        }
        return blockPosition;
    }

    public boolean inField(){

        for (int i = 0; i < this.getLength(); i++) {
            Point blockPosition = findBlockPosition(i);
            if(!(blockPosition.x >= 0 && blockPosition.x < Board.COLUMNS && blockPosition.y >= 0 && blockPosition.y < Board.ROWS)) {
                return false;
            }
        }

        return true;
    }

    public void setHit(int blockNumber) {
        hit[blockNumber] = true;

        boolean naveCompleta = true;

        for (int i = 0; i < length; i++) {
            if (!hit[i]) {
                naveCompleta = false;
                break;
            }
        }

        affondata = naveCompleta;

        if (affondata) {
            Splutch.surroundNave(this);
        }

        affondata = naveCompleta;

    }

    public boolean isPlaceable(ArrayList<Nave> list) {
        for (Nave nave : list) {
            for (int i = 0; i < nave.getLength(); i++) {
                Point centerBlockPosition = new Point(nave.getPos().x + nave.findPosition(i)[0],
                        nave.getPos().y + nave.findPosition(i)[1]);

                for (int j = 0; j < this.getLength(); j++) {
                    int[] blockPosition = {this.getPos().x + this.findPosition(j)[0],
                            this.getPos().y + this.findPosition(j)[1]};

                    if (centerBlockPosition.equals(new Point(blockPosition[0], blockPosition[1])) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] + 0, blockPosition[1] - 1)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] + 1, blockPosition[1] - 1)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] + 1, blockPosition[1] + 0)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] + 1, blockPosition[1] + 1)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] + 0, blockPosition[1] + 1)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] - 1, blockPosition[1] + 1)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] - 1, blockPosition[1] + 0)) ||
                            centerBlockPosition.equals(new Point(blockPosition[0] - 1, blockPosition[1] - 1))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void cycleFacing() {
        if (this.facing == 3) {
            facing = 0;
        } else {
            facing++;
        }
    }

    public void translate(int x, int y) {

        Point oldPos = (Point) this.getPos().clone();

        pos.x = pos.x + x;
        pos.y = pos.y + y;

        if(!this.inField()) {
            this.setPos(oldPos);
        }
    }

    public boolean getHit(int i) {
        return hit[i];
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

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
