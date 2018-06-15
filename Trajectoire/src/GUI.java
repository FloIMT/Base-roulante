//import com.trolltech.qt.gui.QApplication;

//import com.trolltech.qt.gui.QPushButton;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class GUI extends Application {

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		 
		 System.out.println( "Main method inside Thread : " +  Thread.currentThread().getName());
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) throws Exception {
	    
	    	Group root = new Group();

	        Scene scene = new Scene(root, 800, 600, Color.LIGHTBLUE);

	        primaryStage.setScene(scene);

	        Parametre param = new Parametre(0.0, 10.0, "rad∕s", "Vitesse", 5.0, 10, 35);
	        
	        


	       

	        Circle cercle = new Circle();

	        cercle.setCenterX(300);//réglage de la position, de la taille et de la couleur du cercle

	        cercle.setCenterY(200);

	        cercle.setRadius(100);

	        cercle.setFill(Color.YELLOW);

	        cercle.setStroke(Color.ORANGE);//réglage de la couleur de la bordure et de son épaisseur

	        cercle.setStrokeWidth(5);

	     

	        //root.getChildren().add(cercle);//on ajoute le cercle au groupe root
	        root.getChildren().add(param);
	        primaryStage.show();	
	    	
	    	
	    }
	
	
}
