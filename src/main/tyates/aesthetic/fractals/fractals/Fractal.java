package tyates.aesthetic.fractals.fractals;


import java.awt.*;

public interface Fractal {
    void calculate();

    void draw(final Graphics g, final int offsetX, final int offsetY);

    Fractal mutate();
}
