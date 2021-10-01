package tyates.aesthetic.fractals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel implements MouseListener {
    private int offsetX = 0;
    private int offsetY = 0;

    public void setMouseOffset(final int offsetX, final int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 1000, 100);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getButton());
        final Point point = getMousePosition(e);
        System.out.println(point);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private Point getMousePosition(final MouseEvent e) {
        final Point reportedPoint = e.getPoint();
        return new Point((int)reportedPoint.getX() - offsetX, (int)reportedPoint.getY() - offsetY);
    }
}
