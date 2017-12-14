package yorkEngineeringSociety.models;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "members")
public class Member implements Serializable {
	private static final long serialVersionUID = 429486903029384852L;

	@Lob
	@Column(name = "template")
	private String template;
	
	@Column(name = "name")
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "memberId")
	private long memberId;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNewsletterId() {
		return memberId;
	}

	public void setNewsletterId(long memberId) {
		this.memberId = memberId;
	}
	
	public Member() {
	}
	

}	
