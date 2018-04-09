package setup;

import java.util.LinkedList;
import java.util.Queue;

public class Stock extends SolitairePile {
	private Queue<Card> cards;
	private final Waste associatedWastePile;
	
	/**
	 * initializes a stock pile
	 * 
	 * @param cardDeck The deck with the cards that will randomly ordered in the stock
	 * @param associatedWaste The waste pile where all discarded cards from the stock will be placed
	 */
	public Stock(Deck cardDeck, Waste associatedWaste) {
		cards = new LinkedList<>();
		while(!cardDeck.isEmpty()) {
			cards.add(cardDeck.removeRandomCard());
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
		associatedWastePile.addCard(cards.remove());
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
			cards.add(associatedWastePile.removeTopCard());
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
