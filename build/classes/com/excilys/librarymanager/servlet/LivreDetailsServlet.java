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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService = LivreServiceImpl.getInstance();
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		int idLivre = Integer.parseInt(request.getParameter("livre.id"));
		Livre livre;
		try {
			livre = livreService.getById(idLivre);
			request.setAttribute("titre", livre.getTitre());
			request.setAttribute("auteur", livre.getAuteur());
			request.setAttribute("isbn", livre.getIsbn());
			List<Emprunt> emprunts = empruntService.getListCurrentByLivre(idLivre);
			if(emprunts.size()!=0){
				//TODO 
				
				Emprunt emprunt=emprunts.get(0);
				request.setAttribute("emprunts", emprunt);
			}
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			livre=new Livre();
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_detail.jsp");
		dispatcher.forward(request, response);
	}	
}