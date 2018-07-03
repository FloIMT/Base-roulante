
public class Mat {

	private double a;
	private double b;
	private double c;
	private double d;
	
	public Mat(double a, double b, double c, double d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	

	public void setA(double a) {
		this.a = a;
	}


	public void setB(double b) {
		this.b = b;
	}


	public void setC(double c) {
		this.c = c;
	}


	public void setD(double d) {
		this.d = d;
	}


	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public double getD() {
		return d;
	}
	
	public double det() {
		return a*d - b*c;
	}
	public Mat multiplie(double alpha) {
		return new Mat(this.getA()*alpha, this.getB()*alpha, this.getC()*alpha, this.getD()*alpha);
	}
	 
	public Mat inverse() {
		if(this.det() != 0) {
			return (new Mat(this.getD(), -this.getC(), -this.getB(), this.getA()).multiplie(1/this.det())); 
		}
		else {
			return new Mat(0, 0, 0, 0);
		}
	}
	public PointPlan res(PointPlan pt) {
		double x = pt.getX();
		double y = pt.getY();
		return new PointPlan(this.getA()*x + this.getB()*y, this.getC()*x + this.getD()*y);
	}
}
