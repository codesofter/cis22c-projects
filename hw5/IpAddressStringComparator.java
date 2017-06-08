/*
	class IpAddressStringComparator implements Comparator<IpAddress> so it overrides only the compare method, return the result of first parameter's dottedDecimal's compareTo (passing the second parameter's dottedDecimal)
*/
import java.util.Comparator;

class IpAddressStringComparator implements Comparator<IpAddress>{
	@Override
	public int compare(IpAddress ipA, IpAddress ipB) {
		String dotA = ipA.getDottedDecimal();
		return dotA.compareTo(ipB.getDottedDecimal());
	}
}