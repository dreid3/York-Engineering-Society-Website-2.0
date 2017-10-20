package yorkEngineeringSociety.models;

//import java.util.Date; 
import java.util.Calendar;
import java.text.DateFormat; 

public class Event {
	private String Address; 
	//Date eventdate; 
	//calendar instance for the event 
	Calendar calen = Calendar.getInstance(); 
	
	public Event() {
		
	}
	
	public void createEvent(String address, int day, int month, int year) {
		this.setAddress(address);
		day = calen.get(Calendar.DAY_OF_MONTH);
		month = calen.get(Calendar.MONTH);
		year = calen.get(Calendar.YEAR); 
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
}
