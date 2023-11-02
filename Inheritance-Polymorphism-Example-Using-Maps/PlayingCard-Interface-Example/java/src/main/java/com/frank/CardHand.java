package com.frank;

import java.util.ArrayList;

// interface identifies methods (behaviors) a class must implement to be this type of class
//
// If you want to be a type of CardHand, you must implement at least this behaviors
//
public interface CardHand {
	
	public ArrayList<PlayingCard> getHand();  // return the PlayingCards in the hand as an ArrayList

	public void addCard(PlayingCard aCard);   // add a PlayingCard to a hand
	
	public void emptyHand();                  // remove/empty all the cards in a hand

	public void show();                      // display all the cards in a hand

}
