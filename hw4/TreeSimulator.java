/*
	@author So Choi

	Name of Program:  	TreeSimulator
	Description:    	This class tests the HW4 tree files as given in instructions.
	Date:         		5/26/17
	OS:         		Mac OS X
	Compiler:       	terminal (javac)
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TreeSimulator{
	public static Scanner userScanner = new Scanner(System.in);

	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile()
	{
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
	}

	/*
		Tokenize input string into three integers.

		Call its setDate() method passing the 3 ints.
		If setDate returns true, instantiate a NEW Date object (passing the input month, day, and year) ...(continued in main) ... (otherwise, display a message that the input is invalid and display the invalid month, day and year)
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
			return new Date(dateParts[0], dateParts[1], dateParts[2]);
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

	/*
		Call each BST's inorder method (one at a time), passing a YearVisitor for the Year-ordered BST and a MonthVisitor for the Month-ordered BST (please display good headings so we know what is being displayed)
	*/
	private static void displayInorder(BST<Date> year, BST<Date> month){
		YearVisitor yearVisitor = new YearVisitor();
		MonthVisitor monthVisitor = new MonthVisitor();

		System.out.println("\nYear-ordered BST has:");
		year.inorder(yearVisitor);

		System.out.println("\nMonth-ordered BST has:");
		month.inorder(monthVisitor);
	}

	// 	call postorder for the Year-ordered BST passing a YearVisitor
	private static void displayPostorder(BST<Date> year){
		YearVisitor yearVisitor = new YearVisitor();
		System.out.println("\nPostorder Year-ordered BST:");
		year.postorder(yearVisitor);
	}

	/*
		Call writeIndentedTree() passing System.out, for EACH BST (one at a time), with appropriate headings
	*/
	private static void displayIndented(BST<Date> tree){
		tree.writeIndentedTree(System.out);
	}

	private static void displayTests(BST<Date> tree){
		Date first = tree.getFirst();
		Date last = tree.getLast();

		System.out.println("\ngetFirst() returns " + first);
		System.out.println("getLast() returns " + last);
		System.out.println("getEntry() passing a copy of the first returns: " + tree.getEntry(first));

		Date copyLast = new Date(last.getMonth(), last.getDay(), last.getYear()+1);
		System.out.println("Created copy of last Date, with year+1: " + copyLast);

		System.out.println("getEntry(); passing a copy of last with year+1 returns: " + tree.getEntry(copyLast));

		System.out.println("insert() passing the copy of the last with year+1 returns: " + tree.insert(copyLast));
	}

	/*
		call for both trees (ONE AT A TIME) a static method that you write that
		tests the getFirst, getLast, insert, getEntry methods from a BST<Date>
		(parameter) in ONE method so it does the following for the BST parameter:
		- get the first and last Date from the tree (using the BST's getFirst
		and getLast methods), save into local variables, and display them
		- make a copy of the first Date (create a new Date with the same
		month, day and year as the first Date
		- call getEntry passing the copy of the first Date, and display the
		return value (with labels as shown in the test runs)
		- make a copy of the last Date, but CHANGING it (create a new Date
		with the same month, day, but (year+1) as the last Date), then
		display it
		- call getEntry passing the almost copy of the last Date, and display
		the return value (with labels as shown in the test runs)
		- try to insert the almost copy of the last Date and display a
		message showing what the insert method returned
	*/
	private static void testBST(BST<Date> year, BST<Date> month){
		System.out.println("\nStudent Testing Year-ordered tree:");
		displayTests(year);

		System.out.println("\nStudent Testing Month-ordered tree:");
		displayTests(month);
	}

	// CALL THIS METHOD AS INSTRUCTED ON THE ASSIGNMENT
	public static void testMore(BST<Date> tree) 
	{
		Date firstDate, lastDate=null;
		Date testDate1 = new Date(12, 31, 1900);
		Date testDate2 = new Date(1, 2, 2000);
		
		System.out.println("\nClearing tree:");
		firstDate = tree.getFirst();
		while( firstDate != null )
		{
			lastDate = tree.getLast();
			if( tree.delete(firstDate) )
				System.out.println("Removed " + firstDate);
			if( firstDate != lastDate && tree.delete(lastDate) )
				System.out.println("Removed " + lastDate);
			firstDate = tree.getFirst();
		} //
		System.out.println("\nTree is cleared, now writeIndentedTree displays: ");
		tree.writeIndentedTree(System.out);
		System.out.println("End writing tree\nNow adding back last 2 retrieved:");
		tree.insert(testDate1);
		tree.insert(testDate2);
		System.out.println("Now the tree has: ");
		tree.writeIndentedTree(System.out);
	} // end testMore()

	public static void main(String[] args){
		/*
			Write main IN A SEPARATE CLASS AND FILE so it has 2 BST<Date> variables.
			Assign to one of the variables a new BST with a new YearComparator passed as
			an argument. Assign to the 2nd variable a new BST with a new
			MonthComparator as an argument. Then do the following (for which most should
			be a function or points may be deducted if main is too long): (MUST BE IN
			THIS ORDER)
		*/

		BST<Date> yearBST 	= new BST<Date>(new YearComparator());
		BST<Date> monthBST 	= new BST<Date>(new MonthComparator());

		// open an input file using the openInputFile() method given in the HW4_JavaCodeFile (DON'T CHANGE THE openInputFile METHOD)! If it doesn't open, quit the program!
		Scanner file = openInputFile();
		if(file != null){
			while (file.hasNextLine()) {
			    String line = file.nextLine();
				// read from the file, which has 3 ints per line (for month, day and year, IN THAT ORDER). BEFORE the input loop, instantiate a new default Date and assign to a local Date variable. In a loop, read 3 ints, then call the setDate() method for the local Date passing the 3 ints.
			    Date tempDate = convertToDate(line);
			    if(tempDate != null){
			    	//  and insert that same Date object to BOTH BSTs (one at a time)
			    	yearBST.insert(tempDate);
			    	monthBST.insert(tempDate);
			    	
			    }
			}

	    	displayInorder(yearBST, monthBST);

			System.out.println("\nIndented Year-ordered BST:");
	    	displayIndented(yearBST);
			
			System.out.println("\nIndented Month-ordered BST:");
	    	displayIndented(monthBST);

	    	testBST(yearBST, monthBST);
	    	displayPostorder(yearBST);

			System.out.println("\nIndented Month-ordered BST:");
	    	displayIndented(monthBST);

	    	testMore(yearBST);
	    	testMore(monthBST);

			file.close();
		}
	}
}

