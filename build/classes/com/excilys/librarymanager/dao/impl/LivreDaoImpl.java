package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Livre;

import com.excilys.librarymanager.utils.*;

public class LivreDaoImpl implements LivreDao{
    private static LivreDaoImpl instance;
	private LivreDaoImpl() { }	
	public static LivreDao getInstance() {
		if(instance == null) {
			instance = new LivreDaoImpl();
		}
		return instance;
    }
    
    private static final String CREATE_QUERY = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)";
	private static final String SELECT_ONE_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?";
	private static final String SELECT_ALL_QUERY = "SELECT id, titre, auteur, isbn FROM livre";
	private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM livre WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre";


	@Override
	/**
	* Methode pour creer un livre
	* @return l'identifiant du livre créé
	*/
	public int create(String titre, String auteur, String isbn) throws DaoException {
        Livre livreToAdd = new Livre(titre, auteur, isbn);
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			res = setResCreate(titre, auteur, isbn, preparedStatement);
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: " + livreToAdd);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la cr�ation du film: " + livreToAdd, e);
		} 
		return id;
	}
	private ResultSet setResCreate(String titre, String auteur, String isbn, PreparedStatement preparedStatement)
			throws SQLException {
		ResultSet res;
		preparedStatement.setString(1, titre);
		preparedStatement.setString(2, auteur);
		preparedStatement.setString(3, isbn);
		preparedStatement.executeUpdate();
		res = preparedStatement.getGeneratedKeys();
		return res;
	}

	/**
	* Methode pour récuperer un livre par son identifiant
	* @return l'objet livre correspondant à l'identifiant
	*/
	@Override
	public Livre getById(int id) throws DaoException {
		Livre livre = new Livre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			resGetById(livre, res);
			
			System.out.println("GET: " + livre);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la r�cup�ration du livre: id=" + id, e);
		} 
		return livre;
    }
	private void resGetById(Livre livre, ResultSet res) throws SQLException {
		if(res.next()) {
			livre.setId(res.getInt("id"));
			livre.setTitre(res.getString("titre"));
		    livre.setAuteur(res.getString("auteur"));
		    livre.setIsbn(res.getString("isbn"));				
		}
	}
	
	/**
	* Methode pour récuperer les livres de la bdd
	* @return liste d'objet livre de la bdd
	*/
    @Override
	public List<Livre> getList() throws DaoException {
		List<Livre> livres = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre l = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				livres.add(l);
			}
			System.out.println("GET: " + livres);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la r�cup�ration de la liste des livres", e);
		}
		return livres;
	}

	/**
	* Methode pour updater un livre par un objet livre
	*/
	@Override
	public void update(Livre livre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			resUpdate(livre, preparedStatement);

			System.out.println("UPDATE: " + livre);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la mise � jour du livre: " + livre, e);
		} 
	}
	private void resUpdate(Livre livre, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, livre.getTitre());
		preparedStatement.setString(2, livre.getAuteur());
		preparedStatement.setString(3, livre.getIsbn());
		preparedStatement.setInt(4, livre.getId());
		preparedStatement.executeUpdate();
	}

	/**
	* Methode pour supprimer un livre par son identifiant
	*/
	@Override
	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE: " + id);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la suppression du livre: " + id, e);
		}  finally {
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
	* Methode obtenir le nombre de livres dans la BDD
	* @return nombre entier de livres de la bdd
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
			throw new DaoException("Probl�me lors de la r�cup�ration du nombre de livres", e);
		}
		return compteur;
	}
}
