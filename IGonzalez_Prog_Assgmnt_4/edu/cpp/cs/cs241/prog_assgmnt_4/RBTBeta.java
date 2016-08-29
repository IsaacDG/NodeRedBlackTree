/**
 * 
 */
package edu.cpp.cs.cs241.prog_assgmnt_4;

/**
 * @author Isa
 *
 */
public class RBTBeta<V extends Comparable<V>> implements Tree<V> {

	RBTNode<V> root;

	int size;

	public RBTBeta() {
		root = null;
		size = 0;
	}

	@Override
	public void add(V value) {

		if (root == null) {
			root = new RBTNode<V>(null, value);
			root.setColor(Color.BLACK);
		} else {
			addHelper(root, value);
			++size;
		}

	}

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

	public RBTNode<V> fixTreeAdd(RBTNode<V> x) {
		if (x == root) {
			root.setColor(Color.BLACK);
		} else {
			if (x.getParent().getColor() == Color.RED) {
				RBTNode<V> uncle = getUncle(x);
				RBTNode<V> grandParent = getGrandparent(x);
				if (uncle.getColor() == Color.RED) {
					x.getParent().setColor(Color.BLACK);
					uncle.setColor(Color.BLACK);
					grandParent.setColor(Color.RED);
					fixTreeAdd(grandParent);
				} else {
					if(x.getParent() == grandParent.getLeft()){ //left sub
						if(x.getParent().getRight() == x){
							leftRotate(x.getParent());
							fixTreeAdd(x.getParent().getLeft());
						} else {
							rightRotate(grandParent);
							grandParent.setColor(Color.BLACK);
							grandParent.getRight().setColor(Color.RED);
							fixTreeAdd(root);
						}
					} else { //right sub
						if(x.getParent().getLeft() == x){
							rightRotate(x.getParent());
							fixTreeAdd(x.getParent().getRight());
						} else {
							leftRotate(grandParent);
							grandParent.setColor(Color.BLACK);
							grandParent.getLeft().setColor(Color.RED);
							fixTreeAdd(root);
						}
					}
				}
			}
		}
		return null;
	}
	
