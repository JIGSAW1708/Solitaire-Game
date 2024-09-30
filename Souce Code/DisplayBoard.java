package patience;

import java.util.*;

public class Board {

  public static final int NUM_LANES = 7;
  public static final int NUM_SUITS = Suit.values().length;
  public static final int SUIT_SIZE = Rank.values().length;

  private Deck deck; // A deck of cards
  private Stack<Card> pile; // A pile of cards that have been drawn from the deck
  private List<Stack<Card>> lanes; // A list of stacks of cards, representing the lanes on the board
  private List<Stack<Card>> suits; // A list of stacks of cards, representing the suits on the board
  private boolean wasCardReveal; // A flag indicating whether a card was revealed during the last move

  Board () {
    // Initialize the deck and shuffle it
    deck = new Deck();
    deck.shuffle();

    // Initialize the pile
    pile = new Stack<>();

    // Initialize the lanes
    lanes = new ArrayList<>(NUM_LANES);
    for (int i = 0; i < NUM_LANES; i++) {
      lanes.add(new Stack<>());
      // Add a face-up card to the top of each lane
      Card card = deck.pop();
      card.turnFaceUp();
      lanes.get(i).push(card);
    }

    // Initialize the suits
    suits = new ArrayList<>(NUM_SUITS);
    for (int i = 0; i < NUM_SUITS; i++) {
      suits.add(new Stack<>());
    }

    // Set the flag indicating whether a card was revealed to false
    wasCardReveal = false;
  }

  public boolean drawIsPossible () {
    // Return true if there are cards in the deck or in the pile, false otherwise
    return !deck.isEmpty() || !pile.isEmpty();
  }

  public void draw () {
    // If there are cards in the deck, draw a card and add it to the pile
    if (!deck.isEmpty()) {
      Card card = deck.pop();
      card.turnFaceUp();
      pile.add(card);
    } else {
      // If there are no cards in the deck, move all the cards from the pile back to the deck
      int numberOfCards = pile.size();
      for (int i = 0; i < numberOfCards; i++) {
        Card card = pile.pop();
        card.turnFaceDown();
        deck.add(card);
      }
    }
  }

  public boolean moveIsPossible (Command command) {
    // Check if the move is possible based on the following rules:
    // * A card can be moved from the pile to a lane if the lane is empty or the card on top of the lane is the next card in the sequence.
    // * A card can be moved from the pile to a suit if the suit is empty or the card on top of the suit is the next card in the sequence.
    // * A card can be moved from a lane to a suit if the suit is empty or the card on top of the suit is the next card in the sequence.
    // * A card can be moved from a suit to a lane if the lane is empty or the card on top of the lane is the next card in the sequence.
    // * A card can be moved from one lane to another lane if there are at least as many cards in the lane being moved from as the number of cards being moved, and the top card in the lane being moved to is the next card in the sequence.
    boolean isPossible = false;
    if (command.isMoveFromPile() && command.isMoveToLane()) {
      if (!pile.empty()) {
        Card card = pile.peek();
        Stack<Card> lane = lanes.get(command.getToIndex());
        if (lane.empty() || (!lane.empty() && lane.peek().isNextInLane(card))) {
          isPossible = true;
        }