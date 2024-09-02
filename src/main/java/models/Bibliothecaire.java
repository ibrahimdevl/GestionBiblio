package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

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

	public void createBibliothecaireInstance(ResultSet adherentInfoResult) throws SQLException {
		createUserInstance(adherentInfoResult);
		setDateEmbauche(adherentInfoResult.getDate(8));
		setSalaire(adherentInfoResult.getFloat(9));
	}

	public boolean matchesSearch(String searchString) {
		return getNom().contains(searchString) ||
				getPrenom().contains(searchString) ||
				String.valueOf(getDateEmbauche()).contains(searchString);
	}


}
