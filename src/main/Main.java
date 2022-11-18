package main;

public class Main {

    public static void main(String[] args) {
        HeatDiffusion hd = new HeatDiffusion(10, 10, 100, 0, -1, 10);
        hd.runSimulation();
        hd.printMap();
    }
}