	public RBTNode<V> fixTreeRemove(RBTNode<V> x){
		if(x != root){
			RBTNode<V> w = getSibling(x);
			
			if(w.getColor() == Color.RED){ //CASE 1
				if(x.getParent().getRight() == w){
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
			} else { //w is BLACK, CASE 2, 3, 4 handled here
				if(w.getRight().getColor() == Color.BLACK && w.getLeft().getColor() == Color.BLACK){ //CASE 2
					if(x.getParent().getColor() == Color.RED){
						x.getParent().setColor(Color.BLACK);
						w.setColor(Color.RED);
					} else {
						if(x.getParent() != root){
							fixTreeRemove(x.getParent());
						}
					}
				} else { //w only has one RED child.
					RBTNode<V> wGrandparent = getGrandparent(w);
					RBTNode<V> parent = w.getParent();
					
					if(parent.getRight() == w){ //right sub
						if(w.getLeft().getColor() == Color.RED && w.getRight().getColor() == Color.BLACK){
							rightRotate(w);
							w.setColor(Color.BLACK);
							w.getRight().setColor(Color.RED);
							fixTreeRemove(x);
						} else {
							Color green = x.getColor();
							leftRotate(x.getParent());
							x.getParent().setColor(green);
							x.getParent().getRight().setColor(Color.BLACK);
							x.setColor(Color.BLACK);
							fixTreeRemove(root);
						}
					} else {
						if(w.getRight().getColor() == Color.RED && w.getLeft().getColor() == Color.BLACK){
							leftRotate(w);
							w.setColor(Color.BLACK);
							w.getLeft().setColor(Color.RED);
							fixTreeRemove(x);
						} else {
							Color green = x.getColor();
							rightRotate(x.getParent());
							x.getParent().setColor(green);
							x.getParent().getLeft().setColor(Color.BLACK);
							x.setColor(Color.BLACK);
							fixTreeRemove(root);
						}
					}
				}
			}
		}
//		RBTNode<V> w = getSibling(x);
//		
//		if(w.getColor() == Color.RED){ //CASE 1
//			if(x.getParent().getRight() == w){
//				leftRotate(x.getParent());
//				x.getParent().getLeft().setColor(Color.RED);
//				x.getParent().setColor(Color.BLACK);
//				x.getParent().getRight().setColor(Color.BLACK);
//				fixTreeRemove(x.getLeft());
//			} else {
//				rightRotate(x.getParent());
//				x.getParent().getRight().setColor(Color.RED);
//				x.getParent().setColor(Color.BLACK);
//				x.getParent().getLeft().setColor(Color.BLACK);
//				fixTreeRemove(x.getRight());
//			}
//		} else { //w is BLACK, CASE 2, 3, 4 handled here
//			if(w.getRight().getColor() == Color.BLACK && w.getLeft().getColor() == Color.BLACK){ //CASE 2
//				if(x.getParent().getColor() == Color.RED){
//					x.getParent().setColor(Color.BLACK);
//					w.setColor(Color.RED);
//				} else {
//					if(x.getParent() != root){
//						fixTreeRemove(x.getParent());
//					}
//				}
//			} else { //w only has one RED child.
//				RBTNode<V> wGrandparent = getGrandparent(w);
//				RBTNode<V> parent = w.getParent();
//				
//				if(parent.getRight() == w){ //right sub
//					if(w.getLeft().getColor() == Color.RED && w.getRight().getColor() == Color.BLACK){
//						rightRotate(w);
//						w.setColor(Color.BLACK);
//						w.getRight().setColor(Color.RED);
//						fixTreeRemove(x);
//					} else {
//						Color green = x.getColor();
//						leftRotate(x.getParent());
//						x.getParent().setColor(green);
//						x.getParent().getRight().setColor(Color.BLACK);
//						x.setColor(Color.BLACK);
//						fixTreeRemove(root);
//					}
//				} else {
//					if(w.getRight().getColor() == Color.RED && w.getLeft().getColor() == Color.BLACK){
//						leftRotate(w);
//						w.setColor(Color.BLACK);
//						w.getLeft().setColor(Color.RED);
//						fixTreeRemove(x);
//					} else {
//						Color green = x.getColor();
//						rightRotate(x.getParent());
//						x.getParent().setColor(green);
//						x.getParent().getLeft().setColor(Color.BLACK);
//						x.setColor(Color.BLACK);
//						fixTreeRemove(root);
//					}
//				}
//			}
//		}
		
		return null;
	}
	
	public void rightRotate(RBTNode<V> x){
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
	
	public void leftRotate(RBTNode<V> x){
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

	@Override
	public V remove(V value) {

		// Base Cases
		if (root == null) {
			return null;
		} else if (size == 1 && value == root.getData()) {
			root = null;
			return value;
		}

		RBTNode<V> valNode = findValue(root, value);
		if (valNode.getData() != null) {
			if (valNode.isLeaf()) { // Case 1
				RBTNode<V> parent = valNode.getParent();
				if (valNode == parent.getLeft()) {
					parent.setLeft(new RBTNode<V>(parent, null, Color.BLACK));
					fixTreeRemove(parent.getLeft());
				} else {
					parent.setRight(new RBTNode<V>(parent, null, Color.BLACK));
					fixTreeRemove(parent.getRight());
				}
			} else if (!valNode.isFull() && !valNode.isLeaf()) { // Case 2
				if (valNode.getLeft().getData() != null) {
					valNode.setData(valNode.getLeft().getData());
					valNode.setLeft(new RBTNode<V>(valNode, null, Color.BLACK));
					fixTreeRemove(valNode.getLeft());
				} else {
					valNode.setData(valNode.getRight().getData());
					valNode.setRight(new RBTNode<V>(valNode, null, Color.BLACK));
					fixTreeRemove(valNode.getRight());
				}
			} else {
				// do last case removal
				RBTNode<V> predecessor = getRightmost(valNode.getLeft()); // Case
																			// 3
				valNode.setData(predecessor.getData());
				if (!predecessor.isLeaf()) {
					if (predecessor.getParent().getLeft() == predecessor) {
						predecessor.getParent().setLeft(predecessor.getLeft());
						fixTreeRemove(predecessor.getParent().getLeft());
					} else {
						predecessor.getParent().setRight(predecessor.getLeft());
						fixTreeRemove(predecessor.getParent().getRight());
					}
				} else {
					predecessor.getParent().setRight(
							new RBTNode<V>(predecessor.getParent(), null,
									Color.BLACK));
					fixTreeRemove(predecessor.getParent().getRight());
				}
			}
			--size;
		}

		return null;
	}

	public RBTNode<V> getRightmost(RBTNode<V> origin) {
		if (origin.getRight().getData() == null) {
			return origin;
		} else {
			return getRightmost(origin.getRight());
		}
	}

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

	@Override
	public V lookup(V value) {
		RBTNode<V> valueNode = findValue(root, value);
		if (valueNode.getData() != null) {
			return valueNode.getData();
		} else {
			return null;
		}
	}

	@Override
	public String toPrettyString() {
		BTreePrinter printer = new BTreePrinter();
		printer.printNode(root);
		return "";
	}

	public RBTNode<V> getRoot() {
		return root;
	}

	public RBTNode<V> getUncle(RBTNode<V> x) {
		RBTNode<V> xParent = x.getParent();
		if (xParent.getParent().getLeft() == xParent) {
			return xParent.getParent().getRight();
		} else {
			return xParent.getParent().getLeft();
		}
	}

	public RBTNode<V> getGrandparent(RBTNode<V> x) {
		return x.getParent().getParent();
	}
	
	public RBTNode<V> getSibling(RBTNode<V> x){
		RBTNode<V> xParent = x.getParent();
		if(xParent.getLeft() == x){
			return xParent.getRight();
		} else {
			return xParent.getLeft();
		}
	}

}
