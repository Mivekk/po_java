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
    private final int TILE_SIZE = 30;

    private final int MAX_LOGS = 30;

    private final int ABILITY_COOLDOWN = 5;

    private final int ABILITY_DURATION = 5;

    private int organismsSize = 0;

    private final int sizeX, sizeY;

    private JFrame window = new JFrame("Virtual World Simulator");

    private JTextArea logsTextArea = new JTextArea();

    private Human human = null;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        board = new Organism[this.sizeY][this.sizeX];
        tileButtons = new JButton[this.sizeY][this.sizeX];

        init();
    }

    public ArrayList<String> logs = new ArrayList<>();

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

        Sheep sheep = new Sheep(this, new Pair<>(6, 4));
        organisms.add(sheep);

        Antelope antelope = new Antelope(this, new Pair<>(3, 8));
        organisms.add(antelope);

        Fox fox = new Fox(this, new Pair<>(10, 9));
        organisms.add(fox);

        Belladonna belladonna = new Belladonna(this, new Pair<>(7, 4));
        organisms.add(belladonna);

        Belladonna belladonna2 = new Belladonna(this, new Pair<>(7, 3));
        organisms.add(belladonna2);

        Dandelion dandelion = new Dandelion(this, new Pair<>(8, 6));
        organisms.add(dandelion);

        Guarana guarana = new Guarana(this, new Pair<>(8, 4));
        organisms.add(guarana);

        Grass grass = new Grass(this, new Pair<>(5, 8));
        organisms.add(grass);

        Sosnowsky sosnowsky = new Sosnowsky(this, new Pair<>(12, 9));
        organisms.add(sosnowsky);
    }

    public void initLogs() {
        JPanel logsPanel = new JPanel();
        logsPanel.setBounds(sizeX * TILE_SIZE + TILE_SIZE, 0, 400, 600);

        logsTextArea.setEditable(false);
        logsTextArea.setFont(new Font("Arial", Font.PLAIN, 18));

        logsPanel.add(logsTextArea);
        window.add(logsPanel);
    }

    public void showLogs() {
        logsTextArea.setText("");

        for (int i = 0; i < logs.size(); i++) {
            logsTextArea.append(logs.get(i) + "\n");
        }

        logs.clear();
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
        window.setLayout(null);
        window.setFocusable(true);
        window.setSize(1000, 1000);
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

        initLogs();

        drawLegend();

        draw();

        handleInput();

        window.setVisible(true);
    }

    private void initBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(sizeY, sizeX));
        boardPanel.setBounds(0, 0, sizeY * TILE_SIZE, sizeX * TILE_SIZE);

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                JButton tileButton = new JButton();
                tileButton.setFocusable(false);
                tileButton.setBackground(Color.WHITE);

                final int tmpI = i;
                final int tmpJ = j;

                tileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] organismsNames = {
                                "Antelope", "Fox", "Sheep", "Turtle",
                                "Wolf", "Belladonna", "Dandelion", "Grass",
                                "Guarana", "Sosnowsky"
                        };

                        int selectedOrganism = JOptionPane.showOptionDialog(
                                window,
                                "Select an organism:",
                                    "Organisms",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                organismsNames,
                                organismsNames[0]
                        );

                        if (selectedOrganism != JOptionPane.CLOSED_OPTION) {
                            if (organismsNames[selectedOrganism].equals("Wolf")) {
                                Wolf newWolf = new Wolf(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newWolf);
                            } else if (organismsNames[selectedOrganism].equals("Sheep")) {
                                Sheep newSheep = new Sheep(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newSheep);
                            } else if (organismsNames[selectedOrganism].equals("Fox")) {
                                Fox newFox = new Fox(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newFox);
                            } else if (organismsNames[selectedOrganism].equals("Turtle")) {
                                Turtle newTurtle = new Turtle(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newTurtle);
                            } else if (organismsNames[selectedOrganism].equals("Antelope")) {
                                Antelope newAntelope = new Antelope(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newAntelope);
                            } else if (organismsNames[selectedOrganism].equals("Grass")) {
                                Grass newGrass = new Grass(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newGrass);
                            } else if (organismsNames[selectedOrganism].equals("Dandelion")) {
                                Dandelion newDandelion = new Dandelion(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newDandelion);
                            } else if (organismsNames[selectedOrganism].equals("Guarana")) {
                                Guarana newGuarana = new Guarana(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newGuarana);
                            } else if (organismsNames[selectedOrganism].equals("Belladonna")) {
                                Belladonna newBelladonna = new Belladonna(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newBelladonna);
                            } else if (organismsNames[selectedOrganism].equals("Sosnowsky")) {
                                Sosnowsky newSosnowsky = new Sosnowsky(World.this, new Pair<>(tmpJ, tmpI));
                                organisms.add(newSosnowsky);
                            }
                        }

                        draw();
                    }
                });

                boardPanel.add(tileButton);

                tileButtons[i][j] = tileButton;
            }
        }

        window.add(boardPanel);
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
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
        buttonsPanel.setBounds(0, sizeY * TILE_SIZE, 400, 50);
        buttonsPanel.add(nextTurnButton);

        JButton saveButton = new JButton("Save button");
        saveButton.setFocusable(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        buttonsPanel.add(saveButton);

        JButton loadButton = new JButton("Load button");
        loadButton.setFocusable(false);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        buttonsPanel.add(loadButton);

        window.add(buttonsPanel);

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

        showLogs();
    }


    private void drawLegend() {
        JPanel legendPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(5, 2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        legendPanel.setLayout(gridLayout);
        legendPanel.setBounds(0, 700, 300, 200);

        drawLegendHelper(new Color(31, 244, 255), "Antelope", legendPanel);
        drawLegendHelper(new Color(255, 165, 31), "Fox", legendPanel);
        drawLegendHelper(Color.BLACK, "Human", legendPanel);
        drawLegendHelper(new Color(209, 209, 209), "Sheep", legendPanel);
        drawLegendHelper(new Color(59, 150, 32), "Turtle", legendPanel);
        drawLegendHelper(new Color(130, 126, 126), "Wolf", legendPanel);
        drawLegendHelper(new Color(66, 135, 245), "Belladonna", legendPanel);
        drawLegendHelper(Color.YELLOW, "Dandelion", legendPanel);
        drawLegendHelper(new Color(138, 222, 100), "Grass", legendPanel);
        drawLegendHelper(Color.MAGENTA, "Sosnowsky", legendPanel);

        window.add(legendPanel);
    }

    private void drawLegendHelper(Color color, String text, JPanel legendPanel) {
        JButton displayColor = new JButton();
        displayColor.setFocusable(false);
        displayColor.setBackground(color);
        legendPanel.add(displayColor);

        JTextArea legendTextArea = new JTextArea(text);
        legendTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        legendPanel.add(legendTextArea);
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
