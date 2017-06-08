/*
  @author So Choi

  Name of Program:  SortedListTester
  Description:    This class tests the LinkedQueue class as well as the teller/customer simulation as given in instructions.
  Date:         5/8/17
  OS:         Mac OS X
  Compiler:       terminal (javac)
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SortedLList2<T extends Comparable<T>> implements ListInterface<T>{
  private Node2 firstDummyNode;  // Reference to first node of chain
  private Node2 lastDummyNode;
  private int  numberOfEntries;

  public SortedLList2(){
    firstDummyNode = new Node2(null);
    lastDummyNode = new Node2(null);
    initializeDataFields();
  } // end default constructor

  
  // CHANGE the LList2 version so it inserts in the correct place
  // so the list stays in order, BUT stop traversing at the lastDummyNode OR
  // when the newEntry <= current Node's data (using compareTo)  
  // (don't forget to increment the numberOfEntries)
  @Override
  public void add(T newEntry) {
    if (newEntry != null) {
      if (isEmpty()) {
        /*
          insertNode is a helper function to help refactor redundant code.
          See below for private method.
        */
        insertNode(newEntry, firstDummyNode, lastDummyNode);
      } else {
        Node2 prevNode = firstDummyNode;
        Node2 currentNode = prevNode.getNextNode();

        while (currentNode != lastDummyNode) {
          if (newEntry.compareTo(currentNode.getData()) < 0){
            break;
          }
          prevNode = currentNode;
          currentNode = currentNode.getNextNode();
        }

        insertNode(newEntry, prevNode, currentNode);
      }

      numberOfEntries++;
    } else {
      System.out.println("Error: Null entry cannot be added to the list!");
    }
  }

  private void insertNode(T entry, Node2 prev, Node2 next){
    Node2 newNode = new Node2(entry);
    newNode.setPrevNode(prev);
    newNode.setNextNode(next);
    prev.setNextNode(newNode);
    next.setPrevNode(newNode);
  }

  /*  
      COMPLETELY REWRITE the LList2 version so you call add(T) passing newEntry, and ignore newPosition and return true
  */
  @Override
  public boolean add(int newPosition, T newEntry) {
    add(newEntry);
    return true;
  }

  /*
      CHANGE from Lab Ex. 4.2 Answers so it STOPS traversing when you reach the lastDummyNode OR when you either find anEntry (and USE compareTo) or if the currentNode's data is > anEntry (using compareTo) if found, do as done in LList2 (including decrementing numberOfEntries) and return true, otherwise return false
   */
  @Override
  public boolean remove(T anEntry) {
    boolean found = false;
    int isEntryGEQ;

    Node2 currentNode = firstDummyNode.getNextNode();
    while(!found && (currentNode != lastDummyNode)){
      int compareVal = anEntry.compareTo(currentNode.getData());
      if(compareVal == 0){
        found = true;

        Node2 nodeBefore = currentNode.getPrevNode();
        Node2 nodeAfter = currentNode.getNextNode();
        
        nodeBefore.setNextNode(nodeAfter);
        nodeAfter.setPrevNode(nodeBefore);

        numberOfEntries--;
      }else{
        if(compareVal < 0){
          break;
        }
        currentNode = currentNode.getNextNode();
      }
    }

    return found;
  }

  @Override
  public T remove(int givenPosition) {
    T result = null;                           // Return value

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      // same code if givenPosition is 1 or > 1
      Node2 nodeToRemove = getNodeAt(givenPosition);
      Node2 nodeBefore = nodeToRemove.getPrevNode();
      result = nodeToRemove.getData();     // Save entry to be removed
      Node2 nodeAfter = nodeToRemove.getNextNode();
      nodeBefore.setNextNode(nodeAfter);   // Remove entry
      nodeAfter.setPrevNode(nodeBefore);

      numberOfEntries--;                      // Update count
      return result;                          // Return removed entry
    } else {
      return null;
    }
  } // end remove(int)

  @Override
  public void clear() {
    initializeDataFields();
  }

  @Override
  public T getEntry(int givenPosition) {
    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      return getNodeAt(givenPosition).getData();
    }else{
      return null;
    }
  }

  /*
      CHANGE the LList2 version so it traverses the list like in remove(T) so it STOPS traversing when you reach the lastDummyNode OR when you either find anEntry (and USE compareTo) or if the currentNode's data is > anEntry (using compareTo)
      return true if found, false otherwise
  */
  @Override
  public boolean contains(T anEntry) {
    boolean found = false;
    int isEntryGEQ;

    Node2 currentNode = firstDummyNode.getNextNode();
    while (!found && (currentNode != lastDummyNode)) {
      int compareVal = anEntry.compareTo(currentNode.getData());
      if (compareVal == 0) {
        found = true;
      } else {
        if (compareVal < 0) {
          break;
        }
        currentNode = currentNode.getNextNode();
      }
    } // end while

    return found;
  }

  @Override
  public int getLength() {
    return numberOfEntries;
  }

  @Override
  public boolean isEmpty() {
    return numberOfEntries==0;
  }

  @Override
  public void display() {
    Node2 currNode;

    currNode = firstDummyNode.getNextNode();  // FOR LAB EXERCISE 4.2
    while( currNode != lastDummyNode )  // FOR LAB EXERCISE 4.2
    {
        System.out.println(currNode.getData());
        currNode = currNode.getNextNode();
    }   
  } // end display

  // YOU FINISH THIS METHOD SO IT DISPLAYS
  // ALL THE DATA IN THE LIST BACKWARDS
  // (remember to ignore the dummy nodes' data)
  public void displayBackwards() {
    if (isEmpty()) {
      System.out.println("Error: The list is empty!");
    }else{
      Node2 currNode = lastDummyNode.getPrevNode();
      while (currNode != firstDummyNode) {
        System.out.println(currNode.getData());
        currNode = currNode.getPrevNode();
      }      
    }
  }
  
  // Initializes the class's data fields to indicate an empty list.
  private void initializeDataFields()
  {
    firstDummyNode.setNextNode(lastDummyNode);
    lastDummyNode.setPrevNode(firstDummyNode);
    numberOfEntries = 0;
  } // end initializeDataFields

  // Returns a reference to the Node2 at a given position.
  // Precondition: The chain is not empty;
  //               1 <= givenPosition <= numberOfEntries.
  // Returns a reference to the node at a given position.
  // CHANGE TO search forwards ONLY if givenPosition is between 1 and
  //     numberOfEntries/2 (inclusive)
  private Node2 getNodeAt(int givenPosition) 
  { 
    if(isEmpty()){
      System.out.println("Error: This list is empty!");
      return lastDummyNode;
    }else{
      if( givenPosition < 1 ){
        return firstDummyNode;
      } else if( 1 <= givenPosition && givenPosition <= numberOfEntries ) {
        Node2 currentNode;
        if ( givenPosition <= numberOfEntries/2 ){
          currentNode = searchForwards(givenPosition);
        }else{
          currentNode = searchBackwards(givenPosition);
        }

        return currentNode;
      } else {
        return lastDummyNode;
      }
    }
  } // end getNodeAt

  private Node2 searchForwards(int pos){
    Node2 currentNode = firstDummyNode.getNextNode();
    for (int counter = 1; counter < pos; counter++) {
      currentNode = currentNode.getNextNode();
    }
    return currentNode;
  }

  private Node2 searchBackwards(int pos){
    Node2 currentNode = lastDummyNode.getPrevNode();

    for (int counter = numberOfEntries; counter > pos; counter--) {
      currentNode = currentNode.getPrevNode();
    }
    return currentNode;
  }

  /*
    Open an input file and initialize a Scanner to read the file. Throw an exception when the file cannot be found.
      @return Scanner variable to help read the file line by line
  */
  public static Scanner userScanner = new Scanner(System.in);
  public static Scanner openInputFile(){
    String filename;
    Scanner scanner=null;
        
    System.out.print("Enter the input filename: ");
    filename = userScanner.nextLine();
    File file = new File(filename);

    try{
      scanner = new Scanner(file);
    }// end try
    catch(FileNotFoundException fe){
       System.out.println("Can't open input file\n");
      return null; // array of 0 elements
    } // end catch
    return scanner;
  }

  /*
      Tokenize input string into three integers.

      Call its setDate() method passing the 3 ints.
      If setDate returns true, insert it into the SortedLList2 otherwise, display a message on the screen that an invalid date was read (and display it). (Reading from the file could be a separate method.)
  */
  private static Date convertToDate(String line){
    String [] splitArray = line.split("[ \t]+");

    int [] dateParts = new int[3];
    
    for(int i = 0; i < splitArray.length; ++i){
      dateParts[i] = tryParseInt(splitArray[i]);
    }

    Date tempDate = new Date();
    boolean didSetDate = false;
    didSetDate = tempDate.setDate(dateParts[0], dateParts[1], dateParts[2]);
    if (!didSetDate) {
      System.out.println("Invalid date, not added: " + dateParts[0] + "-" + dateParts[1] + "-" + dateParts[2]);
      return null;
    } else {
      return tempDate;
    }
  } // end tokenize

  /*
      Helper function to parse integers to instantiate new Date objects.
      @return   integer version of string
  */
  private static Integer tryParseInt(String str) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public static void testSortedLList2(SortedLList2<Date> dateList)
  {
    if( dateList== null || dateList.getLength() < 2 )
    {
      System.out.println("\nEither empty or not enough nodes in the list; no testing done\n");
      return;
    }
    Date date1, date2, newDate;
    
    int middle = dateList.getLength()/2;
    
    date1 = dateList.getEntry(middle);
    date2 = dateList.getEntry(middle+1);

    System.out.println("Testing removing element #" + (middle+1) 
                  + ": " + date2);
    date2 = dateList.remove(middle+1);
    if(  date2 != null )
      System.out.println("Successfully removed: " + date2);
    else
      System.out.println("Error: unable to remove element #" + (middle+1));

    System.out.println("\nTesting removing element #" + middle + ": " + date1);
    date1 = dateList.remove(middle);
    if(  date1 != null )
      System.out.println("Successfully removed: " + date1);
    else
      System.out.println("Error: unable to remove element #" + middle);

    newDate = new Date();
    System.out.println("Testing adding default Date: "+ newDate);
    if( dateList.add(dateList.getLength()+1, newDate) )
      System.out.println("Default date successfully added!");
    else
      System.out.println("Error: unable to add default Date");
    System.out.println("\nNow the list has: ");
    dateList.display();
  }

  public static void printContainsDate(SortedLList2<Date> list, Date testDate){
    if(list.contains(testDate)){
      System.out.println("The list contains " + testDate);
    }else{
      System.out.println("(Good) The list doesn't contain " + testDate);
    }
  }

  public static void printRemoveDate(SortedLList2<Date> list, Date testDate){
    if(list.remove(testDate)){
      System.out.println("Successfully removed " + testDate);
    }else{
      System.out.println("(Good) Unable to remove " + testDate);
    }
  }

  /*
    In main, call a SEPARATE static method that YOU write (in the main driver class) to test the SortedLList2<Date> (so it must be a parameter). In this method, call the SortedLList2's display() method AND the displayBackwards() method (please display appropriate descriptions first).

    Also, test the SortedLList2's contains(T) and then remove(T) (TEST THESE IN SEPARATE IF STATEMENTS), passing a NEW Date with the same month, day and year as in list's LAST node (so call the getEntry() method passing the length of the list to getEntry(), but DON'T pass the return value of getEntry to the contains(T) or remove(T) method!), displaying the messages that indicate if contains returns true or false (include the Date you're searching for) and if remove returns true or false (include the Date you're tried to remove).

    Then test contains(T) passing a default Date, displaying the messages that indicate if contains returns true or false AND try to remove it (again displaying the messages the indicate if it returned true or false).

    Call the display and displayBackwards methods again.
  */
  public static void testHW3(SortedLList2<Date> list){
    System.out.println("\nThe sorted list:");
    list.display();
    System.out.println("\nThe sorted list (backwards):");
    list.displayBackwards();

    Date lastEntry = list.getEntry(list.numberOfEntries);
    Date testDate = new Date();
    testDate.setDate(lastEntry.getMonth(), lastEntry.getDay(), lastEntry.getYear());

    printContainsDate(list, testDate);
    printRemoveDate(list, testDate);

    printContainsDate(list, new Date());
    printRemoveDate(list, new Date());

    System.out.println("\nThe sorted list:");
    list.display();
    System.out.println("\nThe sorted list (backwards):");
    list.displayBackwards();

    // The list contains 12/10/2023 Successfully removed 12/10/2023
    // (Good) The list doesn't contain 1/1/1000 (Good) Unable to remove 1/1/1000
  }
  public static void main(String[] args){
    /*
        Write main so it has a SortedLList2<Date> local variable.
        Open an input file the same way you did in Prog. HW#1 (using the openInputFile method).

        If it doesn't open, end the program.
        If it opens, assign a new instance of a SortedLList2<Date> to the main variable, and read the file which has several sets of: 3 ints (for month, day and year in the file as MM DD YYYY). For each set of input data, (in a loop) instantiate a new default Date and assign to a local Date variable. 
    */
    SortedLList2<Date> list;

    Scanner file = openInputFile();
    if(file != null){
      list = new SortedLList2<Date>();
      while (file.hasNextLine()) {
        String line = file.nextLine();
        Date tempDate = convertToDate(line);
        if(tempDate != null){
          list.add(tempDate);
        }
      }
      file.close();

      testHW3(list);
      //  Last in main, call the testSortedLList2 method given in the HW3_JavaCodeFile.
      testSortedLList2(list);

    }
  }

  private class Node2{
    private T    data; // Entry in list
    private Node2 next; // Link to next Node2
    private Node2 prev; // Link to previous Node2

    private Node2(T dataPortion){
      data = dataPortion;
      next = null;
      prev = null;
    } // end constructor

    private Node2(T dataPortion, Node2 nextNode){
      data = dataPortion;
      next = nextNode;
      prev = null;
    } // end constructor

    private T getData(){
      return data;
    } // end getData

    private void setData(T newData){
      data = newData;
    } // end setData

    private Node2 getNextNode(){
      return next;
    } // end getNextNode

    private void setNextNode(Node2 nextNode){
      next = nextNode;
    } // end setNextNode

    private Node2 getPrevNode(){
      return prev;
    } // end getNextNode

    private void setPrevNode(Node2 prevNode){
      prev = prevNode;
    } // end setNextNode
  } // end Node2
} // end SortedLList2 class

