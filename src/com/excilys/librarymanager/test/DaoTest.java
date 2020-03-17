package com.excilys.librarymanager.test;

import com.excilys.librarymanager.model.*;

import java.time.LocalDate;

// import javax.swing.text.AbstractDocument.LeafElement;

// import java.nio.channels.MembershipKey;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.EmpruntDaoImpl;
import com.excilys.librarymanager.dao.impl.LivreDaoImpl;
import com.excilys.librarymanager.dao.impl.MembreDaoImpl;

public class DaoTest{
	public static void main(String[] args) {
		MembreDao membreImpl= MembreDaoImpl.getInstance();
		LivreDao livreImpl = LivreDaoImpl.getInstance();
		EmpruntDao empruntImpl= EmpruntDaoImpl.getInstance();
		try {
			// Teste de la création d'un membre et de la mise à jours de certains paramètres
			int id_laetitia= membreImpl.create("Chaillou", "Laetitia", "topSecret", "@ensta", "06");
			Membre Laetitia=membreImpl.getById(id_laetitia);
			Laetitia.setAdresse(" dans le bat E");
			Laetitia.setAbonnement(Abonnement.VIP);
			membreImpl.update(Laetitia);

			// Teste de la création d'un livre et de la mise à jours de certains paramètres
			int id_livre = livreImpl.create("the great gatsby", "F. Scott Fitzgerald", "IBSN000");
			Livre GreatGatsby = livreImpl.getById(id_livre);
			GreatGatsby.setTitre("The great Gatsby");
			GreatGatsby.setIsbn("IBSN001");
			livreImpl.update(GreatGatsby);

			// Teste de la création d'un emprunt 
			LocalDate date_retour= LocalDate.of(2020,03,16);
			int id_emprunt = empruntImpl.create(id_laetitia, id_livre, date_retour);
			Emprunt livreLaetitia=empruntImpl.getById(id_emprunt);

			LocalDate date_emprunt_update= LocalDate.of(2020,03,18);
			livreLaetitia.setDateEmprunt(date_emprunt_update);
			empruntImpl.update(livreLaetitia);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}