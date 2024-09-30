package patience;

import java.util.Scanner;

public class View {

    // Constant for the blank space character
    private static final String BLANK = "  ";

    // Scanner object for reading user input
    private Scanner in;

    // Command object for storing the user's command
    private Command command;

    // Constructor
    public View() {
        in = new Scanner(System.in);
    }

    /**
     * Displays the welcome message
     */
    public void displayWelcome() {
        System.out.println("Welcome to Patience");
    }

    /**
     * Displays the current score
     *
     * @param scoreboard The score object
     */
    public void displayScore(Score scoreboard) {
        System.out.println("        Score " + scoreboard.getScore() + "  Turns " + scoreboard.getNumberOfTurns());
    }

    /**
     * Displays the current board state
     *
     * @param board The board object
     */
    public void displayBoard(Board board) {
        // Get the maximum number of rows in the board
        int numberRows = Math.max(board.maxLaneSize(), 2);

        // Iterate over the rows
        for (int row = 0; row < numberRows; row++) {
            // Display the deck
            if (row == 0) {
                Deck deck = board.getDeck();
                if (deck.isEmpty()) {
                    System.out.print(BLANK);
                } else {
                    System.out.print(deck.peek());
                }
            }

            // Display the pile
            else if (row == 1) {
                Stack<Card> pile = board.getPile();
                if (pile.isEmpty()) {
                    System.out.print(BLANK);
                } else {
                    System.out.print(pile.peek());
                }
            }

            // Display the lanes
            else {
                System.out.print(BLANK);
            }

            // Add some spacing
            System.out.print(BLANK);

            // Iterate over the lanes
            for (int laneIndex = 0; laneIndex < Board.NUM_LANES; laneIndex++) {
                List<Card> lane = board.getLane(laneIndex);

                // If the row is greater than the lane size, display a blank space
                if (row > lane.size() - 1) {
                    System.out.print(BLANK);
                } else {
                    System.out.print(lane.get(row));
                }

                // Add some spacing
                System.out.print(BLANK);
            }

            // Display the suits
            if (row == 0) {
                for (int suitIndex = 0; suitIndex < Board.NUM_SUITS; suitIndex++) {
                    Stack<Card> suit = board.getSuit(suitIndex);

                    // If the suit is empty, display a blank space
                    if (suit.isEmpty()) {
                        System.out.print(BLANK);
                    } else {
                        System.out.print(suit.peek());
                    }

                    // Add some spacing
                    System.out.print(BLANK);
                }
            }

            // Add a newline character
            System.out.println();
        }

        // Display the lane labels
        System.out.println("P   1   2   3   4   5   6   7   D   H   C   S");

        // Add a newline character
        System.out.println();
    }

    /**
     * Gets the user's command
     *
     * @return The user's command
     */
    public Command getUserInput() {
        boolean commandEntered = false;
        do {
            // Prompt the user for a command
            System.out.print("Enter command: ");

            // Read the user's input
            String input = in.nextLine();

            // If the input is a valid command, create a new
