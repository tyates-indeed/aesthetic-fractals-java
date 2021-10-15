package tyates.aesthetic.fractals.fractals;

import tyates.aesthetic.fractals.graphics.Board;
import tyates.aesthetic.fractals.math.DynamicExpression;
import tyates.aesthetic.fractals.math.DynamicExpressionNode;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DynamicFractal implements Fractal {
    private static final int CALCULATIONS = 100_000;
    private static final int FRACTAL_SCALE = 100;
    private static final int FRACTAL_STEP = 5;

    private Map<Point, Integer> drawPoints = new HashMap<>();
    private final DynamicExpression xExpression, yExpression;

    public DynamicFractal() {
        xExpression = new DynamicExpression();
        yExpression = new DynamicExpression();
    }

    public DynamicFractal(final DynamicExpression xExpression, final DynamicExpression yExpression) {
        this.xExpression = xExpression;
        this.yExpression = yExpression;
    }

    @Override
    public void calculate(final Board board) {
        final Map<Point, Integer> newDrawPoints = new HashMap<>();
        double x = 0.0;
        double y = 0.0;

        for (int i = 0; i < CALCULATIONS; i++) {
            final double newX = xExpression.evaluate(x, y);
            final double newY = yExpression.evaluate(x, y);
            x = newX;
            y = newY;

            final int drawX = (int) (Math.round(newX * FRACTAL_SCALE));
            final int drawY = (int) (Math.round(newY * FRACTAL_SCALE));
            final Point drawPoint = new Point(drawX, drawY);

            final int timesAlreadySeen = newDrawPoints.getOrDefault(drawPoint, 0);
            final int drawPointValue = Math.min(255, timesAlreadySeen + FRACTAL_STEP);
            newDrawPoints.put(drawPoint, drawPointValue);
        }

        drawPoints = newDrawPoints;
        board.repaint();
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        if (drawPoints.isEmpty()) {
            // TODO center the drawn string
            g.drawString("Generating...", offsetX, offsetY);
            return;
        }

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
        final DynamicFractal newFractal = (DynamicFractal) cloneFractal();
        mutate(newFractal.xExpression.getRoot());
        mutate(newFractal.yExpression.getRoot());
        return newFractal;
    }

    private void mutate(final DynamicExpressionNode current) {
        if (current.isConstant()) {
            current.setValue(DynamicExpressionNode.getMutatedConstant(current.getValue()));
        }

        if (current.getLeft() != null) {
            mutate(current.getLeft());
        }
        if (current.getRight() != null) {
            mutate(current.getRight());
        }
    }

    @Override
    public Fractal cloneFractal() {
        final DynamicExpression newX = xExpression.cloneExpression();
        final DynamicExpression newY = yExpression.cloneExpression();
        return new DynamicFractal(newX, newY);
    }
}
