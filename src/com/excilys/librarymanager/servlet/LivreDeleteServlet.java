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

public class LivreDeleteServlet extends HttpServlet {	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreService livreService=LivreServiceImpl.getInstance();
        List<Livre> listeDeLivres=new ArrayList<>();
        String id_s="";
        int id =0;
        try {
			listeDeLivres = livreService.getList();
            
            id_s = request.getParameter("id");
            id = Integer.parseInt(id_s);
            livreService.delete(id); 
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException("probleme lors de la suppression d'un livre", e);
        }
        request.setAttribute("ListeDeLivres", listeDeLivres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
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