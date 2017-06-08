/*
	class IpAddressValueComparator implements Comparator<IpAddress> so it overrides only the compare method, return the return value of the Long's compare or compareTo for the first parameter's ipValue and second parameter's ipValue
*/
import java.util.Comparator;

class IpAddressValueComparator implements Comparator<IpAddress>{
	@Override
	public int compare(IpAddress ipA, IpAddress ipB) {
		return (int) (ipA.getIpValue() - ipB.getIpValue());
	}
}