/*
   @author So Choi

   Name of Program:  HashQP
   Description:      This class completes the HashQP file as given in instructions.
   Date:             6/8/17
   OS:               Mac OS X
   Compiler:         terminal (javac)
*/

import java.util.Comparator;

// HashQP class ------------------------
public class HashQP<E> extends HashTable<E>{
   protected static final int ACTIVE = 0;
   protected static final int EMPTY = 1;
   protected static final int DELETED = 2;
   
   static final int INIT_TABLE_SIZE = 97;
   static final double INIT_MAX_LAMBDA = 0.49;
   
   protected HashEntry<E>[] mArray;
   protected int mSize;
   protected int mLoadSize;
   protected int mTableSize;
   protected double mMaxLambda;
   
   // public methods ---------------------------------
   /*
      Add 2 interface parameters to the constructor, one for a Hasher<E> and another for a Comparator<E>, and pass them to their corresponding parameters in the superclass' constructor
   */
   public HashQP(int tableSize, Hasher<E> hash, Comparator<E> comp){
      // PASS corresponding parameters to Comparator<E> and Hasher<E> parameters of the superclass constructor!!!!!
      super(hash, comp);
      mLoadSize = mSize = 0;
      if (tableSize < INIT_TABLE_SIZE)
         mTableSize = INIT_TABLE_SIZE;
      else
         mTableSize = nextPrime(tableSize);

      allocateArray();  // uses mTableSize;
      mMaxLambda = INIT_MAX_LAMBDA;
   }

   public HashQP(Hasher<E> hash, Comparator<E> comp){
      this(INIT_TABLE_SIZE, hash, comp);
   }
   
   public boolean insert( E x)
   {
      int bucket = findPos(x);

      if ( mArray[bucket].state == ACTIVE )
         return false;

      mArray[bucket].data = x;
      mArray[bucket].state = ACTIVE;
      mSize++;
      
      // check load factor
      if( ++mLoadSize > mMaxLambda * mTableSize )
         rehash();

      return true;
   }
   
   public boolean remove( E x )
   {
      int bucket = findPos(x);

      if ( mArray[bucket].state != ACTIVE )
         return false;

      mArray[bucket].state = DELETED;
      mSize--; // mLoadSize not dec'd because it counts any non-EMP location
      return true;
   }
   
   public boolean contains(E x ) 
   {
      return mArray[findPos(x)].state == ACTIVE;
   }
   
   public int size()  { return mSize; }
   
   public void makeEmpty(){
      int k, size = mArray.length;

      for (k = 0; k < size; k++) {
         mArray[k].state = EMPTY;
      }
      mSize = mLoadSize = 0;
   }
   
   public boolean setMaxLambda( double lam ){
      if (lam < .1 || lam > INIT_MAX_LAMBDA) {
         return false;
      }
      mMaxLambda = lam;
      return true;
   }
   
   public void displayStatistics(){
	   System.out.println("\n\nIn the HashQP object:\n");
	   System.out.println( "Table Size = " +  mTableSize );;
	   System.out.println( "Number of entries = " + mSize);
	   System.out.println( "Load factor = " + (double)mSize/mTableSize);
	   System.out.println( "Number of collisions = " + this.numCollisions );
	   System.out.println( "Longest Collision Run = " + this.longestCollisionRun );
   }

   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   // DON'T FORGET TO OVERRIDE displayTable() (YOU WRITE FOR HW#5)
   // FOR EACH ARRAY ELEMENT...
   //    Display the index of the array element, THEN 
   //      if the element isn't ACTIVE, display ".....",
   //      if the element IS ACTIVE, display the data of the entry (toString)
   //      (see the test runs for examples) 
   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   public void displayTable(){
      int k, size = mArray.length;

      for (k = 0; k < size; k++) {
         /*
               Add indent before index to display exactly like test output
         */
         String indent = " ";
         if (k < 100) {
            indent += " ";
         }
         if (k < 10) {
            indent += " ";
         }

         System.out.print("\n" + indent + k + ": ");

         if (mArray[k].state != ACTIVE) {
            System.out.print(".....");
         } else {
            System.out.print(mArray[k].data);
         }
      }
   }

