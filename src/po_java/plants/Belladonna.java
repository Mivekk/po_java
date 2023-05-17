package po_java.plants;

import po_java.Organism;
import po_java.Pair;
import po_java.Plant;
import po_java.World;

import javax.swing.*;
import java.awt.*;

public class Belladonna extends Plant {
    public Belladonna(World world, Pair<Integer, Integer> pos) {
        super(world, pos);

        strength = 99;
    }

    @Override
    public void handleCollision(Organism other) {
        world.board[other.getPos().second][other.getPos().first] = null;
        world.removeOrganism(other);

        other.setPos(new Pair<>(-1, -1));

        world.board[pos.second][pos.first] = null;
        world.removeOrganism(this);
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(66, 135, 245));
    }

    @Override
    public String getType() {
        return "Belladonna";
    }
}
