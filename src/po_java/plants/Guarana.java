package po_java.plants;

import po_java.Organism;
import po_java.Pair;
import po_java.Plant;
import po_java.World;

import javax.swing.*;
import java.awt.*;

public class Guarana extends Plant {
    private final int STRENGTH_INCREMENT = 5;

    public Guarana(World world, Pair<Integer, Integer> pos) {
        super(world, pos);
    }

    @Override
    public void handleCollision(Organism other) {
        other.setStrength(other.getStrength() + STRENGTH_INCREMENT);
        super.handleCollision(other);
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(239, 68, 66));
    }

    @Override
    public String getType() {
        return "Guarana";
    }
}
