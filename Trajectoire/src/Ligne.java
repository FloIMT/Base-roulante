import javafx.scene.Parent;
import javafx.scene.shape.Line;

public class Ligne extends Parent{

	private Point p1;
	private Point p2;
	private int largeur; //Largeur en pixels de la ligne apparaissant dans l'interface graphique

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	public Ligne(Point p1, Point p2) {
		
		this.p1 = p1;
		this.p2 = p2;
		Line ligne = new Line();
		ligne.setStartX(p1.getX());
		ligne.setStartY(p1.getY());
		ligne.setEndX(p2.getX());
		ligne.setEndY(p2.getY());
		this.getChildren().add(ligne);
		
	}
	
	public int getLongeur() {
		return (this.getP1().getDistance(this.getP2()));
	}
	public double getAngle(Ligne l) {
		return getArgument(l.getP1(), l.getP2()) - getArgument(this.getP1(), this.getP2()); 
	}
	public Arc getArc(Ligne l, double longueur_coupe, double echelle, double proportion_ligne) {
		double longueur1;
		double longueur2;
		
		longueur1 = Math.min(longueur_coupe, echelle*this.getLongeur()*proportion_ligne);
		longueur2 = Math.min(longueur_coupe, echelle*l.getLongeur()*proportion_ligne);
		
		double coeff1 = (longueur1/this.getLongeur());
		double coeff2 = (longueur2/l.getLongeur());
		
		PointPlan vect1 = this.getVecteur();
		PointPlan vect2 = l.getVecteur();
		
		PointPlan p_int1 = new PointPlan(this.getP2().getX() - coeff1*vect1.getX(), this.getP2().getY() - coeff1*vect1.getY());
		PointPlan p_int2 = new PointPlan(this.getP2().getX() + coeff2*vect1.getX(), this.getP2().getY() + coeff2*vect2.getY());
		
		PointPlan norm1 = vect1.normal();
		PointPlan norm2 = vect2.normal();
		
		Mat M = new Mat(norm1.getX(), - norm2.getX(), norm1.getY(), -norm2.getY());
		PointPlan Pint = new PointPlan(p_int2.getX() - p_int1.getX(), p_int1.getY() - p_int2.getY());
		PointPlan t = M.inverse().res(Pint);
		
		PointPlan centre = new PointPlan(p_int1.getX() + t.getX()*norm1.getX(), p_int1.getY() + t.getY()*norm1.getY());
		return new Arc(p_int1.getDistance(centre), getArgument(p_int1, p_int2));
	}
	public PointPlan getVecteur() { //Retourne le vecteur directeur de la ligne
		return new PointPlan(this.getP2().getX() - this.getP1().getX(), this.getP2().getY()-this.getP2().getY());
	}
	public static double getArgument(Point c1, Point c2){
		
		int x1 = c1.getX();
		int x2 = c2.getX();
		int y1 = c1.getY();
		int y2 = c2.getY();
		
		if(x2 - x1 > 0) {
			return Math.atan((y2 - y1)/(x2 - x1));
		}
		if(x2 - x1 == 0) {
			if(y2 - y1 >= 0) {
				return Math.PI/2;
			}
			else {
				return -Math.PI/2;
			}
		}
		else {
			return Math.PI + Math.atan((y2 - y1)/(x2 - x1));
		}
		
	}
	public static double getArgument(PointPlan c1, PointPlan c2) {
		
		double x1 = c1.getX();
		double x2 = c2.getX();
		double y1 = c1.getY();
		double y2 = c2.getY();
		
		if(x2 - x1 > 0) {
			return Math.atan((y2 - y1)/(x2 - x1));
		}
		if(x2 - x1 == 0) {
			if(y2 - y1 >= 0) {
				return Math.PI/2;
			}
			else {
				return -Math.PI/2;
			}
		}
		else {
			return Math.PI + Math.atan((y2 - y1)/(x2 - x1));
		}
		
	}
	public String toString() {
		return "[" + this.getP1().toString() + "; " + this.getP2().toString() + "]";
	}
	
	
}
