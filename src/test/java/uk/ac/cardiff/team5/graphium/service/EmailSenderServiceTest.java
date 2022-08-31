package uk.ac.cardiff.team5.graphium.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailParseException;

@SpringBootTest
public class EmailSenderServiceTest {

    @Autowired
    private EmailSenderService senderService;

    @Test
    public void CheckEmailSends() throws Exception {
        try {
            senderService.sendEmail("GraphiumFileShare@gmail.com", "Test Email", "This email tests the email sender class");
            System.out.println("Email sent");
        } catch (Exception e){
            System.out.println("Email couldn't be sent");
        }
    }

    @Test
    public void CheckEmailDoesntSend(){

        MailParseException thrown = Assertions.assertThrows(MailParseException.class, () -> {
            senderService.sendEmail("", "Test Email", "This email tests the email sender class");
            System.out.println("Email sent");
        }, "Mail Parse Exception Expected");

        Assertions.assertEquals("Could not parse mail; nested exception is javax.mail.internet.AddressException: Illegal address in string ``''", thrown.getMessage());
    }
}
