
/**
 * version two didnt work so 
 * I decided to go back to the first
 * 
 * --thats why the whole code was copy pasted
 * --all of a sudden
 */

/**
 * @author Antea klapez and Ardijan Mustafa
 * @version April 29th 2022
 * 
 * Pacman Movement
 * @ASSESME.INTENSITY:LOW
 * 
 * ****References at the end
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javafx.util.Duration;

/**
 * PackmanGEOStarter with JavaFX and Thread.
 * 
 * @param
 */

public class Main extends Application {
   // Window attributes
   public Stage mainStage;
   public Scene scene;
   public BorderPane rootMain;

   private static double effects;
   private static double music;

   Image cursor_img = new Image("/imgs/cursor.png");

   // Rectangle rec = new Rectangle(0, 0, 40, 700);

   public TextField tfScore1 = new TextField();
   public Label lblScore1 = new Label("Score1:");
   public Label lblNumber1 = new Label("0");

   public TextField tfScore2 = new TextField();
   public Label lblScore2 = new Label("Score2:");
   public Label lblNumber2 = new Label("0");

   public static String[] args;

   // myb not needed
   Conman other = null;

   Conman racer = null;
   Ghost ghost1 = null;
   Ghost ghost2 = null;
   Ghost ghost3 = null;
   Soap soap;

   AnimationTimer animTimer;

   List<Donuts> donutList = null;
   private final int DONUT_NUMBER_OUTER = 22;
   private final int DONUT_NUMBER_INNER = 10;
   final int DONUT_NUMBER = DONUT_NUMBER_OUTER + DONUT_NUMBER_INNER;
   int x_counter = 0;
   int y_counter = 0;

   // for multiplayer
   boolean isCoop = false;
   boolean isSingle = false;

   boolean firstMap = false;
   boolean secondMap = false;

   StackPane stack;

   // for chat
   VBox screen = new VBox(2);
   TextField tfChat = new TextField();
   TextArea taChat = new TextArea();
   Button btn = new Button("Open Chat");

   ConmanClient client = new ConmanClient("localhost", this, "Player");

   public static void main(String[] args) {
      launch(args);

   }

   /**
    * Initializing scene using FXMLLoader and loading Screen.fxml file.
    * Stage is set to not be resizable.
    * Scene has a new custom made cursor.
    * Setting stage setOnCloseRequest and at the same time saving the xml file
    * Settings.xml.
    * Loading the soundtrack on opening the game menu.
    * 
    * @param _stage
    */
   public void start(Stage _stage) {
      mainStage = _stage;
      FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Screen.fxml"));
      Parent root = null;
      try {
         root = loader.load();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      scene = new Scene(root, 1300, 740);
      scene.setCursor(new ImageCursor(cursor_img));
      mainStage.setResizable(false);
      mainStage.setScene(scene);
      mainStage.show();

      mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
         @Override
         public void handle(WindowEvent wevt) {
            Main.volumeSave();
            System.exit(0);
         }
      });

      String sounds = "src/sound/soundtrack.mp3";
      Main.volumeRead();

      String soundsFile = new File(sounds).toURI().toString();
      Media soundeffects = new Media(soundsFile);

      MediaPlayer mediaPlayer = new MediaPlayer(soundeffects);
      MediaPlayer mediaPlayer2Player = new MediaPlayer(soundeffects);

      Controler.setMusicPlayer(mediaPlayer);
      Controler.setEffectsPlayer(mediaPlayer2Player);

      mediaPlayer.setVolume(Main.getMusicVolume() / 100);
      mediaPlayer.play();

