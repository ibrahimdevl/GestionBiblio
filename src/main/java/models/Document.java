package models;

public class Document {
	private int documentId;
	private String reference;
	private String title;
	private String author;

	public Document() {}

	public Document(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public Document(int documentId, String reference, String title, String author) {
		this.documentId = documentId;
		this.reference = reference;
		this.title = title;
		this.author = author;
	}

	@Override
	public String toString() {
		return "Document{" +
				"idDoc=" + documentId +
				", reference='" + reference + '\'' +
				", titre='" + title + '\'' +
				", auteur='" + author + '\'' +
				'}';
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean matchesSearch(String searchString) {
		return getTitle().contains(searchString) ||
				getAuthor().contains(searchString);
	}
}
