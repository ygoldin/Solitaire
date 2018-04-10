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
			moveCardsBetweenTableauPiles(model, input);
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
			int suits = Card.Suit.values().length;
			int choice = getFoundationOrTableau(model, input, suits, "What foundation (1-" + suits + ")? ");
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
			int choice = getFoundationOrTableau(model, input, SolitaireModel.TABLEAU_SIZE,
					"What tableau pile (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
			if(!model.canMoveTopWasteCardToTableau(choice)) {
				System.out.println("Cannot move top waste card to tableau " + choice);
			} else {
				model.moveTopWasteCardToTableau(choice);
			}
		}
	}
	
	private static void moveCardsBetweenTableauPiles(SolitaireModel model, Scanner input) {
		int pile1 = getFoundationOrTableau(model, input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile do you want to move from (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		int pile2 = getFoundationOrTableau(model, input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile do you want to move to (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		System.out.println("How many cards do you want to move?");
		int cards = input.nextInt();
		input.nextLine();
		if(!model.moveCards(pile1, pile2, cards)) {
			System.out.println("Couldn't move cards");
		}
	}
	
	private static void moveTopTableauCardToFoundation(SolitaireModel model, Scanner input) {
		int tableau = getFoundationOrTableau(model, input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		int foundation = getFoundationOrTableau(model, input, Card.Suit.values().length,
				"What foundation (1-" + Card.Suit.values().length + ")?");
		//TODO
	}
	
	private static int getFoundationOrTableau(SolitaireModel model, Scanner input, int max,
			String message) {
		int choice = 0;
		while(choice < 1 || choice > max) {
			System.out.println(message);
			choice = input.nextInt();
			input.nextLine();
		}
		return choice;
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
