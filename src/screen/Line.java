package screen;

import javax.swing.*;
import java.awt.*;

public class Line extends JPanel {

    public Line(int width, Color color) {

        setPreferredSize(new Dimension(width, getHeight()));

        setBackground(color);
    }
}
