package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class SmallTarget extends Target {
	public SmallTarget() throws MalformedURLException {
		super(10);
		image = new Image(new File("smallTarget.png").toURI().toURL().toString());
	}
}
