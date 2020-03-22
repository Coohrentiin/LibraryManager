package com.excilys.librarymanager.services.impl;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.*;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.model.*;

import java.util.List;
import java.util.ArrayList;

public class MembreServiceImpl implements MembreService{
	private static MembreServiceImpl instance;
    private MembreServiceImpl() {
    }

    public static MembreService getInstance() {
        if (instance == null) {
            instance = new MembreServiceImpl();
        }
        return instance;
    }
	/**
	 * 
	 * @return
	 */
	public List<Membre> getList() throws ServiceException{
		MembreDao membreDao= MembreDaoImpl.getInstance();
		List<Membre> membres= new ArrayList<>();
		try{
			membres= membreDao.getList();
		} catch(DaoException e1){
			System.out.println(e1.getMessage());
		}
		return membres;
	}

	/**
	 * renvoit la liste des membres qui peuvent réaliser un nouvel emprunt (car n’ayant pas encore atteint leur quota).
	 * @return liste des membres qui peuvent réaliser un nouvel emprunt
	 */
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
		// TODO
		List<Membre> membres_pouvant= new ArrayList<>();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		MembreService membreService = MembreServiceImpl.getInstance();
		try{
			List<Membre> membres= membreService.getList();
			for (Membre  membre : membres) {
				if(empruntService.isEmpruntPossible(membre)){
					membres_pouvant.add(membre);
				}
			}
		} catch(ServiceException e1){
			System.out.println(e1.getMessage());
		}
		return membres_pouvant;
	}

	/**
	 * Permet de récupéré un objet membre associé à l'identifiant donné en paramètres
	 * @return objet membre associé à l'identifiant 
	 */
	public Membre getById(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		Membre membre= new Membre();
		try {
			membre= membreDao.getById(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return membre;
	}

	/**
	 * Permet de creer un nouveau membre et de l'ajouter à la base de donnée avec:
	 * Nom
	 * Prénom
	 * Adresse 
	 * email
	 * telephone
	 * @return identifiant du nouveau membre crée.
	 */
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		int i = -1;
		try {
			if(nom=="" || nom==null){
				DaoException e= new DaoException("Le champ nom est vide");
				throw e;
			}
			if( prenom=="" || prenom==null){
				DaoException e= new DaoException("Le champ prenom est vide");
				throw e;
			}
			i = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
	}

	/**
	 * Met à jour un membre par un objet membre de même identifiant
	 * met à jour l'ensemble des attributs 
	 */
	public void update(Membre membre) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			if(membre.getNom()=="" || membre.getNom()==null){
				DaoException e= new DaoException("Le champ nom est vide");
				throw e;
			}
			if( membre.getPrenom()=="" || membre.getPrenom()==null){
				DaoException e= new DaoException("Le champ prenom est vide");
				throw e;
			}
			membreDao.update(membre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} catch (NumberFormatException e2) {
			throw new ServiceException("Erreur lors du parsing: id=" + membre.getId(), e2);
		}
	}

	/**
	 * Supprime un membre par son identifiant
	 */
	public void delete(int id) throws ServiceException{
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			Membre membre = membreDao.getById(id);
			membreDao.delete(membre.getId());
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} catch (NumberFormatException e2) {
			throw new ServiceException("Erreur lors du parsing: id=" + id, e2);
		}
	}

	/**
	 * Permet d'acceder au nombre de membres dans la base de donnée
	 * @return Nombre de membre (int)
	 */
	public int count() throws ServiceException{
		int n=0;
		MembreDao membreDao = MembreDaoImpl.getInstance();
		try {
			n=membreDao.count();
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return n;
	}
}