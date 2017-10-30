package yorkEngineeringSociety.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "newsletter")
public class Newsletter {
	@Lob
	@Column(name = "template")
	private String template;
	
	@Column(name = "month")
	private String month;
	
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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

