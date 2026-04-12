package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class TowerInterfaceGUI extends JPanel {
    private GameState gameState;
    private Color[] diskColors;
    private final int TOWER_COUNT = 3;

    public TowerInterfaceGUI(GameState state) {
        this.gameState = state;
        this.setLayout(null); // Absolute positioning for UI elements

        // restart button for in between or after the game is finished
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(20, 20, 100, 35);
        restartButton.setFocusable(false);
        this.add(restartButton);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentDisks = gameState.getNumDisks();
                gameState = new GameState(currentDisks); // Resets the brain
                repaint(); // Redraws everything fresh
            }
        });

        JLabel instructionLabel = new JLabel("Move (e.g., 1 3):");
        instructionLabel.setBounds(140, 20, 120, 35);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(instructionLabel);

        JTextField moveInput = new JTextField();
        moveInput.setBounds(260, 20, 80, 35);
        moveInput.setFont(new Font("Arial", Font.BOLD, 16));
        moveInput.setHorizontalAlignment(JTextField.CENTER);
        this.add(moveInput);

        JButton moveButton = new JButton("Submit");
        moveButton.setBounds(350, 20, 90, 35);
        moveButton.setFocusable(false);
        this.add(moveButton);

        // Allow pressing "Enter" key to submit the move!
        moveButton.addActionListener(new MoveActionHandler(moveInput));
        moveInput.addActionListener(new MoveActionHandler(moveInput));

        // --- 3. GENERATE COLORS ---
        int numDisks = state.getNumDisks();
        diskColors = new Color[numDisks + 1];
        Random rand = new Random();
        for (int i = 1; i <= numDisks; i++) {
            diskColors[i] = new Color(rand.nextInt(200) + 55, rand.nextInt(200) + 55, rand.nextInt(200) + 55);
        }
    }

    // --- LOGIC TO HANDLE THE TEXT INPUT ---
    private class MoveActionHandler implements ActionListener {
        private JTextField inputField;

        public MoveActionHandler(JTextField inputField) {
            this.inputField = inputField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameState.isGameWon()) return; // Lock the game if they already won

            String input = inputField.getText().trim();
            String[] parts = input.split("\\s+"); // Splits by any amount of space

            if (parts.length == 2) {
                try {
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);

                    // Try to move. The GameState logic handles invalid numbers!
                    boolean success = gameState.move(from, to);

                    if (!success) {
                        JOptionPane.showMessageDialog(null, "Invalid move! Remember:\n- Use numbers 1, 2, or 3.\n- Don't put a larger disk on a smaller one.\n- Don't move from an empty tower.");
                    }

                    inputField.setText(""); // Clear the box for the next turn
                    inputField.requestFocus(); // Keep the typing cursor in the box
                    repaint(); // Update the visuals

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter numbers only (e.g., 1 3)");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Format error. Please enter two numbers separated by a space (e.g., 1 3)");
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int numDisks = gameState.getNumDisks();

        // Draw Background
        g2.setPaint(new GradientPaint(0, 0, new Color(180, 220, 255), 0, panelHeight, new Color(135, 206, 250)));
        g2.fillRect(0, 0, panelWidth, panelHeight);

        // Draw Base
        int baseWidth = (int)(panelWidth * 0.8);
        int baseHeight = (int)(panelHeight * 0.05);
        int baseX = (panelWidth - baseWidth) / 2;
        int baseY = (int)(panelHeight * 0.8);

        g2.setColor(new Color(139, 69, 19));
        g2.fillRoundRect(baseX, baseY, baseWidth, baseHeight, 15, 15);
        g2.setColor(new Color(101, 67, 33));
        g2.drawRoundRect(baseX, baseY, baseWidth, baseHeight, 15, 15);

        // Draw Poles & Disks
        int[] towerXPositions = {(int)(baseX + baseWidth * 0.2), panelWidth / 2, (int)(baseX + baseWidth * 0.8)};
        int poleWidth = (int)(panelWidth * 0.02);
        int poleHeight = (int)(panelHeight * 0.5);
        int poleY = baseY - poleHeight;

        int diskHeight = (int)(panelHeight * 0.06);
        int maxDiskWidth = (int)((towerXPositions[1] - towerXPositions[0]) * 0.9);
        int widthStep = maxDiskWidth / (numDisks + 1);

        List<Stack<Integer>> towers = gameState.getTowers();
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        for (int i = 0; i < TOWER_COUNT; i++) {
            int xCenter = towerXPositions[i];

            // Pole
            g2.setPaint(new GradientPaint(xCenter - poleWidth / 2, poleY, new Color(160, 160, 160), xCenter + poleWidth / 2, poleY, new Color(120, 120, 120)));
            g2.fillRoundRect(xCenter - poleWidth / 2, poleY, poleWidth, poleHeight, 10, 10);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(xCenter - poleWidth / 2, poleY, poleWidth, poleHeight, 10, 10);

            // Disks
            Stack<Integer> currentStack = towers.get(i);
            for (int j = 0; j < currentStack.size(); j++) {
                int diskSize = currentStack.get(j);
                Color dColor = diskColors[diskSize];

                int diskWidth = maxDiskWidth - ((numDisks - diskSize) * widthStep);
                int xPos = xCenter - (diskWidth / 2);
                int yPos = baseY - (j + 1) * diskHeight;

                g2.setPaint(new GradientPaint(xPos, yPos, dColor, xPos, yPos + diskHeight, dColor.darker()));
                g2.fillRoundRect(xPos, yPos, diskWidth, diskHeight, 10, 10);

                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillRoundRect(xPos + 5, yPos + 2, diskWidth - 10, diskHeight / 2 - 2, 10, 10);

                g2.setColor(Color.BLACK);
                g2.drawRoundRect(xPos, yPos, diskWidth, diskHeight, 10, 10);

                // Disk Numbers
                String numberStr = String.valueOf(diskSize);
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(numberStr);
                int badgeSize = Math.max(labelWidth + 6, metrics.getHeight() + 2);

                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillOval(xCenter - badgeSize / 2, (yPos + diskHeight / 2 + metrics.getAscent() / 2 - 2) - metrics.getAscent() + 1, badgeSize, badgeSize);
                g2.setColor(Color.WHITE);
                g2.drawString(numberStr, xCenter - labelWidth / 2, yPos + diskHeight / 2 + metrics.getAscent() / 2 - 2);
            }
        }

        // Tower Labels (1, 2, 3 so the user knows what to type!)
        g2.setColor(new Color(101, 67, 33));
        FontMetrics labelMetrics = g2.getFontMetrics();
        g2.drawString("Tower 1", towerXPositions[0] - labelMetrics.stringWidth("Tower 1") / 2, baseY + baseHeight + 20);
        g2.drawString("Tower 2", towerXPositions[1] - labelMetrics.stringWidth("Tower 2") / 2, baseY + baseHeight + 20);
        g2.drawString("Tower 3", towerXPositions[2] - labelMetrics.stringWidth("Tower 3") / 2, baseY + baseHeight + 20);

        // EPIC VICTORY SCREEN OVERLAY
        if (gameState != null && gameState.isGameWon()) {
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, panelWidth, panelHeight);

            g2.setFont(new Font("Arial", Font.BOLD, 55));
            FontMetrics fm = g2.getFontMetrics();
            String winText = "CONGRATULATIONS!";
            int x = (panelWidth - fm.stringWidth(winText)) / 2;
            int y = panelHeight / 2;

            g2.setColor(Color.BLACK);
            g2.drawString(winText, x + 4, y + 4);
            g2.setColor(new Color(255, 215, 0));
            g2.drawString(winText, x, y);

            g2.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics subFm = g2.getFontMetrics();
            String subText = "You solved the puzzle!";
            int subX = (panelWidth - subFm.stringWidth(subText)) / 2;

            g2.setColor(Color.WHITE);
            g2.drawString(subText, subX, y + 50);
        }
    }
}