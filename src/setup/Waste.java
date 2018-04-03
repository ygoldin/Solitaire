package setup;

import java.util.LinkedList;
import java.util.List;

/**
 * Waste represents the waste pile of cards from the stock pile
 * @author Yael Goldin
 */
public class Waste {
	private List<Card> cards;
	
	/**
	 * initializes an empty waste pile
	 */
	public Waste() {
		cards = new LinkedList<>();
	}
	
	/**
	 * adds the card to the top of the waste
	 * 
	 * @param card The card to add
	 * @throws IllegalArgumentException if the card is null
	 */
	public void addCard(Card card) {
		Card.checkNullCard(card);
		cards.add(card);
	}
	
	/**
	 * removes the top card from the waste pile
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the waste pile is empty
	 */
	public Card removeTopCard() {
		if(isEmpty()) {
			throw new IllegalStateException("empty waste pile");
		}
		return cards.remove(cards.size() - 1);
	}
	
	/**
	 * checks if the waste pile is empty
	 * 
	 * @return true if it is empty, false otherwise
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
