
//@ASSESME.INTENSITY:LOW
import javax.imageio.ImageIO;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Conman class extends it's super Character. It holds all the 
 * information about the Conman - it's position, collision status, 
 * score, animation, etc. 
 * @param
 */

public class Conman extends Character {
    Main m;
    Ghost ghost;
    Soap soap;
    ImageView imgView;
    AnimationTimer timer;
    Timer loopUpdate = null;
    TimerTask task;

    String name;

    boolean noCollisionLeft = true;
    boolean noCollisionRight = true;
    boolean noCollisionUp = true;
    boolean noCollisionDown = true;
    boolean soapCollision = false;

    boolean canGo = true;
    int i = 0;

    int color;
    BufferedImage img;

    Thread gamer;
    int soap_int = 0;
    int score = 0;

    /**
     * Parameterized constructor for Conman which accepts 
     * the Main it is connected to and a String as it's name. 
     * It sets position automatically and the initial direction 
     * is towards right. It loads all images that will be needed 
     * in the session.
     * @param _m
     * @param _name
     */
    public Conman(Main _m, String _name) {
        this.m = _m;
        this.name = _name;

        x = 675;
        y = 369;
        speed = 1;

        facing = "right";
        loadImages();

        imgView = new ImageView(imgRIGHT_1);
        this.getChildren().add(imgView);
    }

