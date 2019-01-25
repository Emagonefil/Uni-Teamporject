# Client

```java
//generate new client (class that implements the interface Receivable)
Client client = new Client(Receivalbe);

//return a ClientSender which can send things to server
ClientSender sender = client.getSender();

//ClientSender has a method send(String) which sends the parameter to server
//probably can be binded to the keylistener on ui
sender.send(String s);

```

# Receivable(Interface)

```java
public void receive(List<Entity> list);
//the class implements this interface only need to override receive method
//I would pass the list as parameter in this method
//this method would be automatic called everytime receives new message from server
//you would probably do renderer using the list here
```


# Server

```java
//generate new server (no parameter)
Server server = new Server();
//immediately after generating 
//server starts to send all entities' position to all clients connect to it

//get all new moves of clients from last time getMoves()
List<String> moves = server.getMoves();

//send List<Entity>(could be changed later on) to all clients connected to this server
server.Broadcast(List<? extends Entity> list);
```




