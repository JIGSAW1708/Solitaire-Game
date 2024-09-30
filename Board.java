package patience;

import java.util.*;

/**
 * This class models the board.
 * @author Chris Bleakley
 * @version 1.1
 */

public class Board {
	
	public static final int NUM_LANES = 7;
	public static final int NUM_SUITS = Suit.values().length;
	public static final int SUIT_SIZE = Rank.values().length;
	
	private Deck deck;
	private Stack<Card> pile;
	private List<Stack<Card>> lanes;      
	private List<Stack<Card>> suits;
	// Could use arrays of Lists but arrays of generic types are messy
	// I used List and Stack from Collections but ArrayList and a programmer defined class CardStack are ok too
	// Arrays work well with CardStack
	private boolean wasCardReveal;
	
	Board () {
		deck = new Deck();
		deck.shuffle();
		pile = new Stack<>();
		lanes =  new ArrayList<>(NUM_LANES);
		for (int i=0; i<NUM_LANES; i++) {
			lanes.add(new Stack<>());
			for (int j=0; j<i; j++) {
				lanes.get(i).push(deck.pop());
			}
			Card card = deck.pop();
			card.turnFaceUp();
			lanes.get(i).push(card);
		}
		suits =  new ArrayList<>(NUM_SUITS);
		for (int i=0; i<NUM_SUITS; i++) {
			suits.add(new Stack<>());
		}
		wasCardReveal = false;
	}

	public boolean drawIsPossible () {
		return !deck.empty() || !pile.empty();
	}
	
	public void draw () {
		if (!deck.empty()) {
			Card card = deck.pop();
			card.turnFaceUp();
			pile.add(card);
		} else { // need to refill deck,  bottom of the pile becomes top of the deck
			int numberOfCards = pile.size();
			for (int i=0; i<numberOfCards; i++) {
				Card card = pile.pop();
				card.turnFaceDown();
				deck.add(card);
			}
		}
	}
	
	public boolean moveIsPossible (Command command) {
		boolean isPossible = false;
		if (command.isMoveFromPile() && command.isMoveToLane()) {
			if (!pile.empty()) {
				Card card = pile.peek();
				Stack<Card> lane = lanes.get(command.getToIndex());
				if (lane.empty() || (!lane.empty() && lane.peek().isNextInLane(card))) {
					isPossible = true;
				}
			}
		} else if (command.isMoveFromPile() && command.isMoveToSuit()) {
			if (!pile.empty()) {
				Card card = pile.peek();
				Stack<Card> suit = suits.get(command.getToIndex());
				if (suit.empty() || (!suit.empty() && suit.peek().isNextInSuit(card))) {
					isPossible = true;
				}
			}
		} else if (command.isMoveFromLane() && command.isMoveToSuit()) {
			Stack<Card> lane = lanes.get(command.getFromIndex());
			if (!lane.empty()) {
				Card card = lane.peek();
				Stack<Card> suit = suits.get(command.getToIndex());
				if (suit.empty() || (!suit.empty() && suit.peek().isNextInSuit(card))) {
					isPossible = true;
				}
			}	
		} else if (command.isMoveFromSuit() && command.isMoveToLane()) {
			Stack<Card> suit = suits.get(command.getFromIndex());
			if (!suit.empty()) {
				Card card = suit.peek();
				Stack<Card> lane = lanes.get(command.getToIndex());
				if (lane.empty() || (!lane.empty() && lane.peek().isNextInLane(card))) {
					isPossible = true;
				}
			}
		} else {  // lane to lane
			List<Card> fromLane = lanes.get(command.getFromIndex());     // use List interface to the Stack
			if (fromLane.size() >= command.getNumberOfCardsToMove() &&
					fromLane.get(fromLane.size()-command.getNumberOfCardsToMove()).isFaceUp()) {   
				Card card = fromLane.get(fromLane.size()-command.getNumberOfCardsToMove());
				Stack<Card> toLane = lanes.get(command.getToIndex());
				if (toLane.empty() || (!toLane.empty() && toLane.peek().isNextInLane(card))) {
					isPossible = true;
				}		
			}
		}
		return isPossible;
	}
	
	public void move (Command command) {
		wasCardReveal = false;
		if (command.isMoveFromPile() && command.isMoveToLane()) {
			Card card = pile.pop();
			lanes.get(command.getToIndex()).push(card);
		} else if (command.isMoveFromPile() && command.isMoveToSuit()) {
			Card card = pile.pop();
			suits.get(command.getToIndex()).push(card);
		} else if (command.isMoveFromLane() && command.isMoveToSuit()) {
			Stack<Card> lane = lanes.get(command.getFromIndex());
			Card card = lane.pop();
			if (!lane.empty() && lane.peek().isFaceDown()) {
				lane.peek().turnFaceUp();
				wasCardReveal = true;
			}
			suits.get(command.getToIndex()).push(card);	
		} else if (command.isMoveFromSuit() && command.isMoveToLane()) {
			Card card = suits.get(command.getFromIndex()).pop();
			lanes.get(command.getToIndex()).push(card);
		} else { // lane to lane move
			List<Card> fromLane = lanes.get(command.getFromIndex());     // use List interface to the Stack
			int firstCardToMoveIndex = fromLane.size()-command.getNumberOfCardsToMove();
			for (int i=0; i<command.getNumberOfCardsToMove(); i++) {
				Card card = fromLane.get(firstCardToMoveIndex);
				fromLane.remove(firstCardToMoveIndex);
				lanes.get(command.getToIndex()).push(card);
			}
			if (!fromLane.isEmpty() && fromLane.get(fromLane.size()-1).isFaceDown()) {
				fromLane.get(fromLane.size()-1).turnFaceUp();
				wasCardReveal = true;
			}
		}
	}
	
	public boolean isGameOver () {
		for (Stack<Card> suit : suits) {
			if (suit.size() != SUIT_SIZE) {
				return false;
			}
		}
		return true;
	}
	
	public int maxLaneSize () {
		int maxLaneSize = 0;
		for (Stack<Card> lane : lanes) {
			if (lane.size() > maxLaneSize) {
				maxLaneSize = lane.size();
			}
		}
		return maxLaneSize;
	}
	
	public Deck getDeck () {
		return deck;
	}
	
	public Stack<Card> getPile () {
		return pile;
	}
	
	public Stack<Card> getLane (int index) {
		return lanes.get(index);
	}
	
	public Stack<Card> getSuit (int index) {
		return suits.get(index);
	}
	
	public boolean wasCardReveal () {
		return wasCardReveal;
	}
}
