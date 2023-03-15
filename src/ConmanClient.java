import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * ConmanClient class initializes the Client that 
 * is to be connected to the Server. 
 * @param
 */

public class ConmanClient {

    String name;
    String host;
    static final int SERVER_PORT = 29072;
    Socket conSocket = null;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    // LoadingClass load;
    Main m;

    Conman conman1 = null;
    Conman conman2 = null;

    ConmanStatus constat1 = null;
    ConmanStatus constat2 = null;

    int currentID = -1;

    /**
     * Parameterized constructor for ConmanClient class that accepts 
     * a String for the server host, a Main class, and another String 
     * as a name.
     * @param _host
     * @param _m1
     * @param _name
     */
    public ConmanClient(String _host, Main _m1, String _name) {
        this.host = _host;
        m = _m1;
        this.name = _name;

    }

    /**
     * Method for starting the Timer which is supposed to 
     * constantly send the ConmanStatus to the Server in order 
     * to update the position of each Conman on each screen.
     * @param
     */
    void startTimer() {
        Timer time = new Timer();

        time.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (time) {
                    // not working!

                }

            }
        }, 1, 1);
    }

    /**
     * ClientThread class that extends Thread. Accepts 
     * the output from the Server and displays it inside 
     * TextArea of chat in case of a String being received. 
     * As for receiving an Object, it sets the position of the 
     * gotten Object into the stage of every player except for 
     * the one that sent it.
     * @param
     */
    class ClientThread extends Thread {
        @Override
        public void run() {

            try {
                while (true) {
                    Object obj = ois.readObject();

                    if (obj instanceof String) {
                        String msg = (String) obj;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                m.taChat.appendText(msg + "\n");
                            }
                        });
                    }

                    if (obj instanceof ConmanStatus) {
                        ConmanStatus conStatus = (ConmanStatus) obj;

                        if (conStatus.getID() != currentID) {
                            switch (conStatus.getID()) {
                                case 0:
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println(conStatus.getX() + "--- 1");
                                            conman2.setTranslateX(conStatus.getX());
                                            conman2.setTranslateY(conStatus.getY());
                                        }
                                    });
                                    break;

                                case 1:
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println(conStatus.getX() + "--- 2");
                                            conman1.setTranslateX(conStatus.getX());
                                            conman1.setTranslateY(conStatus.getY());
                                        }
                                    });
                                    break;
                            }
                        }
                    } else {
                        return;
                    }

                }

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * Method for initializing ObjectOutputStream, ObjectInputStream, 
     * and a Socket which accepts the host and the port of the Server. 
     * Registers the Client, sets their unique ID and, finally, initializes 
     * and starts the ClientThread which will start accepting the packages 
     * sent from the Server.
     * @param
     */
    void connect() {
        try {
            this.conSocket = new Socket(this.host, SERVER_PORT);
            this.oos = new ObjectOutputStream(this.conSocket.getOutputStream());
            this.ois = new ObjectInputStream(this.conSocket.getInputStream());

            this.oos.writeObject("REGISTER@" + this.name);
            this.oos.flush();

            this.currentID = (Integer) this.ois.readObject();

            switch (currentID) {
                case 0:
                    conman1 = this.m.racer;

                    break;
                case 1:
                    conman2 = this.m.racer;

                    break;
            }

            ClientThread cThread = new ClientThread();
            cThread.start();

            m.mainStage.show();
        } catch (UnknownHostException uhe){
            makeAlert(AlertType.ERROR, uhe.getMessage());
        
        } catch (IOException ioe) {
            makeAlert(AlertType.ERROR, ioe.getMessage());
        } catch (ClassNotFoundException e) {
            makeAlert(AlertType.ERROR, e.getMessage());
        }
        

    }

    /**
     * Method that sends a position of an Object, in this case, ConmanStatus.
     * @param pos_x
     * @param pos_y
     */
    void sendConmanStatus(int _id, int pos_x, int pos_y) {

        if (constat1.getID() == _id) {

            constat1.setStatus(pos_x, pos_y);

            try {
                oos.writeObject(constat1);
                oos.flush();
            } catch (IOException ioe) {
                makeAlert(AlertType.ERROR, ioe.getMessage());
            }

        } else {
            constat2.setStatus(pos_x, pos_y);

            try {
                oos.writeObject(constat2);
                oos.flush();
            } catch (IOException ioe) {
                makeAlert(AlertType.ERROR, ioe.getMessage());
            }

        }

    }

    /**
     * Method that accepts the Main where a Conman 
     * will be set.
     * @param m
     * @param c
     */
    void drawBoth(Main m, Conman c) {
        m.rootMain.getChildren().add(c);
    }

    /**
     * Method that accepts the Main where a Conman 
     * will be removed.
     * @param m
     * @param c
     */
    void remove(Main m, Conman c) {
        m.rootMain.getChildren().remove(c);
    }

    /**
     * Method that send the message of Clients.
     * @param
     */
    void sendMessage() {
        try {
            this.oos.writeObject("CHAT@" + this.name + currentID + ": " + m.tfChat.getText().toString());
            this.oos.flush();

            m.tfChat.clear();

        } catch (IOException ioe) {
            makeAlert(AlertType.ERROR, ioe.getMessage());
        }
    }


    /**
    * Method for making alerts. Accepts AlertType and the message 
    * to be displayed (String).
    * @param type
    * @param msg
    */
    void makeAlert(AlertType type, String msg) {
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }

}
