package po_java;

import po_java.animals.Human;

import java.util.ArrayList;

public class World {
    private int organismsSize = 0;
    private final int sizeX, sizeY;

    private Human human;

    public World(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        board = new Organism[this.sizeY][this.sizeX];

        init();
    }

    public ArrayList<Organism> organisms;
    public Organism[][] board;

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

        run();
    }

    private void run() {
        draw();
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
