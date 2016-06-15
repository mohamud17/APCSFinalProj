package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ShootingGame extends Application {
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	public Image background;
	ImageView imv = null;
	private DoubleProperty mouseX;
	private DoubleProperty mouseY;
	
	public void start(Stage primaryStage) throws MalformedURLException
	{
		//primaryStage.setResizable(false);
		mainMenu(primaryStage);
	}
	
	public void mainMenu(final Stage primaryStage) {
		//Play button
		Button play = new Button();
		play.setText("Play");
		play.setOnAction(new EventHandler<ActionEvent>() {
			//@Override
			public void handle(ActionEvent event) {
				runGame(primaryStage);
			}
		});
		
		//Credits button
		Button credits = new Button();
		credits.setText("Credits");
		credits.setOnAction(new EventHandler<ActionEvent>() {
			//@Override
			public void handle(ActionEvent event) {

				System.out.println("Thank you for playing this game. \nCredits are due to Jayna, Will, and Suad for design and \nfor their endless dedication towards its success. \nSpecial thanks to our hilarious bearded instructor. \nWe hope you enjoyed it!  ");
			}
		});
		
		//Options Button
		Button options = new Button();
		options.setText("Options");
		options.setOnAction(new EventHandler<ActionEvent>() {
			//@Override
			public void handle(ActionEvent event) {
				System.out.println("Ha. You thought you had options.");
			}
		});
		
		//Establish platform of the game.
		StackPane root = new StackPane();

		//Set the scene onto the root platform
		Scene scene = new Scene(root,700,500);
		//Add the details of the objects from the textEditor file.
		scene.getStylesheets().add("FinalProj.css");

		//Instantiate title of the game.
		Label mainLabel= new Label("Shooting Games!");

		primaryStage.setTitle("Welcome to Shooting Games...");

		//Create a vBox object to determine order of the buttons and the main label.
		VBox vertical = new VBox(10);
		//Adds the objects to the vertical box
		vertical.getChildren().addAll(mainLabel, play, options, credits);
		//Adds the vertical box to the platform.
		root.getChildren().add(vertical);
		//Sets the vertical box in the bottom center of the window.
		vertical.setAlignment(Pos.CENTER);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void runGame(Stage primaryStage) {
		primaryStage.setMinHeight(490);
		primaryStage.setMaxHeight(490);
		primaryStage.setMinWidth(735);
		primaryStage.setMaxWidth(735);
		try {
			background = new Image(new File("background.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		primaryStage.setScene(theScene);
		
		Canvas canvas = new Canvas(720, 450);
		
		root.getChildren().add(canvas);
		
		final ArrayList<Target> targets = new ArrayList<Target>();
		final IntValue points = new IntValue(0);
		
		theScene.setOnMouseClicked(
				new EventHandler<MouseEvent>()
				{
					public void handle(MouseEvent e)
					{
						for(Target t : targets) {
							if(t.contains(e.getX(), e.getY()))
							{
								t.setClicked(true);
								points.value += t.getPointValue();
							}
						}
					}
				});
		
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc.setFont(theFont);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		final LongValue startNanoTime = new LongValue(0);
		
		Image img = new Image("crosshair.png", false);
		final ImageView imv = new ImageView(img);
		imv.setFitHeight(100);
		imv.setFitWidth(100);
		mouseX = new SimpleDoubleProperty();
		mouseY = new SimpleDoubleProperty();
		imv.xProperty().bind(mouseX);
		imv.yProperty().bind(mouseY);
		root.getChildren().add(imv);
		theScene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				
				
				//System.out.print(e.getSceneX()+", " +e.getSceneY() + "\t|\t");
				mouseX.set(e.getSceneX()-50);
				mouseY.set(e.getSceneY()-50);
				//System.out.println(mouseX.getValue() + ", " + mouseY.getValue() + "\t|\t" + imv.getX() + ", " + imv.getY());
				//imv.setX(e.getSceneX());
				//imv.setX(e.getSceneY());
				
				

				
			}
		});
		
		new AnimationTimer()
		{
			public void handle(long currentNanoTime)
			{
				if(startNanoTime.value == 0) {
					startNanoTime.value = currentNanoTime;
				}
				gc.setFill(new Color(0.85, 0.85, 1.0, 1.0));
				gc.fillRect(0,0, 512,512);
				
				int badCount = 0;
				for(Target t: targets) {
					if(t instanceof BadTarget) {
						badCount++;
					}
				}
				if(badCount == targets.size()) {
					targets.clear();
				}
				
				if(targets.isEmpty()) {
					targets.add(new MediumTarget(Math.random() * 720, Math.random() * 450));
					targets.add(new BadTarget(Math.random() * 720, Math.random() * 450));
					targets.add(new SmallTarget(Math.random() * 720, Math.random() * 450));
					targets.add(new LargeTarget(Math.random() * 720, Math.random() * 450));
				}
				
				gc.drawImage(background, 0, 0);
				
				Iterator<Target> i = targets.iterator();
				while(i.hasNext()) {
					Target t = i.next();
					if(!t.isClicked()) {
						t.render(gc);
					} else {
						i.remove();
					}
				}
				
				String pointsText = "Points: " + points.value;
				gc.fillText(pointsText, 600, 36);
				gc.strokeText(pointsText, 600, 36);
				
				long currentSecs = (currentNanoTime - startNanoTime.value) / 1000000000;
				long currentMins = currentSecs / 60;
				String timerText = currentMins + ":" + currentSecs;
				gc.fillText(timerText, 20, 36);
				gc.strokeText(timerText, 20, 36);
				/*if(currentSecs >= 20) {
					stop();
				}*/
			}
		}.start();
		
		//gc.fillText("Game Over!", 35, 50);
		//gc.strokeText("Game Over!", 35, 50);
		//gc.fillText("Points: " + points.value, 60, 50);
		//gc.strokeText("Points: " + points.value, 60, 50);
	}
}

class IntValue {
	public int value;
	
	public IntValue(int i) {
		value = i;
	}
}

class LongValue {
	public long value;
	
	public LongValue(long l) {
		value = l;
	}
}
