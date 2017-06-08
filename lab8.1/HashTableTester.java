/*
	Add code to main in the HashTableTester.java file so it ALSO declares a HashSC<Location> variable and adds at least 3 Location objects to it AND test it similar to how the HashSC<Employee> is tested. MAKE SURE YOU KEEP THE EXISTING CODE IN main (DON'T REMOVE ANY EXISTING CODE)!!!

	Upload ONLY the updated Location.java AND HashTableTester.java file with the output copied and pasted to the end of it (commented out).

*/

	//Example of an Employee class (to be used in main below)
	class Employee
	{
	   public static final int MAX_LEN = 50;

	   private String name;
	   private int ss;

	   public Employee( String name , int ss)
	   {
	      this();
	      setName(name);
	      setSS(ss);
	   }

	   public Employee()
	   {
	      name = "undefined";
	      ss = 0;
	   }

	   String getName() { return name; }
	   int getSS() { return ss; }

	   boolean setName( String name )
	   {
	      if (name == null)
	         return false;
	      if (name.length() > MAX_LEN)
	         return false;
	      this.name = name;
	      return true;
	   }

	   boolean setSS( int ss )
	   {
	      if (ss < 0 || ss > 999999999 )
	         return false;
	      this.ss = ss;
	      return true;
	   }

	   public String toString()
	   {
	      return name + " (" + ss + ")";
	   }

	   public boolean equals( Object rhs ) // UPDATED!
	   {
	      return name.equalsIgnoreCase(((Employee)rhs).name);
	   }

	   public int hashCode()
	   {
	      return ss;
	      // return name.hashCode(); // another possibility
	   }
	};


public class HashTableTester {

	   // -------  main --------------
	   public static void main(String[] args) throws Exception
	   {
	      // first set of tests --------------------
	      HashSC<Employee> hashTable = new HashSC<Employee>();
	      HashSC<Location> locHTable = new HashSC<Location>();

	      Employee
	         e1 = new Employee("Jane Austin", 1),
	         e2 = new Employee("Rene Descartes", 2),
	         e3 = new Employee("John Locke", 3);
	      Location
	      	l1 = new Location("City A", "123 An Avenue", 1.23, 4.56),
	      	l2 = new Location("City B", "456 The Place", 2.34, 5.67),
	      	l3 = new Location("City C", "789 A Street", 3.45, 6.78);

	      if (  hashTable.insert(e1) )
	         System.out.println( "inserted " + e1 );
	      if (  hashTable.insert(e1) )
	         System.out.println( "inserted " + e1 );
	      if (  hashTable.insert(e2) )
	         System.out.println( "inserted " + e2 );
	      if (  hashTable.insert(e2) )
	         System.out.println( "inserted " + e2 );
	      if (  hashTable.insert(e3) )
	         System.out.println( "inserted " + e3 );
	      if (  hashTable.insert(e3) )
	         System.out.println( "inserted " + e3 );

	      System.out.println( hashTable.size() );

	      if (  hashTable.contains(e3) )
	         System.out.println( "contains " + e3 );

	      if (  hashTable.remove(e1) )
	         System.out.println( "removed " + e1 );
	      if (  hashTable.remove(e1) )
	         System.out.println( "removed " + e1 );
	      if (  hashTable.remove(e2) )
	         System.out.println( "removed " + e2 );
	      if (  hashTable.remove(e2) )
	         System.out.println( "removed " + e2 );
	      if (  hashTable.remove(e3) )
	         System.out.println( "removed " + e3 );
	      if (  hashTable.remove(e3) )
	         System.out.println( "removed " + e3 );
	      System.out.println( hashTable.size() );

	      if (  hashTable.contains(e3) )
	         System.out.println( "contains " + e3 );


	     /*
			Exercise 8.1 Tests
	     */
	      if (  locHTable.insert(l1) )
	         System.out.println( "inserted " + l1 );
	      if (  locHTable.insert(l1) )
	         System.out.println( "inserted " + l1 );
	      if (  locHTable.insert(l2) )
	         System.out.println( "inserted " + l2 );
	      if (  locHTable.insert(l2) )
	         System.out.println( "inserted " + l2 );
	      if (  locHTable.insert(l3) )
	         System.out.println( "inserted " + l3 );
	      if (  locHTable.insert(l3) )
	         System.out.println( "inserted " + l3 );

	      System.out.println( locHTable.size() );

	      if (  locHTable.contains(l3) )
	         System.out.println( "contains " + l3 );

	      if (  locHTable.remove(l1) )
	         System.out.println( "removed " + l1 );
	      if (  locHTable.remove(l1) )
	         System.out.println( "removed " + l1 );
	      if (  locHTable.remove(l2) )
	         System.out.println( "removed " + l2 );
	      if (  locHTable.remove(l2) )
	         System.out.println( "removed " + l2 );
	      if (  locHTable.remove(l3) )
	         System.out.println( "removed " + l3 );
	      if (  locHTable.remove(l3) )
	         System.out.println( "removed " + l3 );
	      System.out.println( locHTable.size() );

	      if (  locHTable.contains(l3) )
	         System.out.println( "contains " + l3 );

	      // second set of tests --------------------

	      HashSC<String> hashTable2 = new HashSC<String>();
	      String substrate =
	         "asdlkfj asdoiBIUYVuwer slkdjLJfwoe89)B)(798rjMG0293lkJLJ42lk3j)(*";
	      String[] strArray = new String[500];
	      int k, limit;

	      substrate = substrate + substrate;

	      for (k = 0; k < substrate.length() - 6; k++)
	         strArray[k] = substrate.substring(k, k + 5);
	      limit = k;

	      hashTable2.setMaxLambda(.5);
	      for (k = 0; k < limit; k++)
	         if ( hashTable2.insert(strArray[k]) )
	            System.out.println( "inserted #" + k + " " + strArray[k] );
	      System.out.println( "\n#strings generated: " + limit
	         + "\n#elements in ht: " + hashTable2.size() );
	   } // end main

}

