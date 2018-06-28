
public class Point {

	private int X;
	private int Y;
	
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public Point(int x, int y) {
		super();
		X = x;
		Y = y;
	}
	
	public String toString() {
		return "" + this.getX() + ", " + this.getY() + ")"; 
	}
	public int getDistance(Point p) {
		return (int)(Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2)));
	}
	public void tracer() {
		PointGraphique pg = new PointGraphique(this.getX(), this.getY());
		
	}
}
