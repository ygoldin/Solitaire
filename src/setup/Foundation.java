package setup;

import java.util.Stack;
import setup.Card.Suit;

/**
 * Foundation represents one of the four piles on which a whole suit is built up
 * @author Yael Goldin
 */
public class Foundation extends SolitairePile {
	private Stack<Card> cards;
	public final Suit foundationSuit;
	
	/**
	 * initializes a foundation
	 * 
	 * @param foundationSuit The suit of the foundation
	 */
	public Foundation(Suit foundationSuit) {
		cards = new Stack<>();
		this.foundationSuit = foundationSuit;
	}
	
	/**
	 * adds a card to the foundation
	 * 
	 * @param card The card to add
	 * @throws IllegalArgumentException if the card is null
	 * @throws IllegalArgumentException if the card doesn't match the foundation's suit
	 */
	public void addCard(Card card) {
		Card.checkNullCard(card);
		if(card.suit != foundationSuit) {
			throw new IllegalArgumentException("can only add cards of the right suit");
		}
		cards.push(card);
	}
	
	/**
	 * removes the top card of the foundation
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the foundation is empty
	 */
	public Card removeTopCard() {
		exceptionIfEmpty();
		return cards.pop();
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
