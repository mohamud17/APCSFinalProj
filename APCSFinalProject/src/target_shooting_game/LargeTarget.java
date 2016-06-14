package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class LargeTarget extends Target {
	public LargeTarget(double centerX, double centerY) {
		super(centerX, centerY, 64);
		try {
			image = new Image(new File("largeTarget.png").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPointValue() {
		return 1;
	}
}
