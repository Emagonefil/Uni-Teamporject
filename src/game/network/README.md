# Test
**A simple sample to show how these work**
* ***serverAddres*** in **Port.java** needs to be change to **"localhost"** if you are testing
* **Entity** Class 
    * need to implements ***Serializable*** to be able to send through network
    * also need to have generators to be able to test the ***Server.broadcast***

***Just command the line 59 (hopefullly the only line with error) if you do not want to test the broadcast, it still works but sending the empty list to clients since no entities were added adn it does not afftect other methods***




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

//get all new moves of clients from last time getMoves()
List<String> moves = server.getMoves();

//send List<Entity>(could be changed later on) to all clients connected to this server
server.Broadcast(List<? extends Entity> list);
```




