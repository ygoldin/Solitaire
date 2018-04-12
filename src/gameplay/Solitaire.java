package gameplay;

import java.util.Scanner;

import setup.Card;

/**
 * Solitaire can be used to control/view a text-based interface of the game of solitaire
 * @author Yael Goldin
 */
public class Solitaire {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		do {
			SolitaireModel model = new SolitaireModel();
			while(!model.gameOver()) {
				System.out.println("*****");
				System.out.println(model);
				System.out.println("*****");
				oneMove(model, input);
				System.out.println();
			}
		} while(playAgain(input));
		input.close();
	}
	
	//performs one move in the game
	private static void oneMove(SolitaireModel model, Scanner input) {
		int choice = getMoveChoice(input);
		if(choice == 1) {
			flipStockCard(model);
		} else if(choice == 2) {
			moveWasteToStock(model);
		} else if(choice == 3) {
			moveTopWasteCardToFoundation(model);
		} else if(choice == 4) {
			moveTopWasteCardToTableau(model, input);
		} else if(choice == 5) {
			moveCardsBetweenTableauPiles(model, input);
		} else if(choice == 6){
			moveTopTableauCardToFoundation(model, input);
		} else {
			moveTopFoundationCardToTableau(model, input);
		}
	}
	
	//flips the stock card onto the waste
	private static void flipStockCard(SolitaireModel model) {
		if(model.stockIsEmpty()) {
			System.out.println("Stock is empty");
		} else {
			model.moveTopStockCardToWaste();
		}
	}
	
	//moves all of the cards in the waste back onto the stock
	private static void moveWasteToStock(SolitaireModel model) {
		if(!model.stockIsEmpty()) {
			System.out.println("Stock not empty");
		} else if(model.wasteIsEmpty()) {
			System.out.println("Waste is empty");
		} else {
			model.moveWasteToStock();
		}
	}
	
	//moves the top waste card to the relative foundation
	private static void moveTopWasteCardToFoundation(SolitaireModel model) {
		if(model.wasteIsEmpty()) {
			System.out.println("Waste is empty");
		} else {
			if(!model.canMoveTopWasteCardToFoundation()) {
				System.out.println("Cannot move top waste card to foundation");
			} else {
				model.moveTopWasteCardToFoundation();
			}
		}
	}
	
	//moves the top waste card to a tableau pile
	private static void moveTopWasteCardToTableau(SolitaireModel model, Scanner input) {
		if(model.wasteIsEmpty()) {
			System.out.println("Waste is empty");
		} else {
			int choice = getFoundationOrTableau(input, SolitaireModel.TABLEAU_SIZE,
					"What tableau pile (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
			if(!model.canMoveTopWasteCardToTableau(choice)) {
				System.out.println("Cannot move top waste card to tableau " + choice);
			} else {
				model.moveTopWasteCardToTableau(choice);
			}
		}
	}
	
	//moves cards from one tableau pile to another
	private static void moveCardsBetweenTableauPiles(SolitaireModel model, Scanner input) {
		int pile1 = getFoundationOrTableau(input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile do you want to move from (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		int pile2 = getFoundationOrTableau(input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile do you want to move to (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		System.out.print("How many cards do you want to move? ");
		int cards = input.nextInt();
		input.nextLine();
		if(!model.moveCardsWithinTableau(pile1, pile2, cards)) {
			System.out.println("Couldn't move cards");
		}
	}
	
	//moves the top card on a tableau pile to the relative foundation
	private static void moveTopTableauCardToFoundation(SolitaireModel model, Scanner input) {
		int tableau = getFoundationOrTableau(input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		if(!model.canMoveTableauCardToFoundation(tableau)) {
			System.out.println("Couldn't move card");
		} else {
			model.moveTableauCardToFoundation(tableau);
		}
	}
	
	//moves the top card on a foundation to a tableau pile
	private static void moveTopFoundationCardToTableau(SolitaireModel model, Scanner input) {
		int foundation = getFoundationOrTableau(input, Card.Suit.values().length,
				"What foundation (1-" + Card.Suit.values().length + ")?");
		int tableau = getFoundationOrTableau(input, SolitaireModel.TABLEAU_SIZE,
				"What tableau pile (1-" + SolitaireModel.TABLEAU_SIZE + ")?");
		if(!model.canMoveCardFromFoundationToTableau(foundation, tableau)) {
			System.out.println("Cannot move card from foundation to tableau");
		} else {
			model.moveCardFromFoundationToTableau(foundation, tableau);
		}
	}
	
	//asks the user for a tableau/foundation index
	private static int getFoundationOrTableau(Scanner input, int max, String message) {
		int choice = 0;
		while(choice < 1 || choice > max) {
			System.out.print(message + " ");
			choice = input.nextInt();
			input.nextLine();
		}
		return choice - 1; //0-based indexing
	}
	
	//returns what kind of movement the client wants to do
	private static int getMoveChoice(Scanner input) {
		String[] options = {"Flip the next stock card", "Move the waste back to the stock",
				"Move the top waste card to a foundation", "Move the top waste card to a tableau pile",
				"Move cards between tableau piles", "Move the top card on a tableau pile to a foundation",
				"Move the top card on a foundation to a tableau pile"
		};
		printOptions(options);
		int choice = input.nextInt();
		input.nextLine();
		while(choice < 1 || choice > options.length) {
			System.out.println("Invalid choice");
			printOptions(options);
			choice = input.nextInt();
			input.nextLine();
		}
		return choice;
	}
	
	//prints the move options
	private static void printOptions(String[] options) {
		System.out.println("What do you want to do? ");
		for(int i = 0; i < options.length; i++) {
			System.out.println("\t" + options[i] + " (" + (i+1) + ")");
		}
	}
	
	//returns if the user wants to play again
	private static boolean playAgain(Scanner input) {
		System.out.println("Game over! Play again? (y/n): ");
		return input.nextLine().toLowerCase().charAt(0) == 'y';
	}

}
