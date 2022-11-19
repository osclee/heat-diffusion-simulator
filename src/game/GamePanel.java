package game;

import main.HeatDiffusion;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final int screenWidth = 1200, screenHeight = 1200;

    private int mapHeight = 60;
    private int mapWidth = 60;

    private double sourceTemperature = 200;
    private int sourceTemperatureRow = 30;
    private int sourceTemperatureColumn = -1;
    private int iterations = 100000;

    private Thread gameThread;

    private HeatDiffusion heatDiffusion;

    private TileManager tileManager;

    private int iterationsPerSecond = 120;

    private int currentIteration = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        //keyH = new KeyHandler();
        //this.addKeyListener(keyH);
        this.setFocusable(true);

        tileManager = new TileManager(this);
    }

    public void setupGame() {
        heatDiffusion = new HeatDiffusion(mapHeight, mapWidth, sourceTemperature, sourceTemperatureRow, sourceTemperatureColumn, iterations);
        heatDiffusion.runSimulation();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {
        if (currentIteration < iterations) {
            //System.out.println("Current Iteration : " + currentIteration);
            currentIteration++;
        } else if (currentIteration == iterations) {
            System.out.println("Simulation ended. Final iteration: " + currentIteration);
        }


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        tileManager.draw(g2);
        g2.dispose();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / iterationsPerSecond;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public HeatDiffusion getHeatDiffusion() {
        return heatDiffusion;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }
}
