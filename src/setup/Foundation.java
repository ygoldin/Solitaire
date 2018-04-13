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
	 * @throws IllegalArgumentException if the card cannot be added to the foundation
	 */
	public void addCard(Card card) {
		if(!canAddCard(card)) {
			throw new IllegalArgumentException("cannot add this card");
		}
		cards.push(card);
	}
	
	/**
	 * checks if you can add the given card
	 * 
	 * @param card The card to add to the foundation
	 * @return true if:
	 * 		the foundation is not full, and
	 * 		the card matches the suit of the foundation, and
	 * 		the foundation is empty and the card is an ace, or
	 * 		the foundation is not empty and the card's value is one more than the top card currently
	 * @throws IllegalArgumentException if the card is null
	 */
	public boolean canAddCard(Card card) {
		Card.checkNullCard(card);
		if(isFull()) {
			return false;
		}
		boolean addAce = isEmpty() && card.value == Card.SMALLEST_VALUE;
		boolean addOther = (!isEmpty() && card.value == cards.peek().value + 1);
		return card.suit == foundationSuit && (addAce || addOther);
	}
	
	/**
	 * moves the top card of the foundation to the given tableau pile
	 * 
	 * @param pile The tableau pile to move the card to
	 * @throws IllegalStateException if the foundation is empty
	 * @throws IllegalArgumentException if it is illegal to move the top foundation card to the pile
	 */
	public void moveTopCard(TableauPile pile) {
		if(!canMoveTopCard(pile)) {
			throw new IllegalArgumentException("cannot move card to the pile");
		}
		pile.addVisibleCard(cards.pop());
	}
	
	/**
	 * checks if the top card can be moved to the given tableau pile
	 * 
	 * @param pile The pile to move the card to
	 * @return true if the move is legal, false otherwise
	 * @throws IllegalStateException if this is empty
	 */
	public boolean canMoveTopCard(TableauPile pile) {
		exceptionIfEmpty();
		return pile.canAddToPile(cards.peek());
	}
	
	/**
	 * checks if the foundation is full and no more cards can be added to it
	 * 
	 * @return true if it is, false otherwise
	 */
	public boolean isFull() {
		return cards.size() == Card.LARGEST_VALUE - Card.SMALLEST_VALUE + 1;
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	@Override
	public String toString() {
		String result = foundationSuit.toString().charAt(0) + "[";
		if(!isEmpty()) {
			result += cards.peek();
		}
		return result + "]";
	}
}