   /*
      Override the getEntry() method, like remove(), but assigns to returnedItem if found (need to write WHOLE method!)
   */
   public E getEntry(E target){
      // FINISH THIS (should be like remove, but return the data for the Entry if found, null if not found
      int bucket = findPos(target);

      if ( mArray[bucket].state != ACTIVE ) {
         return null;
      } else {
         return mArray[bucket].data;
      }
   }

   // protected methods of class ----------------------
   /*
         change in the HashQP class, the findPos so any comparision with Object will use the compare method
   */
   protected int findPos( E x ){
      int kthOddNum = 1;
      int index = myHash(x);
      // SHOULD DECLARE A LOCAL COUNTER HERE
      int counter = 0;

      while ( mArray[index].state != EMPTY
         && comparator.compare(mArray[index].data, x) != 0) {
         index += kthOddNum; // k squared = (k-1) squared + kth odd #
         kthOddNum += 2;     // compute next odd #
         if (index >= mTableSize) {
            index -= mTableSize;
         }
         ++numCollisions;
         // ADD HERE: update local counter variable for HW#5!!!
         ++counter;
      }

      // System.out.println(numCollisions + " vs " + counter + " vs " + longestCollisionRun);
      // ADD HERE: maybe update longestCollisionRun variable for HW#5!!!!!!!
      if (counter > longestCollisionRun) {
        longestCollisionRun = counter;
      }
      return index;
   }
   
   protected void rehash(){
	   numCollisions = 0;
   	// ADD CODE HERE TO RESET THE HashTable longestCollisionRun TO 0 for HW#5!!!!
      longestCollisionRun = 0;

      // we save old list and size then we can reallocate freely
      HashEntry<E>[] oldArray = mArray;
      int k, oldTableSize = mTableSize;;

      mTableSize = nextPrime(2*oldTableSize);
      
      // allocate a larger, empty array
      allocateArray();  // uses mTableSize;

      // use the insert() algorithm to re-enter old data
      mSize = mLoadSize = 0;
      for (k = 0; k < oldTableSize; k++) {
         if (oldArray[k].state == ACTIVE) {
            insert( oldArray[k].data );
         }
      }
   }
   
   protected int getNumCollisions(){ return numCollisions; }
   
   protected int myHash(E x)
   {
      int hashVal;

      // CHANGE TO USE Hasher's hash method INSTEAD of x.hashCode for HW#5!!!!!!!!!!!
      hashVal = hasher.hash(x) % mTableSize; 
      if(hashVal < 0)
         hashVal += mTableSize;

      return hashVal;
   }
   
   protected static int nextPrime(int n)
   {
      int k, candidate, loopLim;

      // loop doesn't work for 2 or 3
      if (n <= 2) {
         return 2;
      } else if (n == 3) {
         return 3;
      }

      for (candidate = (n%2 == 0)? n+1 : n ; true ; candidate += 2) {
         // all primes > 3 are of the form 6k +/- 1
         loopLim = (int)( (Math.sqrt((double)candidate) + 1)/6 );

         // we know it is odd.  check for divisibility by 3
         if (candidate%3 == 0)
            continue;

         // now we can check for divisibility of 6k +/- 1 up to sqrt
         for (k = 1; k <= loopLim; k++) {
            if (candidate % (6*k - 1) == 0) {
               break;
            }
            if (candidate % (6*k + 1) == 0) {
               break;
            }
         }
         if (k > loopLim) {
            return candidate;
         }
      }
   }
   
   void allocateArray(){
      int k;
      
      mArray = new HashEntry[mTableSize];
      for (k = 0; k < mTableSize; k++) {
         mArray[k] = new HashEntry<E>();
      }
   }

   
   // INNER CLASS: HashEntry, used ONLY internally in HashQP ----------
   class HashEntry<E> {
      public E data;
      public int state;
      
      public HashEntry( E x, int st ){
         data = x;
         state = st;
      }

      public HashEntry(){
         this(null, HashQP.EMPTY);
      }
   }
}
