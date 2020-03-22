package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;

import com.excilys.librarymanager.services.EmpruntService;
import com.excilys.librarymanager.services.impl.EmpruntServiceImpl;

import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.exception.*;

public class EmpruntListServlet extends HttpServlet {	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputShow = request.getParameter("show");
		
		if(inputShow=="all"){
			EmpruntService empruntService = EmpruntServiceImpl.getInstance();
			List<Emprunt> emprunts = new ArrayList<>();
			try {
				emprunts = empruntService.getList();
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			request.setAttribute("emprunts", emprunts);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
			dispatcher.forward(request, response);
		}
		else{
			EmpruntService empruntService = EmpruntServiceImpl.getInstance();
			List<Emprunt> emprunts = new ArrayList<>();
			try {
				emprunts = empruntService.getListCurrent();
			} catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			request.setAttribute("emprunts", emprunts);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
			dispatcher.forward(request, response);
		}
	}
}