package com.frank;

/***************************************************************************************************
 * Class to Simulate an American Playing Card
 ***************************************************************************************************/

public class PlayingCard {
	/***************************************************************************************************
	 * Define constants to represent card attributes
	 * 
	 * public is OK since they are constants and cannot be changed
	 * 
	 * static so it can be referenced using the class name (enum name). ie. no object required
	 * 
	 * enum - define a set of constant values that may be referenced as a data type
	 ***************************************************************************************************/
	public static enum CardColor {
//   Java:	0	1
		BLACK, RED                         
	};

	public static enum CardSuit {          // public is OK since they are constants and cannot be changed
//   Java: 0	1      2       3       4
		SPADE, CLUB, HEART, DIAMOND, JOKER // static so it can be referenced using the class name. ie. no object required
	};

	// enums are assigned integer values starting at 0 internally by Java
	public static enum CardValue {
//   Java:	0	1    2     3      4     5    6     7      8     9     10   11     12    13
		JOKER, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	};

	/***************************************************************************************************
	 * Define constants to represent defaults for card attributes
	 * 
	 * protected so subclasses may access is OK since they are constants and cannot be changed
	 * 
	 * static so it can be referenced using the class name. ie. no object required
	 ***************************************************************************************************/
	//                                                  enumName.enumValue
	protected static final CardValue DEFAULTCARDVALUE = CardValue.JOKER;
	protected static final CardColor DEFAULTCOLOR     = CardColor.BLACK;
	protected static final CardSuit  DEFAULTSUIT      = CardSuit.JOKER;
	/***************************************************************************************************
	 * Member data
	 * 
	 * private to protect as prescribed by encapsulation - method must be used to access the data 
	 ***************************************************************************************************/
	// Member instance variables are enum data types - Java enforces valid values
	private CardValue value;
	private CardColor color;
	private CardSuit  suit;

	/***************************************************************************************************
	 * Default constructor
	 ***************************************************************************************************/
	public PlayingCard() 
	{
		value = DEFAULTCARDVALUE;
		suit  = DEFAULTSUIT;
		setColor(suit);     // Set color in object based on suit - NOTE: suit must have a value before this
	} 
	/***************************************************************************************************
	 * 2-arg (CardValue and CardSuit) constructor
	 ***************************************************************************************************/
	public PlayingCard(CardValue value, CardSuit suit) {
		this.value = value;   // Set value in object to value passed as argument
		setColor(suit);       // Set color in object based on suit passed as argument
		this.suit = suit;     // Set suit in object to suit passed as argument
	}
	/***************************************************************************************************
	 * 2-arg (int and CardSuit) constructor
	 ***************************************************************************************************/
	//  new PlayingCard(1, SPADE)
	public PlayingCard(int value, CardSuit suit) {
		this.value = setValue(value);  // Set value in object based on int value passed as argument
		setColor(suit);                // Set color in object based on suit passed as argument
		this.suit = suit;              // Set suit in object to suit passed as argument
	}
	/***************************************************************************************************
	 * getter methods
	 ***************************************************************************************************/
	public CardValue getValue() { // Return the CardValue stored in the object
		return value;
	}

	// enum .ordinal() method returns the int value Java assigned to enum
	public int getIntValue() {    // Return integer value of CardValue stored in the object
		return value.ordinal();
	}
	
	public CardColor getColor() { // Return CardColor stored in the object
		return color;
	}
	
	public CardSuit getSuit() {   // Return the CardSuit stored in the object
		return suit;
	}
	/***************************************************************************************************
	 * setter methods
	 ***************************************************************************************************/
	//    PlayingCardObject.set(1) - set card value using an int
	//    value is a enum in this class, so we need to convert an int to the enum
	public CardValue setValue(int ivalue) {  // Set the CardValue based on an int value
		/*
		 * switch is an alternative to a set if-else-if-else statements (nested-if)
		 *
		 * switch(value) - the value is what you want the switch to check
		 * following the switch statement is a series of case statements with values
		 *      you want switch to compare to
		 */
		switch (ivalue) {          // check the value in variable ivalue
		case 1:                    // if ivalue == 1 ...
			return CardValue.ONE;  //    return the enum associated with 1
		case 2:                    // else if ivalue == 2 ...
			return CardValue.TWO;  //        return the enum associated with 2
		case 3:                    //      else if ivalue == 3 ...
			return CardValue.THREE;//              return the enum associated with 3
		case 4:                    //           else.....
			return CardValue.FOUR;
		case 5:
			return CardValue.FIVE;
		case 6:
			return CardValue.SIX;
		case 7:
			return CardValue.SEVEN;
		case 8:
			return CardValue.EIGHT;
		case 9:
			return CardValue.NINE;
		case 10:
			return CardValue.TEN;
		case 11:
			return CardValue.JACK;
		case 12:
			return CardValue.QUEEN;
		case 13:
			return CardValue.KING;
		default:
			return CardValue.JOKER;
		}
	}

