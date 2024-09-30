package patience;

public class Command {

    // Enumeration of the different command types
    private enum CommandType {
        DRAW,
        MOVE,
        QUIT
    }

    // Private constants for the valid move from and to locations
    private static final char[] VALID_MOVE_FROM_LOCATIONS = {'P', '1', '2', '3', '4', '5', '6', '7', 'D', 'H', 'C', 'S'};
    private static final char[] VALID_MOVE_TO_LOCATIONS = {'1', '2', '3', '4', '5', '6', '7', 'D', 'H', 'C', 'S'};

    // Variables to store the command type, the move from and to locations, and the number of cards to move
    private CommandType commandType;
    private char moveFrom, moveTo;
    private int numberOfCardsToMove;

    // Constructor
    Command(String input) throws InvalidCommandException {
        // Format the input string by trimming it and converting it to uppercase
        String inputFormatted = input.trim().toUpperCase();

        // Check if the input is a valid command
        if (!isValid(inputFormatted)) {
            throw new InvalidCommandException("Invalid command: " + input);
        }

        // Parse the input string into the command type, move from and to locations, and number of cards to move
        parseInput(inputFormatted);
    }

    // Static method to check if the input is a valid command
    public static boolean isValid(String input) {
        // Format the input string by trimming it and converting it to uppercase
        String inputFormatted = input.trim().toUpperCase();

        // Check if the input is a quit command, a draw command, or a move command
        return inputFormatted.equals("Q") || inputFormatted.equals("D") || inputFormatted.matches("[P1-7DHCS][1-7DHCS][0-9]*");
    }

    // Private method to parse the input string into the command type, move from and to locations, and number of cards to move
    private void parseInput(String inputFormatted) {
        // Check if the input is a quit command
        if (inputFormatted.equals("Q")) {
            commandType = CommandType.QUIT;
        } else if (inputFormatted.equals("D")) {
            commandType = CommandType.DRAW;
        } else {
            // Otherwise, assume the input is a move command
            commandType = CommandType.MOVE;

            // Set the move from and to locations
            moveFrom = inputFormatted.charAt(0);
            moveTo = inputFormatted.charAt(1);

            // Check if the input has a number of cards to move
            if (inputFormatted.length() == 2) {
                numberOfCardsToMove = 1;
            } else {
                numberOfCardsToMove = Integer.valueOf(inputFormatted.substring(2));
            }
        }

        // Validate the move from and to locations
        if (!isValidMoveFromLocation(moveFrom) || !isValidMoveToLocation(moveTo)) {
            throw new InvalidCommandException("Invalid move from or to location: " + inputFormatted);
        }

        // Validate the number of cards to move
        if (numberOfCardsToMove < 1) {
            throw new InvalidCommandException("Invalid number of cards to move: " + inputFormatted);
        }
    }

    // Private method to check if the move from location is valid
    private boolean isValidMoveFromLocation(char moveFrom) {
        return Arrays.contains(VALID_MOVE_FROM_LOCATIONS, moveFrom);
    }

    // Private method to check if the move to location is valid
    private boolean isValidMoveToLocation(char moveTo) {
        return Arrays.contains(VALID_MOVE_TO_LOCATIONS, moveTo);
    }

    // Method to check if the command is a quit command
