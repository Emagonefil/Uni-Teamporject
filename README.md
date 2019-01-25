# Server

```java
//generate new server (no parameter)
Server server = new Server();

//server starts to send everything's position to all clients connect to it
server.run();

//TODO still need to discuss how to handle the info that receives from clients
```



# Client

```java
//generate new client (no parameter)
Client client = new Client();

//run() method would start to receive the update information from server
//(only printing them, not integrate with ui yet)
//and also returns a ClientSender
ClientSender sender = client.run();

//ClientSender has a method send(String) which sends the parameter to server
//probably can be binded to the keylistener on ui
sender.send(String s);

```
