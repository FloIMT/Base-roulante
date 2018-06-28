import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PointGraphique extends Parent {

	
	private int positionX;
	private int positionY;
	
	public PointGraphique(int positionX, int positionY) {
		
		this.positionX = positionX;
		this.positionY = positionY;
		
		Circle cercle = new Circle();
		cercle.setCenterX(this.positionX);
		cercle.setCenterY(this.positionY);
		cercle.setRadius(5);
		cercle.setFill(Color.RED);
		this.getChildren().add(cercle);
		
	}
	
}
