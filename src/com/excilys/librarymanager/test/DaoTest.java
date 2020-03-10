package com.excilys.librarymanager.test;

import com.excilys.librarymanager.model.*;

import java.nio.channels.MembershipKey;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.MembreDaoImpl;

public class DaoTest{
	public static void main(String[] args) {
		MembreDao membreImpl= MembreDaoImpl.getInstance();
		try {
			int id_laetitia= membreImpl.create("Chaillou", "Laetitia", "topSecret", "@ensta", "06????????");
			Membre Laetitia=membreImpl.getById(id_laetitia);
			membreImpl.update(Laetitia);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}