package patience;

public enum Suit {

    // DIAMOND, HEART, CLUB, SPADE
    DIAMOND(DisplayColour.RED + "♦", SuitColour.RED),
    HEART(DisplayColour.RED + "♥︎", SuitColour.RED),
    CLUB(DisplayColour.BLACK + "♣︎", SuitColour.BLACK),
    SPADE(DisplayColour.BLACK + "♠︎", SuitColour.BLACK);

    // The symbol of the suit
    private final String symbol;

    // The color of the suit
    private final SuitColour colour;

    // Constructor
    Suit(String symbol, SuitColour colour) {
        this.symbol = symbol;
        this.colour = colour;
    }

    // Method to get the color of the suit
    public SuitColour getColour() {
        return colour;
    }

    // Method to get the string representation of the suit
    @Override
    public String toString() {
        return symbol;
    }
}
