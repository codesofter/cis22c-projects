public class Date implements Comparable<Date>{
	static final int MIN_MONTH = 1;
	static final int MAX_MONTH = 12;
	static final int MIN_YEAR = 1000;
	static final int MAX_YEAR = 9999;
	static final int [] DAYS_IN_MONTH =
		{ 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private int month = 1;
	private int day = 1;
	private int year = 1000;
	
	public Date() {}
	
	public Date( int m, int d, int y )
	{
		setDate(m, d, y); // else leave default values
	}
	
	public static boolean isLeapYear(int y)
	{
		return (y % 4 == 0 && y % 100 != 0 || y % 400 == 0);
	}
	
	public boolean setDate( int m, int d, int y )
	{
		int isLeap = 0;

		if (y >= MIN_YEAR && y <= MAX_YEAR
			&& m >= MIN_MONTH && m <= MAX_MONTH)
		{
			if (m == 2 && isLeapYear(y))
				isLeap = 1;
			if (d >= 1 && d <= (DAYS_IN_MONTH[m] + isLeap))
			{
				month = m;
				day = d;
				year = y;
				return true;
			}
		}
		return false; // leaves instance vars. as they were before
	} // end setDate
	
	public int getMonth(){ return month; }
	
	public int getDay(){ return day; }
	
	public int getYear(){ return year; }

	public String toString(){
		return month + "/" + day + "/" + year;
	}

	// COMPLETE the compareTo method:
	//    Return the correct int as described in the Comparable example file
	//    so it compares the years, then the months (if the years are equal),
	//    then the days (if the years and months are equal)
	// Remember, return 0 if all 3 are the same, an int > 0 if this > param,
	//    otherwise, return an int < 0
	@Override
	public int compareTo(Date param){
		int result = this.year - param.getYear();
		if (result == 0) {
			result = this.month - param.getMonth();
			if (result == 0) {
				result = this.day - param.getDay();
			}
		}
		return result;
	}
}