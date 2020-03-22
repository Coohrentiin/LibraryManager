package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.services.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LivreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		int idLivre = Integer.parseInt(request.getParameter("id"));
		Livre livre = new Livre();
		String titreLivre = "";
		String auteurLivre = "";
		String isbnLivre = "";
		List<Emprunt> emprunts = new ArrayList<>();
		try {
			livre = livreService.getById(idLivre);
			titreLivre = livre.getTitre();
			auteurLivre = livre.getAuteur();
			isbnLivre = livre.getIsbn();
			emprunts = empruntService.getListCurrentByLivre(idLivre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		request.setAttribute("idLivre", idLivre);
		request.setAttribute("titreLivre", titreLivre);
		request.setAttribute("auteurLivre", auteurLivre);
		request.setAttribute("isbnLivre", isbnLivre);
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		int idLivre = Integer.parseInt(request.getParameter("id"));
		try {
			String titre = request.getParameter("titre");
			String auteur = request.getParameter("auteur");
			String isbn = request.getParameter("isbn");
			Livre livre = new Livre(idLivre, titre, auteur, isbn);
			livreService.update(livre);
			response.sendRedirect(request.getContextPath() + "/livre_details");
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		} catch (ServiceException e3) {
			System.out.println(e3.getMessage());
			throw new ServletException ("Erreur lors de la mise à jour du livre");
		}
	}
}