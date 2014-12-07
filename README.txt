Progress and Ideas:			Last Updated (12/07/14 - 5:25pm) by Rachel Gully

keep username and password as variables in the code (DONE)


Still need to consider:
	Query by event types (Save for Last)

In the JSON Object during communication w/ the app…
	We receive the command first
	Send the number of requests first


JSON Object Fields (Receiving):
	COMMAND
	id
	name
	position
	location
	description
	startTime
	endTime
	numAttendees

JSON Object Fields (Sending):
	NUMEVENTS
	id
	name
	position
	location
	description
	startTime
	endTime
	numAttendees

Requests:
	GET-ALL-EVENTS:   For refreshing all of the events
	GET-EVENT-INFO:   Getting the information of a specified event
	GET-CNT:	  Getting the total number of events
	ADD-EVENT:	  Add the specified event to the db
	DEL-EVENT:	  Delete the specified event from the db
	