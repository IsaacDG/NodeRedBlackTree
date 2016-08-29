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
 * This class is used to construct the Nodes that make up the Red Black Tree.
 * @author Isa
 *
 */
public class RBTNode<V> {

	/**
	 * These fields are the links to nodes that each instance of this class will have.
	 * The parent, left and right children.
	 */
	private RBTNode<V> parent, leftChild, rightChild;
	
	/**
	 * This is the actual data that the node will contain.
	 */
	private V data;
	
	/**
	 * This field is the 'color' of the node, to be used when balancing the tree
	 * and maintaining invariants of the red black tree.
	 */
	private Color color;
	
	/**
	 * This constructor takes in a parent and data and sets them to the appropriate fields.
	 * Additionally, it colors the node RED and sets the two children of the node to empty BLACK
	 * nodes(or nil nodes).
	 * @param parent
	 * The node to set to parent.
	 * @param data
	 * The data to set to data.
	 */
	public RBTNode(RBTNode<V> parent, V data){
		this.parent = parent;
		this.data = data;
		this.color = Color.RED;
		leftChild = new RBTNode<V>(this, null, Color.BLACK);
		rightChild = new RBTNode<V>(this, null, Color.BLACK);
	}
	
	/**
	 * This constructor is a constructor used to create ONE empty black node with no children.
	 * It takes in arguments for the parent, data, and color. In RBT.java I use this constructor
	 * to insure that I create a node with connections to nothing that are black.
	 * @param parent
	 * The node to set to parent
	 * @param data
	 * The V to set to data
	 * @param color
	 * The COLOR to set to color.
	 */
	public RBTNode(RBTNode<V> parent, V data, Color color){
		this.parent = parent;
		this.data = data;
		this.color = color;
		leftChild = null;
		rightChild = null;
	}
	
	/**
	 * Returns the data in the node.
	 * @return
	 * {@link #data}
	 */
	public V getData(){
		return data;
	}
	
	/**
	 * Returns the node that is the parent of the instance of node.
	 * @return
	 * {@link #parent}
	 */
	public RBTNode<V> getParent(){
		return parent;
	}
	
	/**
	 * Returns the node that is the left child of this node.
	 * @return
	 * {@link #leftChild}
	 */
	public RBTNode<V> getLeft(){
		return leftChild;
	}
	
	/**
	 * Returns the node that is the right child of this node.
	 * @return
	 * {@link #rightChild}
	 */
	public RBTNode<V> getRight(){
		return rightChild;
	}
	
	/**
	 * Sets the link to the node that will be the left child for this node. If the left child
	 * being set is NOT null, then it also makes sure that the new left child also sets its parent
	 * to the current instance of the node that the method is being called from. This is useful for
	 * the way I implemented rotations.
	 * @param leftChild
	 * The node to set to left child.
	 */
	public void setLeft(RBTNode<V> leftChild){
		this.leftChild = leftChild;
		if(leftChild != null){
			this.leftChild.setParent(this);
		}
		//this.leftChild.setParent(this);
	}
	
	/**
	 * Sets the link to the node that will be the right child for this node. If the right child
	 * being set is NOT null, then it also makes sure that the new left child also sets its parent
	 * to the current instance of the node that the method is being called from. This is useful for
	 * the way I implemented rotations.
	 * @param rightChild
	 * The node to set to right child.
	 */
	public void setRight(RBTNode<V> rightChild){
		this.rightChild = rightChild;
		if(rightChild != null){
			this.rightChild.setParent(this);
		}
		//this.rightChild.setParent(this);
	}
	
	/**
	 * Sets the node passed in to the parent of the instance of the node calling the method.
	 * @param parent
	 * The node to set to {@link #parent}
	 */
	public void setParent(RBTNode<V> parent){
		this.parent = parent;
	}
	
	/**
	 * Sets the data of the node.
	 * @param data
	 * V to set to {@link #data}
	 */
	public void setData(V data){
		this.data = data;
	}
	
	/**
	 * This method checks for the data in the two child nodes because in a red black tree,
	 * any leaf is not really a leaf because it still has children, but the children contain
	 * null value.
	 * @return
	 * True if the value in the two child nodes are null, false if not.
	 */
	public boolean isLeaf(){
		return (leftChild.getData() == null && rightChild.getData() == null);
	}
	
	/**
	 * This node checks if the two child nodes of any non-null node have values that are not
	 * null.
	 * @return
	 * True if the node has two child nodes that do not contain null value. False if else.
	 */
	public boolean isFull(){
		return (leftChild.getData() != null && rightChild.getData() != null);
	}
	
	/**
	 * 
	 * @return
	 * {@link #color}
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * Sets the value of the color field.
	 * @param color
	 * The value to set to {@link #color}
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	/**
	 * This method is used to return the child of the node that is available. This is useful
	 * code for handling instances in which a node only has one child, but we do not know
	 * which child that is. This code will return the appropriate child whether it is left
	 * or right.
	 * @return
	 * The available child.
	 */
	public RBTNode<V> getAvailChild(){
		if(leftChild.getData() == null){
			return rightChild;
		} else {
			return leftChild;
		}
	}
	
}
