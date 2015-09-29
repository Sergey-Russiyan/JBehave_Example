package ua.test.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openqa.selenium.By.*;

@Component
public class Login extends PageObject {

    //  * * * TEST DATA * * *
    private final String MAIL_URL          = "https://gmail.com";
    private final String SENDER_EMAIL      = "R.S.EmailSender@gmail.com";
    private final String SENDER_PASSWORD   = "R.S.EmailSender123";
    private final String RECEIVER_EMAIL    = "R.S.Email.Receiver@gmail.com";
    private final String RECEIVER_PASSWORD = "R.S.Email.Receiver123";

    // * * * LOCATORS * * *
    private final String EMAIL_FIELD    = "Email";
    private final String NEXT_BUTTON    = "next";
    private final String PASSWORD_FIELD = "Passwd";
    private final String SIGN_BUTTON    = "signIn";

    private final String REMEMBER_ME_CHECKBOX = ".//*[@type='checkbox'][@checked='checked']|.//*[@id='PersistentCookie']";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    public Login(WebDriverProvider webDriverProvider) {
        super(webDriverProvider);
        logger.debug("web driver initialized successfully: {}", webDriverProvider);
    }

    public void go() {
        get(MAIL_URL);
        logger.info("Navigating to URL: "  + MAIL_URL);
    }

    public void loginAsUser(String role) {

        WebElement email = waitForElement(id(EMAIL_FIELD));
        logger.info("Enter 'Email'");

        if(role.trim().equalsIgnoreCase("sender")){
            logger.info("Input to Email field: " + SENDER_EMAIL);
            email.sendKeys(SENDER_EMAIL);
        }
        else if(role.trim().equalsIgnoreCase("receiver")){
            logger.info("Input to Email field: " + RECEIVER_EMAIL);
            email.sendKeys(RECEIVER_EMAIL);
        }
        else{
            logger.error("Unknown user role!. Valid values: 'sender', 'receiver'");
        }

        logger.info("Click 'Next' button");
        WebElement proceedBtn = waitForElement(id(NEXT_BUTTON));
        proceedBtn.click();

        WebElement passwd = waitForElement(id(PASSWORD_FIELD));
        if(role.trim().equalsIgnoreCase("sender")){
            logger.info("Input to Password field: " + SENDER_PASSWORD);
            passwd.sendKeys(SENDER_PASSWORD);
            logger.info("Enter 'Password':" + SENDER_PASSWORD);
        }else { // means 'receiver'
            logger.info("Input to Password field: " + RECEIVER_PASSWORD);
            passwd.sendKeys(RECEIVER_PASSWORD);
            logger.info("Enter 'Password':" + RECEIVER_PASSWORD);

        }

        WebElement stayLoginedCheckbox = waitForElement(xpath(REMEMBER_ME_CHECKBOX));
        logger.info("Click checkbox 'Remember me'");
        stayLoginedCheckbox.click();

        WebElement signInBtn = waitForElement(id(SIGN_BUTTON));
        signInBtn.click();
        logger.info("Click 'Sign In' button");
    }
}
