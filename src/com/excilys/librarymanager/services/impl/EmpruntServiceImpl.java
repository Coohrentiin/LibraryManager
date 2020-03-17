package com.excilys.librarymanager.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.EmpruntDaoImpl;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;

public class EmpruntServiceImpl implements EmpruntService {
    private static EmpruntServiceImpl instance;

    private EmpruntServiceImpl() {
    }

    public static EmpruntService getInstance() {
        if (instance == null) {
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }

    /**
     * 
     * @return liste des emprunts en cours
     */
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunt = new ArrayList<>();
        try {
            emprunt = empruntDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    /**
     * méthode pour obtenir la liste des emprunt en cours
     * @return Renvoi la liste des emprunts courant (qui sont actuellement
     *         empruntés)
     */
    public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunt = new ArrayList<>();
        try {
            emprunt = empruntDao.getListCurrent();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    /**
     * Prend en argument l'id d'un membre et renvoi la liste de ses emprunts en cours 
     * @return Renvoi la liste des emprunts courant d'un membre par son id(qui sont actuellement
     *         empruntés) 
     */
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunt = new ArrayList<>();
        try {
            emprunt = empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    /**
     * Prend en argument l'id d'un livre et renvoi la liste de ses emprunts en cours 
     * @return Renvoi la liste des emprunts courant d'un livre par son id(qui sont actuellement
     *         empruntés) 
     */
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunt = new ArrayList<>();
        try {
            emprunt = empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
        }
        return emprunt;
    }

    /**
     * Methode pour obtenir un emprunt à partir de son identifiant
     * @return un emprunt
     */
    public Emprunt getById(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Probl�me lors de la recherche de l'emprunt d'identifiant: " + id, e1);
        }
        return emprunt;
    }

    /**
     * Permet d'ajouter un nouvel emprunt
     */
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
		try {
			empruntDao.create(idMembre,idLivre,dateEmprunt);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
    }

    /**
     * méthode pour enregistrer le retour d'un livre
     */
    public void returnBook(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        LocalDate dateRetour = LocalDate.now();
        List<Emprunt> emprunts_livre = new ArrayList<>();
        try {
            emprunts_livre=empruntDao.getListCurrentByLivre(id);
            Emprunt emprunt = emprunts_livre.get(0);
            Emprunt new_emprunt = new Emprunt(emprunt.getId(), emprunt.getIdLivre(), emprunt.getIdMembre(), emprunt.getDateEmprunt(), dateRetour);
            empruntDao.update(new_emprunt);
        } catch (DaoException e1) {
            throw new ServiceException("Probl�me lors de l'enregistrement du retour de l'emprunt: "+id, e1);
        }

    }

    /**
     * Methode pour obtenir le nombre d'emprunts
     * @return le nombre d'emprunts (entier)
     */
    public int count() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int number;
        try {
            number = empruntDao.count();
        } catch (DaoException e1) {
            throw new ServiceException("Problème lors du calcul du nombre d'emprunts ", e1);
        }
        return number;
    }

    /**
     * méthode qui donne l'état d'un livre (emprunté/disponible)
     * @return true si le livre est dsiponible, false sinon
     */
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        Boolean dispo = false;
        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
            //TODO
            if (emprunts.isEmpty()){
                dispo = true;
            }
        } catch (DaoException e1) {
            throw new ServiceException("Problème lors de l'évaluation de l'état du livre " + idLivre, e1);
        }
        return dispo;

    }

    /**
     * Verifie que l'emprunt des possible en fonction du nombre d'emprunt en cours d'un membre et de son type d'abonnement 
     * @return true si l'emprunt est possible, false sinon
     */
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		int n=0;
		boolean ispossible=true;
		EmpruntService empruntService= EmpruntServiceImpl.getInstance();
        try {
            List<Emprunt> liste= new ArrayList<>();
            liste = empruntService.getListCurrentByMembre(membre.getId());
			n=liste.size();
			if(n>=membre.getAbonnement().getNbEmpruntMax()){
				ispossible=false;
			}
			else{
				ispossible=true;
			}
        } catch (ServiceException e2){
			System.out.println(e2.getMessage());
		}
        return ispossible;
    }
}