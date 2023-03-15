import java.io.Serializable;

/**
 * Class ConmanStatus sends information about the
 * status of each Conman to the Server.
 * @param
 */

public class ConmanStatus implements Serializable{
    private int ID;
    private int x;
    private int y;
    private static final long VERSION = 1L;
    

    /**
     * Parameterized constructor of ConmanStatus which accepts
     * the unique ID gotten from the server, position x, and
     * position y of the Conman.
     * @param _id
     * @param pos_x
     * @param pos_y
     */
    public ConmanStatus(int _id, int pos_x, int pos_y){
        this.ID = _id;
        this.x = pos_x;
        this.y = pos_y;
    }

    /**
     * Getter for the ID. Returns an Integer.
     * @return
     */
    int getID(){
        return this.ID;
    }

    /**
     * Getter for position X. Returns an Integer.
     * @return
     */
    int getX(){
        return this.x;
    }

    /**
     * Getter for position Y. Returns an Integer.
     * @return
     */
    int getY(){
        return this.y;
    }

    /**
     * Setter for updating the value of the ID.
     * @param id
     */
    void setID(int id){
        this.ID = id;
    }

    /**
     * Setter for updating the status of a Conman.
     * @param pos_x
     * @param pos_y
     */
    void setStatus(int pos_x, int pos_y){
        this.x = pos_x;
        this.y = pos_y;
    }
}
