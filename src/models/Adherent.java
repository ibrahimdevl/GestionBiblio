package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Adherent extends Utilisateur{
	private int cin;
	private Date dateInscription;

	public Adherent() {}

	public Adherent(String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel, int cin,Date dateInscription) {
		super(nomUtlstr, motDePasse, nom, prenom, address, numTel);
		this.cin = cin;
		this.dateInscription = dateInscription;
	}

	public Adherent(int idUtlstr, String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel, int cin, Date dateInscription) {
		super(idUtlstr, nomUtlstr, motDePasse, nom, prenom, address, numTel);
		this.cin = cin;
		this.dateInscription = dateInscription;
	}

	public boolean matchesSearch(String searchString) {
		return getNom().contains(searchString) ||
				getPrenom().contains(searchString) ||
				String.valueOf(getCin()).contains(searchString);
	}

	@Override
	public String toString() {
		return super.toString()+
				"Adherent{" +
				"cin=" + cin +
				", dateInscription=" + dateInscription +
				"} ";
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public void createAdherentInstance(ResultSet adherentInfoResult) throws SQLException {
		createUserInstance(adherentInfoResult);
		setCin(adherentInfoResult.getInt(8));
		setDateInscription(adherentInfoResult.getDate(9));
	}

}
