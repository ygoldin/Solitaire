package setup;

import java.util.Stack;

public class TableauPile extends SolitairePile {
	private Stack<Card> cards;
	private int hiddenCards;
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
		hiddenCards = initialCardsNum - 1;
		visibleCards = 1;
	}
	
	/**
	 * adds a visible card to the top of the pile
	 * 
	 * @param newCard the card to add
	 * @throws IllegalArgumentException if the card is null
	 * @throws IllegalArgumentException if the pile is empty and the card is not a King
	 * @throws IllegalArgumentException if the pile isn't empty and the given card is of the same color
	 * as the top card in the pile
	 * @throws IllegalArgumentException if the pile isn't empty and the given card's value is not one less
	 * than the value of the top card
	 */
	public void addVisibleCard(Card newCard) {
		Card.checkNullCard(newCard);
		if(isEmpty()) {
			if(newCard.value != Card.LARGEST_VALUE) {
				throw new IllegalArgumentException("can only put king in empty pile");
			}
			cards.add(newCard);
			visibleCards++;
		} else {
			Card topCard = cards.peek();
			if(!topCard.isOtherColor(newCard)) {
				throw new IllegalArgumentException("can only place cards of alternating color");
			} else if(newCard.value != topCard.value - 1) {
				throw new IllegalArgumentException("can only place cards in immediate descending order");
			}
			cards.add(newCard);
			visibleCards++;
		}
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

}
