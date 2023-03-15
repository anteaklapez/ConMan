//@ASSESME.INTENSITY:LOW

import javafx.scene.image.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Ghost class that extends it's super Character. It holds all
 * of the information about the Ghost, such as it's position,
 * collision status, and images.
 * 
 * @param
 */
public class Ghost extends Character {
    Main m;
    ImageView imgView;

    int i = 0;

    boolean goUp = false;
    boolean goDown = false;
    boolean goLeft = false;
    boolean goRight = false;

    double random = 0;

    int leftX;
    int rightX;
    int topY;
    int bottomY;

    BufferedImage img;
    int color;

    /**
     * Parameterized constructor for Ghost class that accepts the
     * initial position x and y, path for the image and Main to which
     * it is meant to be connected to.
     * 
     * @param _m
     * @param pos_x
     * @param pos_y
     * @param img_path
     */
    public Ghost(Main _m, int pos_x, int pos_y, String img_path) {
        this.m = _m;

        x = pos_x;
        y = pos_y;

        speed = 1;

        imgView = new ImageView(new Image(img_path));

        this.getChildren().add(imgView);

        // preloading the collision picture depending
        // on chosen map
        if (m.firstMap) {
            try {
                img = ImageIO.read(getClass().getResource("/imgs/Artboard1.png"));
                color = img.getRGB(3, 3);
            } catch (IOException e) {
                System.out.println("IN THE GHOST CONSTRUCTOR");
            }

        } else {
            try {
                img = ImageIO.read(getClass().getResource("/imgs/Artboard2.png"));
                color = img.getRGB(3, 3);
            } catch (IOException e) {
                System.out.println("IN THE GHOST CONSTRUCTOR");
            }
        }
    }

    /**
     * Method for updating the position of the Ghost,
     * checking collision with the map, and changing the
     * direction accordingly.
     * 
     * @param
     */
    void update() {

        synchronized (m.animTimer) {

            this.setTranslateX(x);
            this.setTranslateY(y);

            this.collisionCheck();

            if (goRight == true)
                x += speed;
            if (goLeft == true)
                x -= speed;
            if (goUp == true)
                y -= speed;
            if (goDown == true)
                y += speed;

            i++;
        }
    }

    /**
     * Method for checking the collision of the Ghost with 
     * the map. If they collide the Ghost does not go through 
     * the wall. It is based on RGB reading just like Conman 
     * collision.
     * @param
     */
    void collisionCheck() {

        try {

            leftX = this.x;
            rightX = this.x + 30;
            topY = this.y;
            bottomY = this.y + 50;

            if (i >= 100) {
                random = Math.random();
                i = 0;
            }

            if (random >= 0 && random < 0.25) {
                if (img.getRGB(rightX + 5, topY) == color || img.getRGB(rightX + 5, bottomY) == color || x > 1330) {
                    goRight = false;
                    i = 100;
                } else {
                    goRight = true;
                    goUp = false;
                    goDown = false;
                    goLeft = false;
                }
            }
            if (random >= 0.25 && random < 0.5) {
                if (img.getRGB(leftX - 10, topY) == color || img.getRGB(leftX - 10, bottomY) == color || x < 20) {
                    goLeft = false;
                    i = 100;
                } else {
                    goLeft = true;
                    goUp = false;
                    goDown = false;
                    goRight = false;
                }
            }
            if (random >= 0.5 && random < 0.75) {
                if (img.getRGB(leftX - 11, topY - 5) == color || img.getRGB(rightX + 11, topY - 5) == color) {
                    goUp = false;
                    i = 100;
                } else {
                    goUp = true;
                    goDown = false;
                    goRight = false;
                    goLeft = false;
                }
            }
            if (random >= 0.75 && random < 1) {
                if (img.getRGB(leftX - 5, bottomY + 2) == color || img.getRGB(rightX + 5, bottomY + 2) == color) {
                    goDown = false;
                    i = 100;
                } else {
                    goDown = true;
                    goUp = false;
                    goRight = false;
                    goLeft = false;
                }
            }

        } catch (ArrayIndexOutOfBoundsException aiob) {
            if (this.getTranslateX() < 20) {
                goLeft = false;
                goUp = false;
                goDown = false;
            } else {
                goRight = false;
                goUp = false;
                goDown = false;
            }

        }
    }
}
