package target_shooting_game;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;

public class LargeTarget extends Target {

	public LargeTarget() throws MalformedURLException {
		super(40);
		image = new Image(new File("largeTarget.png").toURI().toURL().toString());
	}

}
