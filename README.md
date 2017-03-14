
# SEC Project

Group 16

* António Freire, ist177969
* João Leite, ist177907
* João Tomázio, ist178039

---

### Project Structure

Maven projects:

* keystore: Create and manage client keys

* client: Client interface to communicate with server

* server: Server with database

* lib: Shared library code between other projects

---

### Description

Project description



### Install and run

* Install lib shared code

	```
	cd lib
	mvn install
	```

* Generate keystore to client

	```
	cd keystore
	mvn package exec:java
	```

	```
	...
	Enter username: example
	Enter password: 123456789
	...
	```

* Run in parallel

	* client

		```
		cd client
		mvn package exec:java
		```

		```
		# 1. Call init
		# 2. Login with username and password from keystore
		# 3. Put and/or get passwords
		```

	* server

		```
		cd server
		mvn package exec:java
		```
