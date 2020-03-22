package com.excilys.librarymanager.test;

import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.services.impl.*;

import java.time.LocalDate;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.EmpruntDaoImpl;
import com.excilys.librarymanager.dao.impl.LivreDaoImpl;
import com.excilys.librarymanager.dao.impl.MembreDaoImpl;
import com.excilys.librarymanager.model.*;

public class ServiceTest{
	public static void main(String[] args) {
		MembreService membreImpl= MembreServiceImpl.getInstance();
		LivreService livreImpl = LivreServiceImpl.getInstance();
		EmpruntService empruntImpl= EmpruntServiceImpl.getInstance();
        try {
			// Création d'un membre, d'un livre et de son emprunt par le membre
            int id_membre_JeanBon = membreImpl.create("Bon", "Jean", "charcuterie", "pate@cornichon.fr", "00000000");
			int idLivre = livreImpl.create("La peste", "Camus", "dlknek");
			empruntImpl.create(id_membre_JeanBon, idLivre, LocalDate.now());

			// Mise à jours de certain paramètres des instances
			Membre membre=membreImpl.getById(id_membre_JeanBon);
			membre.setAdresse("Boulangerie Charcurerie");
			Livre livre = livreImpl.getById(idLivre);
			membreImpl.update(membre);
			livreImpl.update(livre);

			//Observation de ce qu'il est possible de faire
			Boolean bool_membre = empruntImpl.isEmpruntPossible(membre);
			Boolean bool_livre = empruntImpl.isLivreDispo(idLivre);
			System.out.println("Emprunt possible pour le membre:"+membre.toString()+"="+bool_membre);
			System.out.println("Emprunt possible pour le livre:"+livre.toString()+"="+bool_livre);
			/*
			Jean Bon peut encore emprunter un livre (par defaut abonnement basic donc possibilité d'emprunter deux livres)
			Le livre d'A. Camus ne peut plus être emprunté car il est empreinté par Mr Bon.
			*/
			int idLivre2 = livreImpl.create("Le ventre de Paris", "Zola", "fsfk");
			empruntImpl.create(id_membre_JeanBon, idLivre2, LocalDate.now());
			Boolean bool_membre_bis = empruntImpl.isEmpruntPossible(membre);
			System.out.println("Emprunt possible pour le membre:"+membre.toString()+"="+bool_membre_bis);
			
			empruntImpl.returnBook(idLivre);
			Boolean bool_livre_bis = empruntImpl.isLivreDispo(idLivre);
			System.out.println("Emprunt possible pour le livre:"+livre.toString()+"="+bool_livre_bis);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
}