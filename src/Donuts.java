import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.*;

//@ASSESME.INTENSITY:LOW

public class Donuts extends Pane {
    int x, y;
    Main m;
    ImageView donutView;
    Image img = new Image("/imgs/donut.png");

    BufferedImage mapImg;
    int color;

    // has to be in character
    // int score;
    boolean addPoints = false;

    /**
     * Constructor for class Donuts. Accepts the Main class as
     * a parameter in order to connect to the stage into which Donuts
     * are added.
     * @param _m
     */

    public Donuts(Main _m) {
        this.m = _m;
        donutView = new ImageView(img);
        this.getChildren().add(donutView);

        if(m.firstMap){
            try {
                mapImg = ImageIO.read(getClass().getResource("/imgs/Artboard1.png"));
                color = mapImg.getRGB(2, 2);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                mapImg = ImageIO.read(getClass().getResource("/imgs/Artboard2.png"));
                color = mapImg.getRGB(2, 2);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        
    }

    /**
     * Method for setting Donuts onto specified values
     * x and y inside the Main class.
     * @param x
     * @param y
     */

    void drawDonuts(int x, int y) {
        this.x = x;
        this.y = y;
        setTranslateX(x);
        setTranslateY(y);
        this.m.rootMain.getChildren().add(this);

    }

    /**
     * Method for handling collision with the Conman. In case of
     * a collision the ImageView of the Donut is removed and 
     * points are added to the Conman the Donut collided with.
     * When the last Donut is removed an alert is shown to mark
     * the end of the game.
     * @param
     */
    void onCollision() {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/imgs/donut.png"));
            // int color = img.getRGB(25, 3);

            int centerX1 = this.m.racer.x + 20;
            int centerY1 = this.m.racer.y + 20;

            int centerX2 = 0;
            int centerY2 = 0;

            if(m.isCoop){
                 centerX2 = this.m.other.x + 20;
                 centerY2 = this.m.other.y + 20;
            }
            

            if ((this.x <= centerX1 && this.y <= centerY1 && this.y + 20 >= centerY1 && this.x + 50 >= centerX1)
                    && !this.getChildren().isEmpty()) {

                String sounds = "src/sound/eating.mp3";
                String soundsFile = new File(sounds).toURI().toString();
                Media soundeffects = new Media(soundsFile);
                MediaPlayer mediaPlayer = new MediaPlayer(soundeffects);
                mediaPlayer.setVolume(Main.getEffectsVolume() / 100);
                mediaPlayer.play();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        getChildren().remove(donutView);
                        // m.racer.speed = m.racer.speed - 0.07;

                        m.racer.score += 100;

                        m.lblNumber1.setText("" + m.racer.score);
                        if(m.isCoop){
                            if (m.racer.score + m.other.score == m.DONUT_NUMBER * 100) {

                                m.racer.canGo = false;

                                m.animTimer.stop();

                                Alert alert = new Alert(AlertType.CONFIRMATION, "You have escaped.");
                                alert.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
                                alert.getButtonTypes().addAll(new ButtonType("Restart"), new ButtonType("Main Menu"));
                                alert.setHeaderText("Congratulations!");
                                alert.showAndWait();

                                if (alert.getResult().getText().equals("Restart")) {
                                    doRestart();

                                } else {
                                    m.mainStage.close();
                                }

                            }
                        } else {
                            if (m.racer.score == m.DONUT_NUMBER * 100) {

                                m.racer.canGo = false;

                                m.animTimer.stop();

                                Alert alert = new Alert(AlertType.CONFIRMATION, "You have escaped.");
                                alert.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
                                alert.getButtonTypes().addAll(new ButtonType("Restart"), new ButtonType("Main Menu"));
                                alert.setHeaderText("Congratulations!");
                                alert.showAndWait();

                                if (alert.getResult().getText().equals("Restart")) {
                                    doRestart();

                                } else {
                                    m.mainStage.close();
                                }

                            }
                        }

                        

                    }
                });
            }

            if ((this.x <= centerX2 && this.y <= centerY2 && this.y + 20 >= centerY2 && this.x + 50 >= centerX2)
                    && !this.getChildren().isEmpty()) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        getChildren().remove(donutView);

                        m.other.score += 100;

                        m.lblNumber2.setText("" + m.other.score);

                        if (m.racer.score + m.other.score == m.DONUT_NUMBER * 100) {

                            m.racer.canGo = false;

                            m.animTimer.stop();

                            Alert alert = new Alert(AlertType.CONFIRMATION, "You have escaped.");
                            alert.getButtonTypes().removeAll(ButtonType.OK, ButtonType.CANCEL);
                            alert.getButtonTypes().addAll(new ButtonType("Restart"), new ButtonType("Main Menu"));
                            alert.setHeaderText("Congratulations!");
                            alert.showAndWait();

                            if (alert.getResult().getText().equals("Restart")) {
                                doRestart();

                            } else {
                                m.mainStage.close();
                            }

                        }
                    }
                });

            }

        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();

        } catch (ArrayIndexOutOfBoundsException aiob) {
            //if caught do nothing
        }

    }

    /**
     * Method that is called in case of the player
     * restarting game intead of closing the window.
     * It locates which map and gamemode has been chosen, 
     * and restarts the program accordingly.
     * @param
     */

    void doRestart(){
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
    }
}
