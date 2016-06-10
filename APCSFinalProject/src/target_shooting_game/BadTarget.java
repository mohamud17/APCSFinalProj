package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class BadTarget extends Target {
	public BadTarget(double centerX, double centerY) {
		super(centerX, centerY, 20);
		try {
			image = new Image(new File("badTarget.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPointValue() {
		return -50;
	}
}
