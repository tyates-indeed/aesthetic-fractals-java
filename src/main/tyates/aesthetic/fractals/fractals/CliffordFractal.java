package tyates.aesthetic.fractals.fractals;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CliffordFractal implements Fractal {
    private static final int CALCULATIONS = 1_000_000;
    private static final int FRACTAL_SCALE = 50;

    private final Map<Point, Integer> drawPoints = new HashMap<>();
    private final double a, b, c, d;

    public CliffordFractal() {
        final Random random = new Random();
        a = (random.nextDouble() * 4.0) - 2.0;
        b = (random.nextDouble() * 4.0) - 2.0;
        c = (random.nextDouble() * 4.0) - 2.0;
        d = (random.nextDouble() * 4.0) - 2.0;
    }

    public CliffordFractal(final double a, final double b, final double c, final double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public void calculate() {
        drawPoints.clear();
        double x = 0.0;
        double y = 0.0;

        for (int i = 0; i < CALCULATIONS; i++) {
            final double newX = Math.sin(a * y) + c * Math.cos(a * x);
            final double newY = Math.sin(b * x) + d * Math.cos(b * y);
            x = newX;
            y = newY;

            final int drawX = (int) (Math.round(newX * FRACTAL_SCALE));
            final int drawY = (int) (Math.round(newY * FRACTAL_SCALE));
            final Point drawPoint = new Point(drawX, drawY);

            final int timesAlreadySeen = drawPoints.getOrDefault(drawPoint, 0);
            final int drawPointValue = Math.min(255, timesAlreadySeen + 1);
            drawPoints.put(drawPoint, drawPointValue);
        }
    }

    @Override
    public void draw(final Graphics g, final int offsetX, final int offsetY) {
        for (final Map.Entry<Point, Integer> entry : drawPoints.entrySet()) {
            final int drawX = entry.getKey().x + offsetX;
            final int drawY = entry.getKey().y + offsetY;
            final Color color = new Color(255, 255, 255, entry.getValue());

            g.setColor(color);
            g.drawLine(drawX, drawY, drawX, drawY);
        }
    }

    @Override
    public Fractal mutate() {
        final Random random = new Random();
        final double a = this.a + (random.nextDouble() / 5.0 - 0.1);
        final double b = this.b + (random.nextDouble() / 5.0 - 0.1);
        final double c = this.c + (random.nextDouble() / 5.0 - 0.1);
        final double d = this.d + (random.nextDouble() / 5.0 - 0.1);

        return new CliffordFractal(a, b, c, d);
    }
}
