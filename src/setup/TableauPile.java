package setup;

import java.util.Stack;

public class TableauPile extends SolitairePile {
	private Stack<Card> cards;
	private int hiddenCards;
	private int visibleCards;
	
	public TableauPile() {
		//TODO: pass in a Deck and the number of cards to place
		cards = new Stack<>();
	}
	
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
