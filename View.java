import java.util.*;

public class PatienceGame {
    public static void main(String[] args) {
        PatienceGame game = new PatienceGame();
        game.play();
    }

    private final int NUM_LANES = 7;
    private final int NUM_SUITS = 4;
    private final int MAX_LANE_SIZE = 13;
    private final int MAX_DRAW_SIZE = 3;
    private final String[] SUIT_LABELS = {"D", "H", "C", "S"};
    private final String[] LANE_LABELS = {"1", "2", "3", "4", "5", "6", "7"};
    private final String PILE_LABEL = "P";

    private Stack<Card> deck;
    private Stack<Card> pile;
    private Stack<Card>[] lanes;
    private Stack<Card>[] suits;
    private int score;
    private int moves;

    public PatienceGame() {
        initializeGame();
    }

    // Initialize the game, including the deck, piles, lanes, and suits.
    private void initializeGame() {
        deck = new Stack<>();
        pile = new Stack<>();
        lanes = new Stack[NUM_LANES];
        suits = new Stack[NUM_SUITS];
        score = 0;
        moves = 0;

        for (int i = 0; i < NUM_LANES; i++) {
            lanes[i] = new Stack<>();
        }

        for (int i = 0; i < NUM_SUITS; i++) {
            suits[i] = new Stack<>();
        }

        // Create a deck with cards and shuffle it.
        for (int suit = 0; suit < NUM_SUITS; suit++) {
            for (int value = 1; value <= MAX_LANE_SIZE; value++) {
                Card card = new Card(suit, value);
                deck.push(card);
            }
        }
        shuffleDeck();

        // Deal the initial cards to the lanes.
        dealInitialCards();
    }

    // Shuffle the deck.
    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    // Deal the initial set of cards to the lanes.
    private void dealInitialCards() {
        for (int lane = 0; lane < NUM_LANES; lane++) {
            for (int i = 0; i <= lane; i++) {
                Card card = deck.pop();
                if (i == lane) {
                    card.turnFaceUp();
                }
                lanes[lane].push(card);
            }
        }
    }

    // The game loop where the player can interact.
    public void play() {
        displayGame();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a command (Q=quit, D=draw, or Move: e.g., 'P1' or '1D'): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("Game over. Final score: " + score);
                break;
            } else if (input.equals("D")) {
                drawCards();
            } else if (input.length() == 2) {
                String from = input.substring(0, 1);
                String to = input.substring(1, 2);
                moveCard(from, to, 1);
            } else if (input.length() == 3) {
                String from = input.substring(0, 1);
                String to = input.substring(1, 2);
                int num = Character.getNumericValue(input.charAt(2));
                moveCard(from, to, num);
            } else {
                System.out.println("Invalid command. Try again.");
            }

            if (isGameOver()) {
                System.out.println("Congratulations! You won with a score of " + score);
                break;
            }

            displayGame();
        }
    }

    // Draw cards from the deck or put them back in.
    private void drawCards() {
        if (!deck.isEmpty()) {
            for (int i = 0; i < MAX_DRAW_SIZE && !deck.isEmpty(); i++) {
                Card card = deck.pop();
                card.turnFaceUp();
                pile.push(card);
            }
        } else {
            for (int i = pile.size(); i > 0; i--) {
                Card card = pile.pop();
                card.turnFaceDown();
                deck.push(card);
            }
        }
    }

    // Move cards from one stack to another.
    private void moveCard(String from, String to, int num) {
        Stack<Card> fromStack = getStackByLabel(from);
        Stack<Card> toStack = getStackByLabel(to);

        if (fromStack.isEmpty() || toStack == null) {
            System.out.println("Invalid move. Try again.");
            return;
        }

        if (toStack.isEmpty()) {
            if (to.equals(PILE_LABEL) && num != 1) {
                System.out.println("Invalid move. Try again.");
                return;
            }

            if (to.equals(PILE_LABEL)) {
                score += 10;
            } else if (from.equals(PILE_LABEL)) {
                score += 10;
            } else {
                score += 5 * num;
            }

            for (int i = 0; i < num; i++) {
                toStack.push(fromStack.pop());
            }

            if (from.equals(PILE_LABEL)) {
                pile.pop();
                if (!pile.isEmpty()) {
                    pile.peek().turnFaceUp();
                }
            }
        } else if (fromStack.peek().isNextCard(toStack.peek()) && fromStack.peek().isDifferentColor(toStack.peek())) {
            score += 20 * num;
            for (int i = 0; i < num; i++) {
                toStack.push(fromStack.pop());
            }
        } else {
            System.out.println("Invalid move. Try again.");
        }

        moves++;
    }

    // Get the appropriate stack based on the label.
    private Stack<Card> getStackByLabel(String label) {
        if (label.equals(PILE_LABEL)) {
            return pile;
        } else if (label.equals("D")) {
            return null;
        } else if (label.length() == 2) {
            int laneNumber = Character.getNumericValue(label.charAt(1));
            if (laneNumber >= 1 && laneNumber <= NUM_LANES) {
                return lanes[laneNumber - 1];
            }
        }
        return null;
    }

    // Check if the game is over (all suits have reached their maximum size).
    private boolean isGameOver() {
        for (Stack<Card> suit : suits) {
            if (suit.size() != MAX_LANE_SIZE) {
                return false;
            }
        }
        return true;
    }

    // Display the game's current state.
    private void displayGame() {
        System.out.println("Score: " + score);
        System.out.println("Moves: " + moves);

        // Display the PILE stack.
        System.out.print("PILE: ");
        if (!pile.isEmpty()) {
            pile.peek().display();
        } else {
            System.out.println("Empty");
        }

        // Display the LANE stacks.
        for (int i = 0; i < NUM_LANES; i++) {
            System.out.print("LANE " + (i + 1) + ": ");
            if (!lanes[i].isEmpty()) {
                for (Card card : lanes[i]) {
                    card.display();
                }
            } else {
                System.out.println("Empty");
            }
        }

        // Display the SUIT stacks.
        for (int i = 0; i < NUM_SUITS; i++) {
            System.out.print("SUIT " + SUIT_LABELS[i] + ": ");
            if (!suits[i].isEmpty()) {
                suits[i].peek().display();
            } else {
                System.out.println("Empty");
            }
        }
    }
}

// Class representing a playing card.
class Card {
    private int suit;        // Represents the card's suit.
    private int value;       // Represents the card's value.
    private boolean faceUp;  // Indicates whether the card is face-up or face-down.

    // Constructor for creating a card.
    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
        this.faceUp = false;
    }

    // Turn the card face-up.
    public void turnFaceUp() {
        this.faceUp = true;
    }

    // Check if the card is face-up.
    public boolean isFaceUp() {
        return faceUp;
    }

    // Check if this card is the next card in sequence to another card.
    public boolean isNextCard(Card card) {
        return this.value + 1 == card.value;
    }

    // Check if this card is a different color than another card.
    public boolean isDifferentColor(Card card) {
        int thisSuitColor = this.suit < 2 ? 0 : 1;  // 0 for red (diamonds, hearts), 1 for black (clubs, spades)
        int cardSuitColor = card.suit < 2 ? 0 : 1;

        return thisSuitColor != cardSuitColor;
    }

    // Display the card's value and suit (either face-up or face-down).
    public void display() {
        if (faceUp) {
            String[] suits = {"♦", "♥", "♣", "♠"};
            String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
            System.out.print(values[value - 1] + suits[suit] + " ");
        } else {
            System.out.print("XX ");
        }
    }
}
