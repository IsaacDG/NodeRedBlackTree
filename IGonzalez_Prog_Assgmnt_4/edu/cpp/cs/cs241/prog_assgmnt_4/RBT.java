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
 * This class constructs a Binary Search Tree that is balanced using the Red Black Tree
 * Invariants.
 * @author Isa
 *
 */
public class RBT<V extends Comparable<V>> implements Tree<V> {

	/**
	 * This field is a Node that is designated as the root of the tree.
	 */
	RBTNode<V> root;

	/**
	 * This field holds the amount of nodes present in the tree at any time.
	 */
	int size;

	/**
	 * This is the default constructor that sets the root to null and the size to 0.
	 */
	public RBT() {
		root = null;
		size = 0;
	}

	/**
	 * This function is used to add a value to the tree. The base case is when the tree is empty
	 * which means the root is null. If that is the case, it simply creates a new node and colors it black.
	 * Else, it uses the addHelper function to add the value to the tree.
	 */
	@Override
	public void add(V value) {

		if (root == null) {
			root = new RBTNode<V>(null, value);
			root.setColor(Color.BLACK);
			++size;
		} else {
			addHelper(root, value);
			++size;
		}

	}

	/**
	 * This method is a recursive method that is used to add a value to the 
	 * Red Black Tree. It traverses the tree to the correct location by comparing the
	 * value to be added with the value in the origin node. Once the location is found where
	 * there is an empty node(meaning there is room to add), it is added to the tree by setting
	 * the link of the current node to the new node with the value.
	 * Then, fixTreeAdd is called on the newly added node.
	 * @param origin
	 * Otherwise known as the cursor node. This is the node that the algorithm is currently at
	 * and will compare the value with the value in this node.
	 * @param value
	 * The value to be added.
	 */
	public void addHelper(RBTNode<V> origin, V value) {

		if (value.compareTo(origin.getData()) < 0
				|| value.compareTo(origin.getData()) == 0) {
			if (origin.getLeft().getData() == null) {
				origin.setLeft(new RBTNode<V>(origin, value));
				fixTreeAdd(origin.getLeft());
			} else {
				addHelper(origin.getLeft(), value);
			}
		} else {
			if (origin.getRight().getData() == null) {
				origin.setRight(new RBTNode<V>(origin, value));
				fixTreeAdd(origin.getRight());
			} else {
				addHelper(origin.getRight(), value);
			}
		}

	}

	/**
	 * This method is used to keep the tree balanced according to the Red Black Tree
	 * Invariants. The only base case is when x is the root, and if this happens
	 * then we simply color x black and the algorithm terminates. Otherwise, Case One and
	 * Two of fixTreeAdd are checked.
	 * @param x
	 * The node that we are dealing with when checking for invariants that are broken.
	 * @return
	 * dummy value
	 */
	public RBTNode<V> fixTreeAdd(RBTNode<V> x) {
		if (x == root) {
			root.setColor(Color.BLACK);
		} else {
			if (x.getParent().getColor() == Color.RED) {
				RBTNode<V> uncle = getUncle(x);
				RBTNode<V> grandParent = getGrandparent(x);
				if (uncle.getColor() == Color.RED) {
					fixTreeAddCaseOne(x, uncle, grandParent);
				} else {
					fixTreeAddCaseTwoThree(x, uncle, grandParent);
				}
			}
		}
		return null;
	}

	/**
	 * This is case one of fixTreeAdd. If this case occurs, that means that x's uncle is RED
	 * as well as x's parent. To deal with this case, x's parent is colored BLACK, x's uncle
	 * is colored BLACK, and x's grandparent is colored RED. fixTreeAdd is then called on x's
	 * grandparent to continue the process.
	 * @param x
	 * The node that we are focused on
	 * @param uncle
	 * X's uncle.
	 * @param grandParent
	 * X's grandparent.
	 */
	public void fixTreeAddCaseOne(RBTNode<V> x, RBTNode<V> uncle,
			RBTNode<V> grandParent) {
		x.getParent().setColor(Color.BLACK);
		uncle.setColor(Color.BLACK);
		grandParent.setColor(Color.RED);
		fixTreeAdd(grandParent);
	}

