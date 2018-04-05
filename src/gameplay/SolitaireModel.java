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
	
	public static final int TABLEAU_SIZE = 7;
	
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
		
		Card.Suit[] suits = Card.Suit.values();
		foundations = new Foundation[suits.length];
		for(int i = 0; i < suits.length; i++) {
			foundations[i] = new Foundation(suits[i]);
		}
	}
	
	/**
	 * moves all of the cards from the waste pile into the stock pile
	 * 
	 * @throws IllegalStateException if the game is over
	 * @throws IllegalStateException if the stock isn't empty or the waste is empty
	 */
	public void moveWasteToStock() {
		exceptionIfGameIsOver();
		if(!stockIsEmpty()) {
			throw new IllegalStateException("stock is not empty");
		} else if(wasteIsEmpty()) {
			throw new IllegalStateException("waste is empty");
		}
		stock.moveCardsFromWaste();
	}
	
	/**
	 * moves the top card from the stock pile onto the waste pile
	 * 
	 * @throws IllegalStateException if the game is over
	 * @throws IllegalStateException if the stock is empty
	 */
	public void moveTopStockCardToWaste() {
		exceptionIfGameIsOver();
		if(stockIsEmpty()) {
			throw new IllegalStateException("stock is empty");
		}
		stock.removeTopCard();
	}
	
	/* ************************check if any pile is empty*************************************** */
	
	/**
	 * checks if the stock is empty
	 * 
	 * @return true if it is, false otherwise
	 */
	public boolean stockIsEmpty() {
		return stock.isEmpty();
	}
	
	/**
	 * checks if the waste pile is empty
	 * 
	 * @return true if it is, false otherwise
	 */
	public boolean wasteIsEmpty() {
		return waste.isEmpty();
	}
	
	/**
	 * checks if the given tableau pile is empty
	 * 
	 * @param index The index of which tableau pile to look at
	 * @return true if the pile is empty, false otherwise
	 * @throws IllegalArgumentException if index is outside of the range [0, TABLEAU_SIZE] inclusive
	 */
	public boolean tableauPileIsEmpty(int index) {
		if(index < 0 || index > tableau.length) {
			throw new IllegalArgumentException("invalid tableau pile index");
		}
		return tableau[index].isEmpty();
	}
	
	/**
	 * checks if the given foundation is empty
	 * 
	 * @param index The index of which foundation to look at
	 * @return true if the foundation is empty, false otherwise
	 * @throws IllegalArgumentException if index is outside of the range [0, 3] inclusive
	 */
	public boolean foundationIsEmpty(int index) {
		if(index < 0 || index > foundations.length) {
			throw new IllegalArgumentException("invalid foundation index");
		}
		return foundations[index].isEmpty();
	}
	
	/* ***************************************************************************************** */
	
	/**
	 * checks if the game is over
	 * 
	 * @return true if all of the foundations are filled, false otherwise
	 */
	public boolean gameOver() {
		for(Foundation foundation : foundations) {
			if(!foundation.isFull()) {
				return false;
			}
		}
		return true;
	}
	
	//throws exception if a move is attempted when the game is over
	private void exceptionIfGameIsOver() {
		if(gameOver()) {
			throw new IllegalStateException("game is over");
		}
	}
}
