/*
    Change the main program given on page 3 of Lesson 1 to add the 3 Music objects
    to an ArrayList of Music objects, then add those objects FROM THE ARRAYLIST (not array) to the Linked list (YOU MUST USE AN ITERATOR, get an Iterator from the ArrayList, call the hasNext() and next(0 methods).

    Because the Music class hasn't been written yet, you don't have to run this (just re-write main). Upload load the main method ONLY (or the .java file with main in it). 
*/

public static <T> void displayArray(T []myArray)
{
    for( int i =0; i < myArray.length; ++i )
    {
        System.out.println(myArray[i]);
    }
}

public static void main( String [] args )
{
    Music [] musicArray = {
        new Music("Moonlight Sonata",  "Beethoven", 27),
        new Music("Brandenburg Concerto #3", "Bach", 1048),
        new Music("Prelude in e minor", "Chopin", 28) };

    ArrayList<Music> musicList = new ArrayList<Music>();
    
    // Use iterator to add to ArrayList
    Iterator iterator = musicArray.iterator();
    while(iterator.hasNext()){
        musicList.add(iterator.next());
    }

    displayArray(musicArray);// template is Music here
}
// FINISH FOR EXERCISE 1.3