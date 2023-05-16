package po_java.animals;

import po_java.Animal;
import po_java.Pair;
import po_java.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Human extends Animal {
    private final int ARROW_UP = 38;

    private final int ARROW_DOWN = 40;

    private final int ARROW_LEFT = 37;

    private final int ARROW_RIGHT = 39;

    public Human(World world, Pair<Integer, Integer> pos) {
        super(world, pos);
        abilityCooldown = -1;
        abilityDuration = -1;
        abilityActivated = false;
        nextMove = 0;
        strength = 5;
        initiative = 4;
    }

    public void setNextMove(int arrowCode) {
        nextMove = arrowCode;
    }

    public int abilityCooldown;
    public int abilityDuration;
    public boolean abilityActivated;

    @Override
    public Pair<Integer, Integer> getNextPos() {
        Pair<Integer, Integer> afterPos = new Pair<>(pos.first, pos.second);

        switch (nextMove) {
            case ARROW_UP:
                afterPos.second--;

                if (useAbility()) {
                    afterPos.second--;
                }
                break;
            case ARROW_DOWN:
                afterPos.second++;

                if (useAbility()) {
                    afterPos.second++;
                }
                break;
            case ARROW_RIGHT:
                afterPos.first++;

                if (useAbility()) {
                    afterPos.first++;
                }
                break;
            case ARROW_LEFT:
                afterPos.first--;

                if (useAbility()) {
                    afterPos.first--;
                }
                break;
        }

        nextMove = 0;

        if (afterPos.first < world.board[0].length &&
                afterPos.first >= 0 && afterPos.second >= 0 &&
                afterPos.second < world.board.length) {

            return afterPos;
        }

        return pos;
    }

    @Override
    public void draw(JButton tileButton) {
        tileButton.setBackground(Color.BLACK);
    }

    @Override
    public String getType() {
        return "Human";
    }

    private boolean useAbility() {
        Random random = new Random();
        if (abilityDuration > 0) {
            if (abilityDuration < 2) {
                return random.nextInt(2) == 1;
            }
            else {
                return true;
            }
        }

        return false;
    }

    private int nextMove;
}