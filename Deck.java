package patience;

import java.util.*;

/**
 * This class models a full 52 card deck of cards.
 * @author Chris Bleakley
 * @version 1.1
 */

public class Deck extends Stack<Card> {
	Deck () {
		super();
		for (Suit suit : Suit.values()) {
			for (Rank cardValue : Rank.values()) {
				super.add(new Card(suit, cardValue, false));
			}
		}
	}
	
	public void shuffle () {
		Collections.shuffle(this);
	}
}
