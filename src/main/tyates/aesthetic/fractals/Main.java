package tyates.aesthetic.fractals;

import javax.swing.*;

public class Main {
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aesthetic Fractals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);

        frame.getContentPane().add(new Board());

        frame.setVisible(true);
    }
}
