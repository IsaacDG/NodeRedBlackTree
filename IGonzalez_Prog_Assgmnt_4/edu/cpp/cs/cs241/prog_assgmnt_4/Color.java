/**
 * 
 */
package edu.cpp.cs.cs241.prog_assgmnt_4;

/**
 * This is an enum used to represent Colors for the nodes used in the Red Black Tree.
 * @author Isa
 *
 */
public enum Color {
	BLACK, RED;
	
	public char letter(){
		return name().charAt(0);
	}
}
