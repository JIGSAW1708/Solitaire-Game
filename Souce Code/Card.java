package patience;

public class Card {

    // Variables to store the suit, rank, and whether the card is face up
    private Suit suit;
    private Rank value;
    private boolean isFaceUp;

    // Constructor
    Card() {
        suit = Suit.HEART;
        value = Rank.QUEEN;
        isFaceUp = true;
    }

    // Constructor
    Card(Suit suit, Rank value, boolean faceUp) {
        this.suit = suit;
        this.value = value;
        this.isFaceUp = faceUp;
    }

    // Method to turn the card face up
    public void turnFaceUp() {
        isFaceUp = true;
    }

    // Method to turn the card face down
    public void turnFaceDown() {
        isFaceUp = false;
    }

    // Method to check if the card is face up
    public boolean isFaceUp() {
        return isFaceUp;
    }

    // Method to check if the card is face down
    public boolean isFaceDown() {
        return !isFaceUp;
    }

    // Method to get the suit of the card
    public Suit getSuit() {
        return suit;
    }

    // Method to get the rank of the card
    public Rank getValue() {
        return value;
    }

    // Private method to check if the card has the same suit as another card
    private boolean isSameSuit(Card card) {
        return suit == card.getSuit();
    }

    // Private method to check if the card has the next lower value than another card
    private boolean isNextLowerValue(Card card) {
        return this.value.ordinal() - 1 == card.value.ordinal();
    }

    // Private method to check if the card has the next higher value than another card
    private boolean isNextHigherValue(Card card) {
        return this.value.ordinal() + 1 == card.value.ordinal();
    }

    // Private method to check if the card has a different colour than another card
    private boolean isDifferentColour(Card card) {
        return this.suit.getColour() != card.suit.getColour();
    }

    // Method to check if the card is the next card in the lane sequence
    public boolean isNextInLane(Card card) {
        return this.isDifferentColour(card) && this.isNextLowerValue(card);
    }

    // Method to check if the card is the next card in the suit sequence
    public boolean isNextInSuit(Card card) {
        return this.isSameSuit(card) && this.isNextHigherValue(card);
    }

    // Method to get a string representation of the card
    public String toString() {
        if (isFaceUp) {
            return suit.toString() + value.toString() + DisplayColour.RESET;
        } else {
            return DisplayColour.PURPLE + "XXX" + DisplayColour.RESET;
        }
    }
}
