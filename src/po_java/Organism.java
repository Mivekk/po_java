package po_java;

import java.util.Random;

public abstract class Organism {
    protected World world;

    protected int strength;

    protected int initiative;

    protected int age;

    protected Pair<Integer, Integer> pos;

    public Organism(World world) {

    }

    public abstract void update();
    public abstract void handleCollision(Organism other);
    public abstract void draw();

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
            if (afterPos.second >= world.board[0].length ||
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

    }

    public int getStrength() {
        return strength;
    }
    int getInitiative() {
        return initiative;
    }
    int getAge() {
        return age;
    }
    Pair<Integer, Integer> getPos() {
        return pos;
    }

    Pair<Integer, Integer> findFreeSpace(Pair<Integer, Integer> startPos) {
        return new Pair<>(-1, -1);
    }
}
