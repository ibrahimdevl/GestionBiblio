package models;

import java.sql.ResultSet;
import java.util.Date;

public class Emprunt {
	private int idEmprunt;
	private Date dateEmprunt;
	private Date dateRetour;
	private int idAdherent;
	private int idBibliothecaire;
	private int idDocument;
	private boolean retournee;

	public Emprunt() {}

	public Emprunt(int idEmprunt, Date dateEmprunt, Date dateRetour, int idAdherent, int idBibliothecaire, int idDocument, boolean retournee) {
		this.idEmprunt = idEmprunt;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
		this.idAdherent = idAdherent;
		this.idBibliothecaire = idBibliothecaire;
		this.idDocument = idDocument;
		this.retournee = retournee;
	}

	@Override
	public String toString() {
		return "Emprunt{" +
				"idEmprunt=" + idEmprunt +
				", dateEmprunt=" + dateEmprunt +
				", dateRetour=" + dateRetour +
				", adherent=" + idAdherent +
				", bibliothecaire=" + idBibliothecaire +
				", document=" + idDocument +
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

	public int getIdAdherent() {
		return idAdherent;
	}

	public void setIdAdherent(int idAdherent) {
		this.idAdherent = idAdherent;
	}

	public int getIdBibliothecaire() {
		return idBibliothecaire;
	}

	public void setIdBibliothecaire(int idBibliothecaire) {
		this.idBibliothecaire = idBibliothecaire;
	}

	public int getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
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

	public void createEmpruntInstance(ResultSet resultSet) {

	}
}
