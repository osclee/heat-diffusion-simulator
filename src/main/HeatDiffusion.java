package main;

import java.util.Arrays;

public class HeatDiffusion {

    // Store the data as a 3D array, where each timestamp is a 2D array of the map.
    private double[][][] diffusionMap;

    private int iterations;

    private double sourceTemperature;
    private int sourceTemperatureRow, sourceTemperatureCol;

    private int mapHeight = 10, mapWidth = 10;

    private double lowestTemperature = Double.MAX_VALUE;

    public HeatDiffusion(int mapHeight, int mapWidth, double sourceTemperature, int sourceTemperatureRow, int sourceTemperatureCol, int iterations) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.sourceTemperature = sourceTemperature;
        this.sourceTemperatureRow = sourceTemperatureRow;
        this.sourceTemperatureCol = sourceTemperatureCol;
        this.iterations = iterations;
        initDiffusionMap();
    }

    private void initDiffusionMap() {
        double initialTemperature = 50.0;

        diffusionMap = new double[iterations + 1][mapHeight][mapWidth];

        for (double [][] iteration : diffusionMap) {
            for (double[] row : iteration) {
                Arrays.fill(row, initialTemperature);
            }
        }
    }

    public void runSimulation() {
        System.out.println("Running simulation");
        for (int currentIteration = 1; currentIteration <= iterations; currentIteration ++) {
            double[][] previousIterationMap = diffusionMap[currentIteration - 1].clone();
            for (int currentRow = 0; currentRow < mapHeight; currentRow ++) {
                for (int currentColumn = 0; currentColumn < mapWidth; currentColumn ++) {
                    diffusionMap[currentIteration][currentRow][currentColumn] = findTileTemperature(previousIterationMap, currentRow, currentColumn);

                }
            }
        }
    }

    private double findTileTemperature (double[][] previousIteration, int tileRow, int tileCol) {
        double temperature = 0;
        int divideBy = 0;

        if (tileRow == 0) {
            if (tileCol == 0) {
                temperature = previousIteration[tileRow + 1][tileCol] + previousIteration[tileRow][tileCol + 1];
                divideBy = 2;
            } else if (tileCol == mapWidth - 1) {
                temperature = previousIteration[tileRow + 1][tileCol] + previousIteration[tileRow][tileCol - 1];
                divideBy = 2;
            } else {
                temperature = previousIteration[tileRow + 1][tileCol] + previousIteration[tileRow][tileCol - 1] + previousIteration[tileRow + 1][tileCol];
                divideBy = 3;
            }
        } else if (tileCol == 0) {
            if (tileRow == mapHeight - 1) {
                temperature = previousIteration[tileRow - 1][tileCol] + previousIteration[tileRow][tileCol + 1];
                divideBy = 2;
            } else {
                temperature = previousIteration[tileRow + 1][tileCol] + previousIteration[tileRow - 1][tileCol] + previousIteration[tileRow][tileCol + 1];
                divideBy = 3;
            }
        } else if (tileRow == mapHeight - 1) {
            if (tileCol == mapWidth - 1) {
                temperature = previousIteration[tileRow - 1][tileCol] + previousIteration[tileRow][tileCol - 1];
                divideBy = 2;
            } else {
                temperature = previousIteration[tileRow][tileCol - 1] + previousIteration[tileRow][tileCol + 1] + previousIteration[tileRow - 1][tileCol];
                divideBy = 3;
            }
        } else if (tileCol == mapWidth - 1) {
            temperature = previousIteration[tileRow - 1][tileCol] + previousIteration[tileRow + 1][tileCol] + previousIteration[tileRow][tileCol - 1];
            divideBy = 3;
        } else {
            temperature = previousIteration[tileRow - 1][tileCol] + previousIteration[tileRow + 1][tileCol] + previousIteration[tileRow][tileCol - 1] + previousIteration[tileRow][tileCol + 1];
            divideBy = 4;
        }
        if (isTileAdjacentToTempSource(tileRow, tileCol)) {
            temperature += sourceTemperature;
            divideBy ++;
        }

        return temperature / divideBy;

    }

    private boolean isTileAdjacentToTempSource(int tileRow, int tileCol) {
        return (tileRow - 1 == sourceTemperatureRow && tileCol == sourceTemperatureCol)
                || (tileRow + 1 == sourceTemperatureRow &&  tileCol == sourceTemperatureCol)
                || (tileCol - 1 == sourceTemperatureCol && tileRow == sourceTemperatureRow)
                || (tileCol + 1 == sourceTemperatureCol && tileRow == sourceTemperatureRow);
    }

    public void printMap() {
        int iter = 0;

        for (double[][] iteration: diffusionMap) {
            System.out.println("Current iteration = " + iter);
            for (double[] row : iteration) {
                for (double col : row) {
                    System.out.print(" " + col);
                }
                System.out.println();
            }
            iter++;
        }
    }

    public double[][][] getDiffusionMap() {
        return diffusionMap;
    }

    public double getHighestTemperature() {
        return sourceTemperature;
    }

    public double getLowestTemperature() {

        if (lowestTemperature == Double.MAX_VALUE) {
            for (double [] row : diffusionMap[0]) {
                for (double column : row) {
                    if (column < lowestTemperature) {
                        lowestTemperature = column;
                    }
                }
            }
        }
        return lowestTemperature;
    }

}
