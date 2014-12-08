Progress and Ideas:			Last Updated by Rachel Gully

keep username and password as variables in the code (DONE)


Still need to consider:
	Query by event types (Save for Last)

In the JSON Object during communication w/ the app…(Server to app):
	We receive the command first
	Send the number of requests first


JSON Object Fields (Receiving):
	command		(String)
	id	 	(int)
	name		(String)
	position	(String)
	location	(String)
	description	(String)
	startTime	(String)	//format is:  YYYY-MM-DD HH:MM:SS
	endTime		(String)	//format is:  YYYY-MM-DD HH:MM:SS
	numAttendees	(int)

JSON Object Fields (Sending):
	numEvents	(int)
	id		(int)
	name		(String)
	position	(String)
	location	(String)
	description	(String)
	startTime	(String)	//format is:  YYYY-MM-DD HH:MM:SS
	endTime		(String)	//format is:  YYYY-MM-DD HH:MM:SS
	numAttendees	(int)

fields to add/delete to/from the db:
	id		(int)
	name		(String)
	position	(String)
	location	(String)
	description	(String)
	startTime	(String)	//format is:  YYYY-MM-DD HH:MM:SS
	endTime		(String)	//format is:  YYYY-MM-DD HH:MM:SS
	numAttendees	(int)

Requests:
	GET-ALL-EVENTS:   For refreshing all of the events
	GET-EVENT-INFO:   Getting the information of a specified event with (using the id field)
	GET-CNT:	  Getting the total number of events
	ADD-EVENT:	  Add the specified event to the db
	DEL-EVENT:	  Delete the specified event from the db
	