package tyates.aesthetic.fractals.graphics;

import tyates.aesthetic.fractals.fractals.DynamicFractal;
import tyates.aesthetic.fractals.fractals.Fractal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Board extends JPanel implements MouseListener {
    public static final int BOARD_FRAMES = 9;

    private int width, height;
    private int offsetX = 0;
    private int offsetY = 0;
    private final List<BoardFrame> boardFrames;

    private final ExecutorService fractalThreadPool;

    public Board(final int width, final int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        fractalThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        boardFrames = new ArrayList<>();
        for (int i = 0; i < BOARD_FRAMES; i++) {
            final int x = (i % 3) * (width / 3);
            final int y = (i / 3) * (height / 3);
            // TODO better choice of which initial type of fractal
            final Fractal initialFractal = new DynamicFractal();
            boardFrames.add(new BoardFrame(i, x, y, width / 3, height / 3, initialFractal));

            fractalThreadPool.submit(() -> initialFractal.calculate(this));
        }

        this.repaint();
    }

    public void setMouseOffset(final int offsetX, final int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
    }

    private void drawBoard(final Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.WHITE);
        for (final BoardFrame boardFrame : boardFrames) {
            boardFrame.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        final Point point = getMousePosition(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            final Optional<BoardFrame> boardFrame = getBoardFrame(point);
            if (boardFrame.isPresent()) {
                final Fractal selectedFractal = boardFrame.get().getFractal();
                for (int i = 0; i < boardFrames.size(); i++) {
                    // Move the selected fractal to the center frame
                    if (i == (int) (BOARD_FRAMES / 2.0)) {
                        boardFrames.get(i).setFractal(selectedFractal);
                    } else {
                        final Fractal mutatedFractal = selectedFractal.mutate();
                        boardFrames.get(i).setFractal(mutatedFractal);
                        fractalThreadPool.submit(() -> mutatedFractal.calculate(this));
                    }
                }
            }
        }

        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private Point getMousePosition(final MouseEvent e) {
        final Point reportedPoint = e.getPoint();
        return new Point((int) reportedPoint.getX() - offsetX, (int) reportedPoint.getY() - offsetY);
    }

    private Optional<BoardFrame> getBoardFrame(final Point point) {
        for (final BoardFrame boardFrame : boardFrames) {
            if (boardFrame.containsPoint(point)) {
                return Optional.of(boardFrame);
            }
        }
        return Optional.empty();
    }
}
