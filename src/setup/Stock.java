package setup;

import java.util.Stack;

public class Stock extends SolitairePile {
	private Stack<Card> cards;
	private final Waste associatedWastePile;
	
	/**
	 * initializes a stock pile
	 * 
	 * @param cardDeck The deck with the cards that will randomly ordered in the stock
	 * @param associatedWaste The waste pile where all discarded cards from the stock will be placed
	 */
	public Stock(Deck cardDeck, Waste associatedWaste) {
		cards = new Stack<>();
		while(!cardDeck.isEmpty()) {
			cards.push(cardDeck.removeRandomCard());
		}
		associatedWastePile = associatedWaste;
	}
	
	/**
	 * removes the first card from the stock and adds it to the associated waste pile
	 * 
	 * @throws IllegalStateException if the stock is empty
	 */
	public void removeTopCard() {
		exceptionIfEmpty();
		associatedWastePile.addCard(cards.pop());
	}
	
	/**
	 * moves all cards from the waste pile back to the stock in their same original order
	 * 
	 * @throws IllegalStateException if the stock is not empty
	 * @throws IllegalStateException if the associated waste pile is empty
	 */
	public void moveCardsFromWaste() {
		if(!isEmpty()) {
			throw new IllegalStateException("cannot move cards onto stock unless it's empty");
		} else if(associatedWastePile.isEmpty()) {
			throw new IllegalStateException("waste pile is empty");
		}
		
		while(!associatedWastePile.isEmpty()) {
			cards.push(associatedWastePile.removeTopCard());
		}
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	@Override
	public String toString() {
		if(isEmpty()) {
			return "empty";
		}
		return "has cards";
	}
}
