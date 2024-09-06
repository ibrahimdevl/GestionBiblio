package models;

import java.sql.ResultSet;
import java.util.Date;

public class Loan {
	private int loanId;
	private Date loanDate;
	private Date returnDate;
	private int memberId;
	private int librarianId;
	private int document_Id;
	private boolean isReturned;

	public Loan() {}

	public Loan(int loanId, Date loanDate, Date returnDate, int memberId, int librarianId, int document_Id, boolean isReturned) {
		this.loanId = loanId;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.memberId = memberId;
		this.librarianId = librarianId;
		this.document_Id = document_Id;
		this.isReturned = isReturned;
	}

	@Override
	public String toString() {
		return "Emprunt{" +
				"idEmprunt=" + loanId +
				", dateEmprunt=" + loanDate +
				", dateRetour=" + returnDate +
				", adherent=" + memberId +
				", bibliothecaire=" + librarianId +
				", document=" + document_Id +
				", retournee=" + isReturned +
				'}';
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getLibrarianId() {
		return librarianId;
	}

	public void setLibrarianId(int librarianId) {
		this.librarianId = librarianId;
	}

	public int getDocument_Id() {
		return document_Id;
	}

	public void setDocument_Id(int document_Id) {
		this.document_Id = document_Id;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean returned) {
		this.isReturned = returned;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public void createEmpruntInstance(ResultSet resultSet) {

	}
}
