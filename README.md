The game developed by the GoldenAxe team.

In order to run the game using Eclipse:
1.  Pull from the git repo
2.  Create an eclipse project from these files
3.  Go to the project right click and configure buildpath
4.  Under the sources tab add the 'Resources' folder
5.  Go to the libraries tab and add: all libraries in the 'goldenaxe/lib' folder, JavaFX and JDBC
6.  Create a new run configuration for the Main file
7.  Set the following JVM arguments '--module-path /PATH/TO/javafx-sdk-11.0.2/lib --add-modules=javafx.controls,javafx.fxml -Djava.net.preferIPv4Stack=true'
8.  Run the game.