/*
		OUTPUT
*/
/*
		Enter the input filename: HW4 Input.txt  
		Invalid date, not added: 9-31-1939

		Year-ordered BST has:
		1934-11-28
		1935-9-23
		1937-11-8
		1939-7-18
		1940-4-29
		1947-8-17
		1953-2-10
		1955-10-20
		1962-12-30
		1966-12-17
		1981-3-25
		1981-3-27
		1983-7-28
		1995-10-5
		1999-9-23
		2002-8-18
		2007-12-24
		2010-8-17
		2013-11-30
		2016-8-28
		2024-9-18
		2028-9-28

		Month-ordered BST has:
		2/10/1953
		3/25/1981
		3/27/1981
		4/29/1940
		7/18/1939
		7/28/1983
		8/17/1947
		8/17/2010
		8/18/2002
		8/28/2016
		9/18/2024
		9/23/1935
		9/23/1999
		9/28/2028
		10/5/1995
		10/20/1955
		11/8/1937
		11/28/1934
		11/30/2013
		12/17/1966
		12/24/2007
		12/30/1962

		Indented Year-ordered BST:
		                5. 9/28/2028
		            4. 9/18/2024
		                5. 8/28/2016
		        3. 11/30/2013
		                5. 8/17/2010
		            4. 12/24/2007
		    2. 8/18/2002
		1. 9/23/1999
		            4. 10/5/1995
		                5. 7/28/1983
		        3. 3/27/1981
		                    6. 3/25/1981
		                5. 12/17/1966
		            4. 12/30/1962
		    2. 10/20/1955
		            4. 2/10/1953
		        3. 8/17/1947
		            4. 4/29/1940
		                    6. 7/18/1939
		                        7. 11/8/1937
		                5. 9/23/1935
		                    6. 11/28/1934

		Indented Month-ordered BST:
		            4. 12/30/1962
		        3. 12/24/2007
		            4. 12/17/1966
		    2. 11/30/2013
		                5. 11/28/1934
		            4. 11/8/1937
		        3. 10/20/1955
		                5. 10/5/1995
		            4. 9/28/2028
		1. 9/23/1999
		            4. 9/23/1935
		        3. 9/18/2024
		            4. 8/28/2016
		    2. 8/18/2002
		                5. 8/17/2010
		            4. 8/17/1947
		                        7. 7/28/1983
		                    6. 7/18/1939
		                5. 4/29/1940
		        3. 3/27/1981
		                5. 3/25/1981
		            4. 2/10/1953

		Student Testing Year-ordered tree:

		getFirst() returns 11/28/1934
		getLast() returns 9/28/2028
		getEntry() passing a copy of the first returns: 11/28/1934
		Created copy of last Date, with year+1: 9/28/2029
		getEntry(); passing a copy of last with year+1 returns: null
		insert() passing the copy of the last with year+1 returns: true

		Student Testing Month-ordered tree:

		getFirst() returns 2/10/1953
		getLast() returns 12/30/1962
		getEntry() passing a copy of the first returns: 2/10/1953
		Created copy of last Date, with year+1: 12/30/1963
		getEntry(); passing a copy of last with year+1 returns: null
		insert() passing the copy of the last with year+1 returns: true

		Postorder Year-ordered BST:
		1934-11-28
		1937-11-8
		1939-7-18
		1935-9-23
		1940-4-29
		1953-2-10
		1947-8-17
		1981-3-25
		1966-12-17
		1962-12-30
		1983-7-28
		1995-10-5
		1981-3-27
		1955-10-20
		2010-8-17
		2007-12-24
		2016-8-28
		2029-9-28
		2028-9-28
		2024-9-18
		2013-11-30
		2002-8-18
		1999-9-23

		Indented Month-ordered BST:
		                5. 12/30/1963
		            4. 12/30/1962
		        3. 12/24/2007
		            4. 12/17/1966
		    2. 11/30/2013
		                5. 11/28/1934
		            4. 11/8/1937
		        3. 10/20/1955
		                5. 10/5/1995
		            4. 9/28/2028
		1. 9/23/1999
		            4. 9/23/1935
		        3. 9/18/2024
		            4. 8/28/2016
		    2. 8/18/2002
		                5. 8/17/2010
		            4. 8/17/1947
		                        7. 7/28/1983
		                    6. 7/18/1939
		                5. 4/29/1940
		        3. 3/27/1981
		                5. 3/25/1981
		            4. 2/10/1953

		Clearing tree:
		Removed 11/28/1934
		Removed 9/28/2029
		Removed 9/23/1935
		Removed 9/28/2028
		Removed 11/8/1937
		Removed 9/18/2024
		Removed 7/18/1939
		Removed 8/28/2016
		Removed 4/29/1940
		Removed 11/30/2013
		Removed 8/17/1947
		Removed 8/17/2010
		Removed 2/10/1953
		Removed 12/24/2007
		Removed 10/20/1955
		Removed 8/18/2002
		Removed 12/30/1962
		Removed 9/23/1999
		Removed 12/17/1966
		Removed 10/5/1995
		Removed 3/25/1981
		Removed 7/28/1983
		Removed 3/27/1981

		Tree is cleared, now writeIndentedTree displays: 
		End writing tree
		Now adding back last 2 retrieved:
		Now the tree has: 
		    2. 1/2/2000
		1. 12/31/1900

		Clearing tree:
		Removed 2/10/1953
		Removed 12/30/1963
		Removed 3/25/1981
		Removed 12/30/1962
		Removed 3/27/1981
		Removed 12/24/2007
		Removed 4/29/1940
		Removed 12/17/1966
		Removed 7/18/1939
		Removed 11/30/2013
		Removed 7/28/1983
		Removed 11/28/1934
		Removed 8/17/1947
		Removed 11/8/1937
		Removed 8/17/2010
		Removed 10/20/1955
		Removed 8/18/2002
		Removed 10/5/1995
		Removed 8/28/2016
		Removed 9/28/2028
		Removed 9/18/2024
		Removed 9/23/1999
		Removed 9/23/1935

		Tree is cleared, now writeIndentedTree displays: 
		End writing tree
		Now adding back last 2 retrieved:
		Now the tree has: 
		1. 12/31/1900
		    2. 1/2/2000
*/