# Poll Service

This service is the backend for the Dizplai coding challenge where I was asked to develop an application to allow users
to vote on polls. 


## Assumptions
 
### Active poll

The brief mentions that a user should be shown "The most active poll" so I needed to make an assumption to implement this.
A poll has two additional fields: `activeFrom` and `activeTo` and a poll is considered active if the current date/time
falls in between these dates.

### Multiple votes

Under this implementation, it is possible for a user to vote on a poll multiple times, simply by refreshing the page after voting.
This is not ideal but it is this way due to limited time. If I had longer to implement this done, this could be done
with a login mechanism: Users would have to log in to vote, and only one vote is allowed per user. Alternatively, this 
could be done using cookies or limiting votes by IP address.



## Design Decisions



- In the VoteEntity, I have added a SelectedOption and a SelectedOption ID. I wanted the SelectedOption so that a foreign
key constraint would be created. But when casting a vote, there is no need to pass through the entire Option object; an Option ID will suffice.
- In PollEntity, for the list of options, I considered using an @ElementCollection but didn't want to use the string values to link a vote to an option. As implemented, they are linked by an OptionID.
