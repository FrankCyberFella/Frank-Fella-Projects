package com.frank;

// an abstract class is a class that is never meant to be instantiated
//    you CANNOT instantiate an abstract class
//
// abstract classes are meant to be super classes
//
// an abstract class is a generic description of an object - does not have all the details for an object

abstract public class PlayingCard {  // Generic PlayingCard - cannot be instantiated
	
	private   int    value;  // private indicates only members of the class can access
	private   String color;
	protected String suit;   // protected indicates members of the class and sub class can access directly
	                         // sub classes do not need to use getter/setters to access protected data
	
	public PlayingCard(int value, String suit, String color) {
		this.value = value;
		this.suit  = suit;
		this.color = color;
	}
	
	public int getValue() {
		return value;
	}
	public String getColor() {
		return color;
	}
	public String getSuit() {
		return suit;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	@Override   // Ask compiler to verify this is a proper Override - same name, same parameters, same return-type
	public String toString() {
		return "PlayingCard [value=" + value + ", color=" + color + ", suit=" + suit + ", getValue()=" + getValue()
				+ ", getColor()=" + getColor() + ", getSuit()=" + getSuit() + "]";
	}
	
	@Override   // Ask compiler to verify this is a proper Override - same name, same parameters, same return-type
	public boolean equals(Object obj) {  // Receive any type of Object
		if (this == obj) {  // If compare a PlayingCard to itself
			return true;
		}
		if (obj == null) {  // If compare to an undefined PlayingCard
			return false;
		}
		if (getClass() != obj.getClass()) {  // If compare to an object of a different class
			return false;                    // getClass() return the Class of an object
		}
		PlayingCard other = (PlayingCard) obj;  // Define a PlayingCard for the object that was passed
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

	// To allow for Polymorphism a super class must define any method that is to be Polymorphic
	//          even if it doesn't do anything
	// The sub class may override the method to actually do something that it wants it to do
	//
	// making a method abstract indicates this class does not know what the method should do
	//        and requires any sub class to override the method
	//
	// to make a method abstract - code the abstract property in the method signature and no body (; instead {})
	//
	// if any method in a class is abstract, the class must be abstract
	//
	// abstract on a method means we don't know the details, we just know we need it
	//
	// abstract methods are frequently used for Polymorhism
	//       and/or the super wants to require a sub class to implement the behavior
	//
	// any sub class MUST define an abstract method defined in the super class

	abstract public void showCard(); // Required for Polymorphism - making it abstract requires sub class to override
		
}
