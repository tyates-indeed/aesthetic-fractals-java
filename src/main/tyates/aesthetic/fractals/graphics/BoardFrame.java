package tyates.aesthetic.fractals.graphics;

import tyates.aesthetic.fractals.fractals.CliffordFractal;
import tyates.aesthetic.fractals.fractals.Fractal;

import java.awt.*;

public class BoardFrame {
    private final Rectangle rectangle;

    // TODO actual generation
    private Fractal fractal = new CliffordFractal();

    public BoardFrame(final int x, final int y, final int width, final int height) {
        this.rectangle = new Rectangle(x, y, width, height);
        fractal.calculate();
    }

    public void setFractal(final Fractal fractal) {
        this.fractal = fractal;
    }

    public void draw(final Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        if (fractal != null) {
            fractal.draw(g, rectangle.width / 2, rectangle.height / 2);
        }
    }

    public boolean containsPoint(final Point point) {
        return rectangle.contains(point);
    }

    @Override
    public String toString() {
        return String.format("Board[(%d,%d)]", rectangle.x, rectangle.y);
    }
}
