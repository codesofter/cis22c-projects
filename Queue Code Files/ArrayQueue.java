/**   A class that implements the ADT queue by using an expandable   circular array with one unused location after the back of the queue.   @author Frank M. Carrano   @author Timothy M. Henry   @version 4.1
   UPDATED BY C. Lee-Klawender*/public final class ArrayQueue<T> implements QueueInterface<T>{   private T[] queue; // Circular array of queue entries   private int frontIndex; // Index of front entry   private int backIndex;  // Index of back entry
   private int count;
   private static final int DEFAULT_CAPACITY = 5;   private static final int MAX_CAPACITY = 100;   public ArrayQueue()   {      this(DEFAULT_CAPACITY);   } // end default constructor   public ArrayQueue(int initialCapacity)   {      if(initialCapacity < DEFAULT_CAPACITY)
         initialCapacity = DEFAULT_CAPACITY;
      else
         if(initialCapacity > MAX_CAPACITY)
            initialCapacity = MAX_CAPACITY;      // The cast is safe because the new array contains null entries      @SuppressWarnings("unchecked")      T[] tempQueue = (T[]) new Object[initialCapacity];      queue = tempQueue;      frontIndex = 0;      backIndex = queue.length - 1;
      count = 0;   } // end constructor	public boolean enqueue(T newEntry)	{       if( count < queue.length )
       {
	// ADD CODE TO add data to array HERE (AFTER the back)!
	//   BE SURE THIS IS A CIRCULAR ARRAY
	//   (may need to go the beginning of the array)

          ++count;
          return true;       }
       else
          return false;
	} // end enqueue	public T peekFront()	{		if (isEmpty())			return null;		return queue[frontIndex];	} // end getFront	public T dequeue()	{		if (isEmpty())			return null;		else        {
            --count;

	// ADD CODE TO remove data from array HERE!
	//   BE SURE THIS IS A CIRCULAR ARRAY
	//   (may need to go the beginning of the array)

        } // end if	} // end dequeue	public boolean isEmpty()	{	   return ______________;	} // end isEmpty
    public int size()
    {
	   return ______________;
    }}  // end ArrayQueue
