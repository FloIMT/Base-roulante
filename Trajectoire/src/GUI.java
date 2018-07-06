//import com.trolltech.qt.gui.QApplication;

//import com.trolltech.qt.gui.QPushButton;
import java.io.IOException;

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

	        
	        Graphe graphe = new Graphe(800, 1500, 30, Color.WHITE, 30, 50, 0.0025);

	        System.out.println(graphe.getLargeur());
	        
	        
	        Cadre cadre = new Cadre(240, 300, graphe.getLargeur() + graphe.getPositionX() + 10, 50, Color.ALICEBLUE);

	        Parametre param_Wmax = new Parametre(0.0, 10.0, "rad∕s", "Vitesse", 5.0, cadre.getX() + 5, cadre.getY() + 25);
	        Parametre param_Emax = new Parametre(0.0, 15, "rad.s-2","Acc. angulaire", 12.5, param_Wmax.getX(), param_Wmax.getY() + 60);
	        Parametre param_Vmax = new Parametre(0.0, 2, "m/s", "Vitesse", 1.0, param_Emax.getX(), param_Emax.getY() + 60);
	        Parametre param_Amax = new Parametre(0.0, 1.0, "m.s-2", "Acc.", 0.5, param_Vmax.getX(), param_Vmax.getY() + 60);
	        
	        LigneG ligne1 = new LigneG(cadre.getX() + 10, (int)((param_Wmax.getY() + param_Emax.getY())/2), cadre.getX() + cadre.getLargeur() - 10, (int)((param_Wmax.getY() + param_Emax.getY())/2));
	        LigneG ligne2 = new LigneG(cadre.getX() + 10, (int)((param_Emax.getY() + param_Vmax.getY())/2), cadre.getX() + cadre.getLargeur() - 10, (int)((param_Emax.getY() + param_Vmax.getY())/2));
	        LigneG ligne3 = new LigneG(cadre.getX() + 10, (int)((param_Vmax.getY() + param_Amax.getY())/2), cadre.getX() + cadre.getLargeur() - 10, (int)((param_Vmax.getY() + param_Amax.getY())/2));
	        
	        
	        root.getChildren().add(cadre);
	        root.getChildren().add(param_Wmax);
	        root.getChildren().add(param_Emax);
	        root.getChildren().add(param_Vmax);
	        root.getChildren().add(param_Amax);
	        root.getChildren().add(ligne1);
	        root.getChildren().add(ligne2);
	        root.getChildren().add(ligne3);
	        
	        root.getChildren().add(graphe);
	        
	        Echelle e = new Echelle(graphe.getLargeur() - 120 ,graphe.getPositionY() + graphe.getHauteur() - 25);
	        root.getChildren().add(e);
	        
	        Bouton bouton_marche = new Bouton(60, 900, "play-64.png");
	        bouton_marche.getBouton().setOnAction(new EventHandler() {

				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					Programme_principal pg = new Programme_principal("fichier1.txt", 10.5, 12.5, 1, 0.5);
					graphe.genererTrajectoire("fichier.txt");
					pg.Wmax = param_Wmax.getValeur();
					pg.Emax = param_Emax.getValeur();
					pg.Vmax = param_Vmax.getValeur();
					pg.Amax = param_Amax.getValeur();
					graphe.setEchelle(e.getValeur());
					
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
					root.getChildren().remove(e);
					root.getChildren().add(e);
					try
				      {
				      //Runtime rtime = Runtime.getRuntime();
				      //rtime.exec("bash /home/florent/IMT/PRIME/Base-roulante/script.sh");
				      Process process = Runtime.getRuntime().exec("bash script.sh");
				      System.out.println("ok");
				      } catch (IOException e) {
				            e.printStackTrace();
				            System.out.println("erreur");
				        }
				}
	        	
	        });
	       root.getChildren().add(bouton_clear);
	        primaryStage.show();	
	    	
	    	
	    }
	
	
}
