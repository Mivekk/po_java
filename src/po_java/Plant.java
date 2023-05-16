package po_java;

import po_java.plants.*;

import javax.swing.*;
import java.util.Random;

public abstract class Plant extends Organism {
    public final static int REGROW_CHANCE = 30;

    public Plant(World world, Pair<Integer, Integer> pos) {
        super(world);

        this.pos = pos;
        world.board[pos.second][pos.first] = this;
    }

    @Override
    public void update() {
        Random random = new Random();
        int regrow = random.nextInt(100);

        if (regrow < REGROW_CHANCE && !didRegrow) {
            Pair<Integer, Integer> newPos = getNextPos();

            Organism organism = world.board[newPos.second][newPos.first];
            if (organism instanceof Plant) {
                world.board[organism.getPos().second][organism.getPos().first] = null;
                world.removeOrganism(organism);
            } else if (organism != null) {
                return;
            }

            Plant newPlant;

            if (this instanceof Grass) {
                newPlant = new Grass(world, newPos);
            } else if (this instanceof Dandelion) {
                newPlant = new Dandelion(world, newPos);
            } else if (this instanceof Guarana) {
                newPlant = new Guarana(world, newPos);
            } else if (this instanceof Belladonna) {
                newPlant = new Belladonna(world, newPos);
            } else if (this instanceof Sosnowsky) {
                newPlant = new Sosnowsky(world, newPos);
            } else {
                return;
            }

            world.addOrganism(newPlant);

            newPlant.didRegrow = true;
            didRegrow = true;

            return;
        }

        didRegrow = false;
    }

    @Override
    public void handleCollision(Organism other) {
        world.board[other.getPos().second][other.getPos().first] = null;
        world.removeOrganism(this);
    }

    @Override
    public abstract void draw(JButton tileButton);

    @Override
    public abstract String getType();

    protected boolean didRegrow = false;
}
