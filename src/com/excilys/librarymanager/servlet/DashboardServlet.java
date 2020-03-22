package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.services.impl.*;
import com.excilys.librarymanager.services.*;

/*
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreServiceImpl lService = LivreServiceImpl.getInstance();
		MembreServiceImpl mService = MembreServiceImpl.getInstance();
		EmpruntServiceImpl eService = EmpruntServiceImpl.getInstance();
		int nbLivres = 0;
		int nbMembres = 0;
		int nbEmprunts = 0;
		List<Emprunt> emprunts = new ArrayList<>();
		List<Emprunt> empruntsTotal = new ArrayList<>();
		try {
			nbLivres = lService.count();
			nbMembres = mService.count();
			empruntsTotal = eService.getList();
			nbEmprunts = eService.count();
			emprunts = eService.getListCurrent();
			System.out.println(empruntsTotal);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nbLivres", nbLivres);
		request.setAttribute("nbMembres", nbMembres);
		request.setAttribute("nbEmprunts", nbEmprunts);
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		dispatcher.forward(request, response);
	}
	
}
*/

public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService=MembreServiceImpl.getInstance();
		LivreService livreService=LivreServiceImpl.getInstance();
		EmpruntService empruntService=EmpruntServiceImpl.getInstance();		
		int nb_livres = 0;
		int nb_membres = 0;
		int nb_emprunts = 0;
		List<Emprunt> listEmprunts = new ArrayList<>();
		try {
			nb_membres = membreService.count();
			nb_livres = livreService.count();
			nb_emprunts = empruntService.count();
			listEmprunts = empruntService.getListCurrent();
		} catch (Exception e) {  //service exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nbLivres", nb_livres);
		request.setAttribute("nbMembres", nb_membres);
		request.setAttribute("nbEmprunts", nb_emprunts);
		request.setAttribute("emprunts", listEmprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		dispatcher.forward(request, response);
	}
	
}
