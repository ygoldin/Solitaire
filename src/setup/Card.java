package setup;

/**
 * Card represents a single card in a standard deck of cards
 * @author Yael Goldin
 */
public class Card {
	private Suit suit;
	private int value;
	private static final String ACE = "A";
	private static final String[] ROYALTY = {"J", "Q", "K"};
	public static final int SMALLEST_VALUE = 1;
	public static final int LARGEST_VALUE = 13;
	
	/**
	 * the different suits possible
	 */
	public enum Suit {
		SPADES, DIAMONDS, CLUBS, HEARTS;
	}
	
	/**
	 * initializes the card with the given values
	 * 
	 * @param value The value of the card (1 = Ace, 2-10, 11-13 = Jack/Queen/King)
	 * @param suit The suit of the card
	 * @throws IllegalArgumentException if the suit is null or the value is outside of the range
	 * [SMALLEST_VALUE, LARGEST_VALUE] inclusive
	 */
	public Card(int value, Suit suit) {
		if(value < SMALLEST_VALUE || value > LARGEST_VALUE || suit == null) {
			throw new IllegalArgumentException("invalid value/suit");
		}
		this.value = value;
		this.suit = suit;
	}
	
	/**
	 * checks if the card is one of the red suits
	 * 
	 * @return true if it is a diamonds or hearts
	 */
	public boolean isRed() {
		return suit == Suit.DIAMONDS || suit == Suit.HEARTS;
	}
	
	/**
	 * checks if the card is one of the black suits
	 * 
	 * @return true if it is a clubs or spades
	 */
	public boolean isBlack() {
		return suit == Suit.CLUBS || suit == Suit.SPADES;
	}
	
	/**
	 * returns the value of the card
	 * 
	 * @return 1 if it's an Ace, 2-10 for a numbered card, 11-13 for Jack/Queen/King
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		if(value == 1) {
			return ACE;
		} else if(value >= 2 && value <= 10) {
			return "" + value;
		} else {
			return ROYALTY[value - 11];
		}
	}
}
