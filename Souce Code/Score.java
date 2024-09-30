package patience;

public class Score {

    // Static constants for the different score values
    private static final int CARD_PILE_TO_LANE = 5;
    private static final int CARD_PILE_TO_SUIT = 10;
    private static final int CARD_LANE_TO_LANE_WITH_REVEAL = 5;
    private static final int CARD_SUIT_TO_LANE = -10;
    private static final int CARD_LANE_TO_SUIT = 20;

    // The current score
    private int score;

    // The number of turns played
    private int numberOfTurns;

    // Constructor
    public Score() {
        score = 0;
        numberOfTurns = 0;
    }

    // Method to get the current score
    public int getScore() {
        return score;
    }

    // Method to get the number of turns played
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    // Method to update the score for a draw move
    public void drawPlayed(Command command) {
        numberOfTurns++;
    }

    // Method to update the score for a move command
    public void movePlayed(Command command, Board board) {
        numberOfTurns++;

        // Calculate the score for the move
        int moveScore = 0;
        if (command.isMoveFromPile() && command.isMoveToLane()) {
            moveScore += CARD_PILE_TO_LANE;
        } else if (command.isMoveFromPile() && command.isMoveToSuit()) {
            moveScore += CARD_PILE_TO_SUIT;
        } else if (command.isMoveFromLane() && command.isMoveToLane() && board.wasCardRevealed()) {
            moveScore += CARD_LANE_TO_LANE_WITH_REVEAL;
        } else if (command.isMoveFromSuit() && command.isMoveToLane()) {
            moveScore += CARD_SUIT_TO_LANE;
        } else { // lane to suit
            moveScore += CARD_LANE_TO_SUIT;
        }

        // Add the move score to the total score
        score += moveScore;
    }
}
