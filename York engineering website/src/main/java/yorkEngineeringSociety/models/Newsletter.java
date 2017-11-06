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

@Entity
@Table(name = "newsletter")
public class Newsletter implements Serializable {
	private static final long serialVersionUID = -758740495603645942L;

	@Lob
	@Column(name = "template")
	private String template;
	
	@Column(name = "calendar")
	private Calendar calendar;
	
	@Column(name = "name")
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "newsletterId")
	private long newsletterId;

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
	
	public void setDate(int year, int month, int day)
	{
		calendar.set(year, month - 1, day);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNewsletterId() {
		return newsletterId;
	}

	public void setNewsletterId(long newsletterId) {
		this.newsletterId = newsletterId;
	}
	
	public Newsletter() {
	}
	
	}

