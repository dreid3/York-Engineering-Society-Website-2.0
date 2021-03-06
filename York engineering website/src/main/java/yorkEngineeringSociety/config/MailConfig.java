package yorkEngineeringSociety.config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import yorkEngineeringSociety.models.Event;
import yorkEngineeringSociety.models.User;
import yorkEngineeringSociety.repos.EventRepository;
import yorkEngineeringSociety.repos.UserRepository;
import yorkEngineeringSociety.services.EventService;

@Configuration
@EnableAsync
@EnableScheduling
public class MailConfig {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	
	@Autowired
	private EventService eventService;
	
	
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
			for (User user: userRepository.findByVerifiedTrueAndBlacklistFalse()) {
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
					mimeMessage.setText("Here's what events are up and coming! <br></br>" + template  + footer(user), "UTF-8", "html");
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
			for (User user: userRepository.findByVerifiedTrueAndBlacklistFalse()) {
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
					mimeMessage.setText("Here's what events are up and coming! <br></br>" + template  + footer(user), "UTF-8", "html");
					emailSender.send(mimeMessage);
				}
			}
		}
		

		// this handles emails set by reminder thing
		public void reminderEmails() throws MessagingException {
			
			// find events with false reminder, then check if their reminder date is before current date, send email to user about event
			List<Event> events = new ArrayList<Event>();
			events.addAll(eventRepository.findByReminderFalse());
			if (events.isEmpty()) {
				return;
			}
			
			for (Event event : events) {
				Calendar calendar = Calendar.getInstance();
				if (event.getReminderDate().before(calendar)) {
					event.setReminder(true);
					eventRepository.save(event);
					for (User user : userRepository.findByVerifiedTrueAndBlacklistFalse()) {
						basicEventEmail(event, user);
					}

				}
			}
			
				

			
		}
		
		// this one is actually just a daily check for event reminders
		
		@Async
		//@Scheduled(cron = "0 0 9-22/1 * * *")
		@Scheduled(fixedDelay=120000)
		public void dailyEmail() throws MessagingException {
			System.out.println("This is my daily one firing");
			reminderEmails();
			
		}
		
		
		/*
		// this is a test, it is set at 2 minutes, use it for the demo
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
					if (user.isVerified()) {
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
			
			 //uncomment this block to show the subscribe email feature (it will fire every 2 minutes)
			 dailyEmail();
		} */
		
		
	    // async subscribe method
		@Async
		public void sendSubscribedEmail(Long eventId, User user, Event event) throws MessagingException {
			if (!user.isVerified() || user.isBlacklist()) {
				return;
			}
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper;
				helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setTo(user.getEmail());
			helper.setSubject(event.getName() + "Reminder");
			String template = "<h1>" + event.getName() + "</h1>" + "<br></br>" + event.getTemplate() + "<br></br>" + 
					"<a href=\"http://localhost:8080/events/" + event.getEventId() + "\"> Go to Event Page</a> <br></br>";
			mimeMessage.setText("You have subscribed to this event. You will receive a notification"
					+ "a few days before this starts "+ "<br></br>" + template  + footer(user), "UTF-8", "html");
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
			mimeMessage.setText("Please confirm your email at the link below <br></br>"
					+ "<a href=\"http://localhost:8080/confirm?id=" + user.getUuid() + "\">Confirm Here</a>", "UTF-8", "html");
			emailSender.send(mimeMessage);
		}
		
		@Async
		public void massEmail(String template, String subject) throws MessagingException {
			for (User user : userRepository.findByVerifiedTrueAndBlacklistFalse()) {
				MimeMessage mimeMessage = emailSender.createMimeMessage();
				MimeMessageHelper helper;
					helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
				helper.setTo(user.getEmail());
				helper.setSubject(subject);
				mimeMessage.setText(template  + footer(user), "UTF-8", "html");
				emailSender.send(mimeMessage);
			}
		}
		
		public void basicEventEmail(Event event, User user) throws MessagingException {
			
			if (!user.isVerified() || user.isBlacklist()) {
				return;
			}
			
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper;
				helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setTo(user.getEmail());
			helper.setSubject("Event Reminder");
			String template = "";
			template = template + "<h1>" + event.getName() + "</h1>" + "<br></br>"  + event.getTemplate() + "<br></br>" + 
			"<a href=\"http://localhost:8080/events/" + event.getEventId() + "\"> Go to Event Page</a> <br></br>";
			mimeMessage.setText("This is happening soon<br></br>" + template  + footer(user), "UTF-8", "html");
			emailSender.send(mimeMessage);
		}
		
		@Async
		public void sendForgotPasswordEmail(User user) throws MessagingException {
			if (user.isBlacklist()) {
				return;
			}
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper;
				helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setTo(user.getEmail());
			helper.setSubject("Forgot Password");
			String template = "<h1> Did you forget your password and want to reset it? Click below </h1> <br></br> <a href=\"http://localhost:8080/resetPassword?id="
			+ user.getUuid() + "\">Reset Password</a>";
			mimeMessage.setText(template  + footer(user), "UTF-8", "html");
			emailSender.send(mimeMessage);
		}
		
		// add this to every email
		public String footer(User user)  {
			
			return "<br></br> <a href=\"http://localhost:8080/blacklistconfirm?id=" + user.getBlacklistid() + "\">If you would no longer like to receive emails from us, click here</a>";
		}
}
