package yorkEngineeringSociety.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage; 
public class MailSenderService {
	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage preConfiguredMessage;


	public void sendMail(String to, String subject, String body)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}
	
	public void sendPreConfiguredMail(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
	
	
}
