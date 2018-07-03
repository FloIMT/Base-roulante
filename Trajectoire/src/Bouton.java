import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Bouton extends Parent{

	private int positionX;
	private int positionY;
	private String chemin_image;
	private Button bouton;
	
	public Bouton(int positionX, int positionY, String chemin_image) {
	
	this.positionX = positionX;
	this.positionY = positionY;
	this.chemin_image = chemin_image;
		
	FileInputStream input;
	try {
		
		input = new FileInputStream(this.chemin_image);
		Image image = new Image(input);
	    ImageView imageView = new ImageView(image);
		Button bout = new Button("", imageView);
		this.bouton = bout;
		bout.setTranslateX(positionX);
		bout.setTranslateY(positionY);
		this.getChildren().add(bout);
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	}
	public Button getBouton() {
		return this.bouton;
	}
	public int getX() {
		return this.positionX;
	}
	public int getY() {
		return this.positionY;
	}
	
	
	
}
