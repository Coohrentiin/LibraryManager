package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;

import com.excilys.librarymanager.services.MembreService;
import com.excilys.librarymanager.services.impl.MembreServiceImpl;

import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.exception.*;

public class MembreAddServlet extends HttpServlet {	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService=MembreServiceImpl.getInstance();
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		
		Membre membre;
        try {
			int idMembre = membreService.create(nom,prenom,adresse,email,telephone);
			membre = membreService.getById(idMembre);
 
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			membre = new Membre();
            throw new ServletException("Problème lors de l'ajout d'un nouveau membre SERVLET",e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("membre_add.jsp");
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