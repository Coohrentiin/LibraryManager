package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;

import com.excilys.librarymanager.utils.*;

public class EmpruntDaoImpl implements EmpruntDao{
    private static EmpruntDaoImpl instance;
	private EmpruntDaoImpl() { }	
	public static EmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
    }
    
    private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)";
	private static final String SELECT_ONE_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?";
	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC";
	private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?";
    private static final String SELECT_CURRENT_BY_MEMBRE = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?";
    private static final String SELECT_CURRENT_BY_LIVRE = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt";
    private static final String SELECT_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL";

	/**
	* Methode pour creer un emprunt à partir de:
	* l'identifiant du membre qui emprunte
	* l'identifiant du livre enprunté
	* la date de retour du livre
	* @return identifiant de l'emprunt
	*/
    @Override
	public int create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        Emprunt empruntToAdd = new Emprunt(idMembre, idLivre, dateEmprunt);
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3, Date.valueOf(dateEmprunt) );
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: " + empruntToAdd);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création de l'emprunt: " + empruntToAdd, e);
		} finally {
			// Ici pour bien faire les choses on doit fermer les objets utilis�s dans
			// des blocs s�par�s afin que les exceptions lev�es n'emp�chent pas la fermeture des autres !
			// la logique est la m�me pour les autres m�thodes. Pour rappel, le bloc finally sera toujours ex�cut� !
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return(id);
	}

	/**
	* Methode pour récuperer un emprunt par son identifiant
	* @return l'objet Emprunt correspondant à l'identifiant
	*/
	@Override
	public Emprunt getById(int id) throws DaoException {
		Emprunt emprunt = new Emprunt();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				emprunt.setId(res.getInt("id"));
				emprunt.setIdMembre(res.getInt("idMembre"));
                emprunt.setIdLivre(res.getInt("idLivre"));
                emprunt.setDateEmprunt(res.getDate( "dateEmprunt").toLocalDate() );
                emprunt.setDateRetour(res.getDate("dateRetour").toLocalDate());	
			}
			
			System.out.println("GET: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt: id=" + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunt;
    }
	
	/**
	* Methode pour récuperer la liste des emprunt en cours
	* @return liste d'objet emprunt de la BDD
	*/
    @Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Emprunt p = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate() );
				emprunts.add(p);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
		}
		return emprunts;
    }
    
    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Emprunt p = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(p);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts en cours", e);
		}
		return emprunts;
    }

	/**
	* Methode pour récuperer la liste des emprunt d'un membre par l'identifiant du membre
	* @return liste d'objet emprunt d'un membre
	*/
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_MEMBRE);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Emprunt p = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(p);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts en cours d'un membre", e);
		}
		return emprunts;
    }
    
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_LIVRE);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Emprunt p = new Emprunt(res.getInt("id"), res.getInt("idMembre"), res.getInt("idLivre"), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(p);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts en cours d'un livre", e);
		}
		return emprunts;
    }

	/**
	* Methode pour mettre à jours les données d'un emprunt à partir d'un objet emprunt
	*/
	@Override
	public void update(Emprunt emprunt) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setInt(1, emprunt.getId());
			preparedStatement.setInt(2, emprunt.getIdMembre());
            preparedStatement.setInt(3, emprunt.getIdLivre());
            preparedStatement.setDate(4, Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(5, Date.valueOf(emprunt.getDateRetour()));
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour de l'emprunt: " + emprunt, e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	* Methode pour obtenir le nombre d'emprunt en cours
	*/
    @Override
	public int count() throws DaoException {
        int compteur;
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			compteur = res.getInt(1);
			System.out.println("NUMBER: " + compteur);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du nombre d'emprunts", e);
		}
		return compteur;
	}
}