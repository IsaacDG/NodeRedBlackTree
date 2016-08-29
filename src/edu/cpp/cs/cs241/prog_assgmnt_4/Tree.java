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
 * @author Isa
 *
 */
public interface Tree<V extends Comparable<V>> {
	  public void add(V value);
	  public V remove(V value);
	  public V lookup(V value);
	  public String toPrettyString();
}