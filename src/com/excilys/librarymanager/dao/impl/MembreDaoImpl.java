package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.utils.*;

import com.excilys.librarymanager.exception.DaoException;

public class MembreDaoImpl implements MembreDao{
	private static MembreDao instance;
	private MembreDaoImpl(){ }
	public static MembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}
	private static final String CREATE_QUERY = "INSERT INTO Membre (nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT * FROM Membre WHERE id=?;";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM Membre;";
	private static final String UPDATE_QUERY = "UPDATE Membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?;";
	private static final String DELETE_QUERY = "DELETE FROM Membre WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM Membre";

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		Membre membre=new Membre(nom,prenom,adresse,email,telephone);
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, membre.getNom());
			preparedStatement.setString(2, membre.getPrenom());
			preparedStatement.setString(3, membre.getAdresse());
			preparedStatement.setString(4, membre.getEmail());
			preparedStatement.setString(5, membre.getTelephone());
			preparedStatement.setString(6, membre.getAbonnement().toString());

			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation du membre: " + membre, e);
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
		return id;
	} 

	@Override
	public Membre getById(int id) throws DaoException {
		Membre membre = new Membre();
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ONE_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				membre.setId(res.getInt("id"));
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setEmail(res.getString("email"));
				membre.setAdresse(res.getString("adresse"));
				membre.setTelephone(res.getString("telephone"));
							
			}
			
			System.out.println("GET: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation du membre: id=" + id, e);
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
		return membre;
	} // ok

	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Membre m = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"));
				membres.add(m);
			}
			System.out.println("GET: " + membres);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres", e);
		}
		return membres;
	}

	@Override
	public void update(Membre membre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, membre.getNom());
			preparedStatement.setString(2, membre.getPrenom());
			preparedStatement.setString(3, membre.getEmail());
			preparedStatement.setString(4, membre.getAdresse());
			preparedStatement.setString(5, membre.getTelephone());
			preparedStatement.setInt(6, membre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise à jour du membre: " + membre, e);
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
		// return membre.getId();
	}//ok

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
			throw new DaoException("Problème lors de la suppression du membre: " + id, e);
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
		// return membre.getId();
	}

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
			throw new DaoException("Problème lors de la récupération du nombre de membres", e);
		}
		return compteur;
	}
} //ok
