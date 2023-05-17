package po_java;

import javax.swing.*;
import java.util.Random;

public abstract class Organism {
    protected World world;

    protected int strength;

    protected int initiative;

    protected int age;

    protected Pair<Integer, Integer> pos;

    public Organism(World world) {
        this.world = world;
        this.strength = 0;
        this.age = 0;
        this.initiative = 0;
        pos = new Pair<>(0, 0);
    }

    public abstract void update();
    public abstract void handleCollision(Organism other);
    public abstract void draw(JButton tileButton);

    public boolean bounceAttack(Organism organism) { return false; };
    public boolean escaped() { return false; };

    public String getType() {
        return "Organism";
    };

    public Pair<Integer, Integer> getNextPos() {
        Pair<Integer, Integer>[] directions = new Pair[]{
                new Pair<>(1, 0),
                new Pair<>(-1, 0),
                new Pair<>(0, 1),
                new Pair<>(0, -1)
        };

        Random rand = new Random();
        Pair<Integer, Integer> randomDirection = directions[rand.nextInt(4)];

        Pair<Integer, Integer> afterPos = new Pair<>(pos.first + randomDirection.first, pos.second + randomDirection.second);

        if (afterPos.first >= world.board[0].length ||
                afterPos.first < 0) {

            afterPos.first = pos.first - randomDirection.first;
            if (afterPos.first >= world.board[0].length ||
                    afterPos.first < 0) {
                afterPos = pos;
            }
        }
        else if (afterPos.second >= world.board.length ||
                afterPos.second < 0) {

            afterPos.second = pos.second - randomDirection.second;
            if (afterPos.second >= world.board.length ||
                    afterPos.second < 0) {
                afterPos = pos;
            }
        }

        return afterPos;
    };

    public void setStrength(int newStrength) {
        strength = newStrength;
    }

    public void setInitiative(int newInitiative) {
        initiative = newInitiative;
    }

    public void setAge(int newAge) {
        age = newAge;
    }

    public void setPos(Pair<Integer, Integer> newPos) {
        pos = newPos;
    }

    public int getStrength() {
        return strength;
    }
    public int getInitiative() {
        return initiative;
    }
    public int getAge() {
        return age;
    }
    public Pair<Integer, Integer> getPos() {
        return pos;
    }

    public Pair<Integer, Integer> findFreeSpace(Pair<Integer, Integer> startPos) {
        Pair<Integer, Integer>[] directions = new Pair[] {
                new Pair<>(1, 0),
                new Pair<>(-1, 0),
                new Pair<>(0, 1),
                new Pair<>(0, -1)
        };

        Pair<Integer, Integer> freePos = new Pair<>(-1, -1);

        for (Pair<Integer, Integer> dir : directions) {
            Pair<Integer, Integer> afterPos = new Pair<>(
                    startPos.first + dir.first,
                    startPos.second + dir.second
            );

            if (afterPos.first >= world.board[0].length ||
                    afterPos.first < 0 || afterPos.second < 0 ||
                    afterPos.second >= world.board.length) {
                continue;
            }

            Organism space = world.board[afterPos.second][afterPos.first];
            if (space == null) {
                freePos = new Pair<>(afterPos.first, afterPos.second);
                break;
            }
        }

        return freePos;
    }
}
