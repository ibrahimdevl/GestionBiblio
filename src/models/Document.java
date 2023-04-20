package models;

public class Document {
	private int idDoc;
	private String reference;
	private String titre;
	private String auteur;

	public Document() {}

	public Document(String titre, String auteur) {
		this.titre = titre;
		this.auteur = auteur;
	}

	public Document(int idDoc, String reference, String titre, String auteur) {
		this.idDoc = idDoc;
		this.reference = reference;
		this.titre = titre;
		this.auteur = auteur;
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


	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean matchesSearch(String searchString) {
		return getTitre().contains(searchString) ||
				getAuteur().contains(searchString);
	}
}
