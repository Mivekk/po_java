package po_java;

import po_java.animals.Human;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class World {
    private int organismsSize = 0;
    private final int sizeX, sizeY;

    private JFrame window = new JFrame("Virtual World Simulator");

    private Human human = new Human(this, new Pair<>(2, 2));

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        board = new Organism[this.sizeY][this.sizeX];
        tileButtons = new JButton[this.sizeY][this.sizeX];

        init();
    }

    public ArrayList<Organism> organisms;

    public Organism[][] board;

    public JButton[][] tileButtons;


    private void initOrganisms() {

    }

    public void addOrganism(Organism organism) {
        organisms.add(organism);

        organismsSize++;
    }



    public void removeOrganism(Organism organism) {
        int organismIdx = organisms.indexOf(organism);
        if (organismIdx < 0) {
            return;
        }

        if (organisms.get(organismIdx) instanceof Human) {
            human = null;
        }

        organisms.remove(organismIdx);

        organismsSize--;
    }

    private void init() {
        initOrganisms();

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new GridBagLayout());
        window.setSize(800, 800);

        initBoard();

        draw();

        window.setVisible(true);
        // run();
    }

    private void initBoard() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                JButton tileButton = new JButton();
                tileButton.setBackground(Color.WHITE);
                tileButton.setPreferredSize(new Dimension(27, 27));
                window.add(tileButton, constraints);

                tileButtons[i][j] = tileButton;

                constraints.gridx++;
            }
            constraints.gridx = 0;
            constraints.gridy++;
        }
    }

    private void run() {
        while (true) {
            if (readInput()) {
                continue;
            }

            update();

            draw();
        }
    }

    private boolean readInput() {
        return false;
    }

    public void update() {

    }

    private void draw() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {

            }
        }
    }


    private void drawLegend() {

    }

    private void drawLegendHelper() {

    }

    private void save() {

    }

    private void load() {

    }
}
