/**
 * 
 */
package edu.cpp.cs.cs241.prog_assgmnt_4;

import java.util.ArrayList;

import java.lang.StringBuilder;
import java.util.Collections;
import java.util.List;

/**
 * This class is a class that is used to print out the contents of a tree in pyramid
 * fashion. Adapted from michael.kruzman's code from StackOverflow.com
 * 
 * 
 *
 */
class BTreePrinter {

	/**
	 * This method is used to begin printing the tree. First, the deepest level of the tree is found
	 * for use in the printNodeInternal method.
	 * @param root
	 * The root of the tree where the printing begins.
	 */
    public static <T extends Comparable<?>> void printNode(RBTNode<T> root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    /**
     * This method is a recursive method that prints out the contents of a binary tree. The method keeps track
     * of what level of the tree it is at to know how much space it should give the printing so it can
     * display all the relevant information. 2 lists are used to keep track of the current nodes in the
     * current level as well as figuring out the nodes that are needed for the next deepest level of the tree.
     * After the contents of the nodes in the current level are printed and the children of those nodes are
     * added to the next level list, the method then goes on to print out the 'lines' of the tree by going through
     * the list of nodes. After this is done for the current level, the method is called recursively and
     * the process is repeated for the next deepest level.
     * @param nodes
     * The nodes that are in the current level of the tree.
     * @param level
     * The current level of the tree
     * @param maxLevel
     * The deepest level of the tree.
     */
    private static <T extends Comparable<?>> void printNodeInternal(List<RBTNode<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces - 3);

        List<RBTNode<T>> newNodes = new ArrayList<RBTNode<T>>();
        for (RBTNode<T> node : nodes) {
            if (node != null) {
            	if(node.getData() == null){
            		System.out.print("X");
            	} else {
                    System.out.print(node.getColor().letter() + ":" + node.getData());
            	}
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    /**
     * This is a helper method that prints out the correct number of whitespaces to format the tree
     * correctly visually.
     * @param count
     */
    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    /**
     * This method finds the max level of the tree by traversing the tree completely.
     * @param node
     * The current node we are at
     * @return
     * The maximum level of the tree.
     */
    private static <T extends Comparable<?>> int maxLevel(RBTNode<T> node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.getLeft()), BTreePrinter.maxLevel(node.getRight())) + 1;
    }

    /**
     * Method that checks if the tree is full of null objects.
     * @param list
     * The list of elements to check.
     * @return
     * True if list is full of null elements, false otherwise.
     */
    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}