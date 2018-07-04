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
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;


//TODO : ajouter bouton de retour en arriere, pourquoi une gomme
//TOTO : placer les parametres dans un cadre a la droite du graphe
//TODO : ajouter le script permettant de lancer la commande Matlab

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
	        
	        
	        
	        Graphe graphe = new Graphe(800, 1600, 30, Color.WHITE, 50, 50, 0.0025);
	        root.getChildren().add(param);
	        root.getChildren().add(graphe);
	        
	        Echelle e = new Echelle(graphe.getLargeur() - 100 ,graphe.getPositionY() + graphe.getHauteur() - 25);
	        root.getChildren().add(e);
	        
	        Bouton bouton_marche = new Bouton(60, 900, "play-64.png");
	        bouton_marche.getBouton().setOnAction(new EventHandler() {

				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					Programme_principal pg = new Programme_principal("fichier.txt");
					graphe.genererTrajectoire("fichier.txt");
					pg.lancer();
					System.out.println("ok");
					
				}
			});
	        root.getChildren().add(bouton_marche);
	        
	        Bouton bouton_clear = new Bouton(bouton_marche.getX() + 100, bouton_marche.getY(), "clear.png");
	        bouton_clear.getBouton().setOnAction(new EventHandler() {

				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					Graphe nouveau_graphe = new Graphe(graphe);
					root.getChildren().remove(graphe);
					root.getChildren().add(nouveau_graphe);
					
				}
	        	
	        });
	       root.getChildren().add(bouton_clear);
	        primaryStage.show();	
	    	
	    	
	    }
	
	
}
