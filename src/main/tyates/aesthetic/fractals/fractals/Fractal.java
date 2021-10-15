package tyates.aesthetic.fractals.fractals;


import tyates.aesthetic.fractals.graphics.Board;

import java.awt.*;

public interface Fractal {
    void calculate(final Board board);

    void draw(final Graphics g, final int offsetX, final int offsetY);

    Fractal mutate();

    Fractal cloneFractal();
}
