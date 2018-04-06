package setup;

import java.util.Stack;

/**
 * Waste represents the waste pile of cards from the stock pile
 * @author Yael Goldin
 */
public class Waste extends SolitairePile {
	private Stack<Card> cards;
	
	/**
	 * initializes an empty waste pile
	 */
	public Waste() {
		cards = new Stack<>();
	}
	
	/**
	 * adds the card to the top of the waste
	 * 
	 * @param card The card to add
	 * @throws IllegalArgumentException if the card is null
	 */
	public void addCard(Card card) {
		Card.checkNullCard(card);
		cards.push(card);
	}
	
	/**
	 * peeks at the top card from the waste pile without removing it
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the waste pile is empty
	 */
	public Card peekAtTopCard() {
		exceptionIfEmpty();
		return cards.peek();
	}
	
	/**
	 * removes the top card from the waste pile
	 * 
	 * @return the removed card
	 * @throws IllegalStateException if the waste pile is empty
	 */
	public Card removeTopCard() {
		exceptionIfEmpty();
		return cards.pop();
	}
	
	/**
	 * moves the top card from the waste pile to the given tableau pile
	 * 
	 * @param pile the tableau pile to move the card to
	 * @throws IllegalStateException if the waste pile is empty
	 */
	public void moveTopCard(TableauPile pile) {
		exceptionIfEmpty();
		if(!pile.canAddToPile(cards.peek())) {
			throw new IllegalArgumentException("cannot move card to the pile");
		}
		pile.addVisibleCard(cards.pop());
	}
	
	/**
	 * moves the top card from the waste pile to the given foundation pile
	 * 
	 * @param pile the foundation pile to move the card to
	 * @throws IllegalStateException if the waste pile is empty
	 */
	public void moveTopCard(Foundation foundation) {
		exceptionIfEmpty();
		if(!foundation.canAddCard(cards.peek())) {
			throw new IllegalArgumentException("cannot move card to the foundation");
		}
		foundation.addCard(cards.pop());
	}
	
	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
