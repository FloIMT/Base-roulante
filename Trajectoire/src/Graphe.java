import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Graphe extends Parent{

	private List<Point> listePoints;
	private int hauteur;
	private int largeur;
	private int distancePoints; //Distance "d'enchantillonage" entre les points"
	private int positionX;
	private int positionY;
	private Cadre cadre;
	private Point pointCourant;
	public DoubleProperty longueur = new SimpleDoubleProperty(0.0);
	static double proportion_ligne = 0.1;
	static double longueur_coupe = 0.01; //en m
	private double echelle; //1 pixel = echelle metres
	private Color couleur;
	
	
	public Graphe(int hauteur, int largeur, int distancePoints, Color couleur, int positionX, int positionY, double echelle) {
		super();
		this.listePoints = new ArrayList<Point>();
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.distancePoints = distancePoints;
		this.couleur = couleur;
		this.positionX = positionX;
		this.positionY = positionY;
		this.cadre = new Cadre(this.hauteur, this.largeur, this.positionX, this.positionY, this.couleur);
		this.pointCourant = new Point(0, 0);
		this.echelle = echelle;
		this.longueur.bind(this.getLongueur());
		
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {

			
			public void handle(MouseEvent me) {
				
				setPointCourant((int)me.getX(), (int)me.getY());
			}
			
		});
		
	
	
		this.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent me) {
				ajouterPoint(getPointCourant());
				tracerPoint(getPointCourant());
				if(getNbPoints() > 1) {
					Ligne ligne = new Ligne(getPoint(getNbPoints() - 2), getPoint(getNbPoints() - 1));
					tracerLigne(ligne);
				}	
			}
				
				
				
		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent me){

                Point pt = new Point((int)me.getX(), (int)me.getY());
                ajouterPoint(pt);
                tracerPoint(pt);
                if(getNbPoints() > 1) {
                tracerLigne(new Ligne(getPoint(getNbPoints() - 2), pt));
                }

            }

        });
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent me) {
				
				
				if(getNbPoints() > 1) {
					
					//Ligne ligne = new Ligne(getPoint(getNbPoints() - 2), getPoint(getNbPoints() - 1));
					//tracerLigne(ligne);
					
					}
				
				if(((new Point((int)me.getX(), (int)me.getY())).getDistance(getPointCourant()) >= distancePoints)){
					int x = (int)me.getX();
					int y = (int)me.getY();
					if(x <= positionX + largeur && x >= positionX && y <= positionY + hauteur && y >= positionY) {
					Point pt2 = new Point(x, y);
					ajouterPoint(pt2);
					tracerPoint(pt2);
					
					if(getNbPoints() > 1) {
					
					Ligne ligne = new Ligne(getPoint(getNbPoints() - 2), getPoint(getNbPoints() - 1));
					tracerLigne(ligne);
					}
					setPointCourant(x, y);
					
					}
				}
			}
		});
		this.getChildren().add(cadre);
	}
	
	public Graphe(Graphe g) {
		this(g.getHauteur(), g.getLargeur(), g.getDistancePoints(), g.getCouleur(), g.getPositionX(), g.getPositionY(), g.getEchelle());
	}
	
	public Point getPoint(int i) {
		return listePoints.get(i);
	}
	public void clear() {
		this.listePoints = new ArrayList<Point>();
	}
	
	public int getHauteur() {
		return hauteur;
	}
	public int getLargeur() {
		return largeur;
	}
	public int getDistancePoints() {
		return distancePoints;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public Color getCouleur() {
		return couleur;
	}
	public double getEchelle() {
		return this.echelle;
	}
	public int getNbPoints() {
		return this.listePoints.size();
	}
	public void ajouterPoint(Point p) {
		this.listePoints.add(p);
	}
	public void tracerPoint(Point pt) {
		this.getChildren().add(new PointGraphique(pt.getX(), pt.getY()));
	}
	
	public Point getPointCourant() {
		return this.pointCourant;
	}
	public void setPointCourant(int x, int y) {
		this.pointCourant.setX(x);
		this.pointCourant.setY(y);
	}
	public void tracerLigne(Ligne ligne) {
		this.getChildren().add(ligne);
	}
	public List<Point> getListePoints() {
		return this.listePoints;
	}
	public DoubleProperty getLongueur() {
		double l = 0.0;
		
		for(int i = 0; i < this.getNbPoints() - 1; i++) {
			l = l + this.getPoint(i).getDistance(this.getPoint(i));
		}
		return new SimpleDoubleProperty(l);
	}
	public void genererTrajectoire(String nomFichier) {
		List<Point> L = this.getListePoints();
		int n = L.size();
		
		File f = new File(nomFichier);
		
		try {
		    //PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (fichier)));
			FileWriter fw = new FileWriter(f);
			for(int i = 0; i < n - 2; i++) {
				Ligne ligne1 = new Ligne(L.get(i), L.get(i+1));
				Ligne ligne2 = new Ligne(L.get(i + 1), L.get(i + 2));
				//System.out.println(ligne1.toString());
				System.out.println("echelle = " + this.getEchelle());
				fw.write("LIN " + Math.max(ligne1.getLongeur()*this.echelle - longueur_coupe, (1-proportion_ligne)*ligne1.getLongeur())*this.echelle); // Echelle !!
		        fw.write ("\r\n");
				fw.write("CIR " + (ligne1.getArc(ligne2, longueur_coupe, echelle, proportion_ligne).getRayon() + ", " + (ligne1.getArc(ligne2, longueur_coupe, echelle, proportion_ligne).getAngle())));
		        fw.write ("\r\n");
				
			}
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
}
