package po_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Virtual World Simulator");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new GridBagLayout());
        window.setSize(400, 300);

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel labelX = new JLabel("X");
        constraints.gridx = 0;
        constraints.gridy = 0;
        window.add(labelX, constraints);

        JTextField inputSizeX = new JTextField("20");
        constraints.gridx = 0;
        constraints.gridy = 1;
        inputSizeX.setPreferredSize(new Dimension(50, 30));
        window.add(inputSizeX, constraints);

        JLabel labelY = new JLabel("Y");
        constraints.gridx = 1;
        constraints.gridy = 0;
        window.add(labelY, constraints);

        JTextField inputSizeY = new JTextField("20");
        constraints.gridx = 1;
        constraints.gridy = 1;
        inputSizeY.setPreferredSize(new Dimension(50, 30));
        window.add(inputSizeY, constraints);

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeX = Integer.parseInt(inputSizeX.getText());
                int sizeY = Integer.parseInt(inputSizeY.getText());

                window.dispose();

                new World(sizeX, sizeY);
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        enterButton.setPreferredSize(new Dimension(100, 30));
        window.add(enterButton, constraints);

        window.setVisible(true);
    }
}
