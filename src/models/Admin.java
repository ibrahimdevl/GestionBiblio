package models;

public class Admin extends Utilisateur{
    private String departement;
    private String email;

    public Admin(){}

    public Admin(String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel, String departement, String email) {
        super(nomUtlstr, motDePasse, nom, prenom, address, numTel);
        this.departement = departement;
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Admin{" +
                "departement='" + departement + '\'' +
                ", email='" + email + '\'' +
                "} ";
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
