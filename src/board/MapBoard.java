package board;

import core.Nave;
import core.Splutch;

import screen.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class MapBoard extends Board implements shipPlacerListener {

    private Nave tempNave;

    private final ArrayList<shipSelectorListener> listeners;

    private static final Random r = new Random();

    private boolean fase2ready = false;

    public MapBoard() {
        super();

        listeners = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Nave nave : navi) {
            nave.draw(g, this);
        }

        if (tempNave != null) {
            tempNave.draw(g, this);
        }

        for (Splutch splutch : splutch) {
            splutch.draw(g, this);
        }
    }

    @Override
    public void placeShip(int length) {

        do {
            int randomRotation = r.nextInt(3) + 1;
            Point randomPos = new Point(r.nextInt(Board.ROWS), r.nextInt(Board.COLUMNS));
            tempNave = new Nave(randomPos, length, randomRotation);

        } while (!(tempNave.isPlaceable(navi) && tempNave.inField()));

        paintComponent(getGraphics());
    }

    @Override
    public void placeAllShips(ArrayList<Nave> navi) {
        for(Nave nave : navi) {
            placeShip(nave.getLength());

            nave.setVisible(false);

            finalizePlacement(false);
        }

        fase2ready = true;
    }

    private void finalizePlacement(boolean switchListeners) {

        if (tempNave.isPlaceable(navi)) {
            navi.add(tempNave);
        } else {
            return;
        }

        tempNave = null;

        if(navi.size() == Board.NUM_NAVI) {
            fase2ready = true;
            return;
        }

        if (switchListeners) {

            Window.toggleKeyListener("remove", "mapBoard");

            Window.toggleKeyListener("add", "inventoryBoard");

            for (shipSelectorListener listener : listeners) {
                listener.selectShip(true);
            }
        }
    }

    private void abortPlacement() {

        tempNave = null;

        Window.toggleKeyListener("remove", "mapBoard");
        Window.toggleKeyListener("add", "inventoryBoard");

        for (shipSelectorListener listener : listeners) {
            listener.selectShip(false);
        }
    }

    public void addListener (shipSelectorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            tempNave.translate(0, -1);
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            tempNave.translate(1, 0);
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            tempNave.translate(0, 1);
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            tempNave.translate(-1, 0);
        }
        if (key == KeyEvent.VK_SPACE && tempNave != null) {
            finalizePlacement(true);
        }
        if (key == KeyEvent.VK_R && tempNave != null) {
            do {
                tempNave.cycleFacing();
            } while (!tempNave.inField());
        }
        if (key == KeyEvent.VK_BACK_SPACE) {
            abortPlacement();
        }
        if (key == KeyEvent.VK_ENTER && fase2ready) {
            Window.fase2();
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
}
