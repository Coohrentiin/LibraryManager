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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntService empruntService=EmpruntServiceImpl.getInstance();
        try {
			List<Emprunt> listeEmprunts = empruntService.getListCurrent();
            request.setAttribute("listeEmprunts",  listeEmprunts);
            RequestDispatcher dispatcher = request.getRequestDispatcher("emprunt_return.jsp");
            dispatcher.forward(request, response);
 
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException("Problème lors de l'emprunt return servlet",e);
        }
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
