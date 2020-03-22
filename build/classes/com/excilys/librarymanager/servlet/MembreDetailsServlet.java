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

public class MembreDetailsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService = MembreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int idMembre = Integer.parseInt(request.getParameter("id"));
		Membre membre;
		try {
			membre = membreService.getById(idMembre);
			request.setAttribute("id", idMembre);
			request.setAttribute("nom", membre.getNom());
			request.setAttribute("prenom", membre.getPrenom());
            request.setAttribute("adresse", membre.getAdresse());
            request.setAttribute("email", membre.getEmail());
			request.setAttribute("telephone", membre.getTelephone());
			request.setAttribute("abonnement", membre.getAbonnement());
			request.setAttribute("emprunts", empruntService.getListCurrentByMembre(idMembre));
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			membre=new Membre();
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_detail.jsp");
		dispatcher.forward(request, response);
	}	
}