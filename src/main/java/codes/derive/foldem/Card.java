package codes.derive.foldem;

/**
 * Represents a single card in a standard 52-card deck.
 */
public class Card {
	
	/** Labels for cards. **/
	public static final char[] LABEL = { 'A', '2', '3', '4', '5', '6', '7',
			'8', '9', 'T', 'J', 'Q', 'K' };
	
	/*
	 * I decided to do the enumeration like this instead of using an enum since
	 * it simplifies string notation and also resolves namespace conflict for
	 * the name "Card".
	 */

	/* Enumeration of card types. */
	public static final int ACE = 0;
	public static final int DEUCE = 1;
	public static final int TREY = 2;
	public static final int FOUR = 3;
	public static final int FIVE = 4;
	public static final int SIX = 5;
	public static final int SEVEN = 6;
	public static final int EIGHT = 7;
	public static final int NINE = 8;
	public static final int TEN = 9;
	public static final int JACK = 10;
	public static final int QUEEN = 11;
	public static final int KING = 12;
	
	/* The card value. */
	private final int value;
	
	/* The suit of the card. */
	private final Suit suit;
	
	/**
	 * Constructs a new Card using the given card value and suit.
	 * @param value
	 * 		The card value.
	 * @param suit
	 * 		The card suit.
	 */
	protected Card(int value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	/**
	 * Constructs a new Card using the given textual representation.
	 * @param text
	 * 		TODO explain format
	 */
	protected Card(String text) {
		char[] values = text.toCharArray();
		if (values.length != 2) {
			throw new IllegalArgumentException("Invalid hand length '" + text + "'");
		}
		
		// parse card value
		int value = -1;
		for (int i = 0; i < LABEL.length; i++) {
			if (LABEL[i] == values[0]) {
				value = i;
				break;
			}
		}
		if (value == -1) {
			throw new IllegalArgumentException("Invalid card shorthand '" + values[0] + "'");
		}
		this.value = value;
		
		// parse card suit
		Suit suit = null;
		for (Suit s : Suit.values()) {
			if (s.getShorthand() == values[1]) {
				suit = s;
				break;
			}
		}
		if (suit == null) {
			throw new IllegalArgumentException("Invalid suit shorthand '" + values[1] + "'");
		}
		this.suit = suit;
	}
	
	/**
	 * Gets the value of the card.
	 * @return
	 * 		The value of the card.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Gets the suit of the card.
	 * @return
	 * 		The suit of the card.
	 */
	public Suit getSuit() {
		return suit;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) {
			return false;
		}
		Card c = (Card) o;
		return c.suit == suit && c.value == value;
	}
	
	@Override
	public int hashCode() {
		return (31 + value) * 31 + suit.hashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(LABEL[value]).append(suit.getShorthand()).toString();
	}
	
}
