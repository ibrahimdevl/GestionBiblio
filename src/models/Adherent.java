package models;

import java.util.Date;

public class Adherent extends Utilisateur{
	private int cin;
	private Date dateInscription;

	public Adherent() {}

	public Adherent(String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel, int cin,Date dateInscription) {
		super(nomUtlstr, motDePasse, nom, prenom, address, numTel);
		this.cin = cin;
		this.dateInscription = dateInscription;
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

}
