package game;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TileManager {

    private GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {

        int startingX = 100;
        int startingY = 100;

        int endingX = gamePanel.getScreenWidth() - 100;
        int endingY = gamePanel.getScreenHeight() - 100;

        int rows = gamePanel.getMapWidth();
        int columns = gamePanel.getMapHeight();

        int tileWidth = (endingX - startingX) / rows;
        int tileHeight = (endingY - startingY) / columns;

        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                g2.setColor(determineColor(gamePanel.getHeatDiffusion().getDiffusionMap()[gamePanel.getCurrentIteration()][row][column]));
                g2.fillRect(startingX + (tileWidth * row), startingY + (tileHeight * column), tileWidth, tileHeight);
            }
        }
    }

    private Color determineColor(double temperature) {

        double max = gamePanel.getHeatDiffusion().getHighestTemperature();
        double min = gamePanel.getHeatDiffusion().getLowestTemperature();

        double difference = max - min;

        double percent = (temperature - min) / difference;

        if (percent >= .95) {
            return new Color(255, 0, 0);
        } else if (percent >= .9) {
            return new Color(255, 40, 0);
        } else if (percent >= .85) {
            return new Color(255, 80, 0);
        } else if (percent >= .8) {
            return new Color(255, 120, 0);
        } else if (percent >= .75) {
            return new Color(255, 160, 0);
        }else if (percent >= .7) {
            return new Color(255, 200, 0);
        }else if (percent >= .65) {
            return new Color(255, 255, 0);
        }else if (percent >= .6) {
            return new Color(200, 255, 0);
        }else if (percent >= .55) {
            return new Color(160, 255, 0);
        }else if (percent >= .5) {
            return new Color(120, 255, 0);
        }else if (percent >= .45) {
            return new Color(80, 255, 0);
        }else if (percent >= .4) {
            return new Color(40, 255, 0);
        }else if (percent >= .35) {
            return new Color(0, 255, 0);
        }else if (percent >= .3) {
            return new Color(0, 255, 40);
        }else if (percent >= .25) {
            return new Color(0, 255, 80);
        }else if (percent >= .2) {
            return new Color(0, 255, 120);
        }else if (percent >= .15) {
            return new Color(0, 255, 160);
        }else if (percent >= .1) {
            return new Color(0, 255, 200);
        }else if (percent >= 0) {
            return new Color(0, 255, 255);
        }else {
            return new Color(0, 0, 0);
        }

        // Potential future usage.
        //return new Color((int)(255 * percent), (int)(255 * percent), (int)((255 / 2) * (1 - percent)));
    }
}
