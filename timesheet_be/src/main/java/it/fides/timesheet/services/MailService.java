package it.fides.timesheet.services;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import it.fides.timesheet.models.entities.DipendentEntity;
import it.fides.timesheet.models.entities.ResponsableEntity;

@Service
public class MailService {
	
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;
    
    @Value("${sendgrid.mail.from}")
    private String sendGridFromMail;
	
    public void sendEmailToGroupResp(DipendentEntity dipendent, ResponsableEntity responsable) throws IOException {
        Email from = new Email(sendGridFromMail); // sendGrid email account
        Email to = new Email(responsable.getEmailUser());
        String subject = "Timesheet submitted";
        Content content = new Content("text/plain",
        		"Hello " + responsable.getFirstNameUser() + ",\n" +
                dipendent.getFullNameUser() + " has just submitted the timesheet!\n" +
				"You can now approve it from your personal area.\n" +
                "Regards, FidesTeam");

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
    
    public void sendEmailTimesheetApproved(DipendentEntity dipendent) throws IOException {
        Email from = new Email(sendGridFromMail); // sendGrid email account
        Email to = new Email(dipendent.getEmailUser());
        String subject = "Timesheet approved";
        Content content = new Content("text/plain",
        		"Hello " + dipendent.getFirstNameUser() + ",\n" +
                "Your timesheet has just been approved by your responsable!\n" +
                "Regards, FidesTeam");

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
    
    public void sendEmailTimesheetRejected(DipendentEntity dipendent) throws IOException {
        Email from = new Email(sendGridFromMail); // sendGrid email account
        Email to = new Email(dipendent.getEmailUser());
        String subject = "Timesheet denied";
        Content content = new Content("text/plain",
        		"Hello " + dipendent.getFirstNameUser() + ",\n" +
                "We regret to inform you that your timesheet has been rejected.\n" +
        		"You can update it and submit it on your personal area right away.\n" +
				"Regards, FidesTeam");

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
