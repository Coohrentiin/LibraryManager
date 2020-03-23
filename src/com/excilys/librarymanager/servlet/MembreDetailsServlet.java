package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.services.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MembreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int idMembre = Integer.parseInt(request.getParameter("id"));
		Membre membre = new Membre();
		List<Emprunt> emprunts = new ArrayList<>();
		try {
			membre = membreService.getById(idMembre);
			emprunts = empruntService.getListCurrentByMembre(idMembre);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			membre = new Membre();
			e.printStackTrace();
		}
		request.setAttribute("id", idMembre);
		request.setAttribute("nom", membre.getNom());
		request.setAttribute("prenom", membre.getPrenom());
		request.setAttribute("adresse", membre.getAdresse());
		request.setAttribute("email", membre.getEmail());
		request.setAttribute("telephone", membre.getTelephone());
		request.setAttribute("abonnement", membre.getAbonnement());
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_detail.jsp");
		dispatcher.forward(request, response);
	}
}