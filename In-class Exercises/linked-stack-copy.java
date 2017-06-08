public class LinkedStack<T extends DeepCloneable<T>> implements StackInterface<T>
{
	private Node topNode;
	private int count=0; 

	public LinkedStack(){}

	public LinkedStack(LinkedStack<T> param)
	{
		LinkedStack<T> tempLStack = new LinkedStack<T>();
		while(param.topNode != null){
			tempLStack.push(param.pop().deepClone());
		}

		while(tempLStack.topNode != null){
			this.push(tempLStack.pop());
		}
	}
}