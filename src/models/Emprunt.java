package models;

import java.util.Date;

public class Emprunt {
	private int idEmprunt;
	private Date dateEmprunt;
	private Date dateRetour;
	private Adherent adherent;
	private Bibliothecaire bibliothecaire;
	private Document document;
	private boolean retournee;

	public Emprunt() {}

	public Emprunt(int idEmprunt, Date dateEmprunt, Date dateRetour, Adherent adherent, Bibliothecaire bibliothecaire, Document document, boolean retournee) {
		this.idEmprunt = idEmprunt;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
		this.adherent = adherent;
		this.bibliothecaire = bibliothecaire;
		this.document=document;
		this.retournee = retournee;
	}

	@Override
	public String toString() {
		return "Emprunt{" +
				"idEmprunt=" + idEmprunt +
				", dateEmprunt=" + dateEmprunt +
				", dateRetour=" + dateRetour +
				", adherent=" + adherent +
				", bibliothecaire=" + bibliothecaire +
				", document=" + document +
				", retournee=" + retournee +
				'}';
	}

	public Date getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(Date dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Adherent getAdherent() {
		return adherent;
	}

	public void setAdherent(Adherent adherent) {
		this.adherent = adherent;
	}

	public Bibliothecaire getBibliothecaire() {
		return bibliothecaire;
	}

	public void setBibliothecaire(Bibliothecaire bibliothecaire) {
		this.bibliothecaire = bibliothecaire;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public boolean isRetournee() {
		return retournee;
	}

	public void setRetournee(boolean retournee) {
		this.retournee = retournee;
	}

	public int getIdEmprunt() {
		return idEmprunt;
	}

	public void setIdEmprunt(int idEmprunt) {
		this.idEmprunt = idEmprunt;
	}
}
