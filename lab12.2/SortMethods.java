/*
	Author: So Choi
	Lab Exercise 12.2
*/

public class SortMethods {
	public static final int MIN_SIZE = 10; // For quick sort

	// -------------------------------------------------------------------------------

	// INSERTION SORT (all these methods are inside a class)
	public static <T extends Comparable<? super T>> void insertionSort(T[] a, int n){
		insertionSort(a, 0, n - 1);
	} // end insertionSort

	public static <T extends Comparable<? super T>> 
	void insertionSort(T[] a, int first, int last){
		for (int unsorted = first + 1; unsorted <= last; unsorted++){
			// Assertion: a[first] <= a[first + 1] <= ... <= a[unsorted - 1]

			T firstUnsorted = a[unsorted];

			insertInOrder(firstUnsorted, a, first, unsorted - 1);
		} // end for
	} // end insertionSort

	private static <T extends Comparable<? super T>> void insertInOrder(T anEntry, T[] a, int begin, int end) {
		int index = end;

		while ((index >= begin) && (anEntry.compareTo(a[index]) < 0)) {
			a[index + 1] = a[index]; // Make room
			index--;
		} // end while

		// Assertion: a[index + 1] is available
		a[index + 1] = anEntry;  // Insert
	} // end insertInOrder

	// -------------------------------------------------------------------------------

	// SHELL SORT
	public static <T extends Comparable<? super T>> void shellSort(T[] a, int n) {
		shellSort(a, 0, n - 1);
	} // end shellSort

	/** Sorts equally spaced elements of an array into
		ascending order.
		@param a      An array of Comparable objects.
		@param first  An integer >= 0 that is the index of the first
		  array element to consider.
		@param last   An integer >= first and < a.length that is the
		  index of the last array element to consider.
		@param space  The difference between the indices of the
		elements to sort.
	*/
	public static <T extends Comparable<? super T>> void shellSort(T[] a, int first, int last) {
		int n = last - first + 1; // Number of array entries
		for (int space = n / 2; space > 0; space = space / 2) {
			for (int begin = first; begin < first + space; begin++) {
				incrementalInsertionSort(a, begin, last, space);
			} // end for
		} // end outer for
	} // end shellSort


	//  Sorts equally spaced elements of an array into ascending order.
	//  Parameters:
	//     a      An array of Comparable objects.
	//     first  The integer index of the first array entry to
	//            consider; first >= 0 and < a.length.
	//     last   The integer index of the last array entry to
	//            consider; last >= first and < a.length.
	//     space  The difference between the indices of the
	//            entries to sort.
	private static <T extends Comparable<? super T>> void incrementalInsertionSort(T[] a, int first, int last, int space) {
		int unsorted, index;

		for (unsorted = first + space; unsorted <= last; unsorted = unsorted + space) {
			T nextToInsert = a[unsorted];
			index = unsorted - space;
			while ((index >= first) && (nextToInsert.compareTo(a[index]) < 0)) {
				a[index + space] = a[index]; 
				index = index - space;
			} // end while

			a[index + space] = nextToInsert; 
		} // end for
	} // end incrementalInsertionSort

	// -------------------------------------------------------------------------------
	/*
		1. First gap size for shell sort: 10
		2. First two elements compared: 19 and 31
		3. Second gap size: 5
		4. Pivot element value for quicksort: 
	*/
	// QUICK SORT
	/** Sorts an array into ascending order. Uses quick sort with
		median-of-three pivot selection for arrays of at least
		MIN_SIZE entries, and uses insertion sort for other arrays. */
	public static <T extends Comparable<? super T>> void quickSort(T[] array, int n) {
		quickSort(array, 0, n - 1);
	} // end quickSort

	public static <T extends Comparable<? super T>> void quickSort(T[] a, int first, int last) {
		if (last - first + 1 < MIN_SIZE) {
			insertionSort(a, first, last);
		} else {
			// Create the partition: Smaller | Pivot | Larger
			int pivotIndex = partition(a, first, last);

			// Sort subarrays Smaller and Larger
			quickSort(a, first, pivotIndex - 1);
			quickSort(a, pivotIndex + 1, last);
		} // end if
	} // end quickSort

