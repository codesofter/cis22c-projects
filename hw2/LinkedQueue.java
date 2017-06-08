/*
	@author So Choi

	Name of Program: 	LinkedQueue
	Description: 		This class completes and tests the LinkedQueue class as declared in the LinkedQueue.java file.
	Date: 				5/8/17
	OS:					Mac OS X
	Compiler: 			terminal (javac)
*/

public class LinkedQueue<T extends DeepCloneable<T>> implements QueueInterface<T>, DeepCloneable<LinkedQueue<T>>{
	/*
		Private instance variables:
		- frontNode (Node): References node at front of queue
		- backNode (Node): References node at back of queue
		- count (int): Number of items in the queue
	*/
	private Node frontNode;
	private Node backNode;
	private int count = 0;

	public LinkedQueue(){

	} // end default constructor

	/*
		Clone the linked queue completely (including nested objects that may be the data), without using recursion.
			@return a deep copy of the linked queue.
	*/
	public LinkedQueue<T> deepClone(){
		LinkedQueue<T> clone = new LinkedQueue<T>();

		// Make sure the current list has contents to copy. Otherwise, just return.
		if(count > 0 && frontNode != null){
			T content = frontNode.getData();

			/*
				check contents for nested structures and call overridden deepClone (not this method)
			*/
			if(content instanceof Event){
				// System.out.println("Content is DeepCloneable: " + content);
				content = content.deepClone();
			}
			clone.enqueue(content);

			Node currentNode = frontNode.getNextNode();
			while(currentNode != null){
				content = currentNode.getData();
				if(content != null){
					/*
						check contents for nested structures and call overridden deepClone (not this method)
					*/
					if(content instanceof Event){
						// System.out.println("Content is DeepCloneable: " + content);
						content = content.deepClone();
					}
					clone.enqueue(content);
				}

				currentNode = currentNode.getNextNode();
			}
		}

		return clone;
	}
	
	/*
		Adds an item to the linked queue.
			@param newEntry 	Value to add to the queue with
			@return true if adding was successful.
	*/
	public boolean enqueue(T newEntry){
		// In addition to updating the backNode, also make sure you check if the list was empty before adding this and update the correct variable if so
		if(newEntry != null){
			// Update backNode
			Node prevNode = backNode;
			backNode = new Node(newEntry);

			if(isEmpty()){ // Check if list is empty
				frontNode = backNode;
			}else{
				prevNode.setNextNode(backNode);
			}
			++count;
			return true;
		}else{
			return false;
		}
	} // end enqueue
	
	/*
		Removes an item from the linked queue.
			@return the value if adding was successful.
	*/
	public T dequeue(){
		/*
			dequeue adds removes from the front of the queue, but it becomes empty after removing it, you need to update the backNode (you should figure out to what!)
		*/
		if(isEmpty()){
			return null;
		}else{
			T front = peekFront();
			frontNode = frontNode.getNextNode();
			--count;

			if(isEmpty()){
				backNode = null;
			}

			return front;
		}
	} // end dequeue

	/*
		Check if the linked queue is empty.
			@return true if the linked queue is empty (front node is null).
	*/
	public boolean isEmpty(){
		return frontNode == null;
	} // end isEmpty

	/*
		Check the size of the linked queue.
			@return the size of the linked queue.
	*/
	public int size(){
		return count;
	}

	/*
		Clear the linked queue.
	*/
	public void clear(){
		frontNode = null;
		count = 0;
	}

	/*
		Check the front of the queue without removing it from the queue.
			@return the value of the front of the queue if check was successful.
	*/
	public T peekFront(){
		if (isEmpty()){
			return null;
		}
		else{
			return frontNode.getData();
		}
	} // end getFront

	public String toString(){
		String str = "";
		if( count > 0 ){
			Node currentNode = frontNode;

			while(currentNode != null){
				T temp = currentNode.getData();
				str += temp + " ";
				currentNode = currentNode.getNextNode();
				if(temp instanceof DeepCloneable && currentNode != null){
					str += "-> \n";
				}
			}
		}

		return str;
	}

	private class Node{
		private T    data; // Entry in queue
		private Node next; // Link to next node

		private Node(T dataPortion)
		{
			data = dataPortion;
			next = null;
		} // end constructor

		private Node(T dataPortion, Node linkPortion)
		{
			data = dataPortion;
			next = linkPortion;
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
} // end Linkedqueue