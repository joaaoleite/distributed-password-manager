
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

Our Password Manager is divided in 4 major projects: The Client, the Server, the KeyStore and the Lib. It is a RESTful system, so the Client and the Server communicate over HTTP, exchanging JSON Objects. For this process, we are using the Unirest Framework API in the Client-side to send requests, and the Spark Framework API on the Server-side to answer them. Both client and server depend on the Lib Project, that is a library that contains cryptographic methods and other shared code between the two sides of the system, and on the Keystore Project, an independent Key Manager, that generate and provides the client and the server a pair of Public/Private Keys.



### Install and run

* Install lib shared code

	```
	cd lib
	mvn install
	```

* Generate keystore to Tests

	```
	cd keystore
	mvn compile exec:java
	```

	```
	...
	Enter username: username
	Enter password: password
	...
	```
* Generate keystore to client

	```
	cd keystore
	mvn compile exec:java
	```

	```
	...
	Enter username: example
	Enter password: 123456789
	...
	```

* Run in parallel

	* start 4 server replicas

		```
		cd server
		source start.sh 4

	* client

	```
		cd client
		mvn compile exec:java -Dexec.args="localhost 8080 4"
		```

		```
		# 1. Call init
		# 2. Login with username and password from keystore
		# 3. Register
		# 4. Put and/or get passwords
		```
	```


### Tests

* Run mvn tests on Lib

```
cd lib
mvn test
```

	* List of available tests:

	```
	AESTest
	DigitalSignatureTest
	HashTest
	HMACTest
	NouncesTest
	RSANoPaddingTest
	RSATest
	```

* Run mvn tests on Client

> create server / server KeyStore
> create username / password KeyStore

```
mvn test
```

* Run a specific server

```
mvn test -Dtest=ReplayAttackTest
```

	* List of available tests:

	```
	ClientTest
	ConfidenceTest
	ManInTheMiddleTest
	ReplayAttackTest
	ByzantineTest
	```
