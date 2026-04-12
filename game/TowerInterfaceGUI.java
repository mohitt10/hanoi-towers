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
        this.setLayout(null);

        // 1. RESTART BUTTON
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(20, 20, 100, 35);
        this.add(restartButton);

        // 2. TEXT INPUT
        JLabel instructionLabel = new JLabel("Move (e.g., 1 3):");
        instructionLabel.setBounds(140, 20, 120, 35);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.add(instructionLabel);

        JTextField moveInput = new JTextField();
        moveInput.setBounds(260, 20, 80, 35);
        moveInput.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(moveInput);

        JButton moveButton = new JButton("Submit");
        moveButton.setBounds(350, 20, 90, 35);
        this.add(moveButton);

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = moveInput.getText().trim();
                System.out.println("Move received: " + input + ". Waiting for logic... Amish");

                moveInput.setText("");
                repaint();
            }
        });

        // 4. GENERATE DISK COLORS
        int numDisks = state.getNumDisks();
        diskColors = new Color[numDisks + 1];
        Random rand = new Random();
        for (int i = 1; i <= numDisks; i++) {
            diskColors[i] = new Color(rand.nextInt(200) + 55, rand.nextInt(200) + 55, rand.nextInt(200) + 55);
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

        // Background
        g2.setPaint(new GradientPaint(0, 0, new Color(180, 220, 255), 0, panelHeight, new Color(135, 206, 250)));
        g2.fillRect(0, 0, panelWidth, panelHeight);

        // Base
        int baseWidth = (int)(panelWidth * 0.8);
        int baseHeight = (int)(panelHeight * 0.05);
        int baseX = (panelWidth - baseWidth) / 2;
        int baseY = (int)(panelHeight * 0.8);
        g2.setColor(new Color(139, 69, 19));
        g2.fillRoundRect(baseX, baseY, baseWidth, baseHeight, 15, 15);

        // Poles & Disks
        int[] towerXPositions = {(int)(baseX + baseWidth * 0.2), panelWidth / 2, (int)(baseX + baseWidth * 0.8)};
        int poleWidth = (int)(panelWidth * 0.02);
        int poleHeight = (int)(panelHeight * 0.5);
        int poleY = baseY - poleHeight;
        int diskHeight = (int)(panelHeight * 0.06);
        int maxDiskWidth = (int)((towerXPositions[1] - towerXPositions[0]) * 0.9);
        int widthStep = maxDiskWidth / (numDisks + 1);

        List<Stack<Integer>> towers = gameState.getTowers();
        for (int i = 0; i < TOWER_COUNT; i++) {
            int xCenter = towerXPositions[i];
            g2.setColor(Color.GRAY);
            g2.fillRoundRect(xCenter - poleWidth / 2, poleY, poleWidth, poleHeight, 10, 10);

            Stack<Integer> currentStack = towers.get(i);
            for (int j = 0; j < currentStack.size(); j++) {
                int diskSize = currentStack.get(j);
                Color dColor = diskColors[diskSize];
                int diskWidth = maxDiskWidth - ((numDisks - diskSize) * widthStep);
                int xPos = xCenter - (diskWidth / 2);
                int yPos = baseY - (j + 1) * diskHeight;
                g2.setPaint(new GradientPaint(xPos, yPos, dColor, xPos, yPos + diskHeight, dColor.darker()));
                g2.fillRoundRect(xPos, yPos, diskWidth, diskHeight, 10, 10);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(xPos, yPos, diskWidth, diskHeight, 10, 10);
            }
        }

        // end screen
        if (gameState != null && gameState.isGameWon()) {
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, panelWidth, panelHeight);
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("CONGRATULATIONS!", panelWidth/2 - 250, panelHeight/2);
        }
    }
}