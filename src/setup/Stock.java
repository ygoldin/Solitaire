package setup;

import java.util.LinkedList;
import java.util.Queue;

public class Stock extends SolitairePile {
	private Queue<Card> cards;
	
	public Stock(Deck cardDeck) {
		cards = new LinkedList<>();
		while(!cardDeck.isEmpty()) {
			cards.add(cardDeck.removeRandomCard());
		}
	}
	
	/**
	 * removes the first card from the stock
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the stock is empty
	 */
	public Card removeTopCard() {
		exceptionIfEmpty();
		return cards.remove();
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
