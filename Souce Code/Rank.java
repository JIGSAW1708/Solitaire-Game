package patience;

public enum Rank {

    // ACE, TWO, ..., KING
    ACE("A "),
    TWO("2 "),
    THREE("3 "),
    FOUR("4 "),
    FIVE("5 "),
    SIX("6 "),
    SEVEN("7 "),
    EIGHT("8 "),
    NINE("9 "),
    TEN("10"),
    JACK("J "),
    QUEEN("Q "),
    KING("K ");

    // The symbol of the rank
    private final String symbol;

    // Constructor
    Rank(String symbol) {
        this.symbol = symbol;
    }

    // Method to get the symbol of the rank
    public String getSymbol() {
        return symbol;
    }

    // Method to get the string representation of the rank
    @Override
    public String toString() {
        return symbol;
    }
}
