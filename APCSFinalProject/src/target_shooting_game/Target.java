package target_shooting_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

//Use as superclass for all targets
public abstract class Target extends Circle{
	protected Image image;
	private int pointValue;
	public Target(int radius) {
		super(radius);
	}
	
	public int getPointValue() {
		return pointValue;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(image, getCenterX() - getRadius(), getCenterY() - getRadius());
	}
	
	public boolean intersects(Target t) {
		return t.intersects(this.getBoundsInLocal());
	}
}
