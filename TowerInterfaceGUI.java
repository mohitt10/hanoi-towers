import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class TowerInterfaceGUI extends JPanel {
    private ArrayList<Stack<Disk>> towers;
    private int numDisks;
    private final int TOWER_COUNT = 3;

    class Disk {
        int size;
        Color color;

        Disk(int size, Color color) {
            this.size = size;
            this.color = color;
        }
    }

    public TowerInterfaceGUI() {
        towers = new ArrayList<>();
        for (int i = 0; i < TOWER_COUNT; i++) {
            towers.add(new Stack<>());
        }
        generateBlocks();
    }

    // Generates 3 to 6 random disks and stacks them on Tower 1
    public void generateBlocks() {
        Random rand = new Random();
        this.numDisks = rand.nextInt(4) + 3;

        for (Stack<Disk> s : towers) s.clear();

        for (int i = numDisks; i >= 1; i--) {
            Color diskColor = new Color(rand.nextInt(200) + 55, rand.nextInt(200) + 55, rand.nextInt(200) + 55);
            towers.get(0).push(new Disk(i, diskColor));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Dynamic scaling calculations based on window size
        int baseWidth = (int)(panelWidth * 0.8);
        int baseHeight = (int)(panelHeight * 0.05);
        int baseX = (panelWidth - baseWidth) / 2;
        int baseY = (int)(panelHeight * 0.8);

        int[] towerXPositions = new int[TOWER_COUNT];
        towerXPositions[0] = (int)(baseX + baseWidth * 0.2);
        towerXPositions[1] = panelWidth / 2;
        towerXPositions[2] = (int)(baseX + baseWidth * 0.8);

        int poleWidth = (int)(panelWidth * 0.02);
        int poleHeight = (int)(panelHeight * 0.5);
        int poleY = baseY - poleHeight;

        int diskHeight = (int)(panelHeight * 0.06);
        int towerSeparation = towerXPositions[1] - towerXPositions[0];
        int maxDiskWidth = (int)(towerSeparation * 0.9);
        int widthStep = (int)(maxDiskWidth / (numDisks + 1));

        // Draw Background and Base
        GradientPaint bgGradient = new GradientPaint(0, 0, new Color(180, 220, 255), 0, panelHeight, new Color(135, 206, 250));
        g2.setPaint(bgGradient);
        g2.fillRect(0, 0, panelWidth, panelHeight);

        g2.setColor(new Color(139, 69, 19));
        g2.fillRoundRect(baseX, baseY, baseWidth, baseHeight, 15, 15);
        g2.setColor(new Color(101, 67, 33));
        for (int i = 0; i < 5; i++) {
            g2.drawLine(baseX + 10, baseY + 5 + i * 5, baseX + baseWidth - 10, baseY + 5 + i * 5);
        }
        g2.drawRoundRect(baseX, baseY, baseWidth, baseHeight, 15, 15);

        // Draw Poles and Disks
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        for (int i = 0; i < TOWER_COUNT; i++) {
            int xCenter = towerXPositions[i];

            GradientPaint poleGradient = new GradientPaint(xCenter - poleWidth / 2, poleY, new Color(160, 160, 160),
                    xCenter + poleWidth / 2, poleY, new Color(120, 120, 120));
            g2.setPaint(poleGradient);
            g2.fillRoundRect(xCenter - poleWidth / 2, poleY, poleWidth, poleHeight, 10, 10);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRoundRect(xCenter - poleWidth / 2, poleY, poleWidth, poleHeight, 10, 10);

            Stack<Disk> currentStack = towers.get(i);
            for (int j = 0; j < currentStack.size(); j++) {
                Disk d = currentStack.get(j);

                int diskWidth = maxDiskWidth - ((numDisks - d.size) * widthStep);
                int xPos = xCenter - (diskWidth / 2);
                int yPos = baseY - (j + 1) * diskHeight;

                GradientPaint diskGradient = new GradientPaint(xPos, yPos, d.color, xPos, yPos + diskHeight, d.color.darker());
                g2.setPaint(diskGradient);
                g2.fillRoundRect(xPos, yPos, diskWidth, diskHeight, 10, 10);

                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillRoundRect(xPos + 5, yPos + 2, diskWidth - 10, diskHeight / 2 - 2, 10, 10);

                g2.setColor(Color.BLACK);
                g2.drawRoundRect(xPos, yPos, diskWidth, diskHeight, 10, 10);

                String numberStr = String.valueOf(d.size);
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(numberStr);
                int labelHeight = metrics.getHeight();
                int badgeSize = Math.max(labelWidth + 6, labelHeight + 2);

                int labelX = xCenter - labelWidth / 2;
                int labelY = yPos + diskHeight / 2 + metrics.getAscent() / 2 - 2;

                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillOval(xCenter - badgeSize / 2, labelY - metrics.getAscent() + 1, badgeSize, badgeSize);

                g2.setColor(Color.WHITE);
                g2.drawString(numberStr, labelX, labelY);
            }
        }

        g2.setColor(new Color(101, 67, 33));
        FontMetrics labelMetrics = g2.getFontMetrics();
        g2.drawString("Tower 1", towerXPositions[0] - labelMetrics.stringWidth("Tower 1") / 2, baseY + baseHeight + 20);
        g2.drawString("Tower 2", towerXPositions[1] - labelMetrics.stringWidth("Tower 2") / 2, baseY + baseHeight + 20);
        g2.drawString("Tower 3", towerXPositions[2] - labelMetrics.stringWidth("Tower 3") / 2, baseY + baseHeight + 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("THE TOWERS OF HANOI");
            TowerInterfaceGUI gamePanel = new TowerInterfaceGUI();

            // Refresh button to test generation
            JButton refreshButton = new JButton("Generate a new game :)");
            refreshButton.setFocusable(false);
            refreshButton.addActionListener(e -> {
                gamePanel.generateBlocks();
                gamePanel.repaint();
            });

            frame.setLayout(new BorderLayout());
            frame.add(gamePanel, BorderLayout.CENTER);
            frame.add(refreshButton, BorderLayout.SOUTH);

            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}