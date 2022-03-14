package board;

import core.Nave;
import core.Splutch;

import java.awt.*;

public class MapBoard extends Board{

    public MapBoard() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Nave nave : navi) {
            nave.draw(g, this);
        }

        for (Splutch splutch : splutch) {
            splutch.draw(g, this);
        }
    }
}
