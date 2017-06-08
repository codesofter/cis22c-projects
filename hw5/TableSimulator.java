/*
	@author So Choi

	Name of Program:  	TableSimulator
	Description:    	This class tests the HW5 hash table files as given in instructions.
	Date:         		5/26/17
	OS:         		Mac OS X
	Compiler:       	terminal (javac)
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TableSimulator{
	public static Scanner userScanner = new Scanner(System.in);

	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile(){
		String filename;
    	Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try {
    		scanner = new Scanner(file);
    	} catch(FileNotFoundException fe) {
			System.out.println("Can't open input file\n");
			return null; // array of 0 elements
    	} // end catch
    	return scanner;
	}

	public static void testHashTable(HashTable<IpAddress> table, IpAddress targetIpAddress) {
		IpAddress tempIpAddress;
		IpAddress copyIpAddress;
		// found a IpAddress in table1 in table2
		tempIpAddress = table.getEntry(targetIpAddress);
		if ( tempIpAddress != null ) {
			copyIpAddress = new IpAddress(tempIpAddress.getDottedDecimal());
			System.out.println("\nRetrieved in HashTable, IpAddress: " + tempIpAddress + ", now trying to delete it");
			// now delete it 
			testRemove( table, copyIpAddress ); // YOU WRITE THIS (SEE SPECS BELOW)
		} else {
			System.out.println("\nError in HashTable: can't retrieve "+ targetIpAddress);
		}
		
		// now test not finding it:
		copyIpAddress = new IpAddress(); // assign a default IpAddress
		System.out.println("contains for Default IpAddress returns " + table.contains(copyIpAddress));
		tempIpAddress = table.getEntry(targetIpAddress);
		if ( tempIpAddress != null ) {
			System.out.println("Retrieved in HashTable, Default IpAddress (BAD!) ");
		} else {
			System.out.println("Didn't retrieve Default IpAddress (GOOD!)");
		}
		testRemove( table, copyIpAddress ); // call again, with default
	} // testHashTable


	public static void testRemove(HashTable<IpAddress> table, IpAddress targetIpAddress){
		// YOU FINISH SO THIS METHOD DOES THE FOLLOWING
		//     Display the result of calling the parameter HashTable's insert method
		//     		passing the targetIpAdddress, with a text as shown in the test runs
		//			(be sure to display the IpAddress parameter, too) 
		//     Call the parameter HashTable's remove method, passing the targetIpAdddress
		//     If it returns true, display a message that it was removed 
		//			(and display targetIpAddress)
		//     If it returns false, display a message that the remove failed 
		//			(and display targetIpAddress
		// Make sure you don't call the remove() method more than once in this method!
	}


	/*
		Open an input file the same way you did in Prog. HW#1 (using the openInputFile method, also given in the Code file UNCHANGED). If the file doesn't open, return null. If it opens:
	*/
	private static IpAddress fillTables(HashTable<IpAddress> qp, HashTable<IpAddress> sc, Scanner file){
		IpAddress lastIp = null;

		if(file != null){
			while (file.hasNextLine()) {
			    String line = file.nextLine();

			    line = line.trim();
			    // create a new IpAddress with data from the line of input
			    IpAddress ip = new IpAddress(line);

			    // insert the same IpAddress instance to each hash table
			    // (Optional: display if it successfully inserted or not.)
			    System.out.println("Inserted into HashSC: " + line);
			    sc.insert(ip);

			    System.out.println("Inserted into HashSC: " + line);
			    qp.insert(ip);
			}

			file.close();
		}
		
		// RETURN THE LAST IpAddress object inserted into the HashTables, or null if the file didn't open or none were read
		return lastIp;
	}

	/*
		call the displayTable method AND call the displayStatistics for your HashSC and then your HashQP table, (make sure you display a description of which table is being displayed first)
	*/
	private static void displayAll(HashTable<IpAddress> qp, HashTable<IpAddress> sc){
		System.out.println("\nContents of HashSC with the IpValue key:");
		sc.displayTable();
		System.out.println("\nIn the HashSC object:");
		sc.displayStatistics();

		System.out.println("\nContents of HashQP with the IpValue key:");
		qp.displayTable();
		System.out.println("\nIn the HashQP object:");
		qp.displayStatistics();
	}

	/*
		check if the IpAddress returned from the readFile method is contained in the HashSC table (calling contains() method), and if it is, call the testHashTable method (in the Code file, MUST ALSO CALL YOUR OWN test method) for your HashSC table, ALSO passing the IpAddress RETURNED from the readFile method (make sure you display a description of which table is being tested first) (IF contains() RETURNS FALSE, DISPLAY A MESSAGE)
	*/
	private static void testTableMethods(HashTable<IpAddress> table, IpAddress ip){
		if (table.contains(ip)) {
			testHashTable(table, ip);
		} else {
			System.out.println("Didn't retrieve " + ip);
		}
	}

	public static void main(String[] args){
		/*
			Write main so it declares 2 HashTable<IpAddress> variables (they MUST be inside main)
		*/

		HashTable<IpAddress> qpTable = new HashQP<>(new IpAddressStringHasher(), new IpAddressStringComparator());
		HashTable<IpAddress> scTable = new HashSC<>(new IpAddressValueHasher(), new IpAddressValueComparator());

	    /*
			read the file which has several sets of IpAddress, which has a String, one per line (read to the end of line, then trim()), create a new IpAddress with data from the line of input, insert the same IpAddress instance to each hash table.

			Read until the end of file (use the Scanner's hasNext() method). (Optional: display if it successfully inserted or not.) Close the file at the end of the method, then call a method (you write) to fill the a HashSC and HashQP (both are parameters in ONE method because they will refer to the SAME data) (BUT THERE WILL BE AN IpAddress return value).
	    */
		Scanner file = openInputFile();
		IpAddress lastIp = null;
		lastIp = fillTables(qpTable, scTable, file);
		
		// if the file-reading method wasn't successful (returns null), display "Unable to read the file" and end the program.
		if (lastIp == null) {
			System.out.println("Unable to read the file");
			return;
		}

		displayAll(qpTable, scTable);

		System.out.println("\nTesting HashSC with IpValue key:");
		testTableMethods(scTable, lastIp);

		System.out.println("\nTesting HashQP with IpValue key:");
		testTableMethods(qpTable, lastIp);

		/*
			AGAIN, call the displayTable method for your HashSC and then HashQP table
		*/
		displayAll(qpTable, scTable);
	}
}

/*
		OUTPUT
*/
/*

*/