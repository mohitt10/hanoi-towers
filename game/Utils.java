package game;

import java.util.Collections;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Utils {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printState(List<Stack<Integer>> towers, int numDisks) {
	int height = numDisks + 1;
	int maxWidth = 2 * numDisks + 1;

	List<List<Integer>> towersCopy = new ArrayList<>();
	for (int i = 0; i < 3; i++) {
	    towersCopy.add(new ArrayList<>());

	    for (int e : towers.get(i))
		towersCopy.get(i).add(e);

	    Collections.reverse(towersCopy.get(i));
	}
	
	List<Iterator<Integer>> it = new ArrayList<>();
	for (int i = 0; i < 3; i++)
	    it.add(towersCopy.get(i).iterator());
	
	for (int lineNumber = 1; lineNumber <= height; lineNumber++) {
	    for (int rod = 0; rod < 3; rod++) {
		System.out.print(" ");

		int inThisRod = towers.get(rod).size();
		int currWidth = 1;
		char c = '|';
		
		if (lineNumber > height - inThisRod) {
		    currWidth = 2 * it.get(rod).next() + 1;
		    c = '*';
		}

		int numSpace = (maxWidth - currWidth) / 2;
		for (int i = 0; i < numSpace; i++)
		    System.out.print(" ");

		for (int i = 0; i < currWidth; i++)
		    System.out.print(c);
		
		for (int i = 0; i < numSpace; i++)
		    System.out.print(" ");		
	    }
	    System.out.println();
	}
    }
}
