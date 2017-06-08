/*
    Lab Exercise 1.1: Read Lesson 1, page 1, then complete the following.

    Write a driver program in which main has a GenericStack<String> variable (you MUST use the GenericStack class shown on page 1 of Lesson 1).

    Make sure you DON'T change the GenericStack class written above. Upload only main.
*/

public class GenericStackDriver{
    public static void main(String[] args){
        // Instantiate a GenericStack<String> and assign to the main variable. 
        GenericStack<String> gStack = new GenericStack<String>();
        gStack.push("Add several Strings (at least 3, ");
        gStack.push("your choice of Strings) to the GenericStack");
        gStack.push(" (use the push method).");


        // Display the contents using the GenericStack's toString method. 
        System.out.println(gStack.toString());


        // Then use a while loop (loops while the GenericStack isn't empty) that removes an element using the pop method, and displays it.
        while(gStack.getSize() > 0){
            System.out.println("\n\nRemove element using the pop method: " + gStack.pop().toString());
        }
        System.out.println("\n\nDone.");
    }
}
 