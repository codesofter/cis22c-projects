import java.util.*;/**    A class of stacks whose entries are stored in an array.    @author Frank M. Carrano    @author Timothy M. Henry    @version 4.0
    UPDATED by C. Lee-Klawender*/public class ArrayStack<T> implements StackInterface<T>{	private T[] stack;    // Array of stack entries	private int topIndex; // Index of top entry	private static final int DEFAULT_CAPACITY = 5;	private static final int MAX_CAPACITY = 100;	public ArrayStack()	{       this(DEFAULT_CAPACITY); 	} // end default constructor	public ArrayStack(int initialCapacity)	{       if(initialCapacity > MAX_CAPACITY)
           initialCapacity = MAX_CAPACITY;
       else
           if( initialCapacity < DEFAULT_CAPACITY )
               initialCapacity = DEFAULT_CAPACITY;       // The cast is safe because the new array contains null entries       @SuppressWarnings("unchecked")       T[] tempStack = (T[])new Object[initialCapacity];       stack = tempStack;       topIndex = -1;	} // end constructor	public boolean push(T newEntry)	{        if( topIndex+1 < stack.length )
        {            stack[topIndex + 1] = newEntry;            topIndex++;
            return true;
        }
        return false;	} // end push	public T peek()	{		if (isEmpty())  // UPDATE FOR HW#1			return null;		else            return stack[topIndex];	} // end peek	public T pop()	{		if (isEmpty())   // UPDATE FOR HW#1			return null;		else		{			T top = stack[topIndex];			stack[topIndex] = null;			topIndex--;            return top;		} // end if   } // end pop// TWO MORE METHODS ARE REQUIRED HERE (PART OF EXERCISE 2.1)} // end ArrayStack
