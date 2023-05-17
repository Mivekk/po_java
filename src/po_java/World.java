package po_java;

import po_java.animals.*;
import po_java.plants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        human = new Human(this, new Pair<>(0, 0));
        organisms.add(human);

        Wolf wolf = new Wolf(this, new Pair<>(0, 9));
        organisms.add(wolf);

        Wolf wolf2 = new Wolf(this, new Pair<>(0, 12));
        organisms.add(wolf2);

        Turtle turtle = new Turtle(this, new Pair<>(0, 15));
        organisms.add(turtle);
//
//        Sheep sheep = new Sheep(this, new Pair<>(6, 4));
//        organisms.add(sheep);
//
//        Antelope antelope = new Antelope(this, new Pair<>(3, 8));
//        organisms.add(antelope);
//
//        Fox fox = new Fox(this, new Pair<>(10, 9));
//        organisms.add(fox);

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

                    if (e.getKeyCode() == 80 && !human.abilityActivated && human.abilityCooldown < 0) {
                        human.abilityActivated = true;
                        human.abilityDuration = ABILITY_DURATION;
                    }
                }

                run();
            }
        });

        initBoard();

        draw();

        handleInput();

        window.setVisible(true);
    }

    private void initBoard() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                JButton tileButton = new JButton();
                tileButton.setFocusable(false);
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

    private boolean handleInput() {
        JButton nextTurnButton = new JButton("Next turn");
        nextTurnButton.setFocusable(false);
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        window.add(nextTurnButton);

        JButton saveButton = new JButton("Save button");
        saveButton.setFocusable(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        window.add(saveButton);

        JButton loadButton = new JButton("Load button");
        loadButton.setFocusable(false);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        window.add(loadButton);

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt"))) {
            writer.write(organisms.size() + " " + organismsSize + "\n");

            for (int i = 0; i < organisms.size(); i++) {
                Organism organism = organisms.get(i);
                writer.write(organism.getStrength() + " " + organism.getAge() +
                        " " + organism.getPos().first + " " + organism.getPos().second +
                        " " + organism.getType() + " ");

                if (organism.getType().equals("Human")) {
                    writer.write(human.abilityActivated + " " + human.abilityCooldown +
                            " " + human.abilityDuration);
                }

                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("save.txt"))) {
            organisms.clear();
            board = new Organism[sizeY][sizeX];

            int oSize;
            String line = reader.readLine();
            String[] sizeTokens = line.split(" ");
            oSize = Integer.parseInt(sizeTokens[0]);
            organismsSize = Integer.parseInt(sizeTokens[1]);

            ArrayList<Organism> newOrganisms = new ArrayList<>();

            for (int i = 0; i < oSize; i++) {
                int tmpStrength, tmpAge;
                Pair<Integer, Integer> tmpPos;
                String tmpType;

                line = reader.readLine();
                String[] tokens = line.split(" ");
                tmpStrength = Integer.parseInt(tokens[0]);
                tmpAge = Integer.parseInt(tokens[1]);
                int posX = Integer.parseInt(tokens[2]);
                int posY = Integer.parseInt(tokens[3]);
                tmpPos = new Pair<>(posX, posY);
                tmpType = tokens[4];

                Organism organism = null;
                switch (tmpType) {
                    case "Antelope":
                        organism = new Antelope(this, tmpPos);
                        break;
                    case "Fox":
                        organism = new Fox(this, tmpPos);
                        break;
                    case "Sheep":
                        organism = new Sheep(this, tmpPos);
                        break;
                    case "Turtle":
                        organism = new Turtle(this, tmpPos);
                        break;
                    case "Human":
                        human = new Human(this, tmpPos);
                        human.abilityActivated = Boolean.parseBoolean(tokens[5]);
                        human.abilityCooldown = Integer.parseInt(tokens[6]);
                        human.abilityDuration = Integer.parseInt(tokens[7]);
                        organism = human;
                        break;
                    case "Wolf":
                        organism = new Wolf(this, tmpPos);
                        break;
                    case "Belladonna":
                        organism = new Belladonna(this, tmpPos);
                        break;
                    case "Dandelion":
                        organism = new Dandelion(this, tmpPos);
                        break;
                    case "Grass":
                        organism = new Grass(this, tmpPos);
                        break;
                    case "Guarana":
                        organism = new Guarana(this, tmpPos);
                        break;
                    case "Sosnowsky":
                        organism = new Sosnowsky(this, tmpPos);
                        break;
                }

                if (organism != null) {
                    organism.setStrength(tmpStrength);
                    organism.setAge(tmpAge);
                    newOrganisms.add(organism);
                    board[tmpPos.second][tmpPos.first] = organism;
                }
            }

            organisms = newOrganisms;

            draw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
