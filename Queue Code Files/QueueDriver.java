/**   A driver that demonstrates the concrete Queue classes.   @author Frank M. Carrano   @author Timothy M. Henry   @version 4.1
   UPDATED by C. Lee-Klawender*/public class QueueDriver{	public static void main(String[] args)	{
        System.out.println("Create a queue: ");		QueueInterface<String> myQueue = ________________<>(); // get an instance of a Queue		testQueueOperations(myQueue);

		System.out.println("\n\nDone.");	}  // end main	public static void testQueueOperations(QueueInterface<String> myQueue)	{		System.out.println("\n\nisEmpty() returns " + myQueue.isEmpty() + "\n");		System.out.println("Add to queue to get\n" +		                   "Joe Jess Jim Jill Jane Jerry\n");        String [] strArray = {"Jim", "Jess", "Jill", "Jane", "Joe", "Jack"};		for(  int i=0; i < strArray.length ; ++i )
		{
            if(myQueue.enqueue(strArray[i]))
                System.out.println("Successfully added " + strArray[i]);
            else
                System.out.println("UNABLE to enqueue " + strArray[i]);
		}		// FOR LAB EXERCISE 3.1, ADD CODE HERE TO GET THE SIZE FROM THE Queue AND DISPLAY IT		System.out.println("\nisEmpty() returns " + myQueue.isEmpty() + "\n");		System.out.println("\n\nTesting getFront and dequeue:\n");		while (!myQueue.isEmpty())		{			String front = myQueue.peekFront();			System.out.println(front + " is at the front of the queue.");			front = myQueue.dequeue();			System.out.println(front + " is removed from the front of the queue.\n");		} // end while		System.out.print("\nThe queue should be empty: ");		System.out.println("isEmpty() returns " + myQueue.isEmpty() + "\n\n"); 		System.out.println("myQueue.getFront() returns " +  myQueue.peekFront());		System.out.println("myQueue.dequeue() returns " +  myQueue.dequeue() + "\n");

		System.out.println("\nEnd of Queue Test\n");	} // end testQueueOperations}  // end Driver