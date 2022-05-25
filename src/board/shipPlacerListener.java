package board;

import java.util.EventListener;

public interface shipPlacerListener extends EventListener {
    void placeShip(int length);
}
