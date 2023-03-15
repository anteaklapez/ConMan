
import java.io.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Controler class is used for keeping track of changing pictures, scenes,  
 * volume of music and sound effect, and pressing the buttons.
 * @param
 */

public class Controler {
    private Parent root;
    Main m = new Main();

    @FXML
    private Slider sliderMusic;
    @FXML
    private Slider sliderEffects;
    @FXML
    private Label lblMusicVolume;
    @FXML
    private Label lblEffectsVolume;
    public static MediaPlayer musicPlayer;
    public static MediaPlayer effectsPlayer;

    /**
     * Method for off-hover event for the loading screen on the play button switching it to
     * highlighted image
     * 
     * @param event
     */
    public void onHover(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/PlayHover.png")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method for off-hover event that switches the onHover to normal image
     * 
     * @param event
     */
    public void offHover(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/PlayButton.jpg")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Screen 2

    /**
     * Method for on-click on the image "Play" where it switches from Screen1.fxml to
     * Screen2.fxml file with all it's attributes.
     * Printing out a message for the switched scene.
     * Loads the custom cursor from previous scenes.
     * 
     * @param event
     */
    public void onClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Screen2.fxml"));
            m.mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root = loader.load();
            m.scene = new Scene(root);
            m.scene.setCursor(new ImageCursor(m.cursor_img));
            m.mainStage.setScene(m.scene);
            System.out.println("SCREEN2 !!!!");
            m.mainStage.show();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method for on-click event on the Co-Op image where it switches from Screen2.fxml to
     * Screen4.fxml.
     * Prints out a message when switched scene. 
     * Loads the custom cursor from previous scenes.
     * 
     * @param event
     */
    public void onClickCoOp(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Screen4.fxml"));
            m.mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root = loader.load();
            m.scene = new Scene(root);
            m.scene.setCursor(new ImageCursor(m.cursor_img));
            m.mainStage.setScene(m.scene);
            System.out.println("CoOp!!!!");
            m.mainStage.show();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *  Method for off-hover event for the CoOp image that changes the image
     * 
     * @param event
     */
    public void onHoverCoOp(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/CoOpHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover event for Singleplayer image that changes the image
     * 
     * @param event
     */
    public void onHoverSingle(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/SingleHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover event for Multiplayer image that changes the image
     * 
     * @param event
     */

    public void onHoverMulty(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/MultyHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover event for Settings image that changes the image
     * 
     * @param event
     */
    public void onHoverSettings(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/SettingsHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover event for Exit image that changes the image
     * 
     * @param event
     */
    public void onHoverExit(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/ExitHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    // on exit

    /**
     *  Method for off-hover event for Singleplayer image that changes the image
     * 
     * @param event
     */
    public void onExitSingle(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Single.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover event for CoOp image that changes the image
     * 
     * @param event
     */
    public void onExitCoOp(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/CoOp.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover event for Multiplayer image that changes the image
     * 
     * @param event
     */
    public void onExitMulty(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Multy.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *  Method for off-hover for Settings image that changes the image
     * 
     * @param event
     */
    public void onExitSettings(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Settings.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method for off-hover event for Exit image that changes the image.
     * 
     * @param event
     */
    public void onExitExit(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Exit.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Method for on-click event for Exit image that exits the system
     * Printing message for confirmation
     * on Close saves the volume in Setting.xml file
     * 
     * @param event
     */
    public void onClickExit(MouseEvent event) {
        System.out.println("Window Closed!!!!!");
        Main.volumeSave();
        System.exit(0);
    }

    /**
     * Method for on-click event for Singleplayer image that changes the scene from
     * Screen2.fxml to the mainStage
     * 
     * @param event
     */
    public void onClickSingle(MouseEvent event) {
        Main m1 = new Main();
        m1.isSingle = true;
        m1.firstMap = true;
        m1.setupStage();
        m.mainStage = m1.mainStage;
        m.mainStage.setScene(m1.scene);
        m.mainStage.show();

    }

    /**
     * Method for on-click event for Multiplayer image that changes the scene from Screen2.fxml
     * to the main multiplayer stage.
     * 
     * @param event
     */
    public void onClickMulti(MouseEvent event) {
        Main m1 = new Main();
        m1.firstMap = true;
        m1.setupStage();
        m.mainStage = m1.mainStage;
        m.mainStage.setScene(m1.scene);

    }

    // Settings

    /**
     * Method for on-click event for 'Settings' image that changes scenes from
     * Screen2.fxml to Screen3.fxml.
     * Here it loads the slider for music and effects volume from saved xml file.
     * Labels showcase the volume for easier understanding of intensity of the sound
     * Client can adjust it as they like.
     * 
     * @param event
     */
    public void onClickSettings(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Screen3.fxml"));
            m.mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root = loader.load();
            Controler con = loader.getController();
            con.sliderMusic.setValue(Main.getMusicVolume());
            con.lblMusicVolume.setText(String.format("%2.0f", Main.getMusicVolume()) + "%");
            con.sliderEffects.setValue(Main.getEffectsVolume());
            con.lblEffectsVolume.setText(String.format("%2.0f", Main.getEffectsVolume()) + "%");
            m.scene = new Scene(root);
            m.scene.setCursor(new ImageCursor(m.cursor_img));
            m.mainStage.setScene(m.scene);
            System.out.println("SCREEN3 !!!!");
            m.mainStage.show();

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Method for off-hover event for Menu that changes the image.
     * 
     * @param event
     */
    public void onHoverMenu(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/MenuHover.png")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method for off-hover event for Menu that changes the image.
     * 
     * @param event
     */
    public void onExitMenu(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Menu.png")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Method for on-click event for Menu image that changes scenes from
     * Screen3.fxml to Screen2.fxml.
     * On switch scene it saves the music volume and effects volume
     * Loads the custom cursor on the new scene.
     * Prints out the confirmation in the terminal.
     * 
     * @param event
     */
    public void onClickMenu(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Screen2.fxml"));
            m.mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root = loader.load();
            m.scene = new Scene(root);
            m.scene.setCursor(new ImageCursor(m.cursor_img));
            m.mainStage.setScene(m.scene);
            Main.volumeSave();
            System.out.println("SCREEN2 !!!!");
            m.mainStage.show();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method for on-slide updates label of Music Volume.
     * 
     * @param onSlideMusic()
     */
    public void onSlideMusic() {
        double sliderMusicValue = sliderMusic.getValue();
        Platform.runLater(() -> {
            lblMusicVolume.setText(String.format("%2.0f", sliderMusicValue) + "%");
        });
        Main.setMusicVolume(sliderMusicValue);
        musicPlayer.setVolume(sliderMusicValue / 100);

    }

    /**
     * Method for on-slide updates label of Effects Volume.
     * 
     * @param onSlideEffects()
     */
    public void onSlideEffects() {
        double sliderEffectsValue = sliderEffects.getValue();
        Platform.runLater(() -> {
            lblEffectsVolume.setText(String.format("%2.0f", sliderEffectsValue) + "%");
        });
        Main.setEffectsVolume(sliderEffectsValue);
        effectsPlayer.setVolume(sliderEffectsValue / 100);

    }

    /**
     * Setter for music player
     * 
     * @param musicPlayerr
     */
    public static void setMusicPlayer(MediaPlayer musicPlayerr) {
        musicPlayer = musicPlayerr;
    }

    /**
     * setter for Effects player
     * 
     * @param effectsPlayerr
     */
    public static void setEffectsPlayer(MediaPlayer effectsPlayerr) {
        effectsPlayer = effectsPlayerr;
    }

    // * CO-OP
    /**
     * Method for on-click event for Prison image that switches the scene from Screen4.fxml to
     * prison scene main stage.
     * 
     * @param event
     */
    public void onClickPrison(MouseEvent event) {
        Main m1 = new Main();
        m1.isCoop = true;
        m1.firstMap = true;
        m1.secondMap = false;
        m1.setupStage();
        m.mainStage = m1.mainStage;
        m.mainStage.setScene(m1.scene);
        m.mainStage.show();
    }

    /**
     * Method for on-click event for Desert image that switches the scene from Screen4.fxml to
     * desert scene main stage.
     * 
     * @param event
     */
    public void onClickDesert(MouseEvent event) {
        Main m1 = new Main();
        m1.isCoop = true;
        m1.secondMap = true;
        m1.firstMap = false;
        m1.setupStage();
        m.mainStage = m1.mainStage;
        m.mainStage.setScene(m1.scene);
        m.mainStage.show();
    }

    /**
     * Method for on-hover event for Prison image that changes the image
     * 
     * @param event
     */
    public void onHoverPrison(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/PrisonHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method for on-hover event for Desert image that changes the image
     * 
     * @param event
     */
    public void onHoverDesert(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/DesertHover.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     *Method for off-hover event for Prison image that changes the image
     * 
     * @param event
     */
    public void onExitPrison(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Prisson.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method for off-hover event for Desert that changes the image
     * 
     * @param event
     */
    public void onExitDesert(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/Desert.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    // !Alert Hover and function

    // public void onHoverRestart(MouseEvent event) {
    // ImageView iw = (ImageView) event.getSource();
    // try {
    // iw.setImage(new Image(new FileInputStream("src/imgs/RestartHover.png")));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    // public void onHoverMainMenu(MouseEvent event) {
    // ImageView iw = (ImageView) event.getSource();
    // try {
    // iw.setImage(new Image(new FileInputStream("src/imgs/MainMenuHover.png")));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    // public void onExitRestart(MouseEvent event) {
    // ImageView iw = (ImageView) event.getSource();
    // try {
    // iw.setImage(new Image(new FileInputStream("src/imgs/Restart.png")));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    // public void onExitMainMenu(MouseEvent event) {
    // ImageView iw = (ImageView) event.getSource();
    // try {
    // iw.setImage(new Image(new FileInputStream("src/imgs/MainMenu.png")));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    // // * Functions ****************WIN

    // public void onClickRestartWinSingle(MouseEvent event) {

    // }
    // public void onClickRestartWinCoOp(MouseEvent event) {

    // }
    // public void onClickMainMenuWinSingle(MouseEvent event) {

    // }
    // public void onClickMainMenuWinCoOp(MouseEvent event) {

    // }

    // //* FUNCTIONS *****************LOSE

    // public void onClickRestartLoseSingle(MouseEvent event) {

    // }
    // public void onClickRestartLoseCoOp(MouseEvent event) {

    // }
    // public void onClickMainMenuLoseSingle(MouseEvent event) {

    // }
    // public void onClickMainMenuLoseCoOp(MouseEvent event) {

    // }

    // ? HOW TO PLAY
    /**
     * Method for on-hover event for How to play image that changes the image
     * 
     * @param event
     */
    public void onHoverHowTo(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/HowToHover.png")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method for on-hover event for How to play image that changes the image
     * 
     * @param event
     */
    public void onExitHowTo(MouseEvent event) {
        ImageView iw = (ImageView) event.getSource();
        try {
            iw.setImage(new Image(new FileInputStream("src/imgs/HowTo.png")));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Method for on-click event for How To play image that switches Screen2.fxml to howTo.fxml.
     * Shows basic game knowledge to the new player.
     * Loads the custom curser.
     * 
     * @param event
     */
    public void onClickHowTo(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("howTo.fxml"));
            m.mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root = loader.load();
            m.scene = new Scene(root);
            m.scene.setCursor(new ImageCursor(m.cursor_img));
            m.mainStage.setScene(m.scene);
            System.out.println("HowTo !!!!");
            m.mainStage.show();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

}
