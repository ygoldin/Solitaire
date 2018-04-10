package gameplay;

import java.util.Scanner;

import setup.Card;

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
		if(choice == 1) {
			flipStockCard(model);
		} else if(choice == 2) {
			moveWasteToStock(model);
		} else if(choice == 3) {
			moveTopWasteCardToFoundation(model, input);
		} else if(choice == 4) {
			moveTopWasteCardToTableau(model, input);
		} else if(choice == 5) {
			
		} else {
			
		}
	}
	
	private static void flipStockCard(SolitaireModel model) {
		if(model.stockIsEmpty()) {
			System.out.println("Stock is empty");
		} else {
			model.moveTopStockCardToWaste();
		}
	}
	
	private static void moveWasteToStock(SolitaireModel model) {
		if(!model.stockIsEmpty()) {
			System.out.println("Stock not empty");
		} else if(model.wasteIsEmpty()) {
			System.out.println("Waste is empty");
		} else {
			model.moveWasteToStock();
		}
	}
	
	private static void moveTopWasteCardToFoundation(SolitaireModel model, Scanner input) {
		if(model.wasteIsEmpty()) {
			System.out.println("Waste is empty");
		} else {
			int choice = 0;
			int suits = Card.Suit.values().length;
			while(choice < 1 || choice > suits) {
				System.out.println("What foundation (1-" + suits + ")?");
				choice = input.nextInt();
				input.nextLine();
			}
			if(!model.canMoveTopWasteCardToFoundation(choice)) {
				System.out.println("Cannot move top waste card to foundation " + choice);
			} else {
				model.moveTopWasteCardToFoundation(choice);
			}
		}
	}
	
	private static void moveTopWasteCardToTableau(SolitaireModel model, Scanner input) {
		if(model.wasteIsEmpty()) {
			System.out.println("Waste is empty");
		} else {
			int choice = 0;
			while(choice < 1 || choice > SolitaireModel.TABLEAU_SIZE) {
				System.out.println("What tableau pile (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
				choice = input.nextInt();
				input.nextLine();
			}
			if(!model.canMoveTopWasteCardToTableau(choice)) {
				System.out.println("Cannot move top waste card to tableau " + choice);
			} else {
				model.moveTopWasteCardToTableau(choice);
			}
		}
	}
	
	//what kind of movement the client wants to do
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
