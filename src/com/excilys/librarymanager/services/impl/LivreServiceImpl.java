package com.excilys.librarymanager.services.impl;

import java.util.ArrayList;
import java.util.List;
import com.excilys.librarymanager.services.LivreService;
import com.excilys.librarymanager.services.EmpruntService;
import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.impl.LivreDaoImpl;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Livre;

public class LivreServiceImpl implements LivreService {
    private static LivreServiceImpl instance;

    private LivreServiceImpl() {
    }

    public static LivreService getInstance() {
        if (instance == null) {
            instance = new LivreServiceImpl();
        }
        return instance;
    }

    @Override
    /**
     * Methode pour creer un livre
     * 
     * @return l'identifiant du livre créé envoie une exception si le titre est vide
     */
    public int create(String titre, String auteur, String isbn) throws ServiceException {
        Livre livre = new Livre(titre, auteur, isbn);
        LivreDao livreDao = LivreDaoImpl.getInstance();
        if (titre == "") {
            throw new ServiceException("Le titre ne peut pas être vide");
        }
        int i = -1;
        try {
            i = livreDao.create(titre, auteur, isbn);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Problème lors de la creation du film: " + livre, e1);
        }
        return i;
    }

    @Override
    /**
     * Methode pour mettre à jour un livre envoie une exception si le titre est vide
     */
    public void update(Livre livre) throws ServiceException {
        if (livre.getTitre() == "") {
            throw new ServiceException("Le titre ne peut pas être vide");
        }
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livreDao.update(livre);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Probl�me lors de la mise à jour du livre: " + livre, e1);
        }
    }

    @Override
    /**
     * Methode pour supprimer un livre
     */
    public void delete(int id) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livreDao.delete(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Probl�me lors de la suppression du livre: " + id, e1);
        }
    }

    @Override
    /**
     * Methode pour obtenir la liste des livres
     * 
     * @return une liste de livres
     */
    public List<Livre> getList() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreDao.getList();
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Probl�me lors de la recherche des livres", e1);
        }
        return livres;
    }

    @Override
    /**
     * Methode pour obtenir un livre à partir de son identifiant
     * 
     * @return un livre
     */
    public Livre getById(int id) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        try {
            livre = livreDao.getById(id);
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Probl�me lors de la recherche du livre d'identifiant: " + id, e1);
        }
        return livre;
    }

    @Override
    /**
     * Methode pour obtenir le nombre de livres
     * 
     * @return le nombre de livres (entier)
     */
    public int count() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int number;
        try {
            number = livreDao.count();
        } catch (DaoException e1) {
            throw new ServiceException("Probl�me lors du calcul du nombre de livres ", e1);
        }
        return number;
    }

    @Override
    /**
     * Methode pour obtenir les livres disponibles à l'emprunt
     * 
     * @return la liste de livres disponibles
     */
    public List<Livre> getListDispo() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreDao.getList();
            int nb = count();
            int id;
            for (int i = 0; i < nb; i++) {
                id = livres.get(i).getId();
                if (empruntService.isLivreDispo(id) == false) {
                    livres.remove(i);
                }
            }
        } catch (DaoException e1) {
            System.out.println(e1.getMessage());
            throw new ServiceException("Probl�me lors de la recherche des livres", e1);
        } catch (ServiceException e2) {
            System.out.println(e2.getMessage());
            throw new ServiceException("Probl�me lors de la recherche des livres", e2);
        }

        return livres;
    }
}