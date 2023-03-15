
import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Soap class holds the information about the
 * booster - soap. Conman slips on it which either
 * helps or makes the Conman get caught.
 * 
 * @param
 */
public class Soap extends Pane {
    Main m;

    ImageView soapView;

    Thread soapThread = null;

    AnimationTimer timer = null;

    java.util.Timer setTimer = null;

    BufferedImage buffImage = null;
    int color = 0;

    long currentTime;
    long startTime;

    int x, y;

    /**
     * Parameterized constructor for Soap which accepts
     * the Main in which it will be displayed.
     * 
     * @param _m
     */
    public Soap(Main _m) {
        this.m = _m;
        soapView = new ImageView(new Image("/imgs/soap.png"));

        startTime = System.nanoTime();

        // preloading the collision picture depending
        // on chosen map
        if (m.firstMap) {
            try {
                buffImage = ImageIO.read(getClass().getResource("/imgs/Artboard1.png"));
                color = buffImage.getRGB(20, 130); // white!!

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                buffImage = ImageIO.read(getClass().getResource("/imgs/Artboard2.png"));
                color = buffImage.getRGB(95, 80); // white!!

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Method that sets Soap on specified position
     * x and y. It uses a timer to loop this process and
     * see whether it is ready to be positioned elsewhere.
     * 
     * @param pos_x
     * @param pos_y
     */
    void setSoap(int pos_x, int pos_y) {
        this.x = pos_x;
        this.y = pos_y;
        setTimer = new Timer();

        int minX = 20;
        int maxX = 1330;
        int minY = 120;
        int maxY = 700;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if (getChildren().isEmpty()) {
                            x = minX + (int) (Math.random() * ((maxX - minX) + 1));
                            y = minY + (int) (Math.random() * ((maxY - minY) + 1));

                            if (buffImage.getRGB(x, y) == color && buffImage.getRGB(x + 50, y + 38) == color) {
                                setTranslateX(x);
                                setTranslateY(y);
                                getChildren().add(soapView);
                            }

                        }
                    }
                });

            }
        };
        setTimer.schedule(task, 10000, 5000);
    }
}
