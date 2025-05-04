# Dizplai Poll Application

This application is my submission for the Dizplai coding challenge, as part of their recruitment process. This 
repository contains both the frontend and backend.

The backend was written using Java 21, Spring Boot and Maven. The frontend was written using React, NodeJS and Vite.

## Running the application

### Prerequisites

You will need the following installed on your laptop to run the frontend and backend applications:

- Java 21
- Maven 
- NodeJS

### Running the backend

To run the backend, navigate to /backend and run:

``` mvn spring-boot:run ```

The backend will run on http://localhost:8080

To run the tests, use this command. This will run unit tests against the services, a DataJPA test against the 
PollRepository and an integration test class against the PollController.

```` mvn test ````


### Running the frontend

To run the frontend, navigate to /frontend on your terminal and run:

```` npm install ````

followed by

```` npm run dev ````

The frontend will run on http://localhost:5173

The tests can be ran with this command. Only the poll component is tested as a sample of how I would write the tests.

```` npm test ````

### H2 Database console

When the backend is running, you can access the H2 console and interact directly with the database at
http://localhost:8080/h2-console

Use the following details to connect (leave the password empty as this is not set):
````
DriverClass: org.h2.Driver
JDBC URL: jdbc:h2:mem:polldb
Username: sa
Password:
````


## Assumptions
 
### Active poll

The brief mentions that a user should be shown "The currently active poll" so an assumption had to be made to answer the
question: *What is an active poll?*
I considered using a Boolean flag for marking a poll as active/inactive, but I decided to add two date/time fields instead:
`activeFrom` and `activeTo`. A poll is active if the current date/time falls between these two dates. This means it is
possible for more than one poll to be active at a time, therefore the endpoint to get active polls returns a list.

The reason I avoided the Boolean is because, if a user wants to make a poll active on a specific date/time, then they have to
be online on that specific date/time to mark it as active. I thought it was likely that Dizplai pre-plan their polls (e.g.
to run at half time during football matches) so this gives the ability to schedule when a poll will be active.

### One answer per poll

The user can only select one answer for each poll. Clicking an option will submit a vote for that option. Whilst it may
be a future requirement to have polls that allow multiple selections, this has not been implemented and is not 
possible in this application.


### Multiple votes

Under this implementation, it is possible for a user to vote on a poll multiple times, simply by refreshing the page 
after voting and then voting again.
This is not ideal but was implemented this way due to limited time and nature of the task. 
If I had longer to implement this, this could be done with a login mechanism: Users would have to log in to vote, 
and only one vote is allowed per user. Alternatively, this could be done using cookies or limiting votes by IP address if
we wanted to allow non-registered users to vote but this is less secure and users can find work-arounds around this.



## Design Decisions

### Database

The backend uses an H2 in-memory database. H2 was chosen because it was fast & easy to set up and provides a database that
supports SQL.If this was an actual production application, I would use a RDBMS such as Oracle or SQL Server.