package board;

import core.Nave;
import core.Player;
import core.Splutch;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayerBoard extends Board {

    private Player player;

    private static Point mousePos = new Point(0, 0);

    public PlayerBoard() {
        super();

        player = new Player();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        mousePos.x = e.getX();
        mousePos.y = e.getY() - 30;

        player.tick(mousePos);

        player.mousePressed(e);

        super.mouseClicked(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        player.keyPressed(e);
        player.tick();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Nave nave : navi) {

            if (nave.isAffondata()) {
                nave.draw(g, this);
            } else {
                nave.drawX(g, this);
            }
        }

        for (Splutch splutch : splutch) {
            splutch.draw(g, this);
        }

        player.draw(g, this);
    }
}
