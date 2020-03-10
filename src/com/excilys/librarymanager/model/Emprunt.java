package com.excilys.librarymanager.model;
import java.time.LocalDate;

public class Emprunt{
    private Integer id;
    private Integer idMembre;
    private Integer idLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt() {
		super();
    }
    public Emprunt(Integer idMembre, Integer idLivre, LocalDate dateEmprunt) {
		this();
		this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
    }
	public Emprunt(Integer idMembre, Integer idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
		this();
		this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }
    
	public Emprunt(Integer id, Integer idMembre, Integer idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
		this(idMembre, idLivre, dateEmprunt, dateRetour);
		this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLivre() {
        return this.idLivre;
    }

    public void setIdLivre(Integer idLivre) {
        this.idLivre = idLivre;
    }

    public Integer getIdMembre() {
        return this.idMembre;
    }

    public void setIdMembre(Integer idMembre) {
        this.idMembre = idMembre;
    }

    public LocalDate getDateEmprunt() {
        return this.dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return this.dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "Id: " + id + ", "
				+ "IdMembre:" + idMembre + ", "
                + "IdLivre: " + idLivre + ", "
                + "DateEmprunt: " + dateEmprunt + ", "
                + "DateRetour: " + dateRetour
				+ "}";
	}
}