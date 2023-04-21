package board;

import core.Nave;

import java.util.ArrayList;
import java.util.EventListener;

public interface ShipPlacerListener extends EventListener {
    void placeShip(int length);

    void placeAllShips(ArrayList<Nave> navi);
}
