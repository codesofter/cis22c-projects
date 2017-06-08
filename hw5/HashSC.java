import java.util.Comparator;
import java.util.Iterator;

// CHANGE TO MAKE THIS A SUBCLASS OF HashTable for HW#5!!!!!!!!!!
public class HashSC<E> extends HashTable<E>{
   static final int INIT_TABLE_SIZE = 97;
   static final double INIT_MAX_LAMBDA = 1.5;
   
   protected LList<E>[] mLists;
   protected int mSize;
   protected int mTableSize;
   protected double mMaxLambda;

   public HashSC(int tableSize, Hasher<E> hash, Comparator<E> comp){
      // Pass Comparator<E> and Hasher<E> parameters to the SUPERCLASS constructor for HW#5!!!!!!!!!!
      super(hash, comp);
      if (tableSize < INIT_TABLE_SIZE)
         mTableSize = INIT_TABLE_SIZE;
      else
         mTableSize = nextPrime(tableSize);

      allocateMListArray();  // uses mTableSize;
      mMaxLambda = INIT_MAX_LAMBDA;
   }
   
   public HashSC(Hasher<E> hash, Comparator<E> comp){
      this(INIT_TABLE_SIZE, hash, comp);
   }
   
   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   // DON'T FORGET TO OVERRIDE displayTable() (YOU WRITE FOR HW#5)
   // FOR EACH ARRAY ELEMENT...
   //    Display the index of the array element, THEN 
   //      if the linked list at that element is empty, display "....."
   //       otherwise, display the data in each linked list node
   //       all on ONE line, separated with " -> ", BUT
   //     	YOU MUST USE THE ITERATOR RETURNED FROM EACH LINKED LIST
   //     	to retrieve each Node's data (YOU ARE NOT ALLOWED TO CALL getEntry)
   //     	(see the test runs for examples) 
   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   public void displayTable(){
      int k, size = mLists.length;

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

         /*
               Print items in the linked list for this hash table item
         */
         LList<E> theList = mLists[k];
         Iterator<E> iter = theList.iterator();
         E currElem = null;

         for (int i=0; iter.hasNext(); ++i ) {
            String postStr = "";

            currElem = iter.next();
            if (iter.hasNext()) {
               postStr = " -> ";
            }

            System.out.print(currElem + postStr);
         }

         if (currElem == null) {
            System.out.print(".....");
         }
      }
   }
   
   /*
      CHANGE in the HashSC class the insert, getEntry, and contains so any comparisions each method MUST traverse a linked list using its iterator (see remove and rehash for examples) AND will use the compare method from the Comparator (this is similar to the BinarySearchTree's use of the compare method) (DON'T call a linked list's contains() !)
   */
   public E getEntry(E target){
		// FINISH THIS (should be like remove, but return 
		//   what the iterator returned if the comparator's compare 
		//   method returns 0
      LList<E> theList = mLists[myHash(target)];
      Iterator<E> iter = theList.iterator();
      E currElem;

      for (int i=0; iter.hasNext(); ++i ) {
         currElem = iter.next();
         if (comparator.compare(currElem, target) == 0) {
            return currElem;
         }
      }

      // not found
      return target;
   }

   public boolean contains(E x){ 
      LList<E> theList = mLists[myHash(x)];
      Iterator<E> iter = theList.iterator();
      E currElem;

      for (int i=0; iter.hasNext(); ++i ) {
         currElem = iter.next();
         if (comparator.compare(currElem, x) == 0) {
            return true;
         }
      }

      // not found
      return false;

		// may call this' "fixed" getEntry as it traverses as required 
   }
   
   public void makeEmpty(){
      int k, size = mLists.length;

      for (k = 0; k < size; k++) {
         mLists[k].clear();
      }
      mSize = 0;  
   }
   
   public boolean insert(E x){
      /*
         add Code to HashSC's insert and HashQP's findPos so they will increment the collisionCount (declared in the superclass) ONLY when a collision occurs (see answers to Lab Ex.8.2)
      */
      LList<E> theList = mLists[myHash(x)];
      Iterator<E> iter = theList.iterator();
      E currElem;
      int counter = 0;

      for (int i=0; iter.hasNext(); ++i ) {
         currElem = iter.next();
         if (comparator.compare(currElem, x) == 0) {
            // ADD HERE: check and maybe UPDATE member counter variable
            counter++;
            numCollisions++;
            return false;
         }
      }

      // not found so we insert
      theList.add(x);

      // ADD HERE: possibly update longestCollisionRun variable which should be counting the longest linked list
      if (counter > longestCollisionRun) {
        longestCollisionRun = counter;
      }

      // check load factor
      if (++mSize > mMaxLambda * mTableSize) {
         rehash();
      }

      return true;
   }
   
   public boolean remove(E x){
	   LList<E> theList = mLists[myHash(x)];
	   Iterator<E> iter = theList.iterator();
	   E currElem;
	   
	   for (int i=0; iter.hasNext(); ++i ) {
		   currElem = iter.next();
		   if (comparator.compare(currElem, x)==0) {
			   theList.remove(i+1);
			   --mSize;
			   return true;
		   }
	   }

      // not found
      return false;   
   }

   public int size()  { return mSize; }
   
   public boolean setMaxLambda( double lam )
   {
      if (lam < .1 || lam > 100.)
         return false;
      mMaxLambda = lam;
      return true;
   }

   public void displayStatistics(){
	   System.out.println("\nIn the HashSC object:\n");
	   System.out.println( "Table Size = " +  mTableSize );;
	   System.out.println( "Number of entries = " + mSize);
	   System.out.println( "Load factor = " + (double)mSize/mTableSize);
	   System.out.println( "Number of collisions = " + this.numCollisions );
	   System.out.println( "Longest Linked List = " + this.longestCollisionRun );
   }
   
   // protected methods of class ----------------------
   protected void rehash(){
		// ADD CODE HERE TO RESET THE HashTable COUNTERS TO 0 for HW#5!!!!!!!!!!!!!!!!
	   numCollisions = 0;
      longestCollisionRun = 0;
	   
      // we save old list and size then we can reallocate freely
      LList<E>[] oldLists = mLists;
      int k, oldTableSize = mTableSize;
      Iterator<E> iter;

      mTableSize = nextPrime(2*oldTableSize);
      
      // allocate a larger, empty array
      allocateMListArray();  // uses mTableSize;

      // use the insert() algorithm to re-enter old data
      mSize = 0;
      for (k = 0; k < oldTableSize; k++) {
         for (iter = oldLists[k].iterator(); iter.hasNext() ; ) {
            insert(iter.next());
         }
      }
   }
   
   protected int myHash( E x){
      int hashVal;

      // CHANGE TO USE Hasher's hash method INSTEAD of x.hashCode for HW#5!!!!!!!!!!!
      hashVal = hasher.hashCode() % mTableSize; 
      if (hashVal < 0) {
         hashVal += mTableSize;
      }

      return hashVal;
   }
   
   protected static int nextPrime(int n){
      int k, candidate, loopLim;

      // loop doesn't work for 2 or 3
      if (n <= 2 ) {
         return 2;
      } else if (n == 3) {
         return 3;
      }

      for (candidate = (n%2 == 0)? n+1 : n ; true ; candidate += 2) {
         // all primes > 3 are of the form 6k +/- 1
         loopLim = (int)( (Math.sqrt((double)candidate) + 1)/6 );

         // we know it is odd.  check for divisibility by 3
         if (candidate%3 == 0) {
            continue;
         }

         // now we can check for divisibility of 6k +/- 1 up to sqrt
         for (k = 1; k <= loopLim; k++){
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
   
   private  void allocateMListArray(){
      int k;
      
      mLists = new LList[mTableSize];
      for (k = 0; k < mTableSize; k++) {
         mLists[k] = new LList<E>();
      }
   }
}
