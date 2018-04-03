package setup;

import java.util.Stack;

import setup.Card.Suit;

public class Foundation {
	private Stack<Card> cards;
	private Suit suit;
	
	public Foundation(Suit foundationSuit) {
		cards = new Stack<>();
		suit = foundationSuit;
	}
	
	public void addCard(Card card) {
		Card.checkNullCard(card);
		cards.push(card);
	}
}
