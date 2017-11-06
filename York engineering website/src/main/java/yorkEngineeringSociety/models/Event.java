package yorkEngineeringSociety.models;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

//use a long type for dates internally 
//simple date class 

//import java.util.Date;
@Entity
@Table(name = "events")
public class Event implements Serializable {
	
	private static final long serialVersionUID = 6536947785093044779L;
	
	@Column(name = "address")
	private String address; 
	
	@Lob
	@Column(name = "template")
	private String template;
	
	@Column(name = "calendar")
	private Calendar calendar;
	
	@Column(name = "name")
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "eventId")
	private long eventId;
	
	private String description; 
	
	public Event() {
		
	}
		

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	public void setDate(int year, int month, int day, int hour, int minute)
	{
		calendar.set(year, month - 1, day, hour, minute);
	}
	
	public Date getDate() {
		return calendar.getTime();
	}
	
	
	public String getName() {
		return name;
	}
	
	public long getEventId() {
		return eventId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
