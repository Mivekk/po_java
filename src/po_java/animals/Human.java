package po_java.animals;

import po_java.Animal;
import po_java.Organism;
import po_java.Pair;
import po_java.World;

public class Human extends Animal {
    public Human(World world, Pair<Integer, Integer> pos) {
        super(world, pos);
    }

    @Override
    public void update() {

    }

    @Override
    public void handleCollision(Organism other) {

    }

    @Override
    public void draw() {

    }

    @Override
    public boolean bounceAttack(Organism organism) {
        return false;
    }

    @Override
    public boolean escaped() {
        return false;
    }

    @Override
    public String getType() {
        return "Human";
    }
}