package com.frank;


import com.frank.PlayingCard;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SwissPlayingCard extends PlayingCard {
        
        // Making the enum public allows application programs to use the enum as constant too
        public static enum CardColor {  // define words to represent allowable card colors (instead of String)
                YELLOW, GREEN, RED, BLACK                  // These are the only valid values Java will allow
        };
        public static enum CardSuit {          // public is OK since they are constants and cannot be changed
                BALLS, ACORNS, ROSES, SHIELDS, JOKER // an be referenced using the class name. ie. no object required
                // static so it can be referenced using the class name.
                //            ie. no object required
        };
        public static enum CardValue {  // Using the fact that enums are really integers inside value to name our values
                JOKER, AS, UNUSED2, UNUSED3, UNUSED4, UNUSED5, SIX, SEVEN, EIGHT, NINE, BANNER, UNDER, OBER, KOENIG;
        };

        private static final CardValue DEFAULTCARDVALUE = CardValue.JOKER;
        private static final CardColor DEFAULTCOLOR     = CardColor.YELLOW;
        private static final CardSuit  DEFAULTSUIT      = CardSuit.JOKER;

        public SwissPlayingCard()
        {
                super(DEFAULTCARDVALUE.ordinal(), DEFAULTSUIT.toString(), DEFAULTCOLOR.toString());
        }

        public SwissPlayingCard(CardValue value, CardSuit suit) {
                super(value.ordinal(),              // Call super ctor with value passed as a numeric
                        suit.toString(),              // Call super ctor with  suit passed as a String
                        DEFAULTCOLOR.toString());     // Initially set card color to default since we cannot call amethod here

                setColor(determineColor(suit));     // reset color to color based on suit


                // Verify value passed is valid, if not set value to default value
                if (value == CardValue.UNUSED2 || value == CardValue.UNUSED3
                 || value == CardValue.UNUSED4 || value == CardValue.UNUSED5) {   // Swiss Playing cards have no 2,3,4 or 5
                        setValue(DEFAULTCARDVALUE.ordinal());
                }

        }
        private String determineColor(CardSuit suit) {
                switch (suit) {
                        case BALLS: {
                                return CardColor.YELLOW.toString();
                        }
                        case ACORNS: {
                                return CardColor.GREEN.toString();
                        }
                        case ROSES: {
                                return CardColor.RED.toString();
                        }
                        case SHIELDS: {
                                return CardColor.BLACK.toString();
                        }
                        default: {
                                return DEFAULTCOLOR.toString();
                        }
                }
        }

        @Override
        public String toString() {
                // super. is optional when calling a super class method unless you have overriden the method
                return "SwissPlayingCard: "
                        +"Value: "    + getValue()      // call super class method to retreive value of value attribute
                        +" - Color: " + getColor()      // call super class method to retreive value of color attribute
                        +" - Suit: "  + super.getSuit() // call super class method to retreive value is suit attribute
                        +" - super.toString()=" + super.toString() + "\n";  // super, required here as subclass has method with
                                                                            //    the same name.  Also, we are IN the overriden
                                                                            //    so withot super. we would be calling ourselves
                                                                            //    recursively until the system was out of resurces
        }
        // this. reference the object used to invoke the method (this.toString() - this class's toString()
        public void showCard() {
                System.out.println(this.toString());
        }
}

