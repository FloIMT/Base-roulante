import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LigneG extends Parent {

	private int x0;
	private int y0;
	private int x1;
	private int y1;
	
	public LigneG(int x0, int y0, int x1, int y1) {
		
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		
		Line ligne = new Line();
		ligne.setStartX(x0);
		ligne.setStartY(y0);
		ligne.setEndX(x1);
		ligne.setEndY(y1);
		ligne.setStrokeWidth(2);
		ligne.setStroke(Color.GRAY);
		
		this.getChildren().add(ligne);
		
	}
	
	
}
