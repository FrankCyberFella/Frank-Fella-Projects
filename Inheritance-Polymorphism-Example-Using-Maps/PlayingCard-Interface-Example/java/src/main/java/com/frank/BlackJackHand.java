package com.frank;

import java.util.ArrayList;

public class BlackJackHand	 implements CardHand {   // implement an interface
                                                     // this is a type of CardHand
	                                                 // it implements allthe behaviors of CardHand
	private int numCardsInHand = 2;
	
	private ArrayList<PlayingCard> aHand;
	
	public BlackJackHand() {
		aHand = new ArrayList<PlayingCard>(numCardsInHand);
	}

	// methods required because we implemented the CardHand interface
	// override the methods in the interface - same name, same return type, same parameters

	@Override // optional - good idea to code so you are sure you are properly define the method for the interface
	public ArrayList<PlayingCard> getHand() {
		return aHand;
	}

	@Override
	public void addCard(PlayingCard aCard) {
		aHand.add(aCard);
		numCardsInHand++;
	}

	@Override
	public void emptyHand() {
		aHand.clear();

	}

	@Override
	public void show() {
		for (PlayingCard aCard : aHand)
		{
			System.out.println(aCard);
		}
			return;
	}

	//
	// Additional methods not required by the CardHand interface
	//
	public void dealHand(CardDeck aDeck) {
		for (int i=0; i < numCardsInHand; i++ ) {
			aHand.add(aDeck.dealCard());
		}	
	}
	
}
	
