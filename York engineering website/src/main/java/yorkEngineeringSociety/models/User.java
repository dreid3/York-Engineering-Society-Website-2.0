package yorkEngineeringSociety.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
//we need to import the calendar/date system for the membership 
//if we need to make a membership class then so be it. 
//or we do that all through a controller 
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.text.DateFormat; 

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 3832260458606639106L;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	@Transient
	private String password;
	@Column(name = "email")
	@NotEmpty(message = "*Enter a email address")
	private String email;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private long userId;
	
	@Column(name = "verified")
	private boolean verified;
	
	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Column(name = "uuid")
	private String uuid;
	
	public String getBlacklistid() {
		return blacklistid;
	}

	public void setBlacklistid(String blacklistid) {
		this.blacklistid = blacklistid;
	}

	public boolean isBlacklist() {
		return blacklist;
	}

	public void setBlacklist(boolean blacklist) {
		this.blacklist = blacklist;
	}

	@Column(name = "blacklistid")
	private String blacklistid;
	
	@Column(name = "blacklist")
	private boolean blacklist;

	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "notification")
	private String notification;
	
	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "membership")
	private boolean membership = false; 
	
	@Column(name = "isAdmin")
	private boolean isAdmin; 
	
	@Column(name = "isResetPassword")
	private boolean isResetPassword; 
	
	public boolean isResetPassword() {
		return isResetPassword;
	}

	public void setResetPassword(boolean isResetPassword) {
		this.isResetPassword = isResetPassword;
	}

	@Column(name = "isActive")
	private boolean isActive; 
	
	@Column (name = "rsvp")
	private ArrayList<Long> rsvp; 
	
	
	public ArrayList<Long> getRsvp() {
		return rsvp;
	}

	public void setRsvp(ArrayList<Long> rsvp) {
		this.rsvp = rsvp;
	}

	public User(){
		verified = false;
		notification = "none";
		isResetPassword = false;
	}
	
	//utility personal information method
	public void setUserInformation(String fn, String ln, String email) {
		this.firstname = fn;
		this.lastname = ln;
		this.email = email;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	public String getPassword(){
		return this.password;
	}
	
	
	
	public String getName(){
		return this.firstname;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setPassword(String pass){
		this.password = pass;
	}
	
	
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public void setEmail(String email){
			this.email = email;
	}
	
	public void setAsMember() {
		this.membership = true;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isMembership() {
		return membership;
	}

	public void setMembership(boolean membership) {
		this.membership = membership;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public List<String> getRole() {
		if (this.isAdmin()) {
			return Arrays.asList("ROLE_ADMIN");
		}
		return Arrays.asList("ROLE_USER");
	}
}
