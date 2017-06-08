// Adapted from Code By Y. Daniel Liang
// Modified by C. Lee-Klawender
/*
    @author Modified by So Choi

    Name of Program:    BST
    Description:        This class is an extension of BinaryTree and includes method for BST.
    Date:               5/26/17
    OS:                 Mac OS X
    Compiler:           terminal (javac)
*/
import java.util.Comparator;

public class BST<E extends Comparable<E>> extends BinaryTree<E>{
    private boolean foundNode; // helper variable
    // DECLARE A PRIVATE COMPARATOR INSTANCE VARIABLE HERE!
    Comparator<E> comp;

    /** Create a default binary tree */
    public BST(Comparator<E> c)// SEE HW#4 FOR WHAT TO CHANGE HERE
    { 
        /*
            ADD to the default constructor (AND the constructor with an array
            parameter) a Comparator<E> parameter and assign the parameter to the
            Comparator<E> instance variable 
        */
        super();
        comp = c;
    }
    /*
    public BST(BST<E> sourceTree)
    {
        super(sourceTree);
    }*/

    /** Create a binary tree from an array of objects */
    public BST(E[] objects, Comparator<E> c){
        comp = c;
        for (int i = 0; i < objects.length; i++){
            insert(objects[i]);
        }
    }

    @Override /** Returns true if the element is in the tree */
    public boolean contains(E e)
    {
        BinaryNode<E> current = root; // Start from the root

        while (current != null)
        {
            if (comp.compare(current.getData(), e) < 0)//*****CHANGE THIS for HW#4*****
            {
                current = current.getLeftChild();
            }
            else if (comp.compare(current.getData(), e) > 0)//*****CHANGE THIS for HW#4*****
            {
                    current = current.getRightChild();
            }
            else // element matches current.getData()
                return true; // Element is found
        }

        return false;
    }

    @Override
    /**
     * Returns the data of the Node that equals the parameter, null if not found.
     * */
    public E getEntry(E e)         // YOU WRITE FOR HW#4
    {
        // Call findNode starting with the root and save the return value
        // Check the return value to see if it's found or not
        // If it's found, return the return value's data
        //     Otherwise return null
        if (root != null) {
            BinaryNode<E> node = _findNode(root, e);
            if (node != null) {
                return node.getData();
            }
        }
        return null;
    }

    // FOR EXERCISE: CALL A PRIVATE RECURSIVE METHOD THAT RETURNS A BinaryNode that equals THE PARAMETER
    //     If found, return the data in the returned BinaryNode
    private BinaryNode<E> _findNode(BinaryNode<E> node, E e )
    {
        if( node == null )
            return null;
        else if( comp.compare(e, node.getData()) < 0 ) //*****CHANGE THIS for HW#4*****
                return _findNode( node.getLeftChild(), e );
        else if( comp.compare(e, node.getData()) > 0 ) //*****CHANGE THIS for HW#4*****
                return _findNode( node.getRightChild(), e );
        else // found it!
            return node;
    }


    @Override
    /** Insert element e into the binary tree
     * Return true if the element is inserted successfully */
    public boolean insert(E e)
    {
        root = _insert(root, e); // calls private insert that YOU write for HW#4
        size++;
        return true; // Element inserted successfully
    }

    // Private recursive method that returns an updated "root" node from where current node is
    private BinaryNode<E> _insert( BinaryNode<E> node, E e )
    {
        // YOU WRITE FOR HW#4, using this recursive algorithm: (HINT:see _delete)
        // MAKE SURE YOU COMPARE CORRECTLY MUST call the correct METHOD to compare!
        //      IF no more nodes THEN
        //                return a new Node(e)
        //      ELSE IF e < node's data THEN
        //                   set the node's left to _insert(node's left, e)
        //           ELSE
        //                   set the node's right to _insert(node's right, e)
        //           ENDIF
        //           RETURN node
        //       ENDIF
        if (node == null) {
            return new BinaryNode<E>(e);
        } else {
            if (comp.compare(e, node.getData()) < 0) {
                node.setLeftChild(_insert(node.getLeftChild(), e));
            } else {
                node.setRightChild(_insert(node.getRightChild(), e));
            }
            return node;
        }
    }

