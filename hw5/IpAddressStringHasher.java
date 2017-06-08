/*
	class IpAddressStringHasher implements Hasher<IpAddress> so when you override hash(IpAddress) it returns an int for the parameter's dottedDecimal String using the SAME algorithm shown in int Hash( String key ) in Lesson 8, p. 8.2 (NOT String's hashCode), or your own algorithm that manipulates each char
*/
class IpAddressStringHasher implements Hasher<IpAddress>{
	@Override
	public int hash(IpAddress ip){
		int k, retVal = 0;
		String ipStr = ip.getDottedDecimal();

		for(k = 0; k < ipStr.length(); k++ )
		    retVal = 37 * retVal + ipStr.charAt(k);

		return retVal;
	}
}