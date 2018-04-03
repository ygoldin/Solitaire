package setup;

import java.util.LinkedList;
import java.util.Queue;

public class Stock {
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
	public Card removeFirstCard() {
		checkNotEmpty();
		return cards.remove();
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	//throws exception if the deck is empty and someone wants to remove something
	private void checkNotEmpty() {
		if(isEmpty()) {
			throw new IllegalStateException("no cards in deck");
		}
	}
}