	/**
	 * This method deals with Case Two and Three of fixTreeAdd. This method is called when x's
	 * uncle is BLACK. First, the algorithm checks if x is an internal or external node by
	 * seeing where it is located with relation to its grandparent. Once this is determined,
	 * Case 2 or 3 is called depending on whether or not it is an internal node. If it is internal,
	 * a leftRotate is called on its parent and fixTreeAdd is called on x's new left child. Otherwise,
	 * a rightRotate is called on the grandparent and the grandparent is recolored along with its
	 * right child. These cases are mirrored for the case in which x is part of a right subtree.
	 * @param x
	 * The node we are focused on.
	 * @param uncle
	 * X's uncle.
	 * @param grandParent
	 * X's grandparent
	 */
	public void fixTreeAddCaseTwoThree(RBTNode<V> x, RBTNode<V> uncle,
			RBTNode<V> grandParent) {
		if (x.getParent() == grandParent.getLeft()) { // left sub
			if (x.getParent().getRight() == x) { // internal node?
				leftRotate(x.getParent());
				fixTreeAdd(x.getParent().getLeft());
			} else {
				rightRotate(grandParent); // Case 3
				grandParent.setColor(Color.BLACK);
				grandParent.getRight().setColor(Color.RED);
				fixTreeAdd(root);
			}
		} else { // right sub
			if (x.getParent().getLeft() == x) { // internal node?
				rightRotate(x.getParent());
				fixTreeAdd(x.getParent().getRight());
			} else {
				leftRotate(grandParent); // Case 3
				grandParent.setColor(Color.BLACK);
				grandParent.getLeft().setColor(Color.RED);
				fixTreeAdd(root);
			}
		}
	}

	/**
	 * This method is used to balance the tree based on the Red Black Tree Invariants
	 * after the removal of a node. Depending on the coloring of x's sibling, w, the cases
	 * are dealth with. If w is RED, case 1 is called. Else, if w has two BLACK children, case
	 * 2 is called. Else, the only cases that are left are 3 and 4 so the CaseThreeFour method
	 * is called.
	 * @param x
	 * The node to call fixTreeRemove on.
	 * @return
	 * dummy value
	 */
	public RBTNode<V> fixTreeRemove(RBTNode<V> x) {
		if (x != root) {
			RBTNode<V> w = getSibling(x);

			if (w.getColor() == Color.RED) { // CASE 1
				fixTreeRemoveCaseOne(x, w);
			} else { // w is BLACK, CASE 2, 3, 4 handled here
				if (w.getRight().getColor() == Color.BLACK
						&& w.getLeft().getColor() == Color.BLACK) { // CASE 2
					fixTreeRemoveCaseTwo(x, w);
				} else { // w has RED child.
					fixTreeRemoveCaseThreeFour(x, w);
				}
			}
		}
		return null;
	}

	/**
	 * This method deals with case 1 of fixTreeRemove. The method simply rotates and recolors
	 * nodes based on the position of x in the tree. After the rotation and recoloring are done,
	 * fixTreeRemove is called recursively depending on where x was originall in the tree.
	 * @param x
	 * The node we are focused on.
	 * @param w
	 * X's sibling.
	 */
	public void fixTreeRemoveCaseOne(RBTNode<V> x, RBTNode<V> w) {
		if (x.getParent().getRight() == w) {
			leftRotate(x.getParent());
			x.getParent().getLeft().setColor(Color.RED);
			x.getParent().setColor(Color.BLACK);
			x.getParent().getRight().setColor(Color.BLACK);
			fixTreeRemove(x.getLeft());
		} else {
			rightRotate(x.getParent());
			x.getParent().getRight().setColor(Color.RED);
			x.getParent().setColor(Color.BLACK);
			x.getParent().getLeft().setColor(Color.BLACK);
			fixTreeRemove(x.getRight());
		}
	}

	/**
	 * This method deals with case 2 of fixTreeRemove. All that is happening here
	 * is the recoloring of x's sibling w to RED, and the recoloring of x's parent
	 * if it is red.
	 * @param x
	 * The node we are focused on.
	 * @param w
	 * X's sibling.
	 */
	public void fixTreeRemoveCaseTwo(RBTNode<V> x, RBTNode<V> w) {
		w.setColor(Color.RED);
		if (x.getParent().getColor() == Color.RED) {
			x.getParent().setColor(Color.BLACK);
		} else {
			if (x.getParent() != root) {
				fixTreeRemove(x.getParent());
			}
		}
	}

