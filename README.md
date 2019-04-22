# users-credit-cards-manager

This is a Java Spring 5 application that uses Spring Security to manage user authentication.

Card users can register and then log in the application to manage their credit cards.

Credit card details can be stored, updated and deleted, both by the credit card owners and the administrator user. It uses BCrypt to encrypt passwords

For persistence it uses Hibernate &amp; MySQL.

It is built using Maven.

Administrator user credentials: admin / admin123! (Can be configured differently)


Requirements:

* Java 8
* Maven 3
* Database configuration is currently configured as follows:
     -  MySQL 5
     -  Schema name cards_db
     -  Localhost/port 3306 
 
 
 To build and run the project, type:
 
    mvn clean install tomcat7:run
  
 at the same folder where pom.xml is.


