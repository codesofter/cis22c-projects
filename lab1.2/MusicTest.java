/*
    Change the main program given on page 3 of Lesson 1 to add the 3 Music objects
    to an ArrayList of Music objects, then add those objects FROM THE ARRAYLIST (not array) to the Linked list (YOU MUST USE AN ITERATOR, get an Iterator from the ArrayList, call the hasNext() and next(0 methods).

    Because the Music class hasn't been written yet, you don't have to run this (just re-write main). Upload load the main method ONLY (or the .java file with main in it). 
*/
import java.util.ArrayList;
import java.util.Iterator;

public class MusicTest{
    public static <T> void displayArray(T []myArray)
    {
        for( int i = 0; i < myArray.length; i++ )
        {
            System.out.println(myArray[i]);
        }
    }

    public static void main( String [] args )
    {
        Music [] testBank = {
                new Music("Test Music 1", "Composer", 1),
                new Music("Test Music 2", "Composer", 2),
                new Music("Test Music 3", "Composer", 3)
            };
        ArrayList<Music> musicArrayList = new ArrayList<Music>();
        musicArrayList.add(new Music("Moonlight Sonata",  "Beethoven", 27));
        musicArrayList.add(new Music("Brandenburg Concerto #3", "Bach", 1048));
        musicArrayList.add(new Music("Prelude in e minor", "Chopin", 28));
        musicArrayList.add(testBank[0]);
        musicArrayList.add(testBank[1]);
        musicArrayList.add(testBank[2]);

        LList<Music> musicLList = new LList<Music>();
        
        // Use iterator to add to ArrayList
        Iterator<Music> iterator = musicArrayList.iterator();
        while(iterator.hasNext()){
            musicLList.add(iterator.next());
        }

        musicLList.display();
        System.out.println("\n\nRemoving node:\n");

        // Check remove function
        // Music temp = new Music("abc", "def", 4);
        musicLList.remove(musicArrayList.get(6));
        System.out.println("\n\nAfter removing node:\n");
        musicLList.display();
    }
    // FINISH FOR EXERCISE 1.3
}