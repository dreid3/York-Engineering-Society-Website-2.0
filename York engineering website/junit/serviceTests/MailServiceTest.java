package serviceTests;

//import static org.junit.Assert.*;

//import org.junit.Before;
//import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import yorkEngineeringSociety.services.MailSenderService;


//have this set as a main so that we know that the mail service works 
public class MailServiceTest {
	public static void main(String[] args)
    {
        //Create the application context
        ApplicationContext context = new FileSystemXmlApplicationContext("application-context.xml");
         
        //Get the mailer instance
        MailSenderService mailer = (MailSenderService) context.getBean("mailService");
 
        //Send a composed mail
        mailer.sendMail("rvincent@ycp.edu", "Test Subject", "Testing body");
 
        //Send a pre-configured mail
        mailer.sendPreConfiguredMail("Lets hope this works.. what did we get????");
    }
}