	/**
	 * This method deals with case 3 and 4 of fixTreeRemove. First, the location of
	 * w is determined to know which version of the algorithm should be called. Once
	 * this is determined, case 3 is called if w's red child is internal or external.
	 * Case 4 is a terminal case so fixTreeRemove is called on the root of the tree if it
	 * occurs.
	 * @param x
	 * The node we are focused on.
	 * @param w
	 * X's sibling.
	 */
	public void fixTreeRemoveCaseThreeFour(RBTNode<V> x, RBTNode<V> w) {
		RBTNode<V> parent = w.getParent();

		if (parent.getRight() == w) { // right sub
			if (w.getLeft().getColor() == Color.RED
					&& w.getRight().getColor() == Color.BLACK) {
				rightRotate(w);
				w.setColor(Color.BLACK);
				w.getRight().setColor(Color.RED);
				fixTreeRemove(x);
			} else {
				// Case 4
				Color green = x.getParent().getColor();
				leftRotate(x.getParent());
				x = x.getParent();
				x.getParent().setColor(green);
				x.getParent().getRight().setColor(Color.BLACK);
				x.setColor(Color.BLACK);
				fixTreeRemove(root);
			}
		} else {
			if (w.getRight().getColor() == Color.RED
					&& w.getLeft().getColor() == Color.BLACK) {
				leftRotate(w);
				w.setColor(Color.BLACK);
				w.getLeft().setColor(Color.RED);
				fixTreeRemove(x);
			} else {
				// Case 4
				Color green = x.getParent().getColor();
				rightRotate(x.getParent());
				x = x.getParent();
				x.getParent().setColor(green);
				x.getParent().getLeft().setColor(Color.BLACK);
				x.setColor(Color.BLACK);
				fixTreeRemove(root);
			}
		}
	}

	/**
	 * This is a helper method that passes in a boolean that corresponds to whether
	 * the node that we removed in the remove method was red. fixTreeRemove is only called
	 * when the node that we removed is not red.
	 * @param red
	 * Boolean that represents whether the node that was removed was red.
	 * @param origin
	 * Node to call fixTreeRemove on.
	 */
	public void fixTreeRemoveHelper(boolean red, RBTNode<V> origin) {
		if (!red) {
			fixTreeRemove(origin);
		}
	}

	/**
	 * This method simply performs a right rotate on any node in a tree. It uses temp
	 * variables for nodes and finishes by swapping the values in nodes 'a' and 'b' instead
	 * of relinking to avoid issues with reconnecting to a parent.
	 * @param x
	 * The node to call rotate on.
	 */
	public void rightRotate(RBTNode<V> x) {
		RBTNode<V> temp = x.getLeft();
		x.setLeft(x.getRight());
		x.setRight(temp);

		RBTNode<V> temp2 = x.getRight().getRight();
		temp = x.getLeft();
		x.setLeft(x.getRight().getLeft());
		x.getRight().setLeft(temp2);
		x.getRight().setRight(temp);

		V tempData = x.getData();
		x.setData(x.getRight().getData());
		x.getRight().setData(tempData);

	}

	/**
	 * This method simply performs a left rotate on any node in a tree. It uses temp
	 * variables for nodes and finishes by swapping the values in nodes 'a' and 'b' instead
	 * of relinking to avoid issues with reconnecting to a parent.
	 * @param x
	 * The node to call rotate on.
	 */
	public void leftRotate(RBTNode<V> x) {
		RBTNode<V> temp = x.getRight();
		x.setRight(x.getLeft());
		x.setLeft(temp);

		RBTNode<V> temp2 = x.getLeft().getLeft();
		temp = x.getRight();
		x.setRight(x.getLeft().getRight());
		x.getLeft().setRight(temp2);
		x.getLeft().setLeft(temp);

		V tempData = x.getData();
		x.setData(x.getLeft().getData());
		x.getLeft().setData(tempData);
	}

