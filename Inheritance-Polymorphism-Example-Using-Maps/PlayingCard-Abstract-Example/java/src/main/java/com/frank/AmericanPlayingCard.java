package com.frank;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AmericanPlayingCard extends PlayingCard {  // this is sub class of PlayingCard
	
	private static final int    DEFAULTCARDVALUE = 0;
	private static final String DEFAULTCOLOR     = "BLACK";
	private static final String DEFAULTSUIT      = "Joker";
	private static final int    MAXVALUE         = 13;
	private static final int    MINVALUE         = 0;
	private static Map<String,  String> suitMap  = new HashMap<String , String>();  
	private static Map<Integer, String> valueMap = new TreeMap<Integer, String>();
	
			static {             // static method to initialize maps before are ever used
		       initializeMaps();
	        }
	
	public AmericanPlayingCard() 
	{
		super(DEFAULTCARDVALUE, DEFAULTSUIT, DEFAULTCOLOR); // Call the PlayingCard ctor 
	} 

	public AmericanPlayingCard(int value, String suit) {
		super(value, suit, suitMap.get(suit));{  // method call used as a parameter
	
		if (value > MAXVALUE) {
			setValue(MAXVALUE);   // run the setValue in the super class unless there is is a setValue() in this class
		}
		if (value < MINVALUE) {
			super.setValue(MINVALUE);  // Explicitly state we want the super class method
		}
		}
		
	}

	static private void initializeMaps() {   // needs to be static due to use in static method above
		suitMap.put("SPADES"  , "BLACK");
		suitMap.put("CLUBS"   , "BLACK");
		suitMap.put("DIAMONDS", "RED");
		suitMap.put("HEARTS"  , "RED");
		
		valueMap.put(0,"Joker");
		valueMap.put(1,"Ace");
		valueMap.put(2,"Two");
		valueMap.put(3,"Three");
		valueMap.put(4,"Four");
		valueMap.put(5,"Five");
		valueMap.put(6,"Six");
		valueMap.put(7,"Seven");
		valueMap.put(8,"Eight");
		valueMap.put(9,"Nine");
		valueMap.put(10,"Ten");
		valueMap.put(11,"Jack");
		valueMap.put(12,"Queen");
		valueMap.put(13,"King");
	}
	
	@Override
	public String toString() {
		return "AmericanPlayingCard: " 
	          +"Value: "  + valueMap.get(getValue())
	          +" - Color: " + suitMap.get(getSuit())
			  +" - Suit: " + suit	    // we have direct access to the protected data in the super class
		//	  +" - Suit: "  + getSuit() // use the super class method to get suit because it is private super class
			  +" - super.toString()=" + super.toString() + "\n";
	}

	public void showCard() {
		System.out.println(this.toString());
	}

}
