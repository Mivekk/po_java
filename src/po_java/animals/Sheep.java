package po_java.animals;

import po_java.Animal;
import po_java.Pair;
import po_java.World;

import javax.swing.*;
import java.awt.*;

public class Sheep extends Animal {
    public Sheep(World world, Pair<Integer, Integer> pos) {
        super(world, pos);

        strength = 4;
        initiative = 4;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(209, 209, 209));
    }

    @Override
    public String getType() {
        return "Sheep";
    }
}
