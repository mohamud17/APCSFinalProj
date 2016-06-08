package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class MediumTarget extends Target {

	public MediumTarget() throws MalformedURLException {
		super(20);
		image = new Image(new File("mediumTarget.png").toURI().toURL().toString());
	}
	
}
