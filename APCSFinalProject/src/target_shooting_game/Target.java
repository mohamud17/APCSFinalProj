package target_shooting_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

//Use as superclass for all targets
public abstract class Target extends Circle{
	protected Image image;
	private boolean isClicked = false;
	
	public Target(double centerX, double centerY, int i) {
		super(centerX, centerY, i);
	}

	public abstract int getPointValue();
	
	public boolean isClicked() {
		return isClicked;
	}
	
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(image, getCenterX() - getRadius(), getCenterY() - getRadius());
	}
	
	public boolean intersects(Target t) {
		return t.intersects(this.getBoundsInLocal());
	}
}
