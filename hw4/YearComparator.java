/*
	called YearComparator that overrides one method: int compare (Date left, Date right), returns the result of calling compareTo for the left parameter, passing the right parameter, BUT MAKE SURE THE Date's compareTo is correct (I'll give feedback about your compareTo before this is due)
*/
/*
    @author Modified by So Choi

    Name of Program:    YearComparator
    Description:        This program overrides the compare method and returns the difference between two dates.
    Date:               5/26/17
    OS:                 Mac OS X
    Compiler:           terminal (javac)
*/
import java.util.Comparator;

class YearComparator implements Comparator<Date>{
	@Override
	public int compare(Date left, Date right) {
		return left.compareTo(right);
	}
}