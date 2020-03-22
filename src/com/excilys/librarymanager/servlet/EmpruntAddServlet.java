package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService=MembreServiceImpl.getInstance();
		LivreService livreService=LivreServiceImpl.getInstance();
		List<Membre> listeDeMembres= new ArrayList<>();
		List<Livre> listeDeLivres= new ArrayList<>();
        try {
			listeDeMembres = membreService.getListMembreEmpruntPossible();
			listeDeLivres = livreService.getListDispo();
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException(e);
		}
		request.setAttribute("ListeDeMembres", listeDeMembres);
		request.setAttribute("ListeDeLivres", listeDeLivres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
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