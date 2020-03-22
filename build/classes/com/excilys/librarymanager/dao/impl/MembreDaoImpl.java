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
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM Membre;";

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		Membre membre=new Membre(nom,prenom,adresse,email,telephone);
	
		int id = -1;
		try(
			Connection connection = EstablishConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet res = setResCreate(membre, preparedStatement);
		) {
			if(res.next()){
				id = res.getInt(1);				
			}
			System.out.println("CREATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation du membre: " + membre +" error : " +e.getMessage());
		} 
		return id;
	}


	private ResultSet setResCreate(Membre membre, PreparedStatement preparedStatement) throws SQLException {
		
		preparedStatement.setString(1, membre.getNom());
		preparedStatement.setString(2, membre.getPrenom());
		preparedStatement.setString(3, membre.getAdresse());
		preparedStatement.setString(4, membre.getEmail());
		preparedStatement.setString(5, membre.getTelephone());
		preparedStatement.setString(6, Abonnement.BASIC.toString());

		preparedStatement.executeUpdate();
		return preparedStatement.getGeneratedKeys();
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
			resGetById(membre, res);
			
			System.out.println("GET: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation du membre: id=" + id+" error : " +e.getMessage());
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
	private void resGetById(Membre membre, ResultSet res) throws SQLException {
		if(res.next()) {
			membre.setId(res.getInt("id"));
			membre.setNom(res.getString("nom"));
			membre.setPrenom(res.getString("prenom"));
			membre.setEmail(res.getString("email"));
			membre.setAdresse(res.getString("adresse"));
			membre.setTelephone(res.getString("telephone"));
			membre.setAbonnement(Abonnement.valueOf(res.getString("abonnement")));		
		}
	}

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
			throw new DaoException("Problème lors de la récupération de la liste des membres"+" error : " +e.getMessage());
		}
		return membres;
	}

	@Override
	public void update(Membre membre) throws DaoException {
		try (
			Connection connection = EstablishConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
		)
		{			
			SetResUpdate(membre, preparedStatement);
			System.out.println("UPDATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise à jour du membre: " + membre+" error : " +e.getMessage());
		} 
		// return membre.getId();
	}//ok
	private void SetResUpdate(Membre membre, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, membre.getNom());
		preparedStatement.setString(2, membre.getPrenom());
		preparedStatement.setString(3, membre.getEmail());
		preparedStatement.setString(4, membre.getAdresse());
		preparedStatement.setString(5, membre.getTelephone());
		preparedStatement.setString(6, membre.getAbonnement().toString());
		preparedStatement.setInt(7, membre.getId());
		preparedStatement.executeUpdate();
	}

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
			throw new DaoException("Problème lors de la suppression du membre: " + id+" error : " +e.getMessage());
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
			throw new DaoException("Problème lors de la récupération du nombre de membres"+" error : " +e.getMessage());
		}
		return compteur;
	}
} //ok
