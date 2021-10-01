package tyates.aesthetic.fractals;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 1000, 100);
    }
}
