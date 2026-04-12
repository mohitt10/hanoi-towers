package game;

import javax.swing.*;

public class Game {
    public static void mainGame() {
        System.out.println("Game Started");

        int numDisks = -1;

        while (numDisks < 2 || numDisks > 9) {
            String input = JOptionPane.showInputDialog(null, "Enter number of disks (2 to 9):");

            if (input == null) {
                System.exit(0);
            }

            try {
                numDisks = Integer.parseInt(input);
            } catch (NumberFormatException e) {
            }
        }

        GameState state = new GameState(numDisks);

        JFrame frame = new JFrame("Towers of Hanoi");
        TowerInterfaceGUI gamePanel = new TowerInterfaceGUI(state);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null); // Centers the window on the screen
        frame.add(gamePanel);
        frame.setVisible(true); // Boom. Game starts.
    }
}