	//  Partitions an array as part of quick sort into two subarrays
	//  called Smaller and Larger that are separated by a single
	//  entry called the pivot.
	//  Entries in Smaller are <= pivot and appear before the
	//  pivot in the array.
	//  Entries in Larger are >= pivot and appear after the
	//  pivot in the array.
	//  Parameters:
	//     a      An array of Comparable objects.
	//     first  The integer index of the first array entry;
	//            first >= 0 and < a.length.
	//     last   The integer index of the last array entry;
	//            last - first >= 3; last < a.length.
	//   Returns the index of the pivot.
	private static <T extends Comparable<? super T>> int partition(T[] a, int first, int last) {
		int mid = first + (last - first) / 2;
		// System.out.println("Mid: " + mid);

		// System.out.println("Sort first middle last: " + a[first] + ", " + a[mid] + ", " + a[last]);
		sortFirstMiddleLast(a, first, mid, last);

		// Assertion: The pivot is a[mid]; a[first] <= pivot and 
		// a[last] >= pivot, so do not compare these two array entries
		// with pivot.

		// Move pivot to next-to-last position in array
		// System.out.println("Swap: " + a[mid] + ", " + a[last]);
		swap(a, mid, last - 1);


		int pivotIndex = last - 1;
		// System.out.println("pivotIndex: " + pivotIndex);
		T pivotValue = a[pivotIndex];
		// System.out.println("pivotValue: " + pivotValue);

		// Determine subarrays Smaller = a[first..endSmaller]
		// and                 Larger  = a[endSmaller+1..last-1]
		// such that entries in Smaller are <= pivotValue and
		// entries in Larger are >= pivotValue; initially, these subarrays are empty

		int indexFromLeft = first + 1; 
		int indexFromRight = last - 2;

		boolean done = false;
		while (!done) {
			// Starting at beginning of array, leave entries that are < pivotValue;
			// locate first entry that is >= pivotValue; you will find one,
			// since last entry is >= pivot
			while (a[indexFromLeft].compareTo(pivotValue) < 0) {
				indexFromLeft++;
			}

			// Starting at end of array, leave entries that are > pivot;
			// locate first entry that is <= pivot; you will find one, 
			// since first entry is <= pivot

			while (a[indexFromRight].compareTo(pivotValue) > 0) {
				indexFromRight--;
			}

			assert a[indexFromLeft].compareTo(pivotValue) >= 0 && a[indexFromRight].compareTo(pivotValue) <= 0;

			if (indexFromLeft < indexFromRight) {
				swap(a, indexFromLeft, indexFromRight);
				indexFromLeft++;
				indexFromRight--;
			} else {
				done = true;
			}
		} // end while

		// Place pivotValue between the subarrays Smaller and Larger
		swap(a, pivotIndex, indexFromLeft);
		pivotIndex = indexFromLeft;
		// System.out.println("After while loop, pivotIndex: " + pivotIndex + ", pivotValue: " + a[pivotIndex]);

		// Assertion:
		//   Smaller = a[first..pivotIndex-1]
		//   Pivot = a[pivotIndex]
		//   Larger = a[pivotIndex+1..last]

		return pivotIndex; 
	} // end partition

	//  Sorts the first, middle, and last entries of an array into ascending order.
	//  Parameters:
	//     a      An array of Comparable objects.
	//     first  The integer index of the first array entry;
	//            first >= 0 and < a.length.
	//     mid    The integer index of the middle array entry.
	//     last   The integer index of the last array entry;
	//            last - first >= 2, last < a.length.
	private static <T extends Comparable<? super T>> void sortFirstMiddleLast(T[] a, int first, int mid, int last) {
		order(a, first, mid); // Make a[first] <= a[mid]
		order(a, mid, last);  // Make a[mid] <= a[last]
		order(a, first, mid); // Make a[first] <= a[mid]
	} // end sortFirstMiddleLast

	// Orders two given array elements into ascending order
	// so that a[i] <= a[j].
	private static <T extends Comparable<? super T>> void order(T[] a, int i, int j) {
		if (a[i].compareTo(a[j]) > 0) {
			swap(a, i, j);
		}
	} // end order

	// Swaps the array entries array[i] and array[j]. 
	private static void swap(Object[] array, int i, int j) {
		Object temp = array[i];
		array[i] = array[j];
		array[j] = temp; 
	} // end swap

