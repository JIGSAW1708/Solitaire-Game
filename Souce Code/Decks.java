package patience;

import java.util.Collections;
import java.util.Stack;

/**
 * This class models a full 52 card deck of cards.
 *
 * @author Chris Bleakley
 * @version 1.1
 */
public class Deck {

    // Enumeration of the different card suits
    private enum Suit {
        DIAMONDS,
        HEARTS,
        CLUBS,
        SPADES
    }

    // Enumeration of the different card ranks
    private enum Rank {
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING
    }

    // Stack to store the cards in the deck
    private Stack<Card> cards;

    // Constructor
    public Deck() {
        cards = new Stack<>();

        // Iterate over all suits and ranks to create a new card for each combination
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank, false));
            }
        }
    }

    // Method to shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Method to deal a card from the deck
    public Card dealCard() {
        return cards.pop();
    }

    // Method to check if the deck is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Inner class to represent a single card
    private static class Card {

        // Suit of the card
        private Suit suit;

        // Rank of the card
        private Rank rank;

        // Face-up or face-down
        private boolean faceUp;

        // Constructor
        public Card(Suit suit, Rank rank, boolean faceUp) {
            this.suit = suit;
            this.rank = rank;
            this.faceUp = faceUp;
        }

        // Method to get the suit of the card
        public Suit getSuit() {
            return suit;
        }

        // Method to get the rank of the card
        public Rank getRank() {
            return rank;
        }

        // Method to check if the card is face-up
        public boolean isFaceUp() {
            return faceUp;
        }

        // Method to flip the card over
        public void flip() {
            faceUp = !faceUp;
        }

        // Method to get the string representation of the card
        @Override
        public String toString() {
            return rank.name() + " of " + suit.name();
        }
    }
}
