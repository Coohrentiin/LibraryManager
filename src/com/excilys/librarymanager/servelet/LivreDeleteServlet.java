package com.excilys.librarymanager.servelet;

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

public class LivreDeleteServlet extends HttpServlet {	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService=LivreServiceImpl.getInstance();
        try {
			List<Livre> listeDeLivres = livreService.getList();
            request.setAttribute("ListeDeLivres", listeDeLivres);
            String id_s = request.getParameter("id");
            int id = Integer.parseInt(id_s);
            livreService.delete(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("livre_delete.jsp");
            dispatcher.forward(request, response);
 
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException("probleme lors de la suppression d'un livre", e);
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