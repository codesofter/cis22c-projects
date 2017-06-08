/*
	class IpAddressValueHasher implements Hasher<IpAddress> so when you override hash(IpAddress) it returns a Long object for the parameter's ipValue's call to hashCode
*/
class IpAddressValueHasher implements Hasher<IpAddress>{
	@Override
	public int hash(IpAddress ip){
		return (int) ip.getIpValue();
	}
}