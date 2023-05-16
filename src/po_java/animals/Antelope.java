package po_java.animals;

import po_java.Animal;
import po_java.Pair;
import po_java.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Antelope extends Animal {
    private final int ESCAPE_CHANCE = 50;

    public Antelope(World world, Pair<Integer, Integer> pos) {
        super(world, pos);

        strength = 4;
        initiative = 4;
    }

    @Override
    public Pair<Integer, Integer> getNextPos() {
        Pair<Integer, Integer>[] directions = new Pair[]{
                new Pair<>(1, 0), new Pair<>(-1, 0), new Pair<>(0, 1), new Pair<>(0, -1),
                new Pair<>(2, 0), new Pair<>(-2, 0), new Pair<>(0, 2), new Pair<>(0, -2)
        };

        Random random = new Random();
        Pair<Integer, Integer> randomDirection = directions[random.nextInt(8)];

        Pair<Integer, Integer> afterPos = new Pair<>(pos.first + randomDirection.first,
                pos.second + randomDirection.second);

        if (afterPos.first >= world.board[0].length || afterPos.first < 0) {
            afterPos = new Pair<>(pos.first - randomDirection.first, afterPos.second);
        } else if (afterPos.second >= world.board.length || afterPos.second < 0) {
            afterPos = new Pair<>(afterPos.first, pos.second - randomDirection.second);
        }

        return afterPos;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(31, 244, 255));
    }

    @Override
    public boolean escaped() {
        Random random = new Random();
        int escapes = random.nextInt(100);
        if (escapes < ESCAPE_CHANCE) {
            return false;
        }

        // find free space and go there
        Pair<Integer, Integer> afterPos = findFreeSpace(pos);

        if (afterPos.first != -1) {
            move(afterPos);
        }
        else {
            world.board[pos.second][pos.first] = null;
            world.removeOrganism(this);
        }

        return true;
    }

    @Override
    public String getType() {
        return "Antelope";
    }
}
