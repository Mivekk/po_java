package po_java.animals;

import po_java.Animal;
import po_java.Pair;
import po_java.World;

import javax.swing.*;
import java.awt.*;

public class Wolf extends Animal {
    public Wolf(World world, Pair<Integer, Integer> pos) {
        super(world, pos);

        strength = 9;
        initiative = 5;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(130, 126, 126));
    }

    @Override
    public String getType() {
        return "Wolf";
    }
}
