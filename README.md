# Client

```java
//generate new client (no parameter)
Client client = new Client();

//return a ClientSender which can send things to server
ClientSender sender = client.getSender();

//ClientSender has a method send(String) which sends the parameter to server
//probably call this method when certain keys are pressed
sender.send(String s);

//the client starts to receive things from server
client.startReceiver(Receivable)

```

# Receivable(Interface)

```java
public void receive(List list);
//the class implements this interface only need to override receive method
//I would pass the list as parameter in this method
//this method would be automatic called everytime receives new message from server
//you would probably do renderer using the list here
```


# Server

```java
//generate new server put the class wants to send in <>
Server server = new Server<Class>();

//get all new moves of clients from last time getMoves()
List<String> moves = server.getMoves();

//send List to all clients connected to this server
server.Broadcast(List list);
```




