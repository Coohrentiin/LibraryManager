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
	} //OK

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
			throw new DaoException("Probl�me lors de la r�cup�ration du membre: id=" + id, e);
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
	} //OK

	@Override
	public List<Film> getFilms() throws DaoException {
		List<Film> films = new ArrayList<>();
		
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Film f = new Film(res.getInt("id"), res.getString("titre"), res.getString("realisateur"));
				films.add(f);
			}
			System.out.println("GET: " + films);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la r�cup�ration de la liste des films", e);
		}
		return films;
	}

	@Override
	public int update(Film film) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, film.getTitre());
			preparedStatement.setString(2, film.getRealisateur());
			preparedStatement.setInt(3, film.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + film);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la mise � jour du film: " + film, e);
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
		return film.getId();
	}

	@Override
	public int delete(Film film) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = EstablishConnection.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1, film.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE: " + film);
		} catch (SQLException e) {
			throw new DaoException("Probl�me lors de la suppression du film: " + film, e);
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
		return film.getId();
	}
}
