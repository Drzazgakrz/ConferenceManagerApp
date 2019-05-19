# ConferenceManagerApp

To run this project you need: Java (my version is 12 but if necessary you could change it), Maven, PostgreSQL.
1. Customization:
- you could change parameters like url, username, password in file /src/main/resources/application.properties:
a) url spring.datasource.url,
b) username spring.datasource.username
c) password spring.datasource.password
2. Run
- create database named conference_manager_app in your pgAdmin if you prefer comands remember to create schema public
- you can import it to your favourite IDE and then run it or via command line you should run it using mvn spring-boot:run command
