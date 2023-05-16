package po_java;

import po_java.animals.*;
import po_java.plants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

public class World {
    private final int ABILITY_COOLDOWN = 5;

    private final int ABILITY_DURATION = 5;

    private int organismsSize = 0;

    private final int sizeX, sizeY;

    private JFrame window = new JFrame("Virtual World Simulator");

    private Human human = null;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        board = new Organism[this.sizeY][this.sizeX];
        tileButtons = new JButton[this.sizeY][this.sizeX];

        init();
    }

    public ArrayList<Organism> organisms = new ArrayList<>();

    public Organism[][] board;

    public JButton[][] tileButtons;

    private void initOrganisms() {
        human = new Human(this, new Pair<>(2, 2));
        organisms.add(human);

        Wolf wolf = new Wolf(this, new Pair<>(5, 4));
        organisms.add(wolf);

        Wolf wolf2 = new Wolf(this, new Pair<>(5, 5));
        organisms.add(wolf2);

        Turtle turtle = new Turtle(this, new Pair<>(6, 6));
        organisms.add(turtle);

        Sheep sheep = new Sheep(this, new Pair<>(6, 4));
        organisms.add(sheep);

        Antelope antelope = new Antelope(this, new Pair<>(3, 8));
        organisms.add(antelope);

        Fox fox = new Fox(this, new Pair<>(10, 9));
        organisms.add(fox);

//        Belladonna belladonna = new Belladonna(this, new Pair<>(5, 4));
//        organisms.add(belladonna);
//
//        Dandelion dandelion = new Dandelion(this, new Pair<>(6, 6));
//        organisms.add(dandelion);
//
//        Guarana guarana = new Guarana(this, new Pair<>(6, 4));
//        organisms.add(guarana);
//
//        Grass grass = new Grass(this, new Pair<>(3, 8));
//        organisms.add(grass);
//
//        Sosnowsky sosnowsky = new Sosnowsky(this, new Pair<>(10, 9));
//        organisms.add(sosnowsky);
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
        window.setFocusable(true);
        window.setSize(800, 800);
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (human != null) {
                    human.setNextMove(e.getKeyCode());
                } else if (human != null && !human.abilityActivated && human.abilityCooldown < 0) {
                    //human.abilityActivated = true;
                    //human.abilityDuration = ABILITY_DURATION;
                }

                run();
            }
        });

        initBoard();

        draw();

        window.setVisible(true);
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
        update();

        draw();
    }

    private boolean readInput() {

        return false;
    }

    public void update() {
        organisms.sort((a, b) -> {
            if (a.getInitiative() == b.getInitiative()) {
                return Integer.compare(b.getAge(), a.getAge());
            }
            return Integer.compare(b.getInitiative(), a.getInitiative());
        });

        organismsSize = organisms.size();

        for (int i = 0; i < organismsSize; i++) {
            organisms.get(i).update();
        }

        if (human != null) {
            if (human.abilityDuration == 0 && human.abilityActivated) {
                human.abilityActivated = false;
                human.abilityCooldown = ABILITY_COOLDOWN;
            } else {
                human.abilityDuration--;
                human.abilityCooldown--;
            }
        }
    }

    private void draw() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (board[i][j] != null) {
                    board[i][j].draw(tileButtons[i][j]);
                }
                else {
                    tileButtons[i][j].setBackground(Color.WHITE);
                }
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
