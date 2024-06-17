Feature: Verify the Aadhar Record to create Bank Account

Scenario Outline: After aadhar varification do API call to create new bank account
When aadha_no from properties file matches with "Aadhar" database
Given applicant firstname "<Fname>", lastname "<Lname>", address "<Address>" and phone "<Phone>"
Then create request body for API call
And send post call to create bank account with "<url>"
Then read and Verify fname in response with DB
And Verify lname in response with DB
And Verify AadharNo in response with DB
And Verify Address in response with DB
And match phone in response with DB
And validate AccountID is created in response
Then validate AccountID is numeric
Then validate createAt in response and its date should be current date
Examples:
| Fname | Lname | Address | Phone | url |
|Kani|Mozhi|AASAS ADAD|1234567|https://reqres.in/api/users|