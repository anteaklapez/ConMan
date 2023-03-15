/**
 * @ASSESME.INTENSITY:LOW
 */

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Super class to any other entity in the game 
 * (Conman and Ghost). 
 * It holds all attributes that are needed for 
 * both classes to function.
 * @param
 */

public class Character extends Pane {
    public final int CONMAN_WIDTH = 0;
    public final int CONMAN_HEIGHT = 0;

    public int x,y;
    public double speed;
    boolean up_press, down_press, left_press, right_press;

    public Image imgUP_1, imgUP_2, imgUP_3, imgUP_4, 
        imgDOWN_1, imgDOWN_2, imgDOWN_3, imgDOWN_4,
        imgLEFT_1, imgLEFT_2, imgLEFT_3, imgLEFT_4, 
        imgRIGHT_1, imgRIGHT_2, imgRIGHT_3, imgRIGHT_4;
    
    //direction 
    public String facing;

    //for changing images from 1 to 4 (for each direction)
    public int img_num = 0;
    public int animation_num = 1;
}
