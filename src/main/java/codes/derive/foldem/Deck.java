/*
 * This file is part of Fold'em, a Java library for Texas Hold 'em Poker.
 *
 * Fold'em is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fold'em is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fold'em.  If not, see <http://www.gnu.org/licenses/>.
 */
package codes.derive.foldem;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import codes.derive.foldem.util.RandomContext;

/**
 * Represents a standard 52-card deck of cards.
 * <p>
 * This type has stack-like functionality with the {link Deck#pop()} method
 * taking a card from the next available position in the deck and moving the
 * cursor to the following card.
 * </p>
 */
public class Deck {

	/* Backing array containing cards held within this deck. */
	private final Card[] cards = new Card[Constants.DECK_SIZE];

	/* Current cursor offset in the backing array. */
	private int currentIndex = 0;

	/**
	 * Constructs a new {@link Deck} with the cards placed in a basic uniform
	 * order.
	 */
	public Deck() {
		int offset = 0;
		for (Suit suit : Suit.values()) {
			for (int num = Card.ACE; num <= Card.KING; num++) {
				cards[offset++] = new Card(num, suit);
			}
		}
	}

	/**
	 * Orders the the {@link Deck} randomly using the default random number
	 * generator.
	 * 
	 * @return The current {@link Deck} context, for chaining.
	 */
	public Deck shuffle() {
		return shuffle(RandomContext.get());
	}

	/**
	 * Orders the {@link Deck} randomly using the provided
	 * {@link java.util.Random} context.
	 * 
	 * @param random
	 *            The random number generator to use to shuffle the deck.
	 * @return The current {@link Deck} context, for chaining.
	 */
	public Deck shuffle(Random random) {
		if (currentIndex > 0) {
			throw new IllegalStateException("Deck cannot be shuffled after pop()");
		}
		Collections.shuffle(Arrays.asList(cards), random);
		return this;
	}

	/**
	 * Pops the specified {@link Card} from this deck, throwing an exception if
	 * the {@link Card} has already been dealt. This function essentially moves
	 * the specified {@link Card} to the front of the deck, and then pops it
	 * normally.
	 * 
	 * @param c
	 *            The card to pop from the deck.
	 * @return The {@link Card} popped, should be the same as the {@link Card}
	 *         specified.
	 */
	public Card pop(Card c) {
		int index = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].equals(c)) {
				index = i;
				break;
			}
		}
		if (index < currentIndex) {
			throw new IllegalArgumentException("Card already dealt");
		}
		if (index > 0) {
			Card temp = cards[index];
			cards[index] = cards[currentIndex];
			cards[currentIndex] = temp;
		}
		return pop();
	}

	/**
	 * Pops a specific {@link Hand} from this deck, works in the same way as
	 * {@link Deck#pop(Card)} but with a {@link Hand}.
	 * 
	 * @param h
	 *            The hand to pop.
	 * @return The {@link Hand} popped, should be the same as the {@link Hand}
	 *         specified.
	 */
	public Hand pop(Hand h) {
		for (Card c : h.cards()) {
			pop(c);
		}
		return h;
	}

	/**
	 * Pops the next available {@link Card} from the deck.
	 * 
	 * @return The next available {@link Card} from the deck.
	 */
	public Card pop() {
		if (currentIndex >= cards.length) {
			throw new IllegalStateException(
					"No cards, you can use remaining() to check");
		}
		return cards[currentIndex++];
	}

	/**
	 * Obtains the next available {@link Card} on the deck without popping it.
	 * 
	 * @return The next available {@link Card} on the deck.
	 */
	public Card peek() {
		return cards[currentIndex];
	}

	/**
	 * Obtains the number of cards left in the deck that can be popped.
	 * 
	 * @return The number of cards left in the deck that can be popped.
	 */
	public int remaining() {
		return Constants.DECK_SIZE - currentIndex;
	}

	/**
	 * Checks to see if the specified {@link Card} has already been dealt from
	 * this deck.
	 * 
	 * @param c
	 *            The card to check.
	 * @return <code>true</code> if the {@link Card} has been dealt,
	 *         <code>false</code> otherwise.
	 */
	public boolean dealt(Card c) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i].equals(c)) {
				return i < currentIndex;
			}
		}
		throw new AssertionError("Card " + c + " was not found in the deck");
	}

	/**
	 * Returns an array containing all cards in this deck including ones that
	 * have already been dealt.
	 * 
	 * @return An array containing <b>all</b> cards in this deck.
	 */
	public Card[] toArray() {
		return cards;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cards);
		result = prime * result + currentIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deck other = (Deck) obj;
		if (!Arrays.equals(cards, other.cards))
			return false;
		if (currentIndex != other.currentIndex)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return Deck.class.getName() + " [dealt=" + currentIndex + "]";
	}

}
