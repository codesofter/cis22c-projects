/**
   A class that implements the ADT list by using a chain of
   linked nodes that has a head reference.

   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class CircularLList<T> implements ListInterface<T>
{
  private Node firstNode;            // Reference to first node of chain
  private int  numberOfEntries;

  public CircularLList()
  {
    initializeDataFields();
  } // end default constructor

  public void clear()
  {
    initializeDataFields();
  } // end clear

  public void add(T newEntry)         // OutOfMemoryError possible
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

  public boolean add(int newPosition, T newEntry){
    if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)){
      Node newNode = new Node(newEntry);
      if (newPosition == 1){                  // Case 1
        if( numberOfEntries == 0 ){         // Case 1a: empty list
          firstNode = newNode;
        }else{                              // Case 1b: beginning of non-empty list
          Node lastNode = getNodeAt(numberOfEntries); // NEW for CircularLList****
          lastNode.setNextNode(newNode);  // NEW for CircularLList****
        }
        newNode.setNextNode(firstNode);
        firstNode = newNode;
      }
      else{                   // Case 2: list is not empty
        Node nodeBefore = getNodeAt(newPosition - 1); // and newPosition > 1
        Node nodeAfter = nodeBefore.getNextNode();
        newNode.setNextNode(nodeAfter);
        nodeBefore.setNextNode(newNode);
      } // end if
      numberOfEntries++;
      return true;
    }else{
      return false;
    }
} // end add

  public boolean remove(T anEntry)
  {
    if(firstNode != null && numberOfEntries > 0){
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

        while(currentNode != null){
          currentNode = nodeBefore.getNextNode(); // Start from index 2
          if(anEntry.equals(currentNode.getData())){
            nodeBefore = currentNode;

            numberOfEntries--;             // Update count
            return true;                   // Break out of loop and also this function early by returning. Otherwise, continue with loop.
          }
        }
      }
    }
    // if nothing was returned, return false
    return false;
  }

  public T remove(int givenPosition){
    T result = null;                           // Return value
    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)){
      if (givenPosition == 1){                 // Case 1: Remove first entry
        Node nodeBefore = getNodeAt(numberOfEntries); // NEW for CircularLList****
        result = firstNode.getData();        // Save entry to be removed
        if( numberOfEntries == 1 ){ // last node?  NEW for CircularLList****
           firstNode = null;          // NEW for CircularLList****
        }else{
           firstNode = firstNode.getNextNode(); // Remove entry
           nodeBefore.setNextNode(firstNode);     // NEW for CircularLList****
        }
      }else{                                    // Case 2: Not first entry
        Node nodeBefore = getNodeAt(givenPosition - 1);
        Node nodeToRemove = nodeBefore.getNextNode();
        result = nodeToRemove.getData();     // Save entry to be removed
        Node nodeAfter = nodeToRemove.getNextNode();
        nodeBefore.setNextNode(nodeAfter);   // Remove entry
      } // end if
      numberOfEntries--;                      // Update count
      return result;                          // Return removed entry
    }else{
      return null;
    }
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

   /*
      Lab Exercise 4.1: (see Catalyst for where to upload and the due date)

      Rewrite the display method in the LList.java file in the Linked List Code Files link in Catalyst (under Week 1) so the it traverses a circular linked list. Make sure you allow for an empty list (but don't get an exception thrown) AND don't skip displaying the data in the first or last nodes. (HINT: check if the list is empty in a separate if statement, and if not, use a DO-WHILE loop to traverse!)

      Upload ONLY the display method. You're not required to run this (because the rest of the class hasn't been written yet)!
   */
  public void display(){
    // Check for empty list
    if(numberOfEntries > 0 && firstNode != null){
      // Print first node first
      Node currNode = firstNode;
      System.out.println(firstNode.getData());

      // Print rest of list
      Node nextNode = firstNode.getNextNode();
      while( nextNode != null && nextNode != firstNode ){
        System.out.println(nextNode.getData());
        nextNode = nextNode.getNextNode();
      }
    }else{
      System.out.println("This list is empty!");
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



