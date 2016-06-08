package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class BadTarget extends Target {
	
	public BadTarget() throws MalformedURLException {
		super(20);
		image = new Image(new File("badTarget.png").toURI().toURL().toString());
	}
	
}
