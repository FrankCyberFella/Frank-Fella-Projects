package com.frank;

public class TestCardApp {

	public static void main(String[] args) {

		CardDeck myDeck               = new CardDeck();
		BlackJackHand myBlackJackHand = new BlackJackHand();

		System.out.println("-----------------------------------------------------");
		System.out.println("-- Showing Cards in Deck                           --");
		System.out.println("-----------------------------------------------------");

		myDeck.showDeck();

		System.out.println("-----------------------------------------------------");
		System.out.println("-- Shuffling Deck                                  --");
		System.out.println("-----------------------------------------------------");
		
		myDeck.shuffleDeck();
		myDeck.showDeck();

		System.out.println("-----------------------------------------------------");
		System.out.println("-- Dealing a BlackJack Hand                        --");
		System.out.println("-----------------------------------------------------");

		myDeck.resetDeck(false);
		myDeck.shuffleDeck();

		myBlackJackHand.dealHand(myDeck);
		
		myBlackJackHand.show();
		
		System.out.println("-----------------------------------------------------");
		System.out.println("-- Adding a Card to a BlackJack Hand               --");
		System.out.println("-----------------------------------------------------");
	
		myBlackJackHand.addCard(myDeck.dealCard());
		
		for(PlayingCard aCard : myBlackJackHand.getHand()) {
			System.out.println(aCard);
		}
		
	}
}



