public class Person implements Comparable<Person> {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public int getAge() { return this.age; }
    
    public String getName() { return this.name;}

    @Override
    public String toString() {
        return name + ", " + age;
    }

    @Override
    public int compareTo(Person per) {
        int result = this.name.compareToIgnoreCase(per.name);
        if(result == 0 ) // names are the same
		 result = this.age - per.age;
        return result;
    }

    public static void main(String[] args) {
        Person e1 = new Person("Stephanie", 55);
        Person e2 = new Person("STEPHAN", 60);
        
        int retval = e1.compareTo(e2);
        if( retval < 0 )
           System.out.println(e1 + " < " + e2 );
        else if ( retval > 0 )
				  System.out.println(e1 + " > " + e2 );
             else
				  System.out.println(e1 + " == " + e2 );
    }
}
