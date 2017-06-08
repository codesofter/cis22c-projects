/*
	called MonthComparator that overrides one method: int compare (Date left, Date right), which returns difference of the left's month - right's month, BUT if the months are the same, the difference of the left's day - right's day, BUT if the days are the same, too, the difference between left's year - right's year
*/
/*
    @author Modified by So Choi

    Name of Program:    MonthComparator
    Description:        This program overrides the compare method and returns the difference between two dates.
    Date:               5/26/17
    OS:                 Mac OS X
    Compiler:           terminal (javac)
*/
import java.util.Comparator;

class MonthComparator implements Comparator<Date>{
	@Override
	public int compare(Date left, Date right) {
		int diff = left.getMonth() - right.getMonth();
		if (diff == 0) {
			diff = left.getDay() - right.getDay();
			if (diff == 0) {
				diff = left.getYear() - right.getYear();
			}
		}
		return diff;
	}
}