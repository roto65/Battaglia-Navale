package board;

import java.util.EventListener;

public interface ShipSelectorListener extends EventListener {

    void selectShip(boolean exitStatus);
}
