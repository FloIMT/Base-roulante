import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cadre extends Parent {
	
	//private int largeur;
	//private int hauteur;
	//private int positionX;
	//private int positionY;
	//private Color couleur;

	public Cadre(int hauteur, int largeur, int positionX, int positionY, Color couleur) {
		
		Rectangle cadre = new Rectangle();

        cadre.setWidth(largeur);

        cadre.setHeight(hauteur);

        cadre.setArcWidth(30);

        cadre.setArcHeight(30);

        cadre.setFill(couleur);

        this.setTranslateX(positionX);//on positionne le groupe plutôt que le rectangle

        this.setTranslateY(positionY);

        this.getChildren().add(cadre);
		
		
		
	}
	
}
