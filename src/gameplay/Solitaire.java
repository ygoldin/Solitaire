package gameplay;

import java.util.Scanner;

public class Solitaire {

	public static void main(String[] args) {
		do {
			SolitaireModel model = new SolitaireModel();
			Scanner input = new Scanner(System.in);
			System.out.println(model);
			while(!model.gameOver()) {
				oneMove(model, input);
			}
			input.close();
		} while(playAgain());

	}
	
	private static void oneMove(SolitaireModel model, Scanner input) {
		int choice = getMoveChoice(input);		
	}
	
	private static int getMoveChoice(Scanner input) {
		printOptions();
		int choice = input.nextInt();
		input.nextLine();
		while(choice < 1 || choice > 6) {
			System.out.println("Invalid choice");
			printOptions();
			choice = input.nextInt();
			input.nextLine();
		}
		return choice;
	}
	
	private static void printOptions() {
		System.out.println("What do you want to do? ");
		System.out.println("\tFlip the next stock card (1)");
		System.out.println("\tMove the waste back to the stock (2)");
		System.out.println("\tMove the top waste card to a foundation (3)");
		System.out.println("\tMove the top waste card to a tableau pile (4)");
		System.out.println("\tMove cards between tableau piles (5)");
		System.out.println("\tMove the top card on a tableau pile to a foundation (6)");
	}
	
	private static boolean playAgain() {
		return false;
	}

}
