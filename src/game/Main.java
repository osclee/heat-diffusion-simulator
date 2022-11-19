package game;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private final GamePanel gamePanel;

    private MenuBar menuBar;

    public Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setTitle("Heat Diffusion Simulator");

        gamePanel = new GamePanel();
        this.add(gamePanel);

        createMenu();

        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        gameActions();

    }

    private void createMenu() {
        menuBar = new MenuBar(gamePanel);

        this.setJMenuBar(menuBar);
    }

    private void gameActions() {
        //gamePanel.setupGame();
       // gamePanel.startGameThread();
    }
    public static void main(String[] args) {
        new Main();
    }
}
