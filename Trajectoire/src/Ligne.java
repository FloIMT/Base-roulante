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
	
	
	
}
