import java.text.DecimalFormat;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class Parametre extends Parent {
	
	private double min;
	private double max;
	private String unite;
	private String nom;
	public DoubleProperty valeur = new SimpleDoubleProperty(0.0);
	private int positionX;
	private int positionY;
	public Slider slider;
	

	public Parametre(double min, double max, String unite, String nom, double valeur, int positionX, int positionY) {
		
		this.min = min;
		this.max = max;
		this.unite = unite;
		this.nom = nom;
		//DoubleProperty val1 = new SimpleDoubleProperty(0.0);
		//val1.setValue(valeur);
		this.positionX = positionX;
		this.positionY = positionY;
		
		//Text Nom = new Text();
		Text Val = new Text();
		//Nom.setText(this.nom);
		StringProperty TextVal = new SimpleStringProperty();
		Val.setText("" +this.valeur.get());
		
		final Label valeur_parametre = new Label();
		final Label nom_parametre = new Label();
		final Label unit = new Label();
        
        valeur_parametre.setLayoutX(positionX + 250);
        valeur_parametre.setLayoutY(positionY);
        
        unit.setLayoutX(positionX + 300);
        unit.setLayoutY(positionY);
        unit.setText(unite);
        
        nom_parametre.setLayoutX(positionX);
        nom_parametre.setLayoutY(positionY);
        nom_parametre.setText(this.nom);
		
		this.slider = new Slider(this.min, this.max, (this.min + this.max)/2);
		
		this.slider.setOrientation(Orientation.HORIZONTAL);

		this.slider.setTranslateX(positionX + 100);
        this.slider.setTranslateY(positionY);

        //ProgressIndicator indicateur = new ProgressIndicator(0.0);
        //indicateur.progressProperty().bind(this.slider.valueProperty().divide(this.max - this.min));
        this.valeur.bind(this.slider.valueProperty());
        DecimalFormat df = new DecimalFormat("#.00"); 

        valeur_parametre.textProperty().bind(Bindings.format("%.2f", slider.valueProperty()));
        
        Val.setX(positionX + 50);
        Val.setY(positionY);
        this.getChildren().add(this.slider);
        //this.getChildren().add(indicateur);
        this.getChildren().add(unit);
        this.getChildren().add(nom_parametre);
        this.getChildren().add(valeur_parametre);
		this.setTranslateX(positionX);
		this.setTranslateY(positionY);
		
	}
	
	public double getValeur() {
		return this.valeur.get();
	}
	
	
}
