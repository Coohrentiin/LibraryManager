package com.excilys.librarymanager.servelet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.services.impl.*;
import com.excilys.librarymanager.exception.*;

public class DashboardServlet extends HttpServlet {	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService=MembreServiceImpl.getInstance();
		LivreService livreService=LivreServiceImpl.getInstance();
		EmpruntService empruntService=EmpruntServiceImpl.getInstance();		
		try {
			int nb_membres = membreService.count();
			int nb_livres = livreService.count();
			int nb_emprunts = empruntService.count();
			List<Emprunt> listEmprunts = empruntService.getListCurrent();

			request.setAttribute("nb_membres", nb_membres);
			request.setAttribute("nb_livres", nb_livres);
			request.setAttribute("nb_emprunts", nb_emprunts);
			request.setAttribute("listEmprunt", listEmprunts);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp");
			dispatcher.forward(request, response);	
		} catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException(e);
		}
	}
}