/*
		OUTPUT
*/
/*
	inserted Jane Austin (1)
	inserted Rene Descartes (2)
	inserted John Locke (3)
	3
	contains John Locke (3)
	removed Jane Austin (1)
	removed Rene Descartes (2)
	removed John Locke (3)
	0
	inserted Location: City A, 123 An Avenue, (1.23, 4.56)
	inserted Location: City B, 456 The Place, (2.34, 5.67)
	inserted Location: City C, 789 A Street, (3.45, 6.78)
	3
	contains Location: City C, 789 A Street, (3.45, 6.78)
	removed Location: City A, 123 An Avenue, (1.23, 4.56)
	removed Location: City B, 456 The Place, (2.34, 5.67)
	removed Location: City C, 789 A Street, (3.45, 6.78)
	0
	inserted #0 asdlk
	inserted #1 sdlkf
	inserted #2 dlkfj
	inserted #3 lkfj 
	inserted #4 kfj a
	inserted #5 fj as
	inserted #6 j asd
	inserted #7  asdo
	inserted #8 asdoi
	inserted #9 sdoiB
	inserted #10 doiBI
	inserted #11 oiBIU
	inserted #12 iBIUY
	inserted #13 BIUYV
	inserted #14 IUYVu
	inserted #15 UYVuw
	inserted #16 YVuwe
	inserted #17 Vuwer
	inserted #18 uwer 
	inserted #19 wer s
	inserted #20 er sl
	inserted #21 r slk
	inserted #22  slkd
	inserted #23 slkdj
	inserted #24 lkdjL
	inserted #25 kdjLJ
	inserted #26 djLJf
	inserted #27 jLJfw
	inserted #28 LJfwo
	inserted #29 Jfwoe
	inserted #30 fwoe8
	inserted #31 woe89
	inserted #32 oe89)
	inserted #33 e89)B
	inserted #34 89)B)
	inserted #35 9)B)(
	inserted #36 )B)(7
	inserted #37 B)(79
	inserted #38 )(798
	inserted #39 (798r
	inserted #40 798rj
	inserted #41 98rjM
	inserted #42 8rjMG
	inserted #43 rjMG0
	inserted #44 jMG02
	inserted #45 MG029
	inserted #46 G0293
	inserted #47 0293l
	inserted #48 293lk
	inserted #49 93lkJ
	inserted #50 3lkJL
	inserted #51 lkJLJ
	inserted #52 kJLJ4
	inserted #53 JLJ42
	inserted #54 LJ42l
	inserted #55 J42lk
	inserted #56 42lk3
	inserted #57 2lk3j
	inserted #58 lk3j)
	inserted #59 k3j)(
	inserted #60 3j)(*
	inserted #61 j)(*a
	inserted #62 )(*as
	inserted #63 (*asd
	inserted #64 *asdl

	#strings generated: 124
	#elements in ht: 65
*/