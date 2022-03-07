# Assignment
This assignment is a simple role based authentication and authorization, which will allow to view statements using JWT token.
This application's server side is implemented using Java, Spring Boot, Spring Security, JWT and Spring Data concepts.
Logging is done using logback and validation is done using Spring Validation.

APIs:-

It contains of 3 API's.

1. Login API(/auth/login) : This API accepts username and password as a login request and returns Bearer token in response.
This token will be used in other api calls and will be passed as Authorization header.
                                      
2. View Statement API(/statement/statements) : This API accepts Bearer Token as Authorization Header and View statement request.
This API will only be used by Admin Users to view statements.

3. View Statement API(/statement/) : This API accepts Bearer Token as Authorization Header and Account Number.
This API will be used by User/Admin Users to view statements for an account number.

GETTING STARTED

Clone the API : git clone https://github.com/Govind85/assignment.git

Make sure to change below datasource property in properties file. 

spring.datasource.url: jdbc:ucanaccess://C://Users//govkrish//Downloads//accountsdb.accdb

Run the mvn clean install command and go to target folder and run java -jar assignment-0.0.1-SNAPSHOT.jar

Attaching the screenshots for reference. Please find the test reports HTML file in main direcotry named Test Results - All_in_assignment.


