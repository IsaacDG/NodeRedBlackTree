/**
 * 
 */
package edu.cpp.cs.cs241.prog_assgmnt_4;

/**
 * @author Isa
 *
 */
public class RBTNodeBeta<V> {

	private RBTNodeBeta<V> parent, leftChild, rightChild;
	
	private V data;
	
	private Color color;
	
	public RBTNodeBeta(RBTNodeBeta<V> parent, V data){
		this.parent = parent;
		this.data = data;
		this.color = Color.RED;
		leftChild = new RBTNodeBeta<V>(this, null, Color.BLACK);
		rightChild = new RBTNodeBeta<V>(this, null, Color.BLACK);
	}
	
	public RBTNodeBeta(RBTNodeBeta<V> parent, V data, Color color){
		this.parent = parent;
		this.data = data;
		this.color = color;
		leftChild = null;
		rightChild = null;
	}
	
	public V getData(){
		return data;
	}
	
	public RBTNodeBeta<V> getParent(){
		return parent;
	}
	
	public RBTNodeBeta<V> getLeft(){
		return leftChild;
	}
	
	public RBTNodeBeta<V> getRight(){
		return rightChild;
	}
	
	public void setLeft(RBTNodeBeta<V> leftChild){
		this.leftChild = leftChild;
	}
	
	public void setRight(RBTNodeBeta<V> rightChild){
		this.rightChild = rightChild;
	}
	
	public void setParent(RBTNodeBeta<V> parent){
		this.parent = parent;
	}
	
	public void setData(V data){
		this.data = data;
	}
	
	public boolean isLeaf(){
		return (leftChild.getData() == null && rightChild.getData() == null);
	}
	
	public boolean isFull(){
		return (leftChild.getData() != null && rightChild.getData() != null);
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public RBTNodeBeta<V> getAvailChild(){
		if(leftChild.getData() == null){
			return rightChild;
		} else {
			return leftChild;
		}
	}
	
}