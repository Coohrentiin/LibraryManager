package com.excilys.librarymanager.test;

import com.excilys.librarymanager.model.*;

// import javax.swing.text.AbstractDocument.LeafElement;

// import java.nio.channels.MembershipKey;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.LivreDaoImpl;
import com.excilys.librarymanager.dao.impl.MembreDaoImpl;

public class DaoTest{
	public static void main(String[] args) {
		MembreDao membreImpl= MembreDaoImpl.getInstance();
		LivreDao livreImpl = LivreDaoImpl.getInstance();
		try {
			// Teste de la création d'un membre et de la mise à jours de certains paramètres
			int id_laetitia= membreImpl.create("Chaillou", "Laetitia", "topSecret", "@ensta", "06????????");
			Membre Laetitia=membreImpl.getById(id_laetitia);
			Laetitia.setAdresse(" dans le bat E");
			Laetitia.setAbonnement(Abonnement.VIP);
			membreImpl.update(Laetitia);

			// Teste de la création d'un livre et de la mise à jours de certains paramètres
			int id_livre = livreImpl.create("the great gatsby", "F. Scott Fitzgerald", )
			Livre GreatGatsby = livreImpl.getById(id)
			// Teste de la création d'un emprunt 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}