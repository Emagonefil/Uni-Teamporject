
# Clash of Tanks
*The game developed by the GoldenAxe team*

A top-down view tank game. Be the last remaining tank to **win** the game.

### Run the game using Eclipse:
1.  Clone the git repository
2.  Create an eclipse project from these files
3.  Go to the project right click and configure buildpath
4.  Under the sources tab add the 'Resources' folder
5.  Go to the libraries tab and add: all libraries in the 'goldenaxe/lib' folder, JavaFX and JDBC
6.  Create a new run configuration for the Main file
7.  Set the following JVM arguments '--module-path /PATH/TO/javafx-sdk-11.0.2/lib --add-modules=javafx.controls,javafx.fxml -Djava.net.preferIPv4Stack=true'
8.  Run the game.

### Run the game using IntelliJ:
1. Make sure you have Java 11 and JavaFX installed
2. Clone the git repository
3. Create an IntelliJ project from the goldenaxe folder
4. Navigate to File > Project Structure > Libraries and 
make sure you add all the libraries from the lib folder,
5. Include the JavaFX lib folder to the libraries
6. Create a new run configuration for the Main class
7. Navigate to Run  > Edit Configurations > Main run configuration (the one you created)
and add the following to the VM Options field:

    ###### For Mac/Linux users:
    --module-path /path/to/javafx-sdk-11.0.2/lib --add-modules=javafx.controls,javafx.fxml
    (where /path/to is the path where you installed JavaFX)
    ###### For Windows users: 
    --module-path "\path\to\javafx-sdk-11.0.2\lib" --add-modules=javafx.controls,javafx.fxml
    (where \path\to is the path where you installed JavaFX)
    
8. Run the game

*To play a multiplayer game, users must be connected to the same network