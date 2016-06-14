package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
	
	public void start(Stage theStage) throws MalformedURLException
	{
		theStage.setTitle("Target Shoot");
		
		try {
			background = new Image(new File("background.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
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
				if(currentSecs >= 1) {
					stop();
				}
			}
		}.start();
		
		gc.fillText("Game Over!", 35, 50);
		gc.strokeText("Game Over!", 35, 50);
		gc.fillText("Points: " + points.value, 60, 50);
		gc.strokeText("Points: " + points.value, 60, 50);
		
		theStage.show();
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