/*
    OUTPUT
*/
/*
    Invalid date, not added: 9-31-1939

    The sorted list:
    11/28/1934
    9/23/1935
    11/8/1937
    7/18/1939
    4/29/1940
    8/17/1947
    2/10/1953
    10/20/1955
    12/30/1962
    12/17/1966
    3/25/1981
    7/28/1983
    10/5/1995
    8/18/2002
    12/24/2007
    8/17/2010
    11/30/2013
    8/28/2016
    9/18/2024
    9/28/2028

    The sorted list (backwards):
    9/28/2028
    9/18/2024
    8/28/2016
    11/30/2013
    8/17/2010
    12/24/2007
    8/18/2002
    10/5/1995
    7/28/1983
    3/25/1981
    12/17/1966
    12/30/1962
    10/20/1955
    2/10/1953
    8/17/1947
    4/29/1940
    7/18/1939
    11/8/1937
    9/23/1935
    11/28/1934
    The list contains 9/28/2028
    Successfully removed 9/28/2028
    (Good) The list doesn't contain 1/1/1000
    (Good) Unable to remove 1/1/1000

    The sorted list:
    11/28/1934
    9/23/1935
    11/8/1937
    7/18/1939
    4/29/1940
    8/17/1947
    2/10/1953
    10/20/1955
    12/30/1962
    12/17/1966
    3/25/1981
    7/28/1983
    10/5/1995
    8/18/2002
    12/24/2007
    8/17/2010
    11/30/2013
    8/28/2016
    9/18/2024

    The sorted list (backwards):
    9/18/2024
    8/28/2016
    11/30/2013
    8/17/2010
    12/24/2007
    8/18/2002
    10/5/1995
    7/28/1983
    3/25/1981
    12/17/1966
    12/30/1962
    10/20/1955
    2/10/1953
    8/17/1947
    4/29/1940
    7/18/1939
    11/8/1937
    9/23/1935
    11/28/1934
    Testing removing element #10: 12/17/1966
    Successfully removed: 12/17/1966

    Testing removing element #9: 12/30/1962
    Successfully removed: 12/30/1962
    Testing adding default Date: 1/1/1000
    Default date successfully added!

    Now the list has: 
    1/1/1000
    11/28/1934
    9/23/1935
    11/8/1937
    7/18/1939
    4/29/1940
    8/17/1947
    2/10/1953
    10/20/1955
    3/25/1981
    7/28/1983
    10/5/1995
    8/18/2002
    12/24/2007
    8/17/2010
    11/30/2013
    8/28/2016
    9/18/2024
*/