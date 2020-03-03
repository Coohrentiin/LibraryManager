package test;

import com.excilys.librarymanager.model.*;

class ModelTest{
	public static void main(String[] args) {
		Membre membre1= new Membre("Chaillou", "Laetitia", "topSecret", "@ensta", "06????????", Abonnement.VIP);
		Membre membre2= new Membre("Soub", "Corentin", "topSecret", "@ensta", "06????????", Abonnement.BASIC);
		Livre germinal= new Livre("Germinal","Zola","?1");
		Livre premierhomme= new Livre("Le Premier Homme","Camus","?2");
		System.out.println(membre1);
		System.out.println(membre2);
		System.out.println(germinal);
		System.out.println(premierhomme);
	}
}