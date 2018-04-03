package setup;

import java.util.List;
import java.util.Random;

import setup.Card.Suit;

public class Deck {
	private List<Card> cards;
	private Random rand;
	
	public Deck() {
		for(int value = Card.SMALLEST_VALUE; value <= Card.LARGEST_VALUE; value++) {
			for(Suit suitType : Card.Suit.values()) {
				cards.add(new Card(value, suitType));
			}
		}
		rand = new Random();
	}
	
	public Card removeRandomCard() {
		int idx = rand.nextInt(cards.size());
		return cards.remove(idx);
	}
}
