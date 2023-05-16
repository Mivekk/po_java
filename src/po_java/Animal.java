package po_java;

import po_java.animals.*;

import javax.swing.*;

public abstract class Animal extends Organism {
    public Animal(World world, Pair<Integer, Integer> pos) {
        super(world);

        this.pos = pos;
        world.board[pos.second][pos.first] = this;
    }

    public void update() {
        age++;

        didMove = false;

        // skip movement
        if (skipMove) {
            skipMove = false;
            return;
        }

        // move animal
        Pair<Integer, Integer> afterPos = getNextPos();

        // space is occupied -> collision
        if (world.board[afterPos.second][afterPos.first] != null && pos != afterPos) {
            handleCollision(world.board[afterPos.second][afterPos.first]);
        }
        else {
            move(afterPos);
        }

        didMove = true;
    }

    public void handleCollision(Organism other) {
        if (this.getType().equals(other.getType())) {
            breed((Animal) other);
        } else if (other instanceof Animal) {
            fight(other);
        } else {
            Pair<Integer, Integer> afterPos = new Pair<>(other.getPos().first, other.getPos().second);

            other.handleCollision(this);

            if (pos.first >= 0) {
                move(afterPos);
            }
        }
    }

    @Override
    public abstract void draw(JButton tileButton);

    @Override
    public abstract String getType();

    protected void move(Pair<Integer, Integer> dest) {
        world.board[pos.second][pos.first] = null;

        pos.first = dest.first;
        pos.second = dest.second;

        world.board[pos.second][pos.first] = this;
    }

    private void breed(Animal animal) {
        Pair<Integer, Integer> newPos = findFreeSpace(pos);
        if (newPos.first == -1) {
            newPos = findFreeSpace(animal.getPos());
        }

        if (newPos.first != -1) {
            Animal newAnimal = null;

            if (this instanceof Wolf) {
                newAnimal = new Wolf(world, newPos);
            } else if (this instanceof Sheep) {
                newAnimal = new Sheep(world, newPos);
            } else if (this instanceof Fox) {
                newAnimal = new Fox(world, newPos);
            } else if (this instanceof Turtle) {
                newAnimal = new Turtle(world, newPos);
            } else if (this instanceof Antelope) {
                newAnimal = new Antelope(world, newPos);
            }

            world.addOrganism(newAnimal);
        }

        if (!animal.didMove) {
            animal.skipMove = true;
        }
    }

    private void fight(Organism organism) {
        if (strength >= organism.getStrength()) {
            Pair<Integer, Integer> afterPos = new Pair<>(organism.getPos().first, organism.getPos().second);

            if (organism.escaped()) {
                move(afterPos);
                return;
            }

            world.board[afterPos.second][afterPos.first] = null;
            world.removeOrganism(organism);

            move(afterPos);
        } else if (strength < organism.getStrength()) {

            world.board[pos.second][pos.first] = null;
            world.removeOrganism(this);
        }
    }

    private boolean skipMove;

    private boolean didMove = false;
}