    /**
     * Method for setting the x and y position of the Conman.
     * @param _x
     * @param _y
     */
    void setPosition(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    /**
     * Method for getting the position X. Returns an Integer.
     * @return
     */
    Integer getX() {
        return this.x;
    }

    /**
     * Method for getting the position Y. Returns an Integer.
     * @return
     */
    Integer getY() {
        return this.y;
    }

    /**
     * Method for preloading the images for collision and images 
     * for the animation of the Conman.
     * @param
     */
    void loadImages() {

        if(m.firstMap){
            try {
                img = ImageIO.read(getClass().getResource("/imgs/Artboard1.png"));
                color = img.getRGB(2, 2);
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        } else if(m.secondMap){
            try {
                img = ImageIO.read(getClass().getResource("/imgs/Artboard2.png"));
                color = img.getRGB(3, 3);
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            }

        }

        if(this.name.equals("Player1")){
            imgRIGHT_1 = new Image("/imgs/pac1.png");
            imgRIGHT_2 = new Image("/imgs/pac2.png");
            imgRIGHT_3 = new Image("/imgs/pac3.png");
            imgRIGHT_4 = new Image("/imgs/pac4.png");

            imgLEFT_1 = new Image("/imgs/pac1left.png");
            imgLEFT_2 = new Image("/imgs/pac2left.png");
            imgLEFT_3 = new Image("/imgs/pac3left.png");
            imgLEFT_4 = new Image("/imgs/pac4left.png");

            imgUP_1 = new Image("/imgs/pac1up.png");
            imgUP_2 = new Image("/imgs/pac2up.png");
            imgUP_3 = new Image("/imgs/pac3up.png");
            imgUP_4 = new Image("/imgs/pac4up.png");

            imgDOWN_1 = new Image("/imgs/pac1down.png");
            imgDOWN_2 = new Image("/imgs/pac2down.png");
            imgDOWN_3 = new Image("/imgs/pac3down.png");
            imgDOWN_4 = new Image("/imgs/pac4down.png");

        } else if(this.name.equals("Player2")){

            imgRIGHT_1 = new Image("/imgs/Second1.png");
            imgRIGHT_2 = new Image("/imgs/Second2.png");
            imgRIGHT_3 = new Image("/imgs/Second3.png");
            imgRIGHT_4 = new Image("/imgs/Second4.png");

            imgLEFT_1 = new Image("/imgs/SecondLeft1.png");
            imgLEFT_2 = new Image("/imgs/SecondLeft2.png");
            imgLEFT_3 = new Image("/imgs/SecondLeft3.png");
            imgLEFT_4 = new Image("/imgs/SecondLeft4.png");

            imgUP_1 = new Image("/imgs/SecondUp1.png");
            imgUP_2 = new Image("/imgs/SecondUp2.png");
            imgUP_3 = new Image("/imgs/SecondUp3.png");
            imgUP_4 = new Image("/imgs/SecondUp4.png");

            imgDOWN_1 = new Image("/imgs/SecondDown1.png");
            imgDOWN_2 = new Image("/imgs/SecondDown2.png");
            imgDOWN_3 = new Image("/imgs/SecondDown3.png");
            imgDOWN_4 = new Image("/imgs/SecondDown4.png");
        }
       

    }

    /**
     * Method that updates the position of the Conman, 
     * checks the collision with each Ghost, checks the 
     * collision with soap and enables movement for Conman.
     * @param
     */
    void update() {

        synchronized (m.animTimer) {
            this.setTranslateX(x);
            this.setTranslateY(y);

            this.checkCollision();
            checkCollisionGhost(this.m.ghost1);
            checkCollisionGhost(this.m.ghost2);
            checkCollisionGhost(this.m.ghost3);
            checkCollisionSoap(this.m.soap);

            // need an if statement for only if any key is pressed

            if (this.up_press == true || this.down_press == true ||
                    this.left_press == true || this.right_press == true) {
                synchronized (m.animTimer) {
                    if (this.up_press == true && noCollisionUp == true) {
                        facing = "up";
                        y -= speed;

                    }
                }
                if (this.down_press == true && noCollisionDown == true) {
                    facing = "down";
                    y += speed;
                }

                if (this.left_press == true && noCollisionLeft == true) {
                    facing = "left";
                    x -= speed;
                }

                if (this.right_press == true && noCollisionRight == true) {
                    facing = "right";
                    x += speed;
                }

            }

        }

    }

    /**
     * Method that animates the Conman. It increases an Integer called 
     * img_num which decides how fast the mouth will open and close. There 
     * are in total 4 different images for mouth closing represented by the 
     * Integer animation_num which after img_num is greater than specified number 
     * changes from 1 to 2, from 2 to 3 and so on in a loop. Depending on the 
     * number 1-4, an image is set accordingly and shown.
     * @param
     */
    void animate() {
        synchronized (m.animTimer) {

            img_num++;

            // this decides how fast the mouth will
            // open and close
            if (img_num > 12) {
                if (animation_num == 1)
                    animation_num = 2;
                else if (animation_num == 2)
                    animation_num = 3;
                else if (animation_num == 3)
                    animation_num = 4;
                else if (animation_num == 4)
                    animation_num = 1;

                // resetting
                img_num = 0;
            }
            Image img = null;
            switch (facing) {
                case "up":
                    if (animation_num == 1)
                        img = imgUP_1;
                    if (animation_num == 2)
                        img = imgUP_2;
                    if (animation_num == 3)
                        img = imgUP_3;
                    if (animation_num == 4)
                        img = imgUP_4;

                    break;

                case "down":
                    if (animation_num == 1)
                        img = imgDOWN_1;
                    if (animation_num == 2)
                        img = imgDOWN_2;
                    if (animation_num == 3)
                        img = imgDOWN_3;
                    if (animation_num == 4)
                        img = imgDOWN_4;

                    break;

                case "left":
                    if (animation_num == 1)
                        img = imgLEFT_1;
                    if (animation_num == 2)
                        img = imgLEFT_2;
                    if (animation_num == 3)
                        img = imgLEFT_3;
                    if (animation_num == 4)
                        img = imgLEFT_4;

                    break;
                case "right":
                    if (animation_num == 1)
                        img = imgRIGHT_1;
                    if (animation_num == 2)
                        img = imgRIGHT_2;
                    if (animation_num == 3)
                        img = imgRIGHT_3;
                    if (animation_num == 4)
                        img = imgRIGHT_4;

                    break;
            }
            final Image finImage = img;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getChildren().remove(imgView);
                    synchronized (m.animTimer) {
                        imgView = new ImageView(finImage);
                    }

                    getChildren().add(imgView);
                }
            });

        }
    }

    /**
     * Method for checking the collision with the map. Four points of 
     * the Conman are checked for intersection with the red part of the 
     * map. If collision is happening, the Conman may move in all other 
     * directions, except where the collision is happening. 
     * (Additional: wherever you see addition or substitution inside 
     * if statements, it is because there was no other way to give the Conman 
     * the ability to move in all other directions EXCEPT the one it moved prior 
     * to that.)
     * @param
     */

    void checkCollision() {
        try {
            /**
             * LEFT SIDE:
             * top left point (x, y) and bottom left (x, y + height)
             * 
             * RIGHT SIDE:
             * top right point (x + width, y) and bottom right (x + width, y + height)
             * 
             * TOP SIDE:
             * top right point (x + width, y) and top left point (x, y)
             * 
             * BOTTOM SIDE:
             * bottom right (x + width, y + height) and bottom left point (x, y + height)
             * 
             */

            // X calculations for 4 of points
            int leftX = (int) this.getTranslateX();
            int rightX = (int) (this.getTranslateX() + 30);
            int topY = (int) this.getTranslateY();
            int bottomY = (int) (this.getTranslateY() + 30);

            //collision left
            if (img.getRGB(leftX - 5, topY) == color || img.getRGB(leftX - 5, bottomY) == color) {
                noCollisionLeft = false;
            } else {
                noCollisionLeft = true;
            }

            //collision right
            if (img.getRGB(rightX + 5, topY) == color || img.getRGB(rightX + 5, bottomY) == color) {
                noCollisionRight = false;
            } else {
                noCollisionRight = true;
            }

            //collision down
            if (img.getRGB(leftX, bottomY + 7) == color || img.getRGB(rightX, bottomY + 7) == color) {
                noCollisionDown = false;
            } else {
                noCollisionDown = true;
            }

            //collision up
            if (img.getRGB(leftX, topY - 8) == color || img.getRGB(rightX, topY - 8) == color) {
                noCollisionUp = false;
            } else {
                noCollisionUp = true;
            }

        } catch (ArrayIndexOutOfBoundsException aie) {
            if (this.getTranslateX() < 20)
                noCollisionLeft = false;
            else
                noCollisionRight = false;

        }
    }

    /**
     * Method for checking the collision with a specified Ghost. Only the 
     * center point of the Conman is taken and checked to see whether it's 
     * position is between four points of the Ghost. If yes Game Over, if not 
     * game continues.
     * @param _ghost
     */
    void checkCollisionGhost(Ghost _ghost) {
        ghost = _ghost;
        // copy pasted from donuts
        double centerX = this.x + 23;
        double centerY = this.y + 23;

        if ((this.ghost.x <= centerX && this.ghost.y <= centerY && this.ghost.y + 50 >= centerY
                && this.ghost.x + 20 >= centerX)) {
            String sounds = "src/sound/die.mp3";
            String soundsFile = new File(sounds).toURI().toString();
            Media soundeffects = new Media(soundsFile);
            MediaPlayer mediaPlayer = new MediaPlayer(soundeffects);
            mediaPlayer.setVolume(Main.getEffectsVolume() / 100);
            mediaPlayer.play();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    m.racer.canGo = false;

                    Alert alert = new Alert(AlertType.CONFIRMATION, "You have not escaped.");
                    alert.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
                    alert.getButtonTypes().addAll(new ButtonType("Restart"), new ButtonType("Main Menu"));
                    alert.setHeaderText("LOSER!");
                    alert.showAndWait();

                    if (alert.getResult().getText().equals("Restart")) {
                        if(m.firstMap && m.isCoop){
                            m.mainStage.close();
                            Main m1 = new Main();
                            m1.isCoop = true;
                            m1.secondMap = false;
                            m1.firstMap = true;
                            m1.setupStage();
                            m.mainStage = m1.mainStage;
                            m.mainStage.setScene(m1.scene);
                            m.mainStage.show();

                        } else if (m.firstMap && m.isSingle){
                            m.mainStage.close();
                            Main m1 = new Main();
                            m1.isSingle = true;
                            m1.secondMap = false;
                            m1.firstMap = true;
                            m1.setupStage();
                            m.mainStage = m1.mainStage;
                            m.mainStage.setScene(m1.scene);
                            m.mainStage.show();

                        } else if (m.secondMap && m.isCoop){
                            m.mainStage.close();
                            Main m1 = new Main();
                            m1.isCoop = true;
                            m1.secondMap = true;
                            m1.firstMap = false;
                            m1.setupStage();
                            m.mainStage = m1.mainStage;
                            m.mainStage.setScene(m1.scene);
                            m.mainStage.show();
                            
                        } else if (m.secondMap && m.isSingle){
                            m.mainStage.close();
                            Main m1 = new Main();
                            m1.isSingle = true;
                            m1.secondMap = true;
                            m1.firstMap = false;
                            m1.setupStage();
                            m.mainStage = m1.mainStage;
                            m.mainStage.setScene(m1.scene);
                            m.mainStage.show();
                            
                        }
                    } else {
                        m.mainStage.close();
                    }
                }
            });

        }
    }

    /**
     * Method for checking the collision with specified Soap. 
     * The same method as for Ghost collision is applied, except, 
     * this one gives the Conman a boost. The Conman slips which 
     * increases it's speed, but at the same time makes it more 
     * vulnerable since the slip could take the Conman right into 
     * the Ghost.
     * @param _soap
     */
    void checkCollisionSoap(Soap _soap) {
        soap = _soap;

        //Conman's central x and y
        double centerX = this.x + 23;
        double centerY = this.y + 23;

        //determines how long the effect lasts
        if (soap_int >= 20 && speed == 10) {
            speed = 1;
            soap_int = 0;
        }

        //actual collision
        if ((this.soap.x <= centerX && this.soap.y <= centerY && this.soap.y + 38 >= centerY
                && this.soap.x + 50 >= centerX)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    speed = 10;
                    soap.getChildren().remove(soap.soapView);

                }
            });

            // on collision plays a sound
            String sounds = "src/sound/wush_soap.mp3";
            String soundsFile = new File(sounds).toURI().toString();
            Media soundeffects = new Media(soundsFile);
            MediaPlayer mediaPlayer = new MediaPlayer(soundeffects);
            mediaPlayer.setVolume(Main.getEffectsVolume() / 100);
            mediaPlayer.play();
        }

        soap_int++;
    }
}
