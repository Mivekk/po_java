package po_java.plants;

import po_java.Pair;
import po_java.Plant;
import po_java.World;

import javax.swing.*;
import java.awt.*;

public class Grass extends Plant {
    public Grass(World world, Pair<Integer, Integer> pos) {
        super(world, pos);
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(138, 222, 100));
    }

    @Override
    public String getType() {
        return "Grass";
    }
}
