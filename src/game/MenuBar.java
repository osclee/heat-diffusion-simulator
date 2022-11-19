package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {

    private JMenu file;
    private JMenuItem saveData;

    private JMenu settings;
    private JMenuItem goToSettings;

    private JMenu playM;
    private JMenuItem play;
    private JMenuItem pause;
    private JMenuItem restart;

    private final GamePanel gamePanel;

    public MenuBar(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        buildMenu();
    }

    private void buildMenu() {
        file = new JMenu("File");
        saveData = new JMenuItem("Save Data");

        settings = new JMenu("Settings");
        goToSettings = new JMenuItem("Open Game Settings");

        playM = new JMenu("Play");
        play = new JMenuItem("Play");
        pause = new JMenuItem("Pause");
        restart = new JMenuItem("Restart");

        saveData.addActionListener(this);
        goToSettings.addActionListener(this);
        play.addActionListener(this);
        pause.addActionListener(this);
        restart.addActionListener(this);

        file.add(saveData);
        settings.add(goToSettings);
        playM.add(play);
        playM.add(pause);
        playM.add(restart);

        this.add(file);
        this.add(settings);
        this.add(playM);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Play")) {
            gamePanel.play();
        } else if (e.getActionCommand().equals("Pause")) {
            gamePanel.pause();
        } else if (e.getActionCommand().equals("Restart")) {
            gamePanel.restart();
        } else if (e.getActionCommand().equals("Open Game Settings")) {
            new SettingsForm(gamePanel);
        }
    }
}
