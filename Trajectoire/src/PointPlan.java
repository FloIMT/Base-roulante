
public class PointPlan {

	private double x;
	private double y;
	
	public PointPlan(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double getDistance(PointPlan pt) {
		return Math.sqrt(Math.pow(this.getX()-pt.getX(), 2) + Math.pow(this.getY() - pt.getY(), 2));
	}
	public PointPlan normal() {
		return new PointPlan(this.getY(), -this.getX());
	}
}
