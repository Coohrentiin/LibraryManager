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

public class MembreDeleteServlet extends HttpServlet {	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreService membreService=MembreServiceImpl.getInstance();
        try {
			List<Membre> listeDeMembres = membreService.getList();
            request.setAttribute("ListeDeMembres", listeDeMembres);
            String id_s = request.getParameter("id");
            int id = Integer.parseInt(id_s);
            membreService.delete(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("membre_delete.jsp");
            dispatcher.forward(request, response);
 
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException("erreure lors de la suppreession d'un membre", e);
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