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


public class EmpruntReturnServlet extends HttpServlet {	
    private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
		List <Emprunt> emprunts = new ArrayList<>();
		try {
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
		dispatcher.forward(request, response);
	}
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int idEmprunt = Integer.parseInt(request.getParameter("id"));
			EmpruntService empruntService = EmpruntServiceImpl.getInstance();
			empruntService.returnBook(idEmprunt);
			response.sendRedirect(request.getContextPath() + "/emprunt_list");
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage()); 
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
		} catch (ServiceException e3) {
			System.out.println(e3.getMessage());
			throw new ServletException ("Erreur lors du retour de l'emprunt.");
		}
	}
}
