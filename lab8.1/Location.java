import java.text.*;
public class Location {
	private String name="";
	private String address="";
	private double latitude; // default 0
	private double longitude; // default 0
	
	public Location(String nm, String addr, double lat, double lon)
	{
		setName(nm);
		setAddress(addr);
		setCoordinates(lat, lon);
	}
	
	public Location(String nm)
	{
		setName(nm);
	}
	
	public void setName(String nm)
	{
		if( nm != null )
			name = nm;
	}
	
	public void setAddress(String addr)
	{
		if( addr != null )
			address = addr;
	}
	
	public void setCoordinates(double lat, double lon)
	{
		latitude = lat;
		longitude = lon;
	}
	
	public String getName(){ return name; }
	
	public String getAddress(){ return address; }
	
	public double getLatitude(){ return latitude; }
	
	public double getLongitude(){ return longitude; }
	
	public String toString()
	{
		return "Location: "+ name + ", " + address + 
				", (" + latitude + ", " + longitude + ")";
	}
	
	/*
		ADD to the Location class here in the Location.java file (in the "Hash Table Files" link in Catalyst) so it overrides the equals method (compare ONLY the name) AND the hashCode method (use the body of the Hash method shown on page 2, but instead of someStringMember, do the operations on the Location's latitude & longitude as described below).
	*/
	// YOU WRITE equals: public boolean equals(Object obj)
	public boolean equals(Object obj){
	   return name.equalsIgnoreCase(((Location)obj).name);
	}
	
	// YOU WRITE hashCode: public int hashCode()
	/*
		The Locations' hashCode method is assign to a local variable the latitude + 90, and to another local variable the longitude+180.  Then convert each of those into a String (saving in local variables), BUT using a DecimalFormat with "000.0000" (will be shown in the Class Notes for 05-17).  Remove the decimal points in each String, concatenate the 2 Strings, and return the hashCode of the resulting String). (You'll need to import java.text.*; )
	*/
    public int hashCode(){ 
    	double partA = latitude + 90;
    	double partB = longitude + 180;

		DecimalFormat df = new DecimalFormat("000.0000");
    	String convertedA = df.format(partA);
    	String convertedB = df.format(partB);

    	convertedA = convertedA.replace(".", "");
    	convertedB = convertedB.replace(".", "");

    	String hashResult = convertedA + convertedB;
       	return (hashResult != null)?  hashResult.hashCode() : 0;
    }
}
