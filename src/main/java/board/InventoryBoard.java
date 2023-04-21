package board;

import JsonIO.ShipParser;
import core.Nave;
import core.ShipHighlighter;
import screen.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class InventoryBoard extends Board implements ShipSelectorListener {

    private ArrayList<Nave> inventarioNavi;

    private final ArrayList<ShipPlacerListener> listeners;

    private final ShipHighlighter shipHighlighter;

    public InventoryBoard() {
        super();

        inventarioNavi = new ArrayList<>();
        populateNavi();

        listeners = new ArrayList<>();
        shipHighlighter = new ShipHighlighter(inventarioNavi);
    }

    private void populateNavi() {

        /*
        inventarioNavi.add(new Nave(2, 2, 4, 0));

        inventarioNavi.add(new Nave(7, 2, 3, 0));
        inventarioNavi.add(new Nave(9, 2, 3, 0));

        inventarioNavi.add(new Nave(4, 2, 2, 0));
        inventarioNavi.add(new Nave(4, 5, 2, 0));
        inventarioNavi.add(new Nave(4, 8, 2, 0));

        inventarioNavi.add(new Nave(7, 6, 1, 0));
        inventarioNavi.add(new Nave(9, 6, 1, 0));
        inventarioNavi.add(new Nave(7, 8, 1, 0));
        inventarioNavi.add(new Nave(9, 8, 1, 0));
        */

        inventarioNavi = ShipParser.getShips("inventory.json");

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Nave nave : inventarioNavi) {
            nave.draw(g, this);
        }

        shipHighlighter.draw(g, this);

    }

    @Override
    @SuppressWarnings("unchecked")

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_R) {
            placeAllShips();
        }
        if(key == KeyEvent.VK_SPACE) {

            ArrayList<Nave> copiaNavi = (ArrayList<Nave>) inventarioNavi.clone();

            for (Nave nave : copiaNavi) {
                if (nave.isVisible()) {
                    for (int i = 0; i < nave.getLength(); i++) {
                        if (shipHighlighter.getPos().equals(nave.findBlockPosition(i))) {

                            shipHighlighter.getNavi().get(shipHighlighter.getNavi().indexOf(nave)).setVisible(false);
                            inventarioNavi.get(inventarioNavi.indexOf(nave)).setVisible(false);

                            placeShip(nave.getLength());
                        }
                    }
                }
            }
        } else {
            shipHighlighter.keyPressed(key);
            shipHighlighter.tick();
        }
    }


    /*
    exitStatus == true ---> nave piazzata correttamente
    exitStatus == false --> nave non piazzata
     */
    @Override
    public void selectShip(boolean exitStatus) {

        if(!exitStatus){
            for (Nave nave: inventarioNavi){
                if (!nave.isVisible()) {
                    for (int i = 0; i < nave.getLength(); i++) {
                        if (shipHighlighter.getPos().equals(nave.findBlockPosition(i))){
                            nave.setVisible(true);
                            shipHighlighter.getNavi().get(shipHighlighter.getNavi().indexOf(nave)).setVisible(true);
                        }
                    }
                }
            }
        }

        shipHighlighter.setVisible(true);

        paintComponent(getGraphics());
    }

    public void addListener(ShipPlacerListener listener) {
        listeners.add(listener);
    }

    private void placeShip(int length) {

        Window.toggleKeyListener("remove", "inventoryBoard");
        Window.toggleKeyListener("add", "mapBoard");

        shipHighlighter.setVisible(false);

        for (ShipPlacerListener listener : listeners) {
            listener.placeShip(length);
        }
    }

    private void placeAllShips() {
        Window.toggleKeyListener("remove", "inventoryBoard");
        Window.toggleKeyListener("add", "mapBoard");

        shipHighlighter.setVisible(false);

        for (ShipPlacerListener listener : listeners) {
            listener.placeAllShips(inventarioNavi);
        }
    }


}
