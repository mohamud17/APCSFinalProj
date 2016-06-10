package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class SmallTarget extends Target {
	public SmallTarget(double centerX, double centerY) {
		super(centerX, centerY, 16);
		try {
			image = new Image(new File("smallTarget.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPointValue() {
		return 10;
	}
}
