package yorkEngineeringSociety.models;

import java.io.Serializable;

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
	
	@Column(name = "name")
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "eventId")
	private long eventId;
	
	
	public Event(){
		
	}
	
	public Event(String address, String template, String name) {
		this.address = address;
		this.template = template;
		this.name = name;
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
