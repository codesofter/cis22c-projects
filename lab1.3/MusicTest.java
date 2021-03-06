/*
    Add to the main method given on this page so you will ALSO do the following (in addition to what is already in main):
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
        Music [] musicArray = {
           new Music("Moonlight Sonata",  "Beethoven", 27),
           new Music("Brandenburg Concerto #3", "Bach", 1048),
           new Music("Prelude in e minor", "Chopin", 28) };
        LList<Music>  musicList = new LList<Music>(); // creates LinkedList!
        Music tempMusic;

        for( int i=0; i  < 3; ++i ){
            musicList.add(musicArray[i]);
        }

        // 1. Call the musicList's add method to insert a new Music object (make up 2 Strings and an int) to the BEGINNING of the musicList
        musicList.add(1, new Music("Test Music 1", "Composer", 1));

        // 2. Assign to a local Music variable a new Music object with the same 2 Strings you just used in #1 of this exercise
        tempMusic = new Music("Test Music 1", "Composer", 1);

        // 3. Call contains passing the local Music variable you assigned in #2 of this exercise
        // 4. Display the entry if found, or "not found" if not found
        if( tempMusic != null && musicList.contains(tempMusic) ){
            System.out.println( "\nFound " + tempMusic.toString());
        }else{
            System.out.println( "\nNot found");
        }

        // 5. Call display for the musicList
        System.out.println("\nThe list as displayed in LList.java: ");
        musicList.display();
        
        // 6. Call remove to remove the local Music object you created in #2 of this exercise
        musicList.remove(tempMusic);
        
        // 7. Call display for the musicList again. 
        System.out.println("\n\nAfter removing node:\n");
        musicList.display();
    }
    // FINISH FOR EXERCISE 1.3
}