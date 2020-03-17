package com.excilys.librarymanager.model;

public enum Abonnement{
	BASIC(2), PREMIUM(5), VIP(20);
	private int nbEmpruntMax; 
	public int getNbEmpruntMax() 
    { 
        return this.nbEmpruntMax; 
    } 
	private Abonnement(int nbEmpruntMax) 
    { 
        this.nbEmpruntMax = nbEmpruntMax; 
    } 
}