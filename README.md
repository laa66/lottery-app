# Lottery App 🎲

### Spring MVC web app with MySQL database
<p>
After creating an account you can participate in the lottery which takes place once a day 
at 10 pm. You can also check your latest lottery tickets and update user details provided during registration. 
For more functionality log into account with Admin privileges.
</p>

## How to run ⚡

To clone and run this application you'll need installed JDK 19.0.1 and Tomcat 9.0.70. 
You will need to copy .war package 
to Tomcat dir from repository.

    # Clone this repository
    $ git clone https://github.com/laa66/spring-mvc-lottery-webapp.git

    # Go into the repository spring-mvc-lottery-web-app/target folder
    $ cd spring-mvc-lottery-web-app/target

    # From this location copy .war file into Tomcat folder
    $ copy spring-mvc-lottery-web-app.war c:\your_path\Tomcat 9.0\webapps

    # Go to Tomcat bin folder and run the Tomcat Server
    $ cd c:\your_path\Tomcat 9.0\bin -> $ start startup.bat

##### Application will be available on endpoint: 

``http://www.localhost:8080/spring-mvc-lottery-web-app``


## How to use 🗺️
At the very beginning try to register after the page loads. 

1. Click the signup button on the right side of the navigation bar
2. Enter user details and create your account
3. After redirection log in using previously provided username and password

##### Now you will be able to use regular user features of the app. 

<span style="color: aqua;"><b>Note!</b></span>

    If you want to check administrator features provided by the app you will need to
    log into account with Admin credentials.

    username - admin
    password - Adminkey1$

## Features 📌
### Here you can check app features

#### App features: 

- user authentication and authorization with Spring Security
- lottery draw one time a day at 10 pm
- support for CRUD operations on MySQL Database using Hibernate


#### Regular user features:

- user registration
- user login
- participating in the lottery
- checking recent lottery draw results
- checking your lottery numbers history
- user summary
- updating user details


#### Admin user has all the features of a regular user and also:

- app stats
- adding user in admin panel
- updating user details and enable/disable their accounts
- deleting users
- manual execution of lottery draw

<details>
  <summary style="color: grey; font-size:16px; font-weight: 700;">Click here for app development details</summary>

### Application overview

``Simple diagram to show how application layers work``

![App-overview](src/main/webapp/resources/image/app-overview.png?raw=true "layer")

### Architecture


##### Starting with Web Layer

    Browser is acting as a client in our architecture, it renders the web page for 
    the user who can also send requests to our application.
    
    Incoming requests are filtered with Spring Security filters which delegates them 
    to appropriate endpoints based on user authentication and authorization.

    After arriving requests are processed inside or delegated to the injected Services.
    Then Controllers update the view or redirect request to the other endpoints.

<br>

##### Business Logic Layer

    Application contains few Services which process the data in appropriate way or 
    delegate work to the other injected components and repositories. Services can also 
    create instances and manipulate entities if it's required.
<br>

#### Data Access Layer

    This layer handle communication between app and database. It supports basic CRUD 
    operations. Datasource beans which handle integration between web app and 
    MySQL Database has been created and configured in app Configuration. I also
    used connection pooling pattern with c3p0 framework to reuse existing database 
    connection and make overall performance better.

<br>
</details>

## Built with 🔨

#### Technologies & tools used:

- JDK 19
- Spring 5, Spring MVC, Spring Security, Spring AOP
- JSP
- Hibernate 5 & JPA
- MySQL 8.0
- HTML & CSS
- JavaScript
- Bootstrap v5.3
- IntelliJ IDEA Community Edition
- Visual Studio Code
- MySQL Workbench


#### Tested with: 

- Spring Test
- JUnit 5 & AssertJ
- Mockito
- Hamcrest
- H2 Database




## To-do 💡

- Add embedded Tomcat Server for running app with one line command
- Additional tests for task components in Spring container
- Support for sending user confirmation emails



## Screenshoots 🛣️

![Admin_panel](src/main/webapp/resources/image/first.png?raw=true "Home")

![Admin_panel](src/main/webapp/resources/image/second.png?raw=true "Play")

![Admin_panel](src/main/webapp/resources/image/third.png?raw=true "Panel")

![Admin_panel](src/main/webapp/resources/image/fourth.png?raw=true "Mgmt")
