package tyates.aesthetic.fractals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Board extends JPanel implements MouseListener {
    public static final int BOARD_FRAMES = 9;

    private int width, height;
    private int offsetX = 0;
    private int offsetY = 0;
    private final List<BoardFrame> boardFrames;

    public Board(final int width, final int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        boardFrames = new ArrayList<>();
        for (int i = 0; i < BOARD_FRAMES; i++) {
            final int x = (i % 3) * (width / 3);
            final int y = (i / 3) * (height / 3);
            boardFrames.add(new BoardFrame(x, y, width / 3, height / 3));
        }
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
            boardFrame.ifPresent(System.out::println);
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
