package yorkEngineeringSociety.models;

public class Calendar {
	
	//get a day 
	public static int day(int day, int month, int year) {
		int y = year - (14 - month) / 12; 
		int x = y + y/4 - y/100 + y/100; 
		int m = month + 12 * ((14 - month)/12) - 2; 
		int d = (day + x + (31*m)/12) % 7;
		return d;
	}
	
	//is it a leap year
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year %100 != 0)) {
			return true;
		}
		
		if(year % 400 == 0) {
			return true;
		}
		
		else {
			return false; 
		}
		
	}
	
	//the months
	String[] months = {"January", "February", "March", "April", "May", "June"
			, "July", "August", "September", "October", "November", "December"};
	
	//the total number of days 
	int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	
}