package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsForm extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextField rowField;
    private JTextField columnField;
    private JTextField sourceTempField;
    private JTextField sourceTempRowField;
    private JTextField sourceTempColumnField;
    private JTextField numberIterationsField;
    private JButton cancelButton;
    private JButton okButton;

    private final GamePanel gamePanel;

    public SettingsForm(GamePanel gamePanel) {
        this.setContentPane(panel);
        this.setTitle("Heat Diffusion Game Settings");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.gamePanel = gamePanel;

    }

    private void updateGamePanelGameSettings() {
        int mapHeight = Integer.parseInt(rowField.getText());
        int mapWidth = Integer.parseInt(columnField.getText());

        double sourceTemp = Double.parseDouble(sourceTempField.getText());

        int sourceTempRow = Integer.parseInt(sourceTempRowField.getText());
        int sourceTempCol = Integer.parseInt(sourceTempColumnField.getText());

        int iterations = Integer.parseInt(numberIterationsField.getText());

        gamePanel.updateGameSettings(mapHeight, mapWidth, sourceTemp, sourceTempRow, sourceTempCol, iterations);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Ok")) {
            updateGamePanelGameSettings();
        }
        this.dispose();
    }
}
