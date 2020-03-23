package com.excilys.librarymanager.servlet;

import java.io.IOException;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.services.*;
import com.excilys.librarymanager.services.impl.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LivreDeleteServlet extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		Livre livre = new Livre();
		int idLivre = Integer.parseInt(request.getParameter("id"));
		try {
			livre = livreService.getById(idLivre);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("livre", livre);
		request.setAttribute("id_livre", idLivre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int idLivre = Integer.parseInt(request.getParameter("id"));
			LivreService livreService = LivreServiceImpl.getInstance();
			livreService.delete(idLivre);
			response.sendRedirect(request.getContextPath() + "/livre_list");
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage()); 
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		} catch (ServiceException e3) {
			System.out.println(e3.getMessage());
			throw new ServletException ("Erreur lors de la suppression du livre.");
		}
	}
}