package tyates.aesthetic.fractals;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aesthetic Fractals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Board board = new Board(FRAME_WIDTH, FRAME_HEIGHT);
        board.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.getContentPane().add(board);
        frame.addMouseListener(board);
        frame.pack();

        final Insets insets = frame.getInsets();
        board.setMouseOffset(insets.left, insets.top);

        frame.setVisible(true);
    }
}