	/**
	 * This method is called to remove a desired value from the Red Black Tree. Base case 1 is
	 * when the tree is empty, so no removal is necessary. Base case 2 is when the tree is of size 1
	 * and the node we want to remove is the only node in the tree.
	 * 
	 * Otherwise, the node we want to remove is found using the findValue method. If the node
	 * containing the value we want to remove is a leaf, removeCaseOne is called. If the node
	 * containing the value we want to remove has one child, removeCaseTwo is called. Otherwise,
	 * removeCaseThree is called, meaning the node containing the value we want to remove has
	 * two children.
	 */
	@Override
	public V remove(V value) {

		// Base Cases
		if (root == null) {
			return null;
		} else if (size == 1 && value == root.getData()) {
			root = null;
			size = 0;
			return value;
		}

		RBTNode<V> valNode = findValue(root, value);
		if (valNode != null) {
			boolean red = false;
			if (valNode.isLeaf()) { // Case 1
				removeCaseOne(red, valNode);
			} else if (!valNode.isFull() && !valNode.isLeaf()) { // Case 2
				removeCaseTwo(red, valNode);
			} else {
				// do last case removal
				removeCaseThree(red, valNode);
			}
			--size;
		}

		return null;
	}

	/**
	 * This method deals with the case of 'plucking' a node from the tree.
	 * All that occurs here is children of the parent of the node we are removing
	 * are modified depending on which child the node we want to remove is.
	 * After this is done, fixTreeRemoveHelper is called on the now empty node.
	 * @param red
	 * boolean used to represent whether the node we are removing is red.
	 * @param valNode
	 * The node that we are removing.
	 */
	public void removeCaseOne(boolean red, RBTNode<V> valNode) {
		red = valNode.getColor() == Color.RED;
		RBTNode<V> parent = valNode.getParent();
		if (valNode == parent.getLeft()) {
			parent.setLeft(new RBTNode<V>(parent, null, Color.BLACK));
			fixTreeRemoveHelper(red, parent.getLeft());
		} else {
			parent.setRight(new RBTNode<V>(parent, null, Color.BLACK));
			fixTreeRemoveHelper(red, parent.getRight());
		}
		valNode.setLeft(null);
		valNode.setRight(null);
		valNode.setParent(null);
		valNode.setData(null);
		valNode = null;
	}

	/**
	 * This method is used to handle the case when the value we want to remove has one child.
	 * If this is the case, then we simply relink the parent of the node we are removing with the child
	 * of the node we are removing. This essentially skips the node and removes it from the tree completely.
	 * @param red
	 * boolean that represents whether the node we are removing is red.
	 * @param valNode
	 * The node we are removing.
	 */
	public void removeCaseTwo(boolean red, RBTNode<V> valNode) {
		red = valNode.getColor() == Color.RED;
		RBTNode<V> child = null;
		if (valNode.getLeft().getData() != null) {
			child = valNode.getLeft();
		} else {
			child = valNode.getRight();
		}
		child.setParent(valNode.getParent());

		valNode.setData(child.getData());
		valNode.setLeft(child.getLeft());
		valNode.setRight(child.getRight());
		valNode.setParent(null);

		fixTreeRemoveHelper(red, child.getParent());
	}

	/**
	 * This method is used to deal with case 3 of removing from a Red Black Tree.
	 * First, the in-order predecessor of the value we are removing is found by
	 * finding the rightmost child of the left child of the node containing the value
	 * we want to remove. Once this is found, we know that we will be removing the predecessor node from
	 * the tree, but replacing the value of valNode with the value of the predecessor node.
	 * The predecessor node is then removed from the tree. If the predecessor node was a leaf, it is
	 * simply plucked from the tree. Otherwise, the parent of the predecessor node is connected with
	 * the left child of the predecessor node if it had one.
	 * @param red
	 * boolean representing the color of the node we are removing
	 * @param valNode
	 * The node containing the value we want to remove.
	 */
	public void removeCaseThree(boolean red, RBTNode<V> valNode) {
		RBTNode<V> predecessor = getRightmost(valNode.getLeft()); // Case
		// 3
		red = predecessor.getColor() == Color.RED;
		RBTNode<V> parent = predecessor.getParent();
		valNode.setData(predecessor.getData());
		if (!predecessor.isLeaf()) {
			if (parent.getLeft() == predecessor) {
				parent.setLeft(predecessor.getLeft());
				predecessor.setParent(null);
				predecessor = null;
				fixTreeRemoveHelper(red, parent.getLeft());
			} else {
				parent.setRight(predecessor.getLeft());
				predecessor.setParent(null);
				predecessor = null;
				fixTreeRemoveHelper(red, parent.getRight());
			}
		} else {
			if (predecessor == parent.getLeft()) {
				predecessor.setParent(null);
				predecessor = null;
				System.out.println(valNode.getData());
				parent.setLeft(new RBTNode<V>(parent, null, Color.BLACK));
				System.out.println(toPrettyString());
				fixTreeRemoveHelper(red, parent.getLeft());
			} else {
				predecessor.setParent(null);
				predecessor = null;
				parent.setRight(new RBTNode<V>(parent, null, Color.BLACK));
				fixTreeRemoveHelper(red, parent.getRight());
			}

		}
	}

