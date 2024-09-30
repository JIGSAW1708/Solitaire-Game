package patience;

public class Patience {

    // The game board
    private Board board;

    // The score board
    private Score scoreboard;

    // The view
    private View view;

    /**
     * Constructor
     */
    public Patience() {
        board = new Board();
        scoreboard = new Score();
        view = new View();
    }

    /**
     * Starts the game loop
     */
    public void play() {
        // Display the welcome message
        view.displayWelcome();

        // Keep looping until the user quits or the game is over
        Command command;
        do {
            // Display the current score and board
            view.displayScore(scoreboard);
            view.displayBoard(board);

            // Get the user's command
            command = view.getUserInput();

            // Process the command
            if (command.isDraw()) {
                if (board.drawIsPossible()) {
                    board.draw();
                    scoreboard.drawPlayed(command);
                } else {
                    view.displayCommandNotPossible();
                }
            } else if (command.isMove()) {
                if (board.moveIsPossible(command)) {
                    board.move(command);
                    scoreboard.movePlayed(command, board);
                } else {
                    view.displayCommandNotPossible();
                }
            } else if (command.isQuit()) {
                break;
            }
        } while (!command.isQuit() && !board.isGameOver());

        // If the game is over, display the final score and board
        if (board.isGameOver()) {
            view.displayScore(scoreboard);
            view.displayBoard(board);
            view.displayGameOver();
        } else {
            // Otherwise, the user quit
            view.displayQuit();
        }
    }

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Patience patience = new Patience();
        patience.play();
    }
}