	@Override
	/** Delete an element from the binary tree.
	 * Return true if the element is deleted successfully
	 * Return false if the element is not in the tree */
	public boolean delete(E e)
	{
		foundNode = false;		// initialize boolean instance variable
		root = _delete(root, e); //call private method to do actual deletion

		if( foundNode )
		{
			size--;// Element deleted successfully
		}
		return foundNode;
	}

	// Private recursive method that returns an updated "root" node from where current node is
    private BinaryNode<E> _delete( BinaryNode<E> node, E e )
    {
        if( node==null )
        {
            return null;
        }
        if ( comp.compare(e, node.getData()) < 0 )
             node.setLeftChild( _delete(node.getLeftChild(), e) );//recursive call
        else
            if( comp.compare(e, node.getData()) > 0 )
                node.setRightChild( _delete(node.getRightChild(), e) );//recursive call
            else
            {
                foundNode = true;
                node = _deleteNode( node );
            }
        return node;
    } // end _delete

    // Private method that either "moves up" the left or right child, OR
    //    replaces the data in the nodeToDelete with the data in the rightmost child of
    //    the nodeToDelete's left child, then "removes" that node
    private BinaryNode<E> _deleteNode( BinaryNode<E> nodeToDelete )
    {
        if( nodeToDelete.isLeaf() ) // node to delete has no children
        {
           return null;
        }
        if( !nodeToDelete.hasLeftChild() ) // node to delete has no LEFT child
        {
            return nodeToDelete.getRightChild();
        }
        if( !nodeToDelete.hasRightChild() ) // node to delete has no RIGHT child
        {
            return nodeToDelete.getLeftChild();
        }
        // must have left and right children
        // Locate the rightmost node in the left subtree of
        // the node to delete and also its parent
        BinaryNode<E> parentOfRightMost = nodeToDelete;
        BinaryNode<E> rightMost = nodeToDelete.getLeftChild();

        while (rightMost.getRightChild() != null) {
            parentOfRightMost = rightMost;
            rightMost = rightMost.getRightChild(); // Keep going to the right
        }

        // Replace the element in nodeToDelete by the element in rightMost
        nodeToDelete.setData( rightMost.getData() ); // don't really delete the node, just change the data

        // Eliminate rightmost node
        if (parentOfRightMost.getRightChild() == rightMost)
            parentOfRightMost.setRightChild( rightMost.getLeftChild() );
        else
            // Special case: nodeToDelete's leftChild has no rightChild
            parentOfRightMost.setLeftChild( rightMost.getLeftChild() );

        return nodeToDelete;
    } // end private _deleteNode

    public E getFirst(){
        // NON-recursive algorithm:
        // If the tree is empty,  return null
        if (root == null) {
            return null;
        } else {
            // FIND THE LEFT-MOST LEFT CHILD
            // WHEN you can't go left anymore, return the node's data to first Item
            BinaryNode<E> parentOfLeftMost = root;
            BinaryNode<E> leftMost = root.getLeftChild();
            if (leftMost == null) {
                return parentOfLeftMost.getData();
            } else {
                while (leftMost != null) {
                    parentOfLeftMost = leftMost;
                    leftMost = leftMost.getLeftChild();
                }
                return parentOfLeftMost.getData();
            }
        }
    }
    
    public E getLast()// you finish (part of HW#4)
    {
        // If the tree is empty, return null
        if (root == null) {
            return null;
        } else {
            // FIND THE RIGHT-MOST RIGHT CHILD
            // WHEN you can't go RIGHT anymore, return the node's data to last Item
            BinaryNode<E> parentOfRightMost = root;
            BinaryNode<E> rightMost = root.getRightChild();
            if (rightMost == null) {
                return parentOfRightMost.getData();
            } else {
                while (rightMost.getRightChild() != null) {
                    parentOfRightMost = rightMost;
                    rightMost = rightMost.getRightChild();
                }

                return rightMost.getData();
            }
        }
    }
} // end class BST
