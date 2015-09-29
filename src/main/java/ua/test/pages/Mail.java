package ua.test.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openqa.selenium.By.*;

@Component
public class Mail extends PageObject {

    // * * * TEST DATA * * *
    private final String LETTER  = "'Мне кажется, любому пользователю всегда будет достаточно объема оперативной памяти в 640 килобайт' Bill G.";
    private final String RAND_1  =  String.valueOf(System.nanoTime());
    private final String TOPIC   = "[AT][Letter] " + RAND_1;

    // * * * LOCATORS * * *
    private final String WRITE_LETTER_BTN  = "//*[@class='T-I J-J5-Ji T-I-KE L3']";
    private final String TO_WHOM           = "//*[@aria-label='Кому']";
    private final String SEND_LETTER_BTN   = "//*[contains(@data-tooltip, 'Enter)')]";
    private final String SUBJECT_FIELD     = "subjectbox";
    private final String LETTER_SEND_LABEL =  "link_vsm";

    private final String LETTER_LINK      = "(.//*[contains(text(),'" + RAND_1 + "')])[3]";
    private final String LETTER_TEXT      = "//*[@g_editable='true']";
    private final String OPENED_TEXT      = "(.//*[@class='a3s'])[1]";

    private final String LOGOUT_INIT      = "//*[contains(@title, 'Google:')]";
    private final String LOGOUT_ACCEPT    = "//*[contains(@href, 'https://mail.google.com/mail/logout')]";


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    public Mail(WebDriverProvider webDriverProvider) {
        super(webDriverProvider);
        logger.debug("web driver initialized successfully: {}", webDriverProvider);
    }

    public void writeLetterTo(String receiver){

        waitForElement(xpath(WRITE_LETTER_BTN)).click();
        logger.info("Click 'Write letter' button");

        WebElement letterTo = waitForElement(xpath(TO_WHOM));
        letterTo.sendKeys(receiver);
        logger.info("Input 'To' as: " + receiver);

        WebElement letterSubj = waitForElement(name(SUBJECT_FIELD));
        letterSubj.sendKeys(TOPIC);
        logger.info("Input letter 'subject' as: " + TOPIC);

        WebElement letterTextField = waitForElement(xpath(LETTER_TEXT));
        letterTextField.sendKeys(LETTER);
        logger.info("Input letter 'subject' as: " + LETTER);

        WebElement letterSendBtn = waitForElement(xpath(SEND_LETTER_BTN));
        letterSendBtn.click();
        logger.info("Click 'Send' letter button");
    }

    public void logoutFromMail(){
        WebElement logoutLink = waitForElement(xpath(LOGOUT_INIT));
        logoutLink.click();
        logger.info("Click 'Logout' button");

        WebElement logoutAccept = waitForElement(xpath(LOGOUT_ACCEPT));
        logoutAccept.click();
        logger.info("Click 'Exit' button");
    }

    public void openLetter(String partOftext) {
        partOftext = partOftext.contains("lastEmailFromSender")? partOftext = LETTER : partOftext;
        WebElement letterLink = waitForElement(xpath(LETTER_LINK));
        logger.info("Click letter with text: " + partOftext);
        letterLink.click();
    }

    //  * * *  CHECKS  * * *
    public boolean isSentMessage(){
        boolean isSent = waitForElement(id(LETTER_SEND_LABEL)).isDisplayed();

        if(!isSent){logger.error("Absent 'email sent' label");
            return false;
        }else{logger.info("Present 'email sent' label");
            return true;
        }
    }

    public void isOpenedLetterHasText(String textToVerify){
        textToVerify = textToVerify.equals("textFromSender")? textToVerify = LETTER : "UNKNOWN VALUE";

        String actualEmailText = waitForElement(xpath(OPENED_TEXT)).getText();
        boolean isLetterEqual = actualEmailText.equals(textToVerify);
        if (isLetterEqual) {
            logger.info("[PASSED] Letter text are equal to expected value: " + textToVerify);
        }else{
            logger.error("[FAIL] AR: " + actualEmailText + ". But ER is : " + textToVerify);
        }
    }
}
