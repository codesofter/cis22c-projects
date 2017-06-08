// Adapted from code by Y. Daniel Liang
// Modified by C. Lee-Klawender
/*
    @author Modified by So Choi

    Name of Program:    BinaryTree
    Description:        This class is the abstract class for BinaryTree.
    Date:               5/26/17
    OS:                 Mac OS X
    Compiler:           terminal (javac)
*/
import java.io.*;
public abstract class BinaryTree<E> implements TreeInterface<E> {
	protected BinaryNode<E> root = null; // reference to the root
    protected int size = 0; // number of nodes in the tree

    public BinaryTree(){ }

    /*public BinaryTree(BinaryTree<E> sourceTree){
        if (size == 0) {
            this.root = sourceTree.root;
            this.size = sourceTree.size;
        } else {
            BinaryTree<E> clone;
            BinaryNode<E> node = sourceTree.root;

            // deep copy here
            deepCopy(node, clone);

            this.root = clone.root;
            this.size = clone.size;
        }
    }*/

    /*
        Wrote this function because I thought we needed the copy constructor.
    */
    private void deepCopy(BinaryNode<E> node, BinaryTree<E> clone) {
        if (node == null) {
            return;
        } else {
            /*
                DFS implementation:
                if there's a left child, create a new node for left child using original's data,
                then call copy again. when finished, check right side and repeat.
            */
            if (node.hasLeftChild()) {
                BinaryNode<E> left = new BinaryNode<E>(node.getLeftChild().getData());
                clone.root.setLeftChild(left);
                deepCopy(node.getLeftChild(), clone);
            }
            if (node.hasRightChild()) {
                BinaryNode<E> right = new BinaryNode<E>(node.getRightChild().getData());
                clone.root.setRightChild(right);
                deepCopy(node.getRightChild(), clone);
            }
        }
    }

    /** Clears the whole tree */
    public void clear()
    {
    	this.root = new BinaryNode<E>();
        this.size = 0;
    }
    
    public void writeIndentedTree(PrintStream writer)
    {
        // Call the writeTree, passing this parameter, this' root, 1 and ""
        writeTree(writer, this.root, 1, "");
    }

    // FINISH THE FOLLOWING METHOD so it returns if the root is null,
    //     calls print or println for the PrintWriter parameter to write the root's data
    //     then (like inorder, but "reversed" and with level and indent) make
    //     recursive calls to writeTree
    protected void writeTree(PrintStream writer, BinaryNode<E> root, int level, String indent){
        if ( root == null ) {  // (Otherwise, there's nothing to print.)
            return;
        } else {
            if (root.hasRightChild()) {
                writeTree(writer, root.getRightChild(), level+1, indent+"    ");  // Print items in right subtree.
            }
            writer.println(indent + level + ". " + root.getData());  // Print the root item.
            if (root.hasLeftChild()) {
                writeTree(writer, root.getLeftChild(), level+1, indent+"    ");   // Print items in left subtree.
            }
        }
    }

    @Override /** Preorder traversal from the root */
    public void preorder(Visitor<E> visitor) {
        preorder(root, visitor);
    }

    @Override /** Inorder traversal from the root*/
    public void inorder(Visitor<E> visitor) {
        // you finish (part of HW#4)
        inorder(root, visitor);
    }

    @Override /** Postorder traversal from the root */
    public void postorder(Visitor<E> visitor) {
        // you finish (part of HW#4)
        postorder(root, visitor);
    }

    @Override /** Get the number of nodes in the tree */
    public int getSize() {
    	return size;
    }


    @Override /** Return true if the tree is empty */
    public boolean isEmpty() {
    	return getSize() == 0;
    }

    /** Preorder traversal from a subtree */
    protected void preorder(BinaryNode<E> root, Visitor<E> visitor) {
    	if (root == null)
    		return;
    	visitor.visit(root.getData());
    	preorder(root.getLeftChild(), visitor);
    	preorder(root.getRightChild(), visitor);
    }

    /** Inorder traversal from a subtree */
    protected void inorder(BinaryNode<E> root, Visitor<E> visitor) {
        // you finish (part of HW#4)
        if (root == null) {
            return;
        }
        inorder(root.getLeftChild(), visitor);
        visitor.visit(root.getData());
        inorder(root.getRightChild(), visitor);
    }

    /** Posorder traversal from a subtree */
    protected void postorder(BinaryNode<E> root, Visitor<E> visitor) {
        // you finish (part of HW#4)
        if (root == null) {
            return;
        }
        postorder(root.getLeftChild(), visitor);
        postorder(root.getRightChild(), visitor);
        visitor.visit(root.getData());
    }
} // end abstract BinaryTree class
