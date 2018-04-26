
public class Programme_principal {

	static double[] derivPol(double[] Pol) { //Renvoie les coefficients de la derivée du polynôme Pol (coefficient de degré 0 à l'indice 0)
		
		double[] polDeriv = new double[Pol.length]; //On créé un tableau de même taille que Pol pour stocker les coefficients de la dérivée
		
		for (int i = 0; i < 4; i++) {
			polDeriv[i] = i*Pol[i];
		}
		
		return polDeriv;
	}
	
	static double[][] BezierX(double[][] Points) {
	
		double[] B0 = {1.0, -3.0, 3.0, -1.0}; //Definition des trois premiers polynômes de Bezier
		double[] B1 = {0.0, 3*1.0, 3*(-2.0), 3*(1.0)};
		double[] B2 = {0.0, 0.0, 3*(1.0), 3*(-1.0)};
		double[] B3 = {0.0, 0.0, 0.0, 1.0};
		
		double P0 = Points[0][0];
		double P1 = Points[1][0];
		double P2 = Points[2][0];
		double P3 = Points[3][0];
		
		return null;
	}
	
	static double[][] BezierY(double[][] Points) {
		
		double[] B0 = {1.0, -3.0, 3.0, -1.0}; //Definition des trois premiers polynômes de Bezier
		double[] B1 = {0.0, 3*1.0, 3*(-2.0), 3*(1.0)};
		double[] B2 = {0.0, 0.0, 3*(1.0), 3*(-1.0)};
		double[] B3 = {0.0, 0.0, 0.0, 1.0};
		
		int n = Points.length;
		
		double P0 = Points[0][1];
		double P1 = Points[1][1];
		double P2 = Points[2][1];
		double P3 = Points[3][1];
		
		
		return null;
	}
	
	static double[] sommeP(double[] P1, double[] P2) { //Renvoie la somme de deux polynômes
		
		double[] P3 = new double[P1.length];
		
		for (int i = 0; i < P1.length; i ++) {
			
			P3[i] = P1[i] + P2[i];
		}
		
		return P3;
	}
	
	static double[] multiplie(double coeff, double[] P) { //Multiplie un polynôme par un coefficient coeff
		
		double[] polMult = new double[P.length];
		
		for (int i = 0; i < P.length; i++) {
			
			polMult[i] = coeff * P[i];
			
		}
		
		
		return polMult;
	}
	
	static double[][] line(double x0, double y0, double x1, double y1, double dqm, double dq2m, double Te, int k){ //xi et yi : coordonnees de depart et d'arrivee, dqm et dq2m : vitesse et acceleration max. des moteurs
		
		
		
		
		return null;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double[] J = {1, 1, 1, 1};
		double[] K = derivPol(J);
		System.out.println(K[1]);
	}

}
