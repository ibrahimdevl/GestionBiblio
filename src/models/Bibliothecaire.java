package models;

import java.util.Date;

public class Bibliothecaire extends Utilisateur{
	private Date dateEmbauche;
	private float salaire;

	public Bibliothecaire() {}

	public Bibliothecaire(String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel, Date dateEmbauche, float salaire) {
		super(nomUtlstr, motDePasse, nom, prenom, address, numTel);
		this.dateEmbauche = dateEmbauche;
		this.salaire = salaire;
	}

	@Override
	public String toString() {
		return super.toString()+
				"Bibliothecaire{" +
				"dateEmbauche=" + dateEmbauche +
				", salaire=" + salaire +
				"} ";
	}

	public Date getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(Date dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}


}
