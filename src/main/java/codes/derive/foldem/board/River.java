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
package codes.derive.foldem.board;

import codes.derive.foldem.Card;

/**
 * Represents a river in Texas hold 'em
 */
public class River extends AbstractBoard {

	/**
	 * Constructs a new {@link River}.
	 * 
	 * @param cards
	 *            The cards.
	 */
	public River(Card... cards) {
		super(cards);
	}

	@Override
	public Street getStreet() {
		return Street.RIVER;
	}

}
