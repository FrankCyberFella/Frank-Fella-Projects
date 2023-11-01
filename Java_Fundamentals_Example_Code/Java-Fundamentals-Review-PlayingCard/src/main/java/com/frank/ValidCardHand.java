package com.frank;

/***********************************************************************************************
 * an interface lists methods/behaviors that must be implemented if
 * a class wants to be a "type of" the interface
 *
 * If a class wants to be a type of ValidCardHand, it must include these methods
 ***********************************************************************************************/

public interface ValidCardHand {
	
	public void clearHand();                // Remove all cards from a hand
	public void show();                     // Display all cards in a hand 
	public void dealHand(CardDeck aDeck);   // Fill a hand from a CardDeck object
	public void addCard(PlayingCard aCard); // Add card to a hand
}
