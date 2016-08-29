/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodríguez
 *
 * Programming Assignment #4
 *
 * Implementation of Red Black Tree using Nodes
 *
 * Isaac Gonzalez
 */
package edu.cpp.cs.cs241.prog_assgmnt_4;

/**
 * This class is the test driver for the RBT
 * @author Isa
 *
 */
public class program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RBT<Integer> tree = new RBT<>();
		
		
		tree.add(17);
		System.out.println(tree.toPrettyString());
		tree.add(33);
		System.out.println(tree.toPrettyString());
		tree.add(50);
		System.out.println(tree.toPrettyString());
		tree.add(70);
		System.out.println(tree.toPrettyString());
		tree.add(75);
		System.out.println(tree.toPrettyString());
		tree.add(53);
		System.out.println(tree.toPrettyString());
		tree.add(51);
		System.out.println(tree.toPrettyString());
		tree.add(54);
		System.out.println(tree.toPrettyString());
		tree.remove(17);
		System.out.println(tree.toPrettyString());
		tree.remove(25);
		System.out.println(tree.toPrettyString());
		tree.remove(55);
		System.out.println(tree.toPrettyString());
		tree.remove(42);
		System.out.println(tree.toPrettyString());
		tree.remove(40);
		System.out.println(tree.toPrettyString());
		tree.remove(54);
		tree.add(41);
		System.out.println(tree.toPrettyString());
		tree.add(41);
		System.out.println(tree.toPrettyString());
		tree.add(1);
		System.out.println(tree.toPrettyString());
		tree.add(5);
		System.out.println(tree.toPrettyString());
		tree.remove(70);
		System.out.println(tree.toPrettyString());
		tree.add(41);
		System.out.println(tree.toPrettyString());
		tree.remove(33);
		System.out.println(tree.toPrettyString());
		tree.remove(41);
		System.out.println(tree.toPrettyString());

	}

}
