
import java.io.*;
import java.util.*;
public class Programme_principal {

	
	
	static double Wmax = 10.5;
	static double Emax = 12.5;
	
	static double Vmax = 1;
	static double Amax = 0.5;

	static double r0 = 0.4;
	static double dist_roues = 0.1;
	
	static double T0 = 0.010;
	static int k = 4;
	
	
	public static double[][] lireTrajectoire(String nomFichier) {
		
		String commande;
		Vector vitesses = new Vector();
		double[][] vitesseCourante;
		String type;
		
		try
		{
		
		BufferedReader aLire = new BufferedReader(new FileReader(nomFichier));
		
		
		do
		{ 
			
		    commande = aLire.readLine();
		    
		    if (commande != null) {
		    	
		    	type = commande.substring(0, 3);
		    	
		    	if (type == "LIN") {
		    		
		    		double d = Double.parseDouble(commande.substring(4, commande.length()));
		    		vitesseCourante = v(line(d),  d, d);
		    		return vitesseCourante;
		    		
		    	
		    	}
		    	
		    	if (type == "ROT") {
		    		
		    		
		    		double theta = Double.parseDouble(commande.substring(4,  commande.length()));
		    		
		    		vitesseCourante = v_circle(circle(dist_roues/2, theta ), dist_roues/2 , theta);
		    		return vitesseCourante;
		    		
		    		
		    	}
		    	
		    	if(type == "CIR") {
		    		
		    		double theta = Double.parseDouble(commande.substring(4,  commande.length()));
		    		double rayon;
		    		double theta_cir;
		    		
		    		int i = 0;
		    		
		    		String r = "";
		    		String t = "";
		    		
		    		while(commande.charAt(4 + i) != ',') {
		    			
		    			r += commande.charAt(4 + i);
		    			i++;
		    			
		    		}
		    		
		    		t = commande.substring(4+i+1, commande.length());
		    		
		    		rayon = Double.parseDouble(r);
		    		theta_cir = Double.parseDouble(t);
		    		
		    		vitesseCourante = v_circle(circle(rayon, theta_cir ), rayon, theta_cir);
		    		return vitesseCourante;
		    	}
		    	
		    	
		    }
		    	
		    	
		    
		    
		    
		}while (commande != null);
		
		
		
	}
		
		
		catch
		(IOException e) {
		System.out.println("Une operation sur le fichier "+nomFichier
		+" a leve l’exception "+e)
		;
		}
		
		return null;
	}
	
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
	
	static double[] line(double d){ //xi et yi : coordonnees de depart et d'arrivee, dqm et dq2m : vitesse et acceleration max. des moteurs
		
		
		
		double Txy = d/Vmax;
		double Tauxy = Vmax/Amax;
		
		
		
		double T = (2*Math.PI*d/r0)/Wmax;
		double Tau = Wmax/Emax;
		
		if (Txy < Tauxy) {
			
			Tauxy = Math.sqrt(d/Vmax);
			Txy = Tauxy;
		}
		
		if (T < Tau) {
			
			Tau = Math.sqrt(d/Emax);
			T = Tau;
		}
		
		T = max(T, Txy);
		Tau = max(Tau, Tauxy);
		
		T = max(T, k*T0);
		Tau = max(Tau, k*T0);
		
		double[] param = new double[2];
		param[0] = T;
		param[1] = Tau;
		
		return param;
	}
	
	static double[][] v(double[] param, double d1, double d2) {
		
		double T = param[0];
		double Tau = param[1];
		
		int n = (int)((T + Tau)/T0);
		int n0 = (int)(Tau/T0);
		
		double V1max = d1/T;
		double V2max = d2/T;
		
		
		double[][] v = new double[n][2];
		
		for (int i = 0; i < n0; i++) {
			
			v[i][0] = (i/n0)*V1max;
			v[i][1] = (i/n0)*V2max;
			
			
			
		}
		
		for (int i = n0; i < n-n0; i++){
			
			v[i][0] = V1max;
			v[i][1] = V2max;
		}
		
		for (int i = n - n0; i < n; i++) {
			
			double alpha = (i - (n-n0))/n0;
			
			v[i][0] = alpha * V1max;
			v[i][1] = alpha * V2max;
		}
		
		
		
		
		
		return v;
	}
	
	
	static double[] circle(double r, double theta){
		
		double d = dist_roues;
		double longueur_arc = theta * r;
		double Txy = longueur_arc/Vmax;
		
		double T1 = 2*Math.PI*theta*(r-dist_roues)/(r0*Wmax);
		double T2 = 2*Math.PI*theta*(r+d)/(r0*Wmax);
		
		double Tauxy = Vmax/Amax;
		
		
		double Tau1 = Wmax/Emax;
		double Tau2 = Wmax/Emax;
		
		
		
		
		if (Txy < Tauxy) {
					
					Tauxy = Math.sqrt(longueur_arc/Vmax);
					Txy = Tauxy;
				}
				
		
		if (T1 < Tau1) {
			
			Tau1 = Math.sqrt(theta*(r-d)/Emax);
			T1 = Tau1;
		}
				
		if (T2 < Tau2) {
							
			Tau2 = Math.sqrt(theta*(r+d)/Emax);
			T2 = Tau2;
		
		}
		
		
		double T = max(max(Txy, T1), T2);
		double Tau = max(max(Tauxy, Tau1), Tau2);
		
		
		double[] param = new double[2];
		param[0] = T;
		param[1] = Tau;
		
		
		return null;
		
	}
	
	
	static double[][] v_circle(double[] param, double r, double theta){
	
		
		double T = param[0];
		double Tau = param[1];
		
		int n = (int)((T + Tau)/T0);
		int n0 = (int)(Tau/T0);
		
		
		double V1max;
		double V2max;
		
		if(theta >= 0) {
			
			V2max = (r+dist_roues)*theta/T;
			V1max = (r-dist_roues)*theta/T;
		}
		
		else {
			V1max = (r+dist_roues)*theta/T;
			V2max = (r-dist_roues)*theta/T;
		}
		
		
		
		
		double[][] v = new double[n][2];
		
		for (int i = 0; i < n0; i++) {
			
			v[i][0] = (i/n0)*V1max;
			v[i][1] = (i/n0)*V2max;
			
		}
		
		for (int i = n0; i < n-n0; i++){
			
			v[i][0] = V1max;
			v[i][1] = V2max;
		}
		
		for (int i = n - n0; i < n; i++) {
			
			double alpha = (i - (n-n0))/n0;
			
			v[i][0] = alpha * V1max;
			v[i][1] = alpha * V2max;
		}
		
		
		
		
		
		return v;
		
	
		
	}
	
	
	
	 static double max(double a, double b) {
		if (a > b) {
			return a;
		}
		
		return b;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}

}
