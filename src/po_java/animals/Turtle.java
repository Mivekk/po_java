package po_java.animals;

import po_java.Animal;
import po_java.Organism;
import po_java.Pair;
import po_java.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Turtle extends Animal {
    private final int MOVE_CHANCE = 25;

    public Turtle(World world, Pair<Integer, Integer> pos) {
        super(world, pos);

        strength = 2;
        initiative = 1;
    }

    @Override
    public Pair<Integer, Integer> getNextPos() {
        Random random = new Random();
        int moves = random.nextInt(100);
        if (moves >= MOVE_CHANCE) {
            return pos;
        }

        Pair<Integer, Integer> afterPos = super.getNextPos();

        return afterPos;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(59, 150, 32));
    }

    @Override
    public boolean bounceAttack(Organism organism) {
        return organism.getStrength() < 5;
    }

    @Override
    public String getType() {
        return "Turtle";
    }
}
