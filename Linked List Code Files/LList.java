/**
   A class that implements the ADT list by using a chain of
   linked nodes that has a head reference.

   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class LList<T> implements ListInterface<T>
{
	private Node firstNode;            // Reference to first node of chain
	private int  numberOfEntries;

	public LList()
	{
		initializeDataFields();
	} // end default constructor

	public void clear()
	{
		initializeDataFields();
	} // end clear

	public void add(T newEntry) 	      // OutOfMemoryError possible
	{
		Node newNode = new Node(newEntry);

		if (isEmpty())
			firstNode = newNode;
		else                              // Add to end of non-empty list
		{
			Node lastNode = getNodeAt(numberOfEntries);
			lastNode.setNextNode(newNode); // Make last node reference new node
		} // end if

		numberOfEntries++;
	}  // end add

   public boolean add(int newPosition, T newEntry)
	{
 		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1))
		{
			Node newNode = new Node(newEntry);

			if (newPosition == 1)                  // Case 1
			{
				newNode.setNextNode(firstNode);
				firstNode = newNode;
			}
			else									         // Case 2: list is not empty
			{                                      // and newPosition > 1
            Node nodeBefore = getNodeAt(newPosition - 1);
            Node nodeAfter = nodeBefore.getNextNode();
				newNode.setNextNode(nodeAfter);
				nodeBefore.setNextNode(newNode);
			} // end if

			numberOfEntries++;
			return true;
		}
      else
            return false;
   } // end add

  /*   remove the first node for which the parameter matches the data
   *    (note that when removing a Node, the previous Node's next variable must be
   *    updated to refer to the Node after the one removed, which could be null)
   *   
   *    DON'T CALL getNodeAt, DON'T CALL remove(int)
   *    Note that the remove method MUST use the equals method AND
   *    a local variable that refers to the previous node (hint: see the contains method).
   */ 
  public boolean remove(T anEntry)
  {
      Node nodeBefore = firstNode;
      Node currentNode = null;

      // If the node to be removed is the first index, make sure to remove that node and re-set firstNode as the next one.
      if(anEntry.equals(firstNode.getData())){
          firstNode = firstNode.getNextNode();  // Remove entry
          numberOfEntries--;                    // Update count
          return true;
      }else{  // Continue to next cases
        // First node was taken care of, so skip the first node:
        nodeBefore = firstNode.getNextNode();

        /*
            This while loop which is also in the `contains` function
            can be refactored as a `find` function.
            Alternatively this entire section could have been wrapped with a
                `if( contains(anEntry) ){ ... }`
            to check for an early return, but this would still require us to loop another time
            to get the `Node` object, so it would not be efficient.
        */
        while(currentNode != null){
          currentNode = nodeBefore.getNextNode(); // Start from index 2
          if(anEntry.equals(currentNode.getData())){
            nodeBefore = currentNode;

            numberOfEntries--;             // Update count
            return true;                   // Break out of loop and also this function early by returning. Otherwise, continue with loop.
          }
        }
      }
      // if nothing was returned, return false
      return false;
  }


	public T remove(int givenPosition)
	{
      T result = null;                           // Return value

      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
         if (givenPosition == 1)                 // Case 1: Remove first entry
         {
            result = firstNode.getData();        // Save entry to be removed
            firstNode = firstNode.getNextNode(); // Remove entry
         }
         else                                    // Case 2: Not first entry
         {
            Node nodeBefore = getNodeAt(givenPosition - 1);
            Node nodeToRemove = nodeBefore.getNextNode();
            result = nodeToRemove.getData();     // Save entry to be removed
            Node nodeAfter = nodeToRemove.getNextNode();
            nodeBefore.setNextNode(nodeAfter);   // Remove entry
         } // end if

         numberOfEntries--;                      // Update count
         return result;                          // Return removed entry
      }
      else
          return null;
	} // end remove


   public T getEntry(int givenPosition)
   {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
		{
            return getNodeAt(givenPosition).getData();
     	}
      else
          return null;
   } // end getEntry


	public boolean contains(T anEntry)
	{
		boolean found = false;
		Node currentNode = firstNode;

		while (!found && (currentNode != null))
		{
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		} // end while

		return found;
	} // end contains

   public int getLength()
   {
      return numberOfEntries;
   } // end getLength

   public boolean isEmpty()
   {
        return numberOfEntries==0;

   } // end isEmpty

   public void display()
   {
        Node currNode;

        currNode = firstNode;
        while( currNode != null )
        {
            System.out.println(currNode.getData());
            currNode = currNode.getNextNode();
        }
    } // end display

   // Initializes the class's data fields to indicate an empty list.
   private void initializeDataFields()
   {
		firstNode = null;
		numberOfEntries = 0;
   } // end initializeDataFields

   // Returns a reference to the node at a given position.
   // Precondition: The chain is not empty;
   //               1 <= givenPosition <= numberOfEntries.
	private Node getNodeAt(int givenPosition)
	{
		if((1 <= givenPosition) && (givenPosition <= numberOfEntries))
		{
            Node currentNode = firstNode;

            // Traverse the chain to locate the desired node
            // (skipped if givenPosition is 1)
            for (int counter = 1; counter < givenPosition; counter++)
                currentNode = currentNode.getNextNode();

            return currentNode;
        }
        else
            return null;
	} // end getNodeAt

	private class Node
	{
      private T    data; // Entry in list
      private Node next; // Link to next node

      private Node(T dataPortion)
      {
         data = dataPortion;
         next = null;
      } // end constructor

      private Node(T dataPortion, Node nextNode)
      {
         data = dataPortion;
         next = nextNode;
      } // end constructor

      private T getData()
      {
         return data;
      } // end getData

      private void setData(T newData)
      {
         data = newData;
      } // end setData

      private Node getNextNode()
      {
         return next;
      } // end getNextNode

      private void setNextNode(Node nextNode)
      {
         next = nextNode;
      } // end setNextNode
	} // end Node
} // end LList



