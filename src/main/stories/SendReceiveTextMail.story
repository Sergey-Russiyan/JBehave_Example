Gmail Functuionality

Meta:
@author Sergey R.
@category mail

Narrative: 

In order to communicate with colleagues
As a regular user
I want to be able receive sended to me emails with text content

Scenario: Send text email, receive text email

Given I am on Gmail login page
When I log in as email sender
And I send letter to R.S.Email.Receiver@gmail.com
And I see Email Succesfully Sent
And I logout from gmail
And I at Gmail login page
And I log in as email receiver
And I open letter contains lastEmailFromSender
Then I see that letter has textFromSender