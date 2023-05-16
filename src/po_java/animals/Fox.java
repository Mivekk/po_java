package po_java.animals;

import po_java.Animal;
import po_java.Pair;
import po_java.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Fox extends Animal {
    public Fox(World world, Pair<Integer, Integer> pos) {
        super(world, pos);

        strength = 3;
        initiative = 7;
    }


    @Override
    public Pair<Integer, Integer> getNextPos() {
        Random random = new Random();
        Pair<Integer, Integer>[] directions = new Pair[] { new Pair<>(1, 0), new Pair<>(-1, 0), new Pair<>(0, 1), new Pair<>(0, -1) };

        int randomNumber = random.nextInt(4);
        Pair<Integer, Integer> randomDirection;
        Pair<Integer, Integer> afterPos;

        int curI = 0, maxI = 4;
        do {
            randomDirection = directions[randomNumber];

            afterPos = new Pair( pos.first + randomDirection.first, pos.second + randomDirection.second );

            if (afterPos.first >= world.board[0].length || afterPos.first < 0) {
                afterPos.first = pos.first - randomDirection.first;
            } else if (afterPos.second >= world.board.length || afterPos.second < 0) {
                afterPos.second = pos.second - randomDirection.second;
            }
            curI++;
        } while (world.board[afterPos.second][afterPos.first] != null && curI <= maxI &&
                world.board[afterPos.second][afterPos.first].getStrength() > strength);

        if (curI > maxI) {
            return pos;
        }

        return afterPos;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(new Color(255, 165, 31));
    }

    @Override
    public String getType() {
        return "Fox";
    }
}
