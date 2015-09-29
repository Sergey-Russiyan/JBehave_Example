package ua.test.steps;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.test.pages.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.hamcrest.MatcherAssert.assertThat;

@Component
public class LoginMailSteps {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private Mail mail;
    @Autowired(required = true)
    private Login login;

    // * * *  GIVEN  * * *
    @Given("I am on Gmail login page")
    public void homepageGmail() {
        login.go();
    }


    //  * * *  WHEN  * * *
    @When("I log in as email $role")
    public void loginAsSender(String role) {
        login.loginAsUser(role);
    }

    @When ("I send letter to $receiver")
    public void writeAndSend(String receiver){
        mail.writeLetterTo(receiver);
    }

    @When("I open letter contains $partOfText")
    public void openLetter(String partOfText){
        mail.openLetter(partOfText);
    }

    @When("I at Gmail login page")
    public void homePageGmail() {
        login.go();
    }

    @When("I logout from gmail")
    public void logoutFromMail(){
        mail.logoutFromMail();
    }

    @When("I see Email Succesfully Sent")
    public void emailSuccesfullySent() {
        assertThat(mail.isSentMessage(), Matchers.equalTo(true));
    }

    //  * * *  THEN * * *
    @Then("I see that letter has $textToVerify")
    public void isLetterHasText(String textToVerify){
        mail.isOpenedLetterHasText(textToVerify);
    }


    @PostConstruct
    public void initIt() throws Exception {
        logger.debug("instance initialized successfully");
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        logger.debug("cleaning up instance");
    }
}
