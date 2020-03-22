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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        List<Membre> listeDeMembres = new ArrayList<>();
        String id_s="";
        int id = 0;
        try {
            listeDeMembres = membreService.getList();

            id_s = request.getParameter("id");
            id = Integer.parseInt(id_s);
            membreService.delete(id);

        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException("erreure lors de la suppreession d'un membre", e);
        }
        request.setAttribute("ListeDeMembres", listeDeMembres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            doGet(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}