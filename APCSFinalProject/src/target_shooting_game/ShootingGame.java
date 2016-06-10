package target_shooting_game;

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

	public void start(Stage theStage) throws MalformedURLException
	{
		theStage.setTitle("Target Shoot");
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
		Canvas canvas = new Canvas(500, 500);
		
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
				
		new AnimationTimer()
		{
			public void handle(long currentNanoTime)
			{
				// Clear the canvas
				gc.setFill(new Color(0.85, 0.85, 1.0, 1.0));
				gc.fillRect(0,0, 512,512);
				
				if(targets.isEmpty()) {
					targets.add(new MediumTarget(Math.random() * 512, Math.random() * 512));
					targets.add(new SmallTarget(Math.random() * 512, Math.random() * 512));
					targets.add(new MediumTarget(Math.random() * 512, Math.random() * 512));
				}
				
				Iterator<Target> i = targets.iterator();
				while(i.hasNext()) {
					Target t = i.next();
					if(!t.isClicked()) {
						t.render(gc);
					} else {
						i.remove();
					}
				}
				
				gc.setFill(Color.BLUE);
				
				String pointsText = "Points: " + points.value;
				gc.fillText(pointsText, 360, 36);
				gc.strokeText(pointsText, 360, 36);
			}
		}.start();


		theStage.show();
	}
}

class IntValue {
	public int value;
	
	public IntValue(int i) {
		value = i;
	}
}
