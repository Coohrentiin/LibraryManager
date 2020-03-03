package com.excilys.librarymanager.model;

public class Membre{
	private Integer id;
	private String nom;
	private String prenom;
	private String adresse;
	private String email;
	private String telephone;
	private Abonnement abonnement;

	public Membre(){
		super();
	}
	public Membre(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement){
		this();
		this.nom=nom;
		this.prenom=prenom;
		this.adresse=adresse;
		this.email=email;
		this.telephone=telephone;
		this.abonnement=abonnement;
	}

	public void setNom(String nom){
		this.nom=nom;
	}
	public void setPrenom(String prenom){
		this.prenom=prenom;
	}
	public void setAdresse(String adresse){
		this.adresse=adresse;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}
	public void setAbonnement(Abonnement abonnement){
		this.abonnement=abonnement;
	}

	public String getNom(){
		return nom;
	}
	public String getPrenom(){
		return prenom;
	}
	public String getAdresse(){
		return adresse;
	}
	public String getEmail(){
		return email;
	}
	public String getTelephone(){
		return telephone;
	}
	public Abonnement getAbonnement(){
		return abonnement;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "Id: " + id + ", "
				+ "nom:" + nom + ", "
				+ "prenom: " + prenom + ", "
				+ "adresse: " + adresse + ", "
				+ "email: " + email + ", "
				+ "telephone: " + telephone + ", "
				+ "abonnement: " + abonnement
				+ "}";
	}
}