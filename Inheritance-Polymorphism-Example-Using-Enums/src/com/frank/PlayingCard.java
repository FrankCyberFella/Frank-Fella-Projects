package com.frank;

// This defines a generic PlayingCard, since it's abstract it CANNOT ne instantiated
//      it can only be used as a super class
// abstract class provides basic/generic attributes or behaviors for a class
// A generic PlayingCard card cannot exist in teh real world

public abstract class PlayingCard {
	
	private int value;    
	private String color;    
	private String suit;     
	
	
	public PlayingCard(int value, String suit, String color) {
		this.value = value;
		this.suit  = suit;
		this.color = color;
	}

	// a method is defined with: access-type   return-type   name(parameters)
	//                             public       data-type - type of data returned bythe method
	//                             private      void      - no data is returned by the method
	//
	// public void - anyone with object of the class may use the method (public)
	//               no data is returned from the method (void)
	//
	// The body of the method follows the definition inside { }

	public int getValue()    { return value; }
	public String getColor() {
		return color;
	}
	public String getSuit()  { return suit;  }

	public void setValue(int value)    { this.value = value; }
	public void setColor(String color) {
		this.color = color;
	}
	public void setSuit(String suit)   { this.suit  = suit;  }

	// If the behavior of the super class methods is not what you want - you override them to do what you want
	//
	// Right now, we don't like what the Object class toString() and equals() methods do, so we override them

	@Override // Optional - Ask compiler to verify this is proper override of the super class method
	          //            a proper override has same return-type, name, parameters
	          // we are overriding the Object class toString() method which:
	          //            returns a String, is called toString() and receives no parameters
	public String toString() {
		return "PlayingCard [value=" + value + ", color=" + color + ", suit=" + suit + ", getValue()=" + getValue()
				+ ", getColor()=" + getColor() + ", getSuit()=" + getSuit() + "]";
	}

	@Override // Optional - Ask compiler to verify this is proper override of the super class method
	          //            a proper override has same return-type, name, parameters
	          // we are overriding the Object class equals() method which:
 	          //            returns a boolean, is called equals() and receives an Object class object
	          // Note: Using an Object class object usually requires casting to another class to use
	          //       an Object class object is a generic object and Java needs to know the specific class
	public boolean equals(Object obj) {  // return a boolean and receive an Object class object I am calling obj
		if (this == obj) {     // if the object being compared is the same as the object being compared to...
			return true;       //    they are equal
		}
		if (obj == null) {     // if the object being compared tos null...
			return false;      //    they are not equal
		}
		if (getClass() != obj.getClass()) {  // if the class of the object is not the same as the class of the object being compared to...
			return false;                    //    they are not equal
		}
		PlayingCard other = (PlayingCard) obj;  // We need to create a PlayingCard object from the generic object
		                                        // by casting to PlayingCard class anc assign to new object so we can us it
		if (color != other.color) {
			return false;
		}
		if (suit != other.suit) {
			return false;
		}
		if (value != other.value) {
			return false;
		}
		return true;
	}

// super class is required to have any method where Polymorphism might be used
// making the method abstract indicates any subclass must override in order to be a subclass of this class
// I don't know what this behavior is supposed to do, I just know its needs to be implemented by a subclass
//  if you want to be a PlayingCard you have to have a showCard() method defined
public abstract void showCard();  // required for Polymorphism to work; Note: abstract method has no body/processing
	                              //     ends with a ; with no {}

} // End of PlayingCard class
