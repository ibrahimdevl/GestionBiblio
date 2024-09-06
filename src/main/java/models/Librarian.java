package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Librarian extends User {
	private Date hireDate;
	private float salary;

	public Librarian() {}

	public Librarian(String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel, Date hireDate, float salaire) {
		super(nomUtlstr, motDePasse, nom, prenom, address, numTel);
		this.hireDate = hireDate;
		this.salary = salaire;
	}

	@Override
	public String toString() {
		return super.toString()+
				"Bibliothecaire{" +
				"dateEmbauche=" + hireDate +
				", salaire=" + salary +
				"} ";
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public void createLibrarianInstance(ResultSet adherentInfoResult) throws SQLException {
		createUserInstance(adherentInfoResult);
		setHireDate(adherentInfoResult.getDate(8));
		setSalary(adherentInfoResult.getFloat(9));
	}

	public boolean matchesSearch(String searchString) {
		return getLastName().contains(searchString) ||
				getFirstName().contains(searchString) ||
				String.valueOf(getHireDate()).contains(searchString);
	}


}
