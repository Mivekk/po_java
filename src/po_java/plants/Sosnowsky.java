package po_java.plants;

import po_java.Organism;
import po_java.Pair;
import po_java.Plant;
import po_java.World;

import javax.swing.*;
import java.awt.*;

public class Sosnowsky extends Plant {
    public Sosnowsky(World world, Pair<Integer, Integer> pos) {
        super(world, pos);
    }

    @Override
    public void update() {
        super.update();

        Pair<Integer, Integer>[] directions = new Pair[]{
                new Pair<>(1, 0), new Pair<>(-1, 0), new Pair<>(0, 1), new Pair<>(0, -1)
        };

        for (Pair<Integer, Integer> dir : directions) {
            Pair<Integer, Integer> afterPos = new Pair<>(pos.first + dir.first, pos.second + dir.second);

            if (afterPos.first >= world.board[0].length || afterPos.first < 0 ||
                    afterPos.second < 0 || afterPos.second >= world.board.length) {

                continue;
            }

            Organism space = world.board[afterPos.second][afterPos.first];

            if (space != null && !(space instanceof Plant)) {
                handleCollision(space);
            }
        }
    }

    @Override
    public void handleCollision(Organism other) {
        world.board[other.getPos().second][other.getPos().first] = null;
        world.removeOrganism(other);
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(Color.MAGENTA);
    }

    @Override
    public String getType() {
        return "Sosnowsky";
    }
}
