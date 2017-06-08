public class IpAddress 
{	
	private long ipValue=0;
	private String dottedDecimal="0.0.0.0";

	public IpAddress(){}

	public IpAddress(String dec)
	{
		setDottedDecimal(dec);
	}
		
	public boolean setDottedDecimal( String s )
	{
		if( s == null || s.length() == 0 )
			return false;
		dottedDecimal = s;
		ipValue = 0;
		String [] tokens = s.split("[.]");
		for( String tok : tokens ){
			int subVal = Integer.parseInt(tok);
			ipValue  =  ipValue * 256 + subVal;
		} // end for
		return true;
	}
	
	public long getIpValue(){ return ipValue; }
	
	public String getDottedDecimal(){ return dottedDecimal; }

	public String toString(){ return dottedDecimal  + ", " + Long.toHexString(ipValue); }
} // end class IpAddress