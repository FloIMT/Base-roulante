import java.text.DecimalFormat;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Line;

// TODO : binder l'echelle avec la variable echelle du graphe
// TODO : eventuellement rajouter grille d'echelle

public class Echelle extends Parent {
	
	public DoubleProperty valeur = new SimpleDoubleProperty(0.025);//1 px = valeur metres.
	private int positionX;
	private int positionY;
	
	public Echelle(int positionX, int positionY) {
		
		this.positionX = positionX;
		this.positionY = positionY;
		
		Slider slider = new Slider(0.005, 0.1, 0.025);
		
		slider.setOrientation(Orientation.HORIZONTAL);
		slider.setTranslateX(this.positionX);
		slider.setTranslateY(this.positionY);
		
		this.valeur.bind(slider.valueProperty());
		
		
		final Label valeur_parametre = new Label();

		valeur_parametre.setLayoutX(positionX + 43);
        valeur_parametre.setLayoutY(positionY - 20); //On place la valeur de l'échelle au-dessus du slider
        
        //DecimalFormat df = new DecimalFormat("#.00"); 

        valeur_parametre.textProperty().bind(Bindings.format("%.3f", slider.valueProperty().multiply(100)).concat(" m"));
        
        /*final Label unit = new Label();
        unit.setLayoutX(valeur_parametre.getLayoutX() + 39);
        unit.setLayoutY(valeur_parametre.getLayoutY());
        
        unit.setText("m");*/
		Line ligne0 = new Line();
		ligne0.setStartX(this.positionX + 40);
		ligne0.setStartY(valeur_parametre.getLayoutY() - 10);
		ligne0.setEndX(this.positionX + 100);
		ligne0.setEndY(ligne0.getStartY());
		ligne0.setStrokeWidth(2);
		
		Line ligne1 = new Line();
		ligne1.setStartX(ligne0.getStartX());
		ligne1.setEndX(ligne0.getStartX());
		int h = 3;
		ligne1.setStartY(ligne0.getStartY() - h);
		ligne1.setEndY(ligne0.getStartY() + h);
		ligne1.setStrokeWidth(2);
		
		Line ligne2 = new Line();
		ligne2.setStartX(ligne0.getEndX());
		ligne2.setEndX(ligne0.getEndX());
		ligne2.setStartY(ligne0.getStartY() - h);
		ligne2.setEndY(ligne0.getStartY() + h);
		ligne2.setStrokeWidth(2);
		
		
        this.getChildren().add(slider);
        this.getChildren().add(valeur_parametre);
        this.getChildren().add(ligne0);
        this.getChildren().add(ligne1);
        this.getChildren().add(ligne2);
        
        //this.getChildren().add(unit);
        //this.setTranslateX(this.positionX);
        //this.setTranslateY(this.positionY);
        
		
	}
	
}
