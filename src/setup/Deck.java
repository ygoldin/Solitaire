package setup;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import setup.Card.Suit;

/**
 * Deck represents a whole 52-card deck
 * @author Yael Goldin
 */
public class Deck extends SolitairePile {
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
		exceptionIfEmpty();
		int idx = rand.nextInt(cards.size());
		return cards.remove(idx);
	}
	
	/**
	 * finds the size of the deck
	 * 
	 * @return the number of cards in the deck
	 */
	public int size() {
		return cards.size();
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
