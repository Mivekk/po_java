package po_java.plants;

import po_java.Organism;
import po_java.Pair;
import po_java.Plant;
import po_java.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Dandelion extends Plant {
    private final int REGROW_TRIES = 3;

    public Dandelion(World world, Pair<Integer, Integer> pos) {
        super(world, pos);
    }

    @Override
    public void update() {
        if (!didRegrow) {
            Random random = new Random();
            for (int i = 0; i < REGROW_TRIES; i++) {
                int regrow = random.nextInt(100);
                if (regrow < REGROW_CHANCE) {
                    Pair<Integer, Integer> newPos = getNextPos();

                    Organism organism = world.board[newPos.second][newPos.first];
                    if (organism instanceof Plant) {
                        world.board[organism.getPos().second][organism.getPos().first] = null;
                        world.removeOrganism(organism);
                    } else if (organism != null) {
                        return;
                    }

                    Dandelion newPlant = new Dandelion(world, newPos);

                    world.logs.add("Dandelion grew at X: " + newPos.second + " Y: " + newPos.first);
                    world.addOrganism(newPlant);

                    newPlant.didRegrow = true;
                    didRegrow = true;
                }
            }
            return;
        }

        didRegrow = false;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(Color.YELLOW);
    }

    @Override
    public String getType() {
        return "Dandelion";
    }
}
