# Client

```java
//generate new client (no parameter)
Client client = new Client();

//return a ClientSender which can send things to server
ClientSender sender = client.getSender();

//send method of ClientSender sends the str to address
sender.send(String address, String str);

//the client starts to receive things from server
client.startReceiver(Receivable);

//close the current Receiver
client.closeReceiver();

//allows client change the ip of server it is sending to
client.changeServerIP();
```

# Receivable(Interface)

```java
public void receive(Object obj);
//the class implements this interface only need to override receive method
//I would pass the object received as parameter in this method
//this method would be automatic called everytime receives new message from server
```


# Server

```java
//generate new server(receiver starts automatically)
Server server = new Server();

//get all new moves of clients from last time getMoves()
List<String> moves = server.getMoves();

//send Object to all clients connected to this server
server.Broadcast(Object obj);
```




