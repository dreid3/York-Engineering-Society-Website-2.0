package yorkEngineeringSociety.models;

import java.io.Serializable;

import java.util.Calendar; 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

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
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "month")
	private String month;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "name")
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "eventId")
	private long eventId;
	
	
	public Event(String address, String template, String name, String year, String month, String day, String time) {
		this.address = address;
		this.template = template;
		this.name = name;
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
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

	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
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

}
