package yorkEngineeringSociety.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.repos.UserRepository;
import yorkEngineeringSociety.services.CustomUserDetailsService;
import yorkEngineeringSociety.services.EventService;

@Configuration
@EnableWebSecurity
@EnableAsync
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private EventService eventService;
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder(11);
	}
	
	// this is the weekly email notification
	//first it gets all the events ordered by date and selects only the first three.
	//Next for each user that has weekly as their notification setting, sends an email with the next three events
	//Cron is a time expression. This is set to every Sunday at noon.
	@Async
	@Scheduled(cron = "0 34 12 * * SUN")
	public void weeklyEmail() throws MessagingException {
		List<Event> events = eventService.getEventsOrderedByDate();
		List<Event> orderedEvents = new ArrayList<Event>();
		System.out.println("This is my weekly one firing");
		// if there are less than 3 events, just add all the events
		if (events.size() < 3) {
			orderedEvents = events;
		}
		else
		{
		  orderedEvents.add(events.get(0));
		  orderedEvents.add(events.get(1));
		  orderedEvents.add(events.get(2));
		}
		for (User user: userRepository.findAll()) {
			if (user.getNotification().equals("weekly")) {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper helper;
					helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
				helper.setTo(user.getEmail());
				helper.setSubject("Upcoming Events");
				String template = "";
				for (Event event : orderedEvents) {
					template = template + "<h1>" + event.getName() + "</h1>" + "<br></br>" + event.getTemplate() + "<br></br>" + 
				"<a href=\"http://localhost:8080/events/" + event.getEventId() + "\"> Go to Event Page</a> <br></br>";
				}
				mimeMessage.setText("Here's what events are up and coming! <br></br>" + template, "UTF-8", "html");
				emailSender.send(mimeMessage);
			}
		}
	}
	
	//This is the monthly one. Should trigger on the first day of every month
	@Async
	@Scheduled(cron = "0 0 12 1 * ?")
	public void monthlyEmail() throws MessagingException {
		List<Event> events = eventService.getEventsOrderedByDate();
		List<Event> orderedEvents = new ArrayList<Event>();
		System.out.println("This is my monthly one firing");
		// if there are less than 3 events, just add all the events
		if (events.size() < 3) {
			orderedEvents = events;
		}
		else
		{
		  orderedEvents.add(events.get(0));
		  orderedEvents.add(events.get(1));
		  orderedEvents.add(events.get(2));
		}
		for (User user: userRepository.findAll()) {
			if (user.getNotification() == "monthly") {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper helper;
					helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
				helper.setTo(user.getEmail());
				helper.setSubject("Upcoming Events");
				String template = "";
				for (Event event : orderedEvents) {
					template = template + "<h1>" + event.getName() + "</h1>" + "<br></br>" + event.getTemplate() + "<br></br>" + 
				"<a href=\"http://localhost:8080/events/" + event.getEventId() + "\"> Go to Event Page</a> <br></br>";
				}
				mimeMessage.setText("Here's what events are up and coming! <br></br>" + template, "UTF-8", "html");
				emailSender.send(mimeMessage);
			}
		}
	}
	
	// this one is actually just a daily check for event reminders
	/*
	@Async
	@Scheduled(cron = "0 0 12 * * *")
	public void dailyEmail() throws MessagingException {
		System.out.println("This is my daily one firing");
		List<Event> events = eventService.getEventsOrderedByDate();
		List<Event> orderedEvents = new ArrayList<Event>();
		// if there are less than 3 events, just add all the events
		if (events.size() < 3) {
			orderedEvents = events;
		}
		else
		{
		  orderedEvents.add(events.get(0));
		  orderedEvents.add(events.get(1));
		  orderedEvents.add(events.get(2));
		}
		for (User user: userRepository.findAll()) {
			if (user.getNotification() == "daily") {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper helper;
					helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
				helper.setTo(user.getEmail());
				helper.setSubject("Upcoming Events");
				String template = "";
				for (Event event : orderedEvents) {
					template = template + "<h1>" + event.getName() + "</h1>" + "<br></br>"  + event.getTemplate() + "<br></br>" + 
				"<a href=\"http://localhost:8080/events/" + event.getEventId() + "\"> Go to Event Page</a> <br></br>";
				}
				mimeMessage.setText("Here's what events are up and coming! <br></br>" + template, "UTF-8", "html");
				emailSender.send(mimeMessage);
			}
		}
	}
	*/
	
	/*
	// this is a test, it is set at 2 minutes
	@Async
	@Scheduled(fixedDelay=120000)
	public void testEmail() throws MessagingException {
		List<Event> events = eventService.getEventsOrderedByDate();
		List<Event> orderedEvents = new ArrayList<Event>();
		// if there are less than 3 events, just add all the events
		if (events.size() < 3) {
			orderedEvents = events;
		}
		else
		{
		  orderedEvents.add(events.get(0));
		  orderedEvents.add(events.get(1));
		  orderedEvents.add(events.get(2));
		}
		for (User user: userRepository.findAll()) {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper helper;
					helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
				helper.setTo(user.getEmail());
				helper.setSubject("Upcoming Events");
				String template = "";
				for (Event event : orderedEvents) {
					template = template + "<h1>" + event.getName() + "</h1>" + "<br></br>" + event.getTemplate() + "<br></br>" + 
				"<a href=\"http://localhost:8080/events/" + event.getEventId() + "\"> Go to Event Page</a> <br></br>";
				}
				mimeMessage.setText("Here's what events are up and coming! <br></br>" + template, "UTF-8", "html");
				emailSender.send(mimeMessage);
		}
	}
	*/
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setUsername("yorksoctest@gmail.com");
	    mailSender.setPort(587);
	    mailSender.setProtocol("smtp");
	    mailSender.setPassword("fcanezluogzqerqt");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    mailSender.setJavaMailProperties(props);
	    
	     
	    return mailSender;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/events", "/css/**", "/signup", "/events", "/events/**", "/bye", "/confirm").permitAll()
                .antMatchers("/profile").authenticated()
                .anyRequest().hasAuthority("ROLE_ADMIN")
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/events")
                .failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
            .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }
    
    // async subscribe method
	@Async
	public void sendSubscribedEmail(Long eventId, User user, Event event) throws MessagingException {
		String url = "<a href=\"http://localhost:8080/events/" + eventId + "\"> Go to Event Page</a>";
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper;
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		helper.setTo(user.getEmail());
		helper.setSubject(event.getName() + "Reminder");
		mimeMessage.setText(event.getTemplate() + "<br></br>" + url, "UTF-8", "html");
		emailSender.send(mimeMessage);
	}
	
	//async confirmation email method
	@Async
	public void sendConfirmationEmail(User user) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper;
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		helper.setTo(user.getEmail());
		helper.setSubject("Confirm your Account");
		mimeMessage.setText("Thank you for creating an account. Please confirm your email at the link below <br></br>"
				+ "<a href=\"http://localhost:8080/confirm?id=" + user.getUuid() + "\">Confirm Here</a>", "UTF-8", "html");
		emailSender.send(mimeMessage);
	}
}