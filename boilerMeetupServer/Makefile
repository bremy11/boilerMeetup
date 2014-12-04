
all: makeServer makeClient testSql

makeServer: boilerMeetServer.java
	javac boilerMeetServer.java
	

makeClient: boilerMeetTestClient.java
	javac boilerMeetTestClient.java
	
client: makeClient
	 java boilerMeetTestClient
	 
testSql: testSql.java
	 javac testSql.java
	 
server: makeServer
	java boilerMeetServer
