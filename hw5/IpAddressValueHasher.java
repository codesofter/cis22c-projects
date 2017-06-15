/*
	@author So Choi

	Name of Program:  	IpAddressStringComparator
	Description:    	Implements Hasher<IpAddress> so when you override hash(IpAddress) it returns a Long object for the parameter's ipValue's call to hashCode
	Date:         		6/8/17
	OS:         		Mac OS X
	Compiler:       	terminal (javac)
*/
class IpAddressValueHasher implements Hasher<IpAddress>{
	@Override
	public int hash(IpAddress ip){
		return (int) ip.getIpValue();
	}
}