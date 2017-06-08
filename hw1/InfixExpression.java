/*
	@author So Choi

	Name of Program: 	InfixExpression
	Description: 		This class saves and evaluates a string of infix expression and returns its result as a double.
	Date: 				4/23/17
	OS:					Mac OS X
	Compiler: 			terminal (javac)
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class InfixExpression{
	/*
		Private instance variables:
		- exp (String): Infix expression
		- tokens (ArrayList <String>): Tokenized infix expression
		- result (double): Evaluated result of infix expression
	*/
	private String exp = "";
	private ArrayList<String> tokens = new ArrayList<String>();
	private double result;

	/*
		List of public instance methods
		+ InfixExpression()
		+ InfixExpression(String)
		+ setExpression(String): void
		+ getExpression(): String
		+ getResult(): double
	*/

	/*
		+ InfixExpression()
		- default constructor (initializes the instance variables to empty objects if not done in their declarations, but NOT null)
	*/
	public InfixExpression(){}

	/*
		+ InfixExpression(String)
		- constructor with a String parameter: call the default constructor (if the instance variables are not intitialized in their declaration), then call the mutator for the String instance variable
	*/
	public InfixExpression(String exp){
		this();
		setExpression(exp);
	}

	/*
		+ setExpression(String)
		- mutator for the String instance variable (whole expression), which not only assigns the parameter to the String instance variable (if it's not null), but also calls the private tokenize method, then
		private evaluate method (DON'T DO ANYTHING IF THE parameter is null)
			@param exp 		Infix expression to tokenize and evaluate
	*/
	public void setExpression(String exp){
		if(exp != null){
			this.exp = exp;
			this.tokenize();
			this.evaluate();
		}
	}

	/*
		+ getExpression()
		- accessor for the String instance variable (return a String)
			@return the infix expression
	*/
	public String getExpression(){
		return this.exp;
	}

	/*
		+ getResult()
		- accessor for the double instance variable
			@return the evaluated result of the infix expression
	*/
	public double getResult(){
		return this.result;
	}

	/*
		- toString() method (that overrides Object's toString) that returns a
		String with the instance variable for the whole infix expression and
		the double result in the form as shown in the TEST RUNS
			@return the infix expression and its result
	*/
	public String toString(){
		return "Infix String: " + this.exp + ", result: " + this.result;
	}

	/*
		List of private instance methods
		- tokenize(): void
		- evaluate(): void
	*/
	/*
		- tokenize()
		tokenizes the input String, assigns to ArrayList of Strings
	*/
	private void tokenize(){
		String [] tokenArray = exp.split("[ \t]+");
		tokens.clear(); // clear the ArrayList
		for(int i=0; i < tokenArray.length; ++i)
		{
			tokens.add(tokenArray[i]); // add the next token to the ArrayList
		}
	} // end tokenize

	/*
		- evaluate()
		evaluates the infix expression (stored in ArrayList of Strings), USING A LinkedStack of Doubles AND an ArrayStack of Strings.
	*/
	private void evaluate(){
		// declare ArrayStack<String> for the operators
		ArrayStack<String> operators = new ArrayStack<String>();
		// declare LinkedStack<Double> for the operands
		LinkedStack<Double> operands = new LinkedStack<Double>();

		// for each token in the ArrayList of strings (member variable)
		for(String token : tokens){
			// if the token is an operator ("+", "-", "*", "/") 
			if(isOperator(token)){
				if(operators.isEmpty()){
					operators.push(token);
				}else{
					if(precedence(token) > precedence(operators.peek())){
						operators.push(token);
					}else{
						while(!operators.isEmpty() && 
							precedence(token) <= precedence(operators.peek())){
							execute(operators, operands);
						}
						operators.push(token);
					}
				}
			}else if(token.equals("(")){
				operators.push(token);
			}else if(token.equals(")")){
				while(!operators.peek().equals("(")){
					execute(operators, operands);
				}
				operators.pop();
			}else{
	            Double operand = Double.parseDouble(token);
				if(!Double.isNaN(operand)){
					operands.push(operand);
				}else{
					System.out.println("Cannot parse token '" + token + "' to a double!");
		        }
			}
		}

		while(!operators.isEmpty()){
			execute(operators, operands);
		}
		if(operands.size() == 1){
			result = operands.peek();
		}else{
			result = 0;
		}
	}

	/*
		Retrieves parts of the parsed infix expression and evaluates it based on operator type.
	*/
	private void execute(StackInterface<String> operators, StackInterface<Double> operands){
		String operator 	= operators.pop();
		Double leftOperand	= null;
		Double rightOperand	= null;

		if(operands.isEmpty()){
			return;
		}else{
			rightOperand = operands.pop();
		}

		if(operands.isEmpty()){
			return;
		}else{
			leftOperand = operands.pop();
		}

		Double temp = null;
		switch(operator){
			case "+":
				temp = leftOperand + rightOperand;
				break;
			case "-":
				temp = leftOperand - rightOperand;
				break;
			case "*":
				temp = leftOperand * rightOperand;
				break;
			case "/":
				/*
					Note of discretion:
					Checking a double or float with '==' and 0.0 is not entirely accurate and it's better to compare against a threshold.
				*/
				if(rightOperand == 0.0){
					System.out.println("Can't divide by 0!");
				}else{
					temp = leftOperand / rightOperand;
				}
				break;
		}

		if(temp != null){
			operands.push(temp);
		}
	}

	/*
		Checks if the given parameter is an operator.
			@param op 	Token containing operator
			@return true if the parameter is an operator: "+", "-", "*", or "/"

	*/
	private boolean isOperator(String op){
		return (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"));
	}

	/*
		Return an integer based on precedence level for order of operations.
			@param op 	Token containing operator
			@return precedence level
	*/
	private int precedence(String op){
		switch(op){
			case "*":
			case "/":
				return 100;
			case "+":
			case "-":
				return 10;
			case "(":
			case ")":
				return 1;
			default:
				throw new IllegalArgumentException("Unspecified operator!");
		}
	}

	/*
		Open an input file and initialize a Scanner to read the file. Throw an exception when the file cannot be found.
			@return Scanner variable to help read the file line by line
	*/
	// YOU'RE NOT ALLOWED TO CHANGE THIS METHOD, AND YOU MUST USE IT:
	public static Scanner userScanner = new Scanner(System.in);
	public static Scanner openInputFile(){
		String filename;
        	Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    	   System.out.println("Can't open input file\n");
   	    return null; // array of 0 elements
    	} // end catch
    	return scanner;
	} // end openInputFile

	/*
		Method to test infix variables besides input files
	*/
	// call this method as indicated in HW#1, and don't change the method NOR the size of your ArrayStack:
	public static void testHW1(){
		// testing InfixExpression more:
		InfixExpression infix1, infix2;
		infix1 = new InfixExpression(null);
		infix2 = new InfixExpression("( 234.5 * ( 5.6 + 7.0 ) ) / 100.2" );

		System.out.println("\nTesting InfixExpression:");
		System.out.println("When passing null, the String and double = " 
			+ infix1);
		System.out.println("When passing a valid String, the String and double = " 
			+ infix2);

		// testing ArrayStack and LinkedStack more:
		ArrayStack<String> arrStack = new ArrayStack<>();
		LinkedStack<String> linkStack = new LinkedStack<>();
		String [] strArray = {"First", "Second", "Third", "Fourth", "Fifth",
				"Sixth", "Seventh", "Eighth", "Ninth", "Tenth"};
		
		// Testing ArrayStack
		System.out.println("\nTesting the ArrayStack:");
		for( int i=0; i < strArray.length; ++i )
		{
			if(!arrStack.push(strArray[i] + " 1"))
				System.out.println("Error: couldn't push "+ strArray[i]+" 1");
		}
		for( int i=0; i < strArray.length; ++i )
		{
			if(!arrStack.push(strArray[i] + " 2"))
			{
				System.out.println("Out of space, removing " +
							arrStack.pop());
				if(!arrStack.push(strArray[i] + " 2"))
					System.out.println("Error pushing!"+strArray[i]+" 2");
			}
		}
		System.out.println("The size of the ArrayStack is now " + arrStack.size());
		while( !arrStack.isEmpty() )
		{
			System.out.println("Popping "+ arrStack.pop());
		}
		System.out.println("The size of the ArrayStack is now " + arrStack.size());
		if(!arrStack.push(strArray[0] + " 3"))
			System.out.println("Error: couldn't push "+ strArray[0]+" 3");
		else
			System.out.println("After pushing " + arrStack.peek() + 
				", the size of the ArrayStack is now " + arrStack.size());
		// testing LinkedStack
		System.out.println("\nTesting the LinkedStack:");
		for( int i=0; i < strArray.length; ++i )
			linkStack.push(strArray[i] + " 4");
		System.out.println("The size of the LinkedStack is " + linkStack.size());
		for( int i=0; i < strArray.length/2; ++i )
		{
			System.out.println("Popping " + linkStack.pop());
		}
		System.out.println("The size of the LinkedStack is now " + linkStack.size());
		while( !linkStack.isEmpty() )
		{
			System.out.println("Popping "+ linkStack.pop());
		}
		System.out.println("The size of the LinkedStack is now " + linkStack.size());
		if(!linkStack.push(strArray[0] + " 5"))
			System.out.println("Error: couldn't push "+ strArray[0]+" 5");
		else
			System.out.println("After pushing " + linkStack.peek() + 
				", the size of the LinkedStack is now " + linkStack.size());
	} // end stackTester

	/*
		In main, declare a InfixExpression variable and instantiate using the default constructor. Call the openInputFile() method given in the HW1_Java_CodeFile. If the file doesn't open, display an error message and end the program. If it opens, read a line, then process each one (in a loop) the following way until the end of file:
	*/
	public static void main(String[] args){
		InfixExpression expression = new InfixExpression();
		Scanner file = openInputFile();
		if(file != null){
			while (file.hasNextLine()) {
	            String line = file.nextLine();
	            expression.setExpression(line);
		        System.out.println(expression);
	        }
	        file.close();
			testHW1();
		}else{
    		System.out.println("Can't open input file\n");
		}
	}

	/*
		OUTPUT for HW1 Input.txt
	*/
	/*
		Enter the input filename: HW1 Input.txt     
		Infix String: 5.0 * 7.0, result: 35.0
		Infix String: -4.0 + 30. / 2, result: 11.0
		Infix String: 25. / 5. -  3. * 3., result: -4.0
		Infix String: 54.32, result: 54.32
		Can't divide by 0!
		Infix String: 3.5 / 0., result: 0.0
		Infix String: 11.1  * 22.2 + - , result: 0.0
		Infix String: 100. /  -20., result: -5.0
		Infix String: ( 5.0  + 2.5 ) * 3.0, result: 22.5
		Infix String: ( 9.8 - 3. ) * ( 2. +  1. ), result: 20.400000000000002

		Testing InfixExpression:
		When passing null, the String and double = Infix String: , result: 0.0
		When passing a valid String, the String and double = Infix String: ( 234.5 * ( 5.6 + 7.0 ) ) / 100.2, result: 29.488023952095805

		Testing the ArrayStack:
		Out of space, removing Fifth 2
		Out of space, removing Sixth 2
		Out of space, removing Seventh 2
		Out of space, removing Eighth 2
		Out of space, removing Ninth 2
		The size of the ArrayStack is now 15
		Popping Tenth 2
		Popping Fourth 2
		Popping Third 2
		Popping Second 2
		Popping First 2
		Popping Tenth 1
		Popping Ninth 1
		Popping Eighth 1
		Popping Seventh 1
		Popping Sixth 1
		Popping Fifth 1
		Popping Fourth 1
		Popping Third 1
		Popping Second 1
		Popping First 1
		The size of the ArrayStack is now 0
		After pushing First 3, the size of the ArrayStack is now 1

		Testing the LinkedStack:
		The size of the LinkedStack is 10
		Popping Tenth 4
		Popping Ninth 4
		Popping Eighth 4
		Popping Seventh 4
		Popping Sixth 4
		The size of the LinkedStack is now 5
		Popping Fifth 4
		Popping Fourth 4
		Popping Third 4
		Popping Second 4
		Popping First 4
		The size of the LinkedStack is now 0
		After pushing First 5, the size of the LinkedStack is now 1
	*/
}