package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.services.impl.*;

public class EmpruntAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		MembreService membreService = MembreServiceImpl.getInstance();
		List<Livre> livres = new ArrayList<>();
		List <Membre> membres = new ArrayList<>();
		try {
			livres = livreService.getListDispo();
			membres = membreService.getListMembreEmpruntPossible();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("livres", livres);
		request.setAttribute("membres", membres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int idMembre = Integer.parseInt(request.getParameter("idMembre"));
			int idLivre = Integer.parseInt(request.getParameter("idLivre"));
			EmpruntService empruntService = EmpruntServiceImpl.getInstance();
			empruntService.create(idMembre, idLivre, LocalDate.now());
			response.sendRedirect(request.getContextPath() + "/emprunt_list");
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		} catch (ServiceException e3) {
			System.out.println(e3.getMessage());
			throw new ServletException ("Erreur lors de la création d'emprunt.");
		}
	}
}