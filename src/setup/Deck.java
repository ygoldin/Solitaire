package setup;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import setup.Card.Suit;

/**
 * Deck represents a whole 52-card deck
 * @author Yael Goldin
 */
public class Deck {
	private List<Card> cards;
	private Random rand;
	
	/**
	 * initializes a deck with all 52 cards
	 */
	public Deck() {
		cards = new LinkedList<>();
		for(int value = Card.SMALLEST_VALUE; value <= Card.LARGEST_VALUE; value++) {
			for(Suit suitType : Card.Suit.values()) {
				cards.add(new Card(value, suitType));
			}
		}
		rand = new Random();
	}
	
	/**
	 * removes a random card from the deck
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the deck is empty
	 */
	public Card removeRandomCard() {
		checkNotEmpty();
		int idx = rand.nextInt(cards.size());
		return cards.remove(idx);
	}
	
	/**
	 * removes the first card from the deck
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the deck is empty
	 */
	public Card removeFirstCard() {
		checkNotEmpty();
		return cards.remove(0);
	}
	
	//throws exception if the deck is empty and someone wants to remove something
	private void checkNotEmpty() {
		if(isEmpty()) {
			throw new IllegalStateException("no cards in deck");
		}
	}
	
	/**
	 * checks if the deck is empty
	 * 
	 * @return true if it is, false otherwise
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	/**
	 * adds the card to the end of the deck
	 * 
	 * @param card The card to add
	 * @throws IllegalArgumentException if the card is null
	 */
	public void addCard(Card card) {
		if(card == null) {
			throw new IllegalArgumentException("null card");
		}
		cards.add(card);
	}
}
