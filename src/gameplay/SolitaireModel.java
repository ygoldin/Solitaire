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
		}
		checkEmptyWaste();
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
	
	/**
	 * moves the top card from the waste pile to the matching foundation
	 * 
	 * @throws IllegalStateException if the waste pile is empty
	 * @throws IllegalArgumentException if the top card cannot be moved to the foundation
	 */
	public void moveTopWasteCardToFoundation() {
		if(!canMoveTopWasteCardToFoundation()) {
			throw new IllegalArgumentException("cannot move top waste card to foundation");
		}
		Card topCard = waste.removeTopCard();
		int foundationIndex = foundationForCardSuit(topCard);
		foundations[foundationIndex].addCard(topCard);		
	}
	
	/**
	 * moves the top card from the waste pile to the given tableau pile
	 * 
	 * @param tableauIndex Which tableau pile to move the card to
	 * @throws IllegalStateException if the waste pile is empty
	 * @throws IllegalArgumentException if given an invalid tableau index
	 * @throws IllegalArgumentException if the top card cannot be moved to the tableau
	 */
	public void moveTopWasteCardToTableau(int tableauIndex) {
		if(!canMoveTopWasteCardToTableau(tableauIndex)) {
			throw new IllegalArgumentException("cannot move top waste card to tableau pile");
		}
		tableau[tableauIndex].addVisibleCard(waste.removeTopCard());		
	}
	
	/* ************************check if top waste card can be moved***************************** */
	
	/**
	 * checks if it is legal to move the top card from the waste pile to the matching foundation
	 * 
	 * @return true if the top card can be moved to the foundation of its suit, false otherwise
	 * @throws IllegalStateException if the waste pile is empty
	 */
	public boolean canMoveTopWasteCardToFoundation() {
		checkEmptyWaste();
		Card topCard = waste.peekAtTopCard();
		int foundationIndex = foundationForCardSuit(topCard);
		return foundations[foundationIndex].canAddCard(topCard);
	}
	
	/**
	 * checks if it is legal to move the top card from the waste pile to the given tableau pile
	 * 
	 * @param tableauIndex Which tableau pile to move the card to
	 * @return true if the top card can be moved to the given tableau pile, false otherwise
	 * @throws IllegalStateException if the waste pile is empty
	 * @throws IllegalArgumentException if given an invalid tableau index
	 */
	public boolean canMoveTopWasteCardToTableau(int tableauIndex) {
		checkEmptyWaste();
		checkInvalidTableauIndex(tableauIndex);
		return tableau[tableauIndex].canAddToPile(waste.peekAtTopCard());
	}
	
	/* ************************find which foundation a card fits in***************************** */
	
	//returns the index representing the foundation matching the card's suit
	private int foundationForCardSuit(Card card) {
		for(int i = 0; i < foundations.length; i++) {
			if(card.suit == foundations[i].foundationSuit) {
				return i;
			}
		}
		throw new IllegalStateException("non-existent suit");
	}
	
	/**
	 * moves the top card from the given foundation to the given tableau pile
	 * 
	 * @param foundationIndex The foundation pile to move from
	 * @param tableauIndex The tableau pile to move to
	 * @throws IllegalArgumentException if given an invalid foundation or tableau index
	 * @throws IllegalArgumentException if it is not legal to perform that move
	 */
	public void moveCardFromFoundationToTableau(int foundationIndex, int tableauIndex) {
		if(!canMoveCardFromFoundationToTableau(foundationIndex, tableauIndex)) {
			throw new IllegalStateException("cannot move card from foundation to tableau");
		}
		Foundation foundation = foundations[foundationIndex];
		TableauPile pile = tableau[tableauIndex];
		foundation.moveTopCard(pile);
	}
	
	/**
	 * checks if it's legal to move the top card from the given foundation to the given tableau pile
	 * 
	 * @param foundationIndex The foundation pile to move from
	 * @param tableauIndex The tableau pile to move to
	 * @return true if the foundation isn't empty and the move is legal, false otherwise
	 * @throws IllegalArgumentException if given an invalid foundation or tableau index
	 */
	public boolean canMoveCardFromFoundationToTableau(int foundationIndex, int tableauIndex) {
		checkInvalidFoundationIndex(foundationIndex);
		checkInvalidTableauIndex(tableauIndex);
		if(foundationIsEmpty(foundationIndex)) {
			return false;
		}
		Foundation foundation = foundations[foundationIndex];
		TableauPile pile = tableau[tableauIndex];
		return foundation.canMoveTopCard(pile);
	}
	
	/* ************************move tableau cards around**************************************** */
	
	/**
	 * moves the given number of cards from one tableau pile to the other
	 * 
	 * @param startTableauIndex The tableau pile to start at
	 * @param endTableauIndex The tableau pile to move to
	 * @return true if the move was successful, false otherwise
	 * @throws IllegalArgumentException if given an invalid start or end tableau index
	 */
	public boolean moveCardsWithinTableau(int startTableauIndex, int endTableauIndex) {
		checkInvalidTableauIndex(startTableauIndex);
		checkInvalidTableauIndex(endTableauIndex);
		if(startTableauIndex == endTableauIndex) {
			return false;
		}
		TableauPile start = tableau[startTableauIndex];
		TableauPile end = tableau[endTableauIndex];
		for (int cardsToMove = 1; cardsToMove <= start.numVisibleCards(); cardsToMove++) {
			if (end.addStackOfCards(start, cardsToMove)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * moves the top card from the given tableau pile to the foundation matching the card's suit
	 * @param tableauIndex The tableau pile to move from
	 * @throws IllegalArgumentException if given an invalid tableau index
	 * @throws IllegalArgumentException if the move is illegal
	 */
	public void moveTableauCardToFoundation(int tableauIndex) {
		if(!canMoveTableauCardToFoundation(tableauIndex)) {
			throw new IllegalArgumentException("can't move card to foundation");
		}
		TableauPile pile = tableau[tableauIndex];
		int foundationIndex = foundationForCardSuit(pile.peekAtTopCard());
		pile.moveTopCard(foundations[foundationIndex]);
	}
	
	/**
	 * checks if it's legal to move the top card from the given tableau pile to the foundation matching
	 * the card's suit
	 * @param tableauIndex The tableau pile to move from
	 * @return true if the pile is not empty and the card can be moved to the foundation, false otherwise
	 * @throws IllegalArgumentException if given an invalid tableau index
	 */
	public boolean canMoveTableauCardToFoundation(int tableauIndex) {
		checkInvalidTableauIndex(tableauIndex);
		if(tableauPileIsEmpty(tableauIndex)) {
			return false;
		}
		TableauPile pile = tableau[tableauIndex];
		int foundationIndex = foundationForCardSuit(pile.peekAtTopCard());
		return pile.canMoveTopCardToFoundation(foundations[foundationIndex]);		
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
		checkInvalidTableauIndex(index);
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
		checkInvalidFoundationIndex(index);
		return foundations[index].isEmpty();
	}
	
	/* ********************************exception checks***************************************** */
	
	//throws exception if the waste is empty
	private void checkEmptyWaste() {
		if(wasteIsEmpty()) {
			throw new IllegalStateException("waste is empty");
		}
	}
	
	//throws exception for invalid tableau pile index
	private void checkInvalidTableauIndex(int index) {
		if(index < 0 || index > tableau.length) {
			throw new IllegalArgumentException("invalid tableau pile index");
		}
	}
	
	//throws exception for invalid foundation pile index
	private void checkInvalidFoundationIndex(int index) {
		if(index < 0 || index > foundations.length) {
			throw new IllegalArgumentException("invalid foundation index");
		}
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
	
	@Override
	public String toString() {
		String result = "Stock: " + stock;
		result += "\nWaste: " + waste;
		result += "\nFoundations:";
		for(Foundation f : foundations) {
			result += "\n\t" + f;
		}
		result += "\nTableau:";
		for(TableauPile t : tableau) {
			result += "\n\t" + t;
		}
		return result;
	}
}
