package board;

import core.Nave;
import core.Splutch;

import java.awt.*;
import java.util.ArrayList;

public class InventoryBoard extends Board {
    
    private ArrayList<Nave> inventarioNavi;

    public InventoryBoard() {
        super();

        inventarioNavi = new ArrayList<Nave>();
        populateNavi();
    }

    private void populateNavi() {

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

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Nave nave : inventarioNavi) {
            nave.draw(g, this);
        }

    }
}
