package game;

import main.HeatDiffusion;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private int mapHeight;
    private int mapWidth;

    private double sourceTemperature = 100.0;
    private int sourceTemperatureRow = 0;
    private int sourceTemperatureColumn = -1;
    private int iterations = 10;

    private Thread gameThread;

    private HeatDiffusion heatDiffusion;

    public GamePanel() {



        //this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        //keyH = new KeyHandler();
        //this.addKeyListener(keyH);
        this.setFocusable(true);

        //sound = new Sound();

        //assetSetter = new AssetSetter(this);
        //obj = new SuperObject[10];

        //tileManager = new TileManager(this);



        //collisionChecker = new CollisionChecker(this);
        //player = new Player(this, keyH);
    }

    public void setupGame() {
        heatDiffusion = new HeatDiffusion(mapHeight, mapWidth, sourceTemperature, sourceTemperatureRow, sourceTemperatureColumn, iterations);
        heatDiffusion.runSimulation();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

    }
}
