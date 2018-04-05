package gameplay;

import setup.*;

/**
 * SolitaireModel can be used to model a game of solitaire
 * @author Yael Goldin
 */
public class SolitaireModel {
	private Stock stock;
	private Waste waste;
	private TableauPile[] tableau;
	private Foundation[] foundations;
	
	private static final int TABLEAU_SIZE = 7;
	
	/**
	 * initializes the solitaire model
	 */
	public SolitaireModel() {
		Deck startingDeck = new Deck();
		
		tableau = new TableauPile[TABLEAU_SIZE];
		for(int i = 0; i < TABLEAU_SIZE; i++) {
			tableau[i] = new TableauPile(startingDeck, i + 1);
		}
		
		waste = new Waste();
		stock = new Stock(startingDeck, waste);
		
		Card.Suit[] suits = setup.Card.Suit.values();
		foundations = new Foundation[suits.length];
		for(int i = 0; i < suits.length; i++) {
			foundations[i] = new Foundation(suits[i]);
		}
	}
}
