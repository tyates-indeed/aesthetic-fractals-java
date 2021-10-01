package tyates.aesthetic.fractals;

import java.awt.*;

public class BoardFrame {
    private final int x,y;
    private final int width, height;

    public BoardFrame(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(final Graphics g) {
        g.setColor(Color.WHITE);

        g.drawRect(this.x, this.y, this.width, this.height);
    }
}