	public static void main(String[] args){
		final int MIN_ARRAY_SIZE = 10000;
		final int MAX_ARRAY_SIZE = 20000;
		final int MAX_INT = 50000;

		Integer[] randIntsA = new Integer[MIN_ARRAY_SIZE];
		// System.out.println("Array contents: ");
		for (int i = 0; i < MIN_ARRAY_SIZE; i++) {
			// generate random numbers large enough to be sortable (so we don't get too many equal numbers)
			int n = (int)(Math.random() * MAX_INT + 1);
			randIntsA[i] = n;

			// System.out.print(randIntsA[i] + " ");
		}
		Integer[] randIntsB = randIntsA.clone();

		long startTime, stopTime; // for timing
		double elapsedTime = 0; // for timing

		// call sort method here
		SortMethods sorter = new SortMethods();

		// System.out.println("\nArray contents after quick sort: " + a);
		// for(int i=0; i < a.length; i++){
		// 	System.out.print(a[i] + " ");
		// }

		startTime = System.nanoTime();
		sorter.shellSort(randIntsA, MIN_ARRAY_SIZE);
		stopTime = System.nanoTime();
		elapsedTime = (double) (stopTime - startTime) / 1000000.0;
		System.out.println("N: " + MIN_ARRAY_SIZE + " _____ Shell Sort Time: " + elapsedTime + " milliseconds.");

		startTime = System.nanoTime();
		sorter.quickSort(randIntsB, MIN_ARRAY_SIZE);
		stopTime = System.nanoTime();
		elapsedTime = (double) (stopTime - startTime) / 1000000.0;
		System.out.println("N: " + MIN_ARRAY_SIZE + " _____ Quick Sort Time: " + elapsedTime + " milliseconds.");

		/*
			double array size, reuse old variables
		*/
		// reuse old variables
		randIntsA = null;
		randIntsA = new Integer[MAX_ARRAY_SIZE];
		for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
			int n = (int)(Math.random() * MAX_INT + 1);
			randIntsA[i] = n;
		}
		randIntsB = null;
		randIntsB = randIntsA.clone();

		startTime = System.nanoTime();
		sorter.shellSort(randIntsA, MAX_ARRAY_SIZE);
		stopTime = System.nanoTime();
		elapsedTime = (double) (stopTime - startTime) / 1000000.0;
		System.out.println("\nN: " + MAX_ARRAY_SIZE + " _____ Shell Sort Time: " + elapsedTime + " milliseconds.");

		startTime = System.nanoTime();
		sorter.quickSort(randIntsB, MAX_ARRAY_SIZE);
		stopTime = System.nanoTime();
		elapsedTime = (double) (stopTime - startTime) / 1000000.0;
		System.out.println("N: " + MAX_ARRAY_SIZE + " _____ Quick Sort Time: " + elapsedTime + " milliseconds.");
	}
} // end SortMethods class

/*
	OUTPUT (ran multiple times for various results)
*/
/*
	~/github/.../lab12.2 (master)$ java SortMethods
	N: 10000 _____ Shell Sort Time: 14.542398 milliseconds.
	N: 10000 _____ Quick Sort Time: 5.56362 milliseconds.

	N: 20000 _____ Shell Sort Time: 14.859673 milliseconds.
	N: 20000 _____ Quick Sort Time: 11.213639 milliseconds.

	~/github/.../lab12.2 (master)$ java SortMethods
	N: 10000 _____ Shell Sort Time: 8.472983 milliseconds.
	N: 10000 _____ Quick Sort Time: 6.09575 milliseconds.

	N: 20000 _____ Shell Sort Time: 35.601397 milliseconds.
	N: 20000 _____ Quick Sort Time: 7.492032 milliseconds.

	~/github/.../lab12.2 (master)$ java SortMethods
	N: 10000 _____ Shell Sort Time: 11.392647 milliseconds.
	N: 10000 _____ Quick Sort Time: 5.270145 milliseconds.

	N: 20000 _____ Shell Sort Time: 26.990252 milliseconds.
	N: 20000 _____ Quick Sort Time: 8.543375 milliseconds.
*/

/*
	QUESTIONS

	Run again with the size 2 times what you had previously.
	How much longer did it take?
		Although the output time varied every time the program ran, on average the Shell sort time seems to have at least doubled when the size was doubled. This should change based on gap size as well. Quick sort time also seems to have the potential to at least double, or nearly double.
	Which sort method was faster?
		Quick sort was much faster.
	Why do you think one was faster than the other?
		The gap sequence may not have been the best for this shell sort. Quick sort is usually faster than most well-known algorithms and is also usually fast: O(n log n)
*/
