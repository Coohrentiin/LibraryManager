package com.excilys.librarymanager.model;

public class Livre{
    private Integer id;
    private String titre;
    private String auteur;
    private String isbn;

    public Livre() {
		super();
	}
	public Livre(String titre, String auteur, String isbn) {
		this();
		this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
	}
	public Livre(Integer id, String titre, String auteur, String isbn) {
		this(titre, auteur, isbn);
		this.id = id;
    }
    
    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "Id: " + id + ", "
				+ "titre:" + titre + ", "
                + "auteur: " + auteur + ", "
                + "isbn: " + isbn
				+ "}";
	}
}