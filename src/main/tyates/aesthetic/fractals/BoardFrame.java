package tyates.aesthetic.fractals;

import java.awt.*;

public class BoardFrame {
    private final Rectangle rectangle;

    public BoardFrame(final int x, final int y, final int width, final int height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public void draw(final Graphics g) {
        g.setColor(Color.WHITE);

        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public boolean containsPoint(final Point point) {
        return rectangle.contains(point);
    }

    @Override
    public String toString() {
        return String.format("Board[(%d,%d)]", rectangle.x, rectangle.y);
    }
}
