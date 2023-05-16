package po_java;

public abstract class Animal extends Organism {
    public Animal(World world, Pair<Integer, Integer> pos) {
        super(world);
    }

    public void update() {

    }

    public void handleCollision(Organism other) {

    }

    public abstract void draw();

    public abstract String getType();

    protected void move(Pair<Integer, Integer> dest) {

    }

    private void breed(Animal animal) {

    }

    private void fight(Organism organism) {

    }

    private boolean skipMove;

    private boolean didMove;
}
