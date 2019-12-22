package avito.testingAvito.service.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailValidator {
    public static boolean validateMail(String mail) {
            Pattern pattern = Pattern.compile
                    ("([A-Za-z0-9]{1,}[\\\\.-]{0,1}[A-Za-z0-9]{1,})+" +
                            "@([A-Za-z0-9]{1,}[\\\\.-]{0,1}[A-Za-z0-9]{1,})+[\\\\.]{1}[a-z]{2,4}");
            Matcher matcher = pattern.matcher(mail);
            return matcher.matches();
    }
}
