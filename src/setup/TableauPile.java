package setup;

import java.util.Stack;

public class TableauPile extends SolitairePile {
	private Stack<Card> cards;
	private int visibleCards;
	
	/**
	 * sets up a tableau pile with the given amount of cards from the given deck
	 * 
	 * @param deck The deck to pick cards from
	 * @param initialCardsNum The amount of cards to place in the pile
	 * @throws IllegalArgumentException if the deck is null or does not have at least 'initialCardsNum' cards
	 * @throws IllegalArgumentException if the initialCardsNum < 1
	 */
	public TableauPile(Deck deck, int initialCardsNum) {
		if(deck == null || deck.size() < initialCardsNum) {
			throw new IllegalArgumentException("invalid deck");
		} else if(initialCardsNum < 1) {
			throw new IllegalArgumentException("need at least one card to start");
		}
		cards = new Stack<>();
		for(int i = 0; i < initialCardsNum; i++) {
			cards.push(deck.removeRandomCard());
		}
		visibleCards = 1;
	}
	
	/**
	 * adds a visible card to the top of the pile
	 * 
	 * @param newCard the card to add
	 * @throws IllegalArgumentException if the card is null
	 * @throws IllegalArgumentException if the card cannot be added to the pile
	 */
	public void addVisibleCard(Card newCard) {
		Card.checkNullCard(newCard);
		if(!canAddToPile(newCard)) {
			throw new IllegalArgumentException("cannot add card to pile");
		}
		cards.add(newCard);
		visibleCards++;
	}
	
	/**
	 * adds the given number of cards from the other pile onto this one
	 * does nothing if it was not a valid move
	 * 
	 * @param otherPile The pile to move cards from
	 * @param numberOfCards The number of cards to move from it to this one
	 * @throws IllegalArgumentException if the other pile is null or does not have enough cards to move
	 * @throws IllegalArgumentException if numberOfCards < 1
	 * @return true if the move was successful, false otherwise
	 */
	public boolean addStackOfCards(TableauPile otherPile, int numberOfCards) {
		if(otherPile == null || otherPile.visibleCards < numberOfCards) {
			throw new IllegalArgumentException("other pile not valid");
		} else if(numberOfCards < 1) {
			throw new IllegalArgumentException("have to move at least one card");
		}
		Stack<Card> temp = new Stack<>();
		for(int i = 0; i < numberOfCards; i++) {
			temp.add(otherPile.cards.pop());
		}
		Stack<Card> addTo;
		if(!canAddToPile(temp.peek())) {
			addTo = otherPile.cards;
		} else {
			addTo = cards;
		}
		while(!temp.isEmpty()) {
			addTo.push(temp.pop());
		}
		
		if(addTo == cards) {
			visibleCards += numberOfCards;
			otherPile.visibleCards -= numberOfCards;
			return true;
		}
		return false;
	}
	
	/**
	 * checks if you can add the given card to the pile
	 * 
	 * @param newCard The card to add
	 * @return true if the pile is empty and the card is a King, or
	 * the pile isn't empty and the given card is of the opposite color to the top card on the pile, or
	 * the pile isn't empty and the given card's value is one less than the value of the top card
	 */
	public boolean canAddToPile(Card newCard) {
		if(isEmpty()) {
			return newCard.value == Card.LARGEST_VALUE;
		} else {
			Card topCard = cards.peek();
			return topCard.isOtherColor(newCard) && newCard.value == topCard.value - 1;
		}
	}
	
	/**
	 * moves the top card from the tableau pile to the given foundation pile
	 * 
	 * @param pile the foundation pile to move the card to
	 * @throws IllegalStateException if the tableau pile is empty
	 */
	public void moveTopCard(Foundation foundation) {
		if(!canMoveTopCardToFoundation(foundation)) {
			throw new IllegalArgumentException("cannot move card to the foundation");
		}
		foundation.addCard(cards.pop());
		if(visibleCards > 1 || isEmpty()) { //empty after removing the one card left
			visibleCards--;
		} 
	}
	
	public boolean canMoveTopCardToFoundation(Foundation foundation) {
		exceptionIfEmpty();
		return foundation.canAddCard(cards.peek());
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	/**
	 * checks how many cards are visible on the pile
	 * 
	 * @return the number of visible cards
	 */
	public int numVisibleCards() {
		return visibleCards;
	}
	
	/**
	 * checks how many cards are hidden on the pile
	 * 
	 * @return the number of hidden cards
	 */
	public int numHiddenCards() {
		return cards.size() - visibleCards;
	}
	
	@Override
	public String toString() {
		if(isEmpty() || numHiddenCards() == 0) {
			return cards.toString();
		}
		Card topVisible = cards.pop();
		String result = topVisible.toString();
		Stack<Card> temp = new Stack<>();
		temp.push(topVisible);
		for(int i = 1; i < visibleCards; i++) {
			Card visible = cards.pop();
			result = visible.toString() + ", " + result;
			temp.add(visible);
		}
		String hidden = "";
		for(int i = 0; i < cards.size(); i++) {
			hidden += "??, ";
		}
		while(!temp.isEmpty()) {
			cards.push(temp.pop());
		}
		return "[" + hidden + result + "]";
	}
}
