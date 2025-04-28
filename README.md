# Poll Service






## Design Decisions

- In the VoteEntity, I have added a SelectedOption and a SelectedOption ID. I wanted the SelectedOption so that a foreign
key constraint would be created. But when casting a vote, there is no need to pass through the entire Option object; an Option ID will suffice.
- In PollEntity, for the list of options, I considered using an @ElementCollection but didn't want to use the string values to link a vote to an option. As implemented, they are linked by an OptionID.
