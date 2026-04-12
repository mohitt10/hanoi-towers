package game;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Stack<Integer>> towers;
    private int numDisks;

    public GameState(int numDisks) {
        this.numDisks = numDisks;
        towers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            towers.add(new Stack<>());
        }

        for (int i = numDisks; i >= 1; i--) {
            towers.get(0).push(i);
        }
    }

    public boolean isValidMove(int a, int b) {
        if (a < 1 || a > 3 || b < 1 || b > 3) {
            return false;
        }

        Stack<Integer> fromTower = towers.get(a - 1);
        Stack<Integer> toTower = towers.get(b - 1);

        if (fromTower.isEmpty()) {
            return false;
        }

        if (toTower.isEmpty()) {
            return true;
        }

        return fromTower.peek() < toTower.peek();
    }

    public boolean move(int a, int b) {
        if (isValidMove(a, b)) {
            Stack<Integer> fromTower = towers.get(a - 1);
            Stack<Integer> toTower = towers.get(b - 1);
            toTower.push(fromTower.pop());
            System.out.println("Moved disk from tower " + a + " to tower " + b);
            return true;
        }
        else {
            System.out.println("Error: Move Invalid!");
        }
        return false;
    }

    public boolean isGameWon() {
        return towers.get(2).size() == numDisks;
    }


    public List<Stack<Integer>> getTowers() {
        return towers;
    }

    public int getNumDisks() {
        return numDisks;
    }
}
