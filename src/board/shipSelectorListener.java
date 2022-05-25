package board;

import java.util.EventListener;

public interface shipSelectorListener extends EventListener {

    void selectShip(boolean exitStatus);
}
