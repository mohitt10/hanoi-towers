package game;

import java.util.Scanner;

public class Game {
    public static void mainGame() {
	Scanner input = new Scanner(System.in);
	
        boolean run = true;
	while (run) {
	    System.out.println("Enter number of disks: ");
	    int numDisks = input.nextInt();

	    Utils.clearScreen();

	    GameState currState = new GameState(numDisks);

	    while (!currState.isGameWon()) {
		Utils.printState(currState.getTowers(), numDisks);

		System.out.println("Enter rod number to move from (1 = left, 2 = middle, 3 = right): ");
		int fromRod = input.nextInt();
	    
		System.out.println("Enter rod number to move to (1 = left, 2 = middle, 3 = right): ");
		int toRod = input.nextInt();
		
		Utils.clearScreen();
		if (!currState.move(fromRod, toRod)) {
		    System.out.println("Invalid move\n");
		    continue;
		}
	    }

	    Utils.printState(currState.getTowers(), numDisks);
	    
	    System.out.println("Congratulations!!! You solved it!");
	    System.out.println("Do you want to play again? (y/n)");
	    String response = input.next();

	    if (response.toLowerCase().equals("n"))
		run = false;
	    else
		Utils.clearScreen();
	}
    }   
}