	// private was used so only members can use the method
	private void setColor(CardSuit suit) {  // Set the color based on the suit of the object
		// cases in a switch may be "stacked" to simulate an OR ==
		// unless the case has a return statement
		//     a break must be coded to exit the switch statement
		//     if a break is omitted at the end of the case, you fall through to the next case
		//                which does its processing whether it should or not
		switch (suit) {                    // Check the value in suit
			case SPADE:                        // if == SPADE
			case CLUB:                         //    == CLUB
				this.color = CardColor.BLACK;  //    set the color to BLACK
				break;                         //    and exit the switch
			case DIAMOND:
			case HEART:
				this.color = CardColor.RED;
				break;
			default:                           // no previous case is true, do this
				this.color = DEFAULTCOLOR;
		}  // no break required after the last case - the } indicates end of switch
	}

	public void setSuit(CardSuit suit) {  // Set the suit of the object 
		this.suit = suit;
	}

	/***************************************************************************************************
	 * Object super class overrides
	 ***************************************************************************************************/
@Override   // Ask compiler to assure this is a valid super class override

	public String toString() {                         // Return a String representation of the object
		//-------------------------------------------------------------------------------------------------
		// StringBuffer is a mutable version of String - allows a new value to be assign
		//                                               without creating a new object
		//
		// String name = "Frank"; // Java will allocate memory for "Frank"
		//                        //           return its location
		//                        //           store it in reference variable name
		//
		// name = "Grace";       // Java will allocate memory for "Grace"
	    //		                 //           return its location
	    //		                 //           store it in reference variable name
		//                       // the memory holding "Frank" is released
	    //
	    // StringBuffer attempts to reuse memory already allocated
		//-------------------------------------------------------------------------------------------------
		StringBuffer stringCard = new StringBuffer();  // Define an object to hold String version of object

		int firstColumnSize = 16;                      // position of first tab position of screen line 

		// StringBuffer uses the .append() to concatenate rather than + like String
		stringCard.append("Value: " + value);          // Add literal to StringBuffer
		stringCard.append(" (" + getIntValue() + ")"); // Add integer value of CardValue to StringBuffer
		if (stringCard.length() < firstColumnSize) {   // If current StringBuffer size less than first tab position
			stringCard.append("\t");                   //     tab to first tab position
		}
		stringCard.append("\tSuit: " + suit);          // Add tab and suit value
		stringCard.append("\tColor: " + color);        // Add tab and color value
		return stringCard.toString();                  // Convert StringBuffer to String and return
	}

@Override   // Ask compiler to assure this is a valid super class override

public boolean equals(Object otherObject) {   // Compare two PlayingCards for equality - note generic Object parameter
	
		if (otherObject == this) {   // if object being compared to itself
			return true;             // objects are equal
		}
		else {
			// instanceof operator return true if the object to the left is a class specified on right
			if ((otherObject instanceof PlayingCard)) {            // If the object being compared to is the same class as object 
				PlayingCard otherCard = (PlayingCard) otherObject; // Define a PlayingCard object from object being compared to
				return (this.value == otherCard.value && this.color == otherCard.color && this.suit == otherCard.suit);
			}
	    return false;    // Required so IntelliJ realizes we are returning a boolean
		}
		
}
@Override   // Ask compiler to assure this is a valid super class override

	public int hashCode() {      // Generate hashCode for object if Java needs one e.g. for a HashMap
	                             // HashCode is a unique value representing an instance of an object
		int hashValue = 17;      // A prime number used in calculating the HashCode
		int primeMultipler = 59; // A prime numbet used in calculating the HashCode

		hashValue = hashValue * primeMultipler + value.ordinal();  // Same values used in equals() 
		hashValue = hashValue * primeMultipler + suit.ordinal();   //     should be used in the 
		hashValue = hashValue * primeMultipler + color.ordinal();  //        HashCode method
		return hashValue;
	}

	/***************************************************************************************************
	 * Miscellaneous class methods
	 ***************************************************************************************************/
	public void showCard() {                 // Display an instance of a PlayingCard
		System.out.println(this.toString());
	}

	public void showCardWithHash() {          // Display an instance of a PlayingCard wirh HashCode
		System.out.println(this.toString() + "\thashCode: " + hashCode());
	}
}
