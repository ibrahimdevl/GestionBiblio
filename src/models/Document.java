package models;

public class Document {
	private int idDoc;
	private String reference;
	private String titre;
	private String auteur;
	private static int nbCopies = 0;
	private static int nbDispo = 0;

	public Document() {}

	public Document(String titre, String auteur) {
		this.titre = titre;
		this.auteur = auteur;
		nbCopies++;
		nbDispo++;
	}

	@Override
	public String toString() {
		return "Document{" +
				"idDoc=" + idDoc +
				", reference='" + reference + '\'' +
				", titre='" + titre + '\'' +
				", auteur='" + auteur + '\'' +
				'}';
	}

	public int getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public static int getNbCopies() {
		return nbCopies;
	}

	public static void setNbCopies(int nbCopies) {
		Document.nbCopies = nbCopies;
	}

	public static int getNbDispo() {
		return nbDispo;
	}

	public static void setNbDispo(int nbDispo) {
		Document.nbDispo = nbDispo;
	}
	
	
	
}
