import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * ConmanServer class instantiates a multithreaded server 
 * that accepts connections from more Clients and
 * allows them to communicate between each other.
 * @param
 */
public class ConmanServer extends Application {
    // attributes for gui
    private Stage stage;
    private Scene scene;
    private Button btnStart = new Button("Start");
    private TextArea tArea = new TextArea();

    //net attributes
    private ServerSocket sSocket;
    private static final int SERVER_PORT = 29072;

    //arraylist of object output streams
    private ArrayList<ObjectOutputStream> list_of_oos = new ArrayList<>();

    //counter for clients
    int clientIDCounter = 0;

    /**
     * Main method that sets up and launches
     * the whole program.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * A method that sets up the GUI of the server and 
     * starts the whole program. Called by 'launch(args)' in
     * the main method.
     * @param _stage
     */
    @Override
    public void start(Stage _stage) throws Exception {
        stage = _stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent wevt) {
                System.exit(0);
            }
        });

        VBox root = new VBox(8);

        tArea.setMinSize(400, 400);
        tArea.setWrapText(true);

        root.getChildren().addAll(btnStart, tArea);

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent avt) {
                tArea.appendText("Starting server\n");
                ServerThread sThread = new ServerThread();
                sThread.start();
                tArea.appendText("Server started!\n");
            }
        });

        scene = new Scene(root, 400, 400);
        // root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * ServerThread is an inner class that extends Thread. 
     * It opens the port and awaits new Clients to connect in
     * order to start the Client's thread.
     * @param
     */
    protected class ServerThread extends Thread {
        @Override
        public void run() {

            try {
                System.out.println("Opening port...");
                sSocket = new ServerSocket(SERVER_PORT);

                while (true) {
                    Socket cSocket = sSocket.accept();

                    ClientThread cThread = new ClientThread(cSocket);
                    cThread.start();
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
    }

    /**
     * ClientThread is a class that extends Thread.
     * It initializes the connection between Client and
     * the Server and accepts Objects that the Server 
     * sends back. In case of receiving an Object, it 
     * is processed accordingly and the result is sent 
     * back to the connected Clients.
     * @param
     */

    protected class ClientThread extends Thread {
        private Socket cSocket = null;
        private ObjectOutputStream oos = null;
        private ObjectInputStream ois = null;

        public ClientThread(Socket _cSocket) {
            this.cSocket = _cSocket;
        }

        @Override
        public void run() {
            try {
                this.ois = new ObjectInputStream(this.cSocket.getInputStream());
                this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());

                list_of_oos.add(this.oos);

                while (true) {
                    System.out.println("DOBIJEEEEE!! ::DDDDD");
                    Object obj = this.ois.readObject();
                    
                    if (obj instanceof String) {
                        String msg = (String) obj;
                        String[] arrayOfMessage = msg.split("@");
                       
                        if (arrayOfMessage.length == 2) {
                            switch (arrayOfMessage[0]) {
                                case "REGISTER":
                                    oos.writeObject(clientIDCounter);
                                    oos.flush();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run(){
                                            tArea.appendText(arrayOfMessage[1] +" has connected\n");
                                        }
                                    });
                                    clientIDCounter++;

                                    break;
                                case "CHAT":
                                    String chatMsg = arrayOfMessage[1];
                                    // has to be sent to all clients
                                    for (int i = 0; i < list_of_oos.size(); i++) {
                                       
                                        list_of_oos.get(i).writeObject(chatMsg);
                                        list_of_oos.get(i).flush();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run(){
                                                tArea.appendText(chatMsg +" has connected\n");
                                            }
                                        });
                                    }
                                    System.out.println(chatMsg);
                                    break;
                            }
                        }
                    } else if (obj instanceof ConmanStatus) {
                        System.out.println("DOBIJE StATUS LOL GET REKT");

                        ConmanStatus conStatus = (ConmanStatus) obj;

                        for (int i = 0; i < list_of_oos.size(); i++) {
                            // only needs to be sent to other
                            if (list_of_oos.get(i) != this.oos) {
                                list_of_oos.get(i).writeObject(conStatus);
                                list_of_oos.get(i).flush();
                            }
                        }
                    }

                }

            } catch (IOException e) {
                makeAlert(AlertType.ERROR, e.getMessage());
            } catch (ClassNotFoundException e) {
                makeAlert(AlertType.ERROR, e.getMessage());
            }
        }

        /**
         * Method for making alerts. Accepts AlertType and the message 
         * to be displayed (String).
         * @param type
         * @param msg
         */
        void makeAlert(AlertType type, String msg) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(type, msg);
                    alert.showAndWait();
                }
            });
        }
    }

}
