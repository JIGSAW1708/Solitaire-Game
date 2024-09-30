package patience;

public enum SuitColour {

    // RED, BLACK
    RED,
    BLACK;

    // Method to get the string representation of the suit color
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