	/**
	 * This method simply finds and returns the rightmost child of the node that
	 * calls the method.
	 * @param origin
	 * The node we are finding the rightmost child for.
	 * @return
	 * The rightmost child of the origin node.
	 */
	public RBTNode<V> getRightmost(RBTNode<V> origin) {
		if (origin.getRight().getData() == null) {
			return origin;
		} else {
			return getRightmost(origin.getRight());
		}
	}

	/**
	 * This method finds the node in the tree that contains the value we
	 * pass in as a parameter. The first case deals with the case in which 
	 * the value is not contained in the tree. If this is the case, null is returned
	 * signifying that the value was not found in the tree. Otherwise, if the value is
	 * equal to the value in the node currently being called on, the node is returned.
	 * 
	 * This method is called recursively on origin's left or right child depending on which
	 * path it should take according to the value we are looking for.
	 * @param origin
	 * The node that is currently being compared with value.
	 * @param value
	 * The value we are looking for.
	 * @return
	 * null if the value is not contained in the tree, the node that contains the value
	 * otherwise.
	 */
	public RBTNode<V> findValue(RBTNode<V> origin, V value) {
		if (origin.getData() == null) {
			return null;
		}
		if (value.compareTo(origin.getData()) == 0) {
			return origin;
		}
		if (value.compareTo(origin.getData()) < 0
				|| value.compareTo(origin.getData()) == 0) {
			return findValue(origin.getLeft(), value);
		} else {
			return findValue(origin.getRight(), value);
		}
	}

	/**
	 * This method uses the findValue function to find the node that
	 * contains the value we want to find if it exists. Thus, it returns the data
	 * that is contained in the node if the value is found, otherwise it returns null to
	 * signify that the value was not contained in the tree.
	 */
	@Override
	public V lookup(V value) {
		RBTNode<V> valueNode = findValue(root, value);
		if (valueNode.getData() != null) {
			return valueNode.getData();
		} else {
			return null;
		}
	}

	/**
	 * Prints out the tree in a pyramid fashion.
	 */
	@Override
	public String toPrettyString() {
		BTreePrinter printer = new BTreePrinter();
		printer.printNode(root);
		return "";
	}

	/**
	 * Test method that returns the root of the tree.
	 * @return
	 */
	public RBTNode<V> getRoot() {
		return root;
	}

	/**
	 * Utility method that returns the uncle of the node that it is called on.
	 * @param x
	 * The node we want to find the uncle for.
	 * @return
	 * X's uncle.
	 */
	public RBTNode<V> getUncle(RBTNode<V> x) {
		RBTNode<V> xParent = x.getParent();
		if (xParent.getParent().getLeft() == xParent) {
			return xParent.getParent().getRight();
		} else {
			return xParent.getParent().getLeft();
		}
	}

	/**
	 * Utility method that returns the grandparent of the node that it is called on.
	 * @param x
	 * The node we want to find the grandparent for.
	 * @return
	 * X's grandparent.
	 */
	public RBTNode<V> getGrandparent(RBTNode<V> x) {
		return x.getParent().getParent();
	}

	/**
	 * Utility method that returns the sibling of the node that it is called on.
	 * @param x
	 * The node that we want to find the sibling for.
	 * @return
	 * X's sibling.
	 */
	public RBTNode<V> getSibling(RBTNode<V> x) {
		RBTNode<V> xParent = x.getParent();
		if (xParent.getLeft() == x) {
			return xParent.getRight();
		} else {
			return xParent.getLeft();
		}
	}
	
}