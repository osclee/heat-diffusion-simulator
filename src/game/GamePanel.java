package game;

import main.HeatDiffusion;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final int screenWidth = 800, screenHeight = 600;

    private int mapHeight = 20;
    private int mapWidth = 20;

    private double sourceTemperature = 100;
    private int sourceTemperatureRow = 0;
    private int sourceTemperatureColumn = -1;
    private int iterations = 200;

    private Thread gameThread;

    private HeatDiffusion heatDiffusion;

    private TileManager tileManager;

    private int iterationsPerSecond = 2;

    private int currentIteration = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.setFocusable(true);

        //tileManager = new TileManager(this);
    }

    public void updateGameSettings(int mapHeight, int mapWidth, double sourceTemperature, int sourceTemperatureRow, int sourceTemperatureCol, int iterations) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.sourceTemperature = sourceTemperature;
        this.sourceTemperatureRow = sourceTemperatureRow;
        this.sourceTemperatureColumn = sourceTemperatureCol;
        this.iterations = iterations;
    }

    public void play() {

        if (currentIteration > 0) {
            resumeGameThread();
        } else {
            if (heatDiffusion == null) {
                tileManager = new TileManager(this);
                heatDiffusion = new HeatDiffusion(mapHeight, mapWidth, sourceTemperature, sourceTemperatureRow, sourceTemperatureColumn, iterations);
                heatDiffusion.runSimulation();
            }

            startGameThread();
        }

    }

    public void pause() {
        //try {
            pauseGameThread();
       // } catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
    }

    public void restart() {
        currentIteration = 0;
    }

    public void setupGame() {
        //heatDiffusion = new HeatDiffusion(mapHeight, mapWidth, sourceTemperature, sourceTemperatureRow, sourceTemperatureColumn, iterations);
        heatDiffusion.runSimulation();
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // TODO: Fix...
    private void pauseGameThread() {//throws InterruptedException{

        synchronized (gameThread){
            try{
                gameThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //gameThread.wait();
    }

    private void resumeGameThread() {
        gameThread.notify();
    }

    private void update() {
        if (currentIteration < iterations) {
            currentIteration++;
        } else if (currentIteration == iterations) {
            System.out.println("Simulation ended. Final iteration: " + currentIteration);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        if (tileManager != null) {
            tileManager.draw(g2);
        }

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
