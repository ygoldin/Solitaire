package setup;

import java.util.LinkedList;
import java.util.List;

/**
 * Waste represents the waste pile of cards from the stock pile
 * @author Yael Goldin
 */
public class Waste extends SolitairePile {
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
		exceptionIfEmpty();
		return cards.remove(cards.size() - 1);
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
