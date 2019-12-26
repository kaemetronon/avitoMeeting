package avito.testingAvito.service.mail;

import avito.testingAvito.model.Meeting;
import avito.testingAvito.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MyMailSender {

    @Autowired
    public JavaMailSender emailSender;

    public void sendMessages(Meeting meeting) {

        String title = meeting.getTitle();
        String date = meeting.getDate();
        Set<Person> personSet = meeting.getPersonSet();
        SimpleMailMessage message;

        for (Person person : personSet) {
            message = new SimpleMailMessage();
            message.setTo(person.getMail());
            message.setSubject(title);
            message.setText("Hello! You're scheduled for a meeting at " + date + ".");

            this.emailSender.send(message);
        }
    }
}
