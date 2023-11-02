package com.frank;

import java.util.ArrayList;
import java.util.List;

public class UsePlayingCards {
// This is our application program which will instantiate object and use their methods to manipulate them
// We know this is the application program because it has the main() method
	public static void main(String[] args) {

		AmericanPlayingCard aUSACard = new AmericanPlayingCard(AmericanPlayingCard.CardValue.ONE, AmericanPlayingCard.CardSuit.HEART);
		System.out.println("aUSACard is :");
		aUSACard.showCard();  // runs the AmericanPlayingCard showCard()

		AmericanPlayingCard aUSACard2 = new AmericanPlayingCard(AmericanPlayingCard.CardValue.KING, AmericanPlayingCard.CardSuit.SPADE);
		System.out.println("aUSACard2 is :");
	//     object.method() - the class of the object determines which method is run
		aUSACard2.showCard();  // runs the AmericanPlayingCard showCard()

		ItalianPlayingCard anItalianCard1 = new ItalianPlayingCard(ItalianPlayingCard.CardValue.CAVALLO, ItalianPlayingCard.CardSuit.COINS);
		System.out.println("anItalianCard1 is :");
		anItalianCard1.showCard();  // runs the ItalianPlayingCard showCard()

		SwissPlayingCard aSwissCard1 = new SwissPlayingCard(SwissPlayingCard.CardValue.AS, SwissPlayingCard.CardSuit.ROSES);
		System.out.println("aSwissCard1 is :");
		aSwissCard1.showCard();    // runs the SwissPlayingCard showCard()

		System.out.println("-".repeat(80) + "\n Polymorphism examples start here \n" + "-".repeat(80));

		PlayingCard aCard;    // define a reference to the super class which may or may not be abstract

		System.out.println("Assigned an ItalianPlayingCard to PlayingCard reference");
		aCard = anItalianCard1; // Assign a subclass object to the super class reference
		aCard.showCard();       // Use the super class reference to run the commom/overridden methods

		System.out.println("Assigned an AmericanPlayingCard to PlayingCard reference");
		aCard = aUSACard;       // Assign a subclass object to the super class reference
		aCard.showCard();       // Use the super class reference to run the commom/overridden methods

		System.out.println("Assigned an SwissPlayingCard to PlayingCard reference");
		aCard = aSwissCard1;    // Assign a subclass object to the super class reference
		aCard.showCard();       // Use the super class reference to run the commom/overridden methods

		System.out.println("-".repeat(80) + "\n Process an Arraylist of subclass objects using Polymorphism \n" + "-".repeat(80));

        // Define an ArrayList of super class references
		List<PlayingCard> someCards = new ArrayList<PlayingCard>();

		// Add subclass class objects to the super class ArrayList
		someCards.add(aSwissCard1);   // its OK to assign a sub class object to a super class reference
		someCards.add(aUSACard);      // its OK to assign a sub class object to a super class reference
		someCards.add(anItalianCard1);// its OK to assign a sub class object to a super class reference

		// Process the elements in the ArrayList using the super class reference
		for(PlayingCard theCard : someCards){
			theCard.showCard();  // use the super class reference to process an element in the ArrayList
			                     // the correct subclass method is run for the current element processed automatically
			                     // so we don't have to worry about which class of object is being processed
		}
	}  // End of main()

}  // End of class that holds main()