      mediaPlayer.setOnEndOfMedia(() -> {
         mediaPlayer.seek(Duration.ZERO);
         mediaPlayer.play();
      });
   }

   /**
    * Method for setting up the score TextFields and Labels.
    * Dependning on gamemode chosen it sets up one or two TextFields.
    * 
    * @param
    */
   void setUpScore() {
      stack = new StackPane();

      tfScore1.setDisable(true);
      tfScore1.setMinSize(100, 50);

      tfScore1.setTranslateX(715);
      tfScore1.setTranslateY(695);
      tfScore1.setFont(new Font("Verdana", 20));
      tfScore1.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

      lblScore1.setFont(new Font("Verdana", 20));
      lblScore1.setTextFill(Color.WHITE);

      lblScore1.setTranslateX(630);
      lblScore1.setTranslateY(695);
      lblScore1.setMinSize(100, 50);

      lblNumber1.setFont(new Font("Verdana", 20));
      lblNumber1.setTextFill(Color.WHITE);

      lblNumber1.setTranslateX(720);
      lblNumber1.setTranslateY(695);
      lblNumber1.setMinSize(100, 50);

      if (isCoop) {

         tfScore2.setDisable(true);
         tfScore2.setMinSize(100, 50);

         tfScore2.setTranslateX(715);
         tfScore2.setTranslateY(750);
         tfScore2.setFont(new Font("Verdana", 20));
         tfScore2.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

         lblScore2.setFont(new Font("Verdana", 20));
         lblScore2.setTextFill(Color.WHITE);

         lblScore2.setTranslateX(630);
         lblScore2.setTranslateY(750);
         lblScore2.setMinSize(100, 50);

         lblNumber2.setFont(new Font("Verdana", 20));
         lblNumber2.setTextFill(Color.WHITE);

         lblNumber2.setTranslateX(720);
         lblNumber2.setTranslateY(750);
         lblNumber2.setMinSize(100, 50);

         stack.getChildren().addAll(lblScore1, tfScore1, lblNumber1, soap, lblScore2, tfScore2, lblNumber2);
         soap.setSoap(700, 700);
      } else {
         stack.getChildren().addAll(lblScore1, tfScore1, lblNumber1, soap);
         soap.setSoap(700, 700);

      }

      if (!isCoop && !isSingle) {
         makeChatGUI();
      }

      rootMain.getChildren().add(stack);

      scene.setCursor(new ImageCursor(new Image("/imgs/cursor.png")));
   }

   /**
    * Method for setting up stage which is going to be shown next. It calls
    * initializeScene() method in order to set up the scene for this stage.
    * Starts collision checker for donuts which loops constantly while the game
    * is running. Starts AnimationTimer whihch calls for updates for characters.
    * 
    * @param
    */
   void setupStage() {
      mainStage = new Stage();

      mainStage.setTitle("Game2D Starter");
      mainStage.setOnCloseRequest(
            new EventHandler<WindowEvent>() {
               public void handle(WindowEvent evt) {
                  System.exit(0);
               }
            });

      // root pane

      rootMain = new BorderPane();

      initializeScene();
      // racer.startThread();
      Thread t = new Thread(new Runnable() {
         @Override
         public void run() {
            int i = 0;
            while (Thread.currentThread() != null) {
               donutList.get(i).onCollision();
               i++;
               if (i > DONUT_NUMBER - 1)
                  i = 0;
            }

         }
      });
      t.start();
      animTimer = new AnimationTimer() {
         @Override
         public void handle(long now) {
            synchronized (animTimer) {
               if (racer.canGo) {
                  ghost1.update();
                  ghost2.update();
                  ghost3.update();

                  racer.animate();
                  racer.update();

                  if (isCoop) {
                     other.update();
                     other.animate();

                  }
               }
            }

         }
      };

      if (isCoop) {
         rootMain.getChildren().addAll(racer, other, ghost1, ghost2, ghost3);
      } else {
         rootMain.getChildren().addAll(racer, ghost1, ghost2, ghost3);
      }

      setUpScore();
      animTimer.start();

   }

   /**
    * Method for initializing new Scene for a new Stage. Donut drawing is
    * done here in a loop and connection between scene and according css file
    * is established.
    * 
    * @param
    */
   public void initializeScene() {
      donutList = new ArrayList<>();
      rootMain.setId("pane");

      if(firstMap){
         x_counter = 30;
         y_counter = 130;

         for (int i = 0; i < DONUT_NUMBER_OUTER; i++) {
            donutList.add(new Donuts(this));
            donutList.get(i).drawDonuts(x_counter, y_counter);

            if (x_counter >= 30 && x_counter < 1200 && y_counter == 130) {
               x_counter += 158;
            } else if (x_counter >= 1200 && y_counter < 610) {
               y_counter += 163;
            } else if (y_counter >= 610 && x_counter > 30) {
               x_counter -= 158;
            } else if (x_counter <= 50) {
               y_counter -= 163;
            }

         }

         x_counter = 430;
         y_counter = 260;

         for (int i = DONUT_NUMBER_OUTER; i < DONUT_NUMBER; i++) {
            donutList.add(new Donuts(this));
            donutList.get(i).drawDonuts(x_counter, y_counter);

            if (x_counter >= 430 && x_counter < 750 && y_counter == 260) {
               x_counter += 150;
            } else if (x_counter >= 750 && y_counter < 450) {
               y_counter += 115;
            } else if (y_counter >= 450 && x_counter > 430) {
               x_counter -= 150;
            } else if (x_counter <= 600) {
               y_counter -= 115;
            }
         }

      } else {

         x_counter = 230;
         y_counter = 117;

         for (int i = 0; i < DONUT_NUMBER; i++) {
            donutList.add(new Donuts(this));
            donutList.get(i).drawDonuts(x_counter, y_counter);

            if (x_counter >= 230 && x_counter < 1055 && y_counter == 117) {
               x_counter += 220;
            } else if (x_counter >= 1055 && y_counter < 620) {
               y_counter += 73;
            } else if (y_counter >= 605 && x_counter > 310) {
               x_counter -= 220;
            } else if (x_counter <= 250) {
               y_counter -= 73;
            }

         }

         

      }
      mainStage.setResizable(false);

      // display the window
      scene = new Scene(rootMain, 1366, 768);

      if (firstMap)
         scene.getStylesheets().addAll(this.getClass().getResource("style_map1.css").toExternalForm());
      else
         scene.getStylesheets().addAll(this.getClass().getResource("style_map2.css").toExternalForm());

      // stage.setScene(scene);
      // stage.show();
      System.out.println("Starting race...");

      if (isCoop) {
         // if the gamemode is co-op, then the player1
         // is controlled by WASD and arrow keys while player 2
         // is controlled by arrow keys
         scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent kevt) {
               if (kevt.getCode() == KeyCode.W)
                  racer.up_press = true;
               if (kevt.getCode() == KeyCode.S)
                  racer.down_press = true;
               if (kevt.getCode() == KeyCode.D)
                  racer.right_press = true;
               if (kevt.getCode() == KeyCode.A)
                  racer.left_press = true;
               if (kevt.getCode() == KeyCode.UP)
                  other.up_press = true;
               if (kevt.getCode() == KeyCode.DOWN)
                  other.down_press = true;
               if (kevt.getCode() == KeyCode.RIGHT)
                  other.right_press = true;
               if (kevt.getCode() == KeyCode.LEFT)
                  other.left_press = true;

            }
         });

         scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent kevt) {
               if (kevt.getCode() == KeyCode.W)
                  racer.up_press = false;
               if (kevt.getCode() == KeyCode.S)
                  racer.down_press = false;
               if (kevt.getCode() == KeyCode.D)
                  racer.right_press = false;
               if (kevt.getCode() == KeyCode.A)
                  racer.left_press = false;
               if (kevt.getCode() == KeyCode.UP)
                  other.up_press = false;
               if (kevt.getCode() == KeyCode.DOWN)
                  other.down_press = false;
               if (kevt.getCode() == KeyCode.RIGHT)
                  other.right_press = false;
               if (kevt.getCode() == KeyCode.LEFT)
                  other.left_press = false;

            }
         });

      } else {
         // if the gamemode is singleplayer, then the player 
         // can be controlled by both WASD and arrow keys
         scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent kevt) {
               if (kevt.getCode() == KeyCode.W || kevt.getCode() == KeyCode.UP)
                  racer.up_press = true;
               if (kevt.getCode() == KeyCode.S || kevt.getCode() == KeyCode.DOWN)
                  racer.down_press = true;
               if (kevt.getCode() == KeyCode.D || kevt.getCode() == KeyCode.RIGHT)
                  racer.right_press = true;
               if (kevt.getCode() == KeyCode.A || kevt.getCode() == KeyCode.LEFT)
                  racer.left_press = true;
            }
         });

         scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent kevt) {
               if (kevt.getCode() == KeyCode.W || kevt.getCode() == KeyCode.UP)
                  racer.up_press = false;
               if (kevt.getCode() == KeyCode.S || kevt.getCode() == KeyCode.DOWN)
                  racer.down_press = false;
               if (kevt.getCode() == KeyCode.D || kevt.getCode() == KeyCode.RIGHT)
                  racer.right_press = false;
               if (kevt.getCode() == KeyCode.A || kevt.getCode() == KeyCode.LEFT)
                  racer.left_press = false;

            }
         });
      }

      racer = new Conman(this, "Player1");
      if (isCoop)
         other = new Conman(this, "Player2");

      if (firstMap) {
         ghost1 = new Ghost(this, 20, 120, "/imgs/ghost1.png");
         ghost2 = new Ghost(this, 450, 300, "/imgs/ghost2.png");
         ghost3 = new Ghost(this, 1300, 600, "/imgs/ghost3.png");
      } else {
         ghost1 = new Ghost(this, 20, 120, "/imgs/ghost1.png");
         ghost2 = new Ghost(this, 385, 245, "/imgs/ghost2.png");
         ghost3 = new Ghost(this, 1300, 600, "/imgs/ghost3.png");

         racer.setPosition(645, 225);
         other.setPosition(695, 225);
      }

      soap = new Soap(this);

   }

   /**
    * 'Settings' setter for Effects volume
    * 
    * @param effectsVolume
    */

   public static void setEffectsVolume(double effectsVolume) {
      effects = effectsVolume;

   }

   /**
    * 'Settings' getter for Effects volume
    * 
    * @return
    */
   public static double getEffectsVolume() {
      return effects;
   }

   /**
    * 'Settings' setter for Music volume
    * 
    * @param MusicVolume
    */
   public static void setMusicVolume(double MusicVolume) {
      music = MusicVolume;
   }

   /**
    * 'Settings' a getter for Music volume
    * 
    * @return
    */

   public static double getMusicVolume() {
      return music;
   }

   /**
    * Method for saving sound using DocumentBuilderFactory which defines the
    * factory's API. 
    * Building a new save file with builder.
    * Saving elements and appending them.
    * Using FileOutputStream outputting a new File called Settings.xml.
    *
    * @param volumeSave()
    */
   public static void volumeSave() {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = null;

      try {
         builder = factory.newDocumentBuilder();
      } catch (ParserConfigurationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      Document saveFile = builder.newDocument();
      Element element1 = saveFile.createElement("Settings");
      saveFile.appendChild(element1);
      Element element2 = saveFile.createElement("Music");
      element2.appendChild(saveFile.createTextNode(getMusicVolume() + ""));
      element1.appendChild(element2);
      Element element3 = saveFile.createElement("Effects");
      element3.appendChild(saveFile.createTextNode(getEffectsVolume() + ""));
      element1.appendChild(element3);
      try {
         FileOutputStream fos = new FileOutputStream(new File("Settings.xml"));
         TransformerFactory tf = TransformerFactory.newInstance();
         Transformer t = tf.newTransformer();
         DOMSource source = new DOMSource(saveFile);
         StreamResult sr = new StreamResult(fos);
         t.transform(source, sr);
      } catch (FileNotFoundException e) {
         // TODO: handle exception
         e.printStackTrace();
      } catch (TransformerException e) {
         e.printStackTrace();
      }

   }

   /**
    * Reading volume from Settings.xml file
    * 
    * @param volumeRead()
    */
   public static void volumeRead() {
      DocumentBuilder builder;
      try {
         builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         Document document = builder.parse(new File("Settings.xml"));
         NodeList n1 = document.getElementsByTagName("Settings");
         for (int i = 0; i < n1.getLength(); i++) {
            org.w3c.dom.Node node = n1.item(i);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
               Element e = (Element) node;
               setMusicVolume(Double.parseDouble(e.getElementsByTagName("Music").item(0).getTextContent()));
               setEffectsVolume(Double.parseDouble(e.getElementsByTagName("Effects").item(0).getTextContent()));

            }

         }

      } catch (ParserConfigurationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         defaultSound();
      } catch (SAXException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         defaultSound();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         defaultSound();
      }

   }

   /**
    * 'Settings' for default Sound. If the settings get corrupted and setting everything to
    * 100%.
    * 
    * @param defaultSound()
    */
   public static void defaultSound() {
      setMusicVolume(100);
      setEffectsVolume(100);
   }

   /**
    * Method for initializing chat and making the whole GUI for it. Since chat is only 
    * avaliable on multiplayer, the client can be called to connect inside this method. 
    * @param
    */
   void makeChatGUI() {
      client.connect();

      taChat.setEditable(false);
      taChat.setWrapText(true);

      screen.getChildren().addAll(taChat, tfChat);
      screen.setVisible(false);

      screen.setMinSize(300, 100);
      screen.setTranslateX(1050);
      screen.setTranslateY(723);

      btn.setMinSize(120, 50);
      btn.setTranslateX(1300);
      btn.setTranslateY(700);

      btn.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
      btn.setFont(new Font("Verdana", 15));
      btn.setTextFill(Color.WHITE);

      stack.getChildren().addAll(btn, screen);

      btn.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent avt) {
            Button btn = (Button) avt.getSource();

            switch (btn.getText()) {
               case "Open Chat":
                  btn.setText("Close Chat");
                  btn.setTextFill(Color.RED);
                  screen.setVisible(true);
                  break;
               case "Close Chat":
                  btn.setText("Open Chat");
                  btn.setTextFill(Color.WHITE);
                  screen.setVisible(false);
                  break;
            }
         }
      });

      tfChat.setOnKeyPressed(new EventHandler<KeyEvent>() {
         public void handle(KeyEvent avt) {
            if (avt.getCode() == KeyCode.ENTER) {
               client.sendMessage();
            }
         }
      });

   }

}

/**
 * REFERENCES:
 * 
 * Oracle:
 * 
 * https://docs.oracle.com/javase/7/docs/api/java/util/TimerTask.html --
 * TIMERTASK
 * https://docs.oracle.com/javase/7/docs/api/java/util/Timer.html -- TIMER
 * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
 * -- KEYEVENT
 * 
 * 
 * Additional pages visited to learn more about TimerTask, Timer and KeyEvent:
 * 
 * https://www.iitk.ac.in/esc101/05Aug/tutorial/essential/threads/timer.html
 * https://stackoverflow.com/questions/13552229/how-to-use-keyevent
 * 
 * 
 * BroCode - for MP3 Player:
 * https://www.youtube.com/watch?v=-D2OIekCKes
 * 
 * 
 * For xml Files
 * https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 * 
 * 
 * 
 * 
 * 
 */