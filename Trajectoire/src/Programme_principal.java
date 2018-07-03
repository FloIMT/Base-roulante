

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import javafx.*;


public class Programme_principal {

	
	
	static double Wmax = 10.5; //Vitesse angulaire max. du moteur
	static double Emax = 12.5; //Acceleration max. du moteur
	
	static double Vmax = 1; //Vitesse maximale en coordonnees cartesiennes
	static double Amax = 0.5; //Acceleration max en coord. cartesiennes

	static double r0 = 0.04; //Rayon des roues
	static double dist_roues = 0.2; //Distance entre les roues
	
	static double T0 = 0.010; //Temps d'echantillonage
	static int k = 4; //Cf poly
	
	private String nomFichier;
	
	public Programme_principal(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	
	public void lancer() {
		ecrire(lireTrajectoire(this.nomFichier));
	}
	
	public static Vector<double[]> lireTrajectoire(String nomFichier) { //Transforme le fichier texte en vecteur de vitesses et angles pour les deux moteurs
		
		String commande;
		Vector<double[]> vect=new Vector();
		double[][] vitesseCourante;
		String type;
		
		double theta1 = 0;
		double theta2 = 0;
		
		try
		{
		
		BufferedReader aLire = new BufferedReader(new FileReader(nomFichier));
	
		do
		{ 
			
		    commande = aLire.readLine();
		    
		    if (commande != null) {
		    	
		    	type = commande.substring(0, 3);
		    	
		    	if (type.equals("LIN")) { //Si les caracteres LIN sont rencontres en debut de ligne, les commandes suivantes sont traitees pour retourner des vitesses relavites a une ligne
		    		
		    		System.out.println("LIN");
		    		
		    		double d = Double.parseDouble(commande.substring(4, commande.length()));
		    		vitesseCourante = v(line(d),  d, d);
		    		for(int i = 0; i < vitesseCourante.length; i ++) {
		    			double[] tab = new double[4];
		    			tab[0] = vitesseCourante[i][0];
		    			tab[1] = vitesseCourante[i][1];
		    			tab[2] = theta1 + T0*vitesseCourante[i][0];
		    			tab[3] = theta2 + T0*vitesseCourante[i][1];
		    			theta1 += T0*vitesseCourante[i][0];
		    			theta2 += T0*vitesseCourante[i][1];
		    			
		    			vect.addElement(tab);
		    		
		    		}
		    			    	
		    	}
		    	
		    	if (type.equals("ROT")) {
		    		
		    		
		    		double theta = Double.parseDouble(commande.substring(4,  commande.length()));
		    		
		    		System.out.println("theta = " + theta);
		    		
		    		vitesseCourante = rot(theta);
		    		System.out.println(vitesseCourante.length);
		    		for(int i = 0; i < vitesseCourante.length; i ++) {
		    			
		    			double[] tab = new double[4];
		    			tab[0] = vitesseCourante[i][0];
		    			tab[1] = vitesseCourante[i][1];
		    			tab[2] = theta1 + T0*vitesseCourante[i][0];
		    			tab[3] = theta2 + T0*vitesseCourante[i][1];
		    			
		    			vect.addElement(tab);
		    		}	
		    		
		    	}
		    	
		    	if(type.equals("CIR")) {
		    		
		    		//double theta = commande.substring(4,  commande.length());
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
		    		
		    		for(int j = 0; j < vitesseCourante.length; j ++) {
		    			double[] tab = new double[4];
		    			tab[0] = vitesseCourante[j][0];
		    			tab[1] = vitesseCourante[j][1];
		    			tab[2] = theta1 + T0*vitesseCourante[j][0];
		    			tab[3] = theta2 + T0*vitesseCourante[j][1];
		    			
		    			vect.addElement(tab);
		    		}
		    		
		    		
		    	}
		    	
		    	
		    }
		    	
		    	
		    
			    
		}while (commande != null);
		
		aLire.close(); //On ferme le fichier
		
		
		
	}
		
		
		catch //Si il y a un probleme avec l'ouverture du fichier on renvoie un message d'erreur
		(IOException e) {
		System.out.println("Une operation sur le fichier "+nomFichier
		+" a leve l’exception "+e)
		;
		}
		
		return vect;
	}
	
	
	static double estSup(double a) {
		if (a > 0.001) {
			return a;
		}
		else {
			return 0;
		}
	}

	static double[] line(double d){ //Prend une longueur de ligne en entree et retourne les parametres T et Tau correspondant (cf poly)
		
		double Txy = d/Vmax;
		double Tauxy = Vmax/Amax;
		double T = (d/(r0*Wmax));
		double Tau = Wmax/Emax; // Cf poly
		
		
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
	
		double V1max = d1/(r0*T);
		//System.out.println("V1max = " + V1max);
		
		double V2max = d2/(r0*T);
		//System.out.println("V2max = " + V2max);
		
		
		double[][] v = new double[n][2];
		
		for (int i = 0; i < n0; i++) {
			
			v[i][0] = ((i*1.0)/(n0*1.0))*V1max;
			v[i][1] = ((i*1.0)/(n0*1.0))*V2max;
			
			
			
		}
		
		for (int i = n0; i < n-n0; i++){
			
			v[i][0] = V1max;
			v[i][1] = V2max;
		}
		
		for (int i = n - n0; i < n; i++) {
			
			double alpha = 1-(i*1.0 - (n*1.0-n0*1.0))/(n0*1.0);
			
			v[i][0] = alpha * V1max;
			v[i][1] = alpha * V2max;
		}
		
		
		
		
		//System.out.println("ok3");
		//System.out.println("long = " + v.length);
		return v;
	}
	
	
	static double[] circle(double r, double theta){
		/**
		 * @param rayon r et angle du cercle a effectuer
		 * @return tableau des parametres T et Tau respectivement
		 */
		double longueur_arc = theta * r;
		//distance parcourue par le robot
		double Txy = longueur_arc/Vmax;
		//System.out.println("Txy = " + Txy);
		
		double T1 = theta*(r-dist_roues)/(r0*Wmax);
		//System.out.println("T1 = " + T1);//distance parcourue par la roue 1
		double T2 = theta*(r+dist_roues)/(r0*Wmax);
		//System.out.println("T2 = " + T2);
		double Tauxy = Vmax/Amax;
		//System.out.println("Tauxy = " + Tauxy);
		
		double Tau1 = Wmax/Emax;
		//System.out.println("Tau1 = " + Tau1);
		double Tau2 = Wmax/Emax;
		//System.out.println("Tau2 = " + Tau2);
		//System.out.println("longueur_arc = " + longueur_arc);
		//System.out.println("Txy = " + Txy);
		
		if (Txy < Tauxy) {
					
					Tauxy = Math.sqrt(longueur_arc/Vmax);
					Txy = Tauxy;
				}
				
		if (T1 < Tau1) {
			
			Tau1 = Math.sqrt(theta*(r-dist_roues)/Emax);
			T1 = Tau1;
		}
				
		if (T2 < Tau2) {
							
			Tau2 = Math.sqrt(theta*(r+dist_roues)/Emax);
			T2 = Tau2;
		
		}
		
		double T = max(max(Txy, T1), T2);
		double Tau = max(max(Tauxy, Tau1), Tau2);
		
		double[] param = new double[2];
		param[0] = T;
		param[1] = Tau;
		
		return param;
		
}
	
	
	static double[][] rot(double theta){ //Prend un angle de rotation en entree et retourne le vecteur des vitesses
		
		double Txy = 0;
		double Tauxy = Vmax/Amax;
		
		double T = (theta*(dist_roues/2))/(Wmax*r0);
		double Tau = Wmax/Emax;
		
		if (Txy < Tauxy) {
			
			Tauxy = Math.sqrt(0);
			System.out.println("Tauxy = " + Tauxy);
			
			Txy = Tauxy;
		}
		
		if (T < Tau) {
					
					Tau = Math.sqrt(theta*(dist_roues/2)/Emax);
					T = Tau;
				}
		
		T = max(Txy, T);
		Tau = max(Tauxy, Tau);
		
		//System.out.println("T = " + T);
		//System.out.println("Tau = " + Tau);
		double[] param = new double[2];
		param[0] = T;
		param[1] = Tau;
		
		
		if(theta >= 0) {
			
	        double d1 = -theta*(dist_roues/2);
			double d2 = -d1;
			//System.out.println("ok");
			return v(param, d1, d2);
					
		}
		
		else {
			
			double d1 = theta*(dist_roues/2);
			double d2 = -d1;
			//System.out.println("ok");
			return v(param, d1, d2);
		}	
	}


	static double[][] v_circle(double[] param, double r, double theta){ //Retourne le vecteur des vitesses correspondant à un arc de cercle de rayon r et d'angle theta
	
		double T = param[0];
		double Tau = param[1];
		//System.out.println(T);
		int n = (int)((T + Tau)/T0);
		int n0 = (int)(Tau/T0);
		
		double V1max;
		double V2max;
		
		if(theta >= 0) {
			
			V2max = ((r+dist_roues/2)*theta)/(r0*T);
			V1max = ((r-dist_roues/2)*theta)/(r0*T);
		}
		
		else {
			V1max = ((r+dist_roues/2)*theta)/(r0*T);
			V2max = ((r-dist_roues/2))*theta/(r0*T);
		}
		
		
		double[][] v = new double[n][2];
		
		for (int i = 0; i < n0; i++) {
			
			v[i][0] = ((1.0*i)/(1.0*n0))*V1max;
			v[i][1] = ((1.0*i)/(1.0*n0))*V2max;
			
		}
		
		for (int i = n0; i < n-n0; i++){
			
			v[i][0] = V1max;
			v[i][1] = V2max;
		}
		
		for (int i = n - n0; i < n; i++) {
			
			double alpha = 1.0 - ((1.0*i) - ((1.0*n)-(1.0*n0)))/(1.0*n0);
			
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

	static String estPlus(String a) {
		double b = Double.parseDouble(a);
		
		if(b > 0.001) {
			return String.valueOf(b);
		}
		else {
			return "0.000";
		}
		
	}
	static double estPlus(double a) {
		if(a>0.001) {
			return a;
		}
		else {
			return 0;
		}
	}
	
	static void ecrire(Vector<double[]> tab) //Ecrit les vitesses et angles des moteurs dans un fichier.
	
	{
		double[] tab_intermediaire;
		File f = new File ("vitesses.txt");
		
		try
		{
		    FileWriter fw = new FileWriter (f);
		 
		    
		    for (int i = 0 ; i < tab.size() ; i++ )
		    {
		    	//DecimalFormat df = new DecimalFormat("0.##########");
		    	
		    	tab_intermediaire = tab.elementAt(i);
		    	String l = String.valueOf (tab_intermediaire[0]);
		        fw.write (estPlus((l.substring(0, Math.min(5, l.length())))));
		        fw.write("; " );
		        String m = String.valueOf (tab_intermediaire[1]);
		        fw.write (estPlus(m.substring(0, Math.min(5, m.length()))));
		        fw.write("; ");
		        String n = String.valueOf (estPlus(tab_intermediaire[2]));
		        fw.write (estPlus(n.substring(0, Math.min(5, n.length()))));
		        fw.write("; ");
		        String o = String.valueOf (estPlus(tab_intermediaire[3]));
		        fw.write(estPlus(o.substring(0, Math.min(5, o.length()))));
		        fw.write("; ");
		        fw.write ("\r\n");
		    	
		    	//System.out.println(tab_intermediaire[2]);
		    }
		 
		    fw.close();
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
	
	

}
