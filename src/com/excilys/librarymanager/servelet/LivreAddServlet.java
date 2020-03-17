package com.excilys.librarymanager.servelet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;

import com.excilys.librarymanager.services.LivreService;
import com.excilys.librarymanager.services.impl.LivreServiceImpl;

import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.exception.*;

public class LivreAddServlet extends HttpServlet {	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService=LivreServiceImpl.getInstance();
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");
		Livre livre;
        try {
			int idLivre = livreService.create(titre, auteur, isbn);
			livre = livreService.getById(idLivre);
 
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			livre = new Livre();
            throw new ServletException("Problème lors de l'ajout d'un nouveau livre SERVLET",e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("livre_add.jsp");
        dispatcher.forward(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			doGet(request, response);
		} catch(ServletException e){
			e.printStackTrace();
		}
	}
}