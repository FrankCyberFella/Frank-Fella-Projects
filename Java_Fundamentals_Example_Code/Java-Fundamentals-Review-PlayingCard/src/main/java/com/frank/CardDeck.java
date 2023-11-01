package com.frank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.frank.PlayingCard.CardSuit;
import com.frank.PlayingCard.CardValue;
/***************************************************************************************************
 * Class to Simulate a Standard Deck of  American Playing Cards
 *
 * CardDeck has a set of PlayingCards
 *
 *     "has-a" indicates containership - one class contains instances of another
 *     "a set of" indicates we need a way to define a group of instances
 *         1. simple array - we need know how many we have when we start, cannot add/remove elements
 *         2. ArrayList - we don't need to know how many we have when we start, can add/remove elements
 *         3. Map - associate a key with a value
 *         4. Stack - LIFO structure where elements are removed when referenced
 *         5. Queue - FIFO structure where elements are removed when referenced
 *         6. Set - like an ArrayList, but does not allow duplicate objects
 *
 *  A CardDeck was most like a Queue, so use Queue of PlayingCards for our CardDeck
 ***************************************************************************************************/

public class CardDeck {
	/***************************************************************************************************
	 * Standard Deck represented as a Queue / LinkedList
	 *
	 * LinkedList was used, so we can use Collections Class methods like shuffle()
	 ***************************************************************************************************/
	LinkedList<PlayingCard> deckOfCards = new LinkedList<PlayingCard>();  // Define a Queue to hold PlayingCards
	
	/***************************************************************************************************
	 * Number of non-Joker cards per suit
	 ***************************************************************************************************/
	private static final int numberCardsForSuit = 13; 

	/***************************************************************************************************
	 * Default Constructor
	 ***************************************************************************************************/
	
	public CardDeck() {
		// Using a public method from the ctor
		resetDeck(true); // create deck with jokers
	}

	/***************************************************************************************************
	 * Display cards in deck
	 ***************************************************************************************************/
	
	public void showDeck() {
		System.out.println("Number of Cards in Deck: " + deckOfCards.size());
		for (PlayingCard aCard : deckOfCards) {
			aCard.showCardWithHash();
		}
	}
	/***************************************************************************************************
	 * Answer question are they any cards in the deck
	 ***************************************************************************************************/
	
	public boolean anyCardsInDeck() {
		if (deckOfCards.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/***************************************************************************************************
	 * Deal a card from the top of the deck
	 ***************************************************************************************************/
	public PlayingCard dealCard() {
		if (deckOfCards.size() > 0) {
			return deckOfCards.remove(); // .remove() - return first element and remove it
		}
		else {
			 return null;
		}
	}
	/***************************************************************************************************
	 * Create a deck with or without Jokers
	 ***************************************************************************************************/
	public void resetDeck(boolean withJoker) {
		deckOfCards.clear();              // Remove any existing cards from the deck

		for (int i = 1; i <= numberCardsForSuit; i++) {    // Generate a set of clubs
			deckOfCards.add(new PlayingCard(i, PlayingCard.CardSuit.CLUB));
		}
		for (int i = 1; i <= numberCardsForSuit; i++) {    // Generate a set of hearts
			deckOfCards.add(new PlayingCard(i, PlayingCard.CardSuit.HEART));
		}
		for (int i = 1; i <= numberCardsForSuit; i++) {    // Generate a set of spades
			deckOfCards.add(new PlayingCard(i, PlayingCard.CardSuit.SPADE));
		}
		for (int i = 1; i <= numberCardsForSuit; i++) {     // Generate a set of diamonds
			deckOfCards.add(new PlayingCard(i, PlayingCard.CardSuit.DIAMOND));
		}

		if (withJoker) {  // If Jokers requested, add them
			deckOfCards.add(new PlayingCard(CardValue.JOKER, CardSuit.JOKER));
			deckOfCards.add(new PlayingCard(CardValue.JOKER, CardSuit.JOKER));
		}
	}
	/***************************************************************************************************
	 * Create New Deck with Shuffled Cards
	 ***************************************************************************************************/
	public void shuffleDeck() {
		resetDeck(false);                  // Reload deck without Jokers
		Collections.shuffle(deckOfCards);  // Use Collections class shuffle() to randomize cards in deck
	}
	
	/***************************************************************************************************
	 * Remove any Jokers from a Deck
	 ***************************************************************************************************/
	public int removeJokers() {
		// .removeAll(CollectionsClassObject) so... we create an ArrayList of JOKER so we could use
		ArrayList<PlayingCard> aJoker = new ArrayList<PlayingCard>();  // Needed to hold a Joker
		aJoker.add(new PlayingCard(CardValue.JOKER, CardSuit.JOKER));  // Add a Joker to ArrayList
		int numCardsBefore = deckOfCards.size();                       // Remember # cards before removal
		deckOfCards.removeAll(aJoker);                                 // Remove any Jokers from deck
		return numCardsBefore - deckOfCards.size();                    // Return # of Jokers removed
	}
}