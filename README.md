# netty-playground
just trying things out with netty

## running the app
* go inside CommandServer folder and run `mvn spring-boot:run`
* connect to server via telnet (example: `telnet localhost 8180`)
  * default port is 8180
   
  
## available commands
* `sumar <number> <number>`
  * returns the sum of both numbers
* `fibonacci <number>`
  * returns the fibonacci sequence number
* `fortune`
  * returns random fortune text, similar to unix fortune command
* `echo <text`
  * Echoes text
* `chau`
  * Prints good-bye message and closes connection
* `var set <var name> <var value>`
  * sets var
* `var get <var name>`
  * retrieves var value
  
## other stuff
* when connection is idle for some time server sends some text
  * just trying out IdleStateEvent handling.
* Welcome message handler that removes itself from pipeline after printing welcome message.
* Prompt printer handler.
