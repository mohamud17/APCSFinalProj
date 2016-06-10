package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class MediumTarget extends Target {
	public MediumTarget(double centerX, double centerY) {
		super(centerX, centerY, 32);
		try {
			image = new Image(new File("mediumTarget.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPointValue() {
		return 5;
	}
}
