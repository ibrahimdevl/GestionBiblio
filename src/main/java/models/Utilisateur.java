package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {
    private int idUtlstr;
    private String nomUtlstr;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String address;
    private int numTel;

    public Utilisateur() {}

    public Utilisateur(String nomUtlstr, String motDePasse, String nom, String prenom, String address,
                       int numTel) {
        this.nomUtlstr = nomUtlstr;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.address = address;
        this.numTel = numTel;
    }

    public Utilisateur(int idUtlstr, String nomUtlstr, String motDePasse, String nom, String prenom, String address, int numTel) {
        this.idUtlstr = idUtlstr;
        this.nomUtlstr = nomUtlstr;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.address = address;
        this.numTel = numTel;
    }

    @Override
    public String toString() {
        return  "idUtlstr=" + idUtlstr +
                ", nomUtlstr='" + nomUtlstr + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", address='" + address + '\'' +
                ", numTel=" + numTel ;
    }

    public int getIdUtlstr() {
        return idUtlstr;
    }
    public void setIdUtlstr(int idUtlstr) {
        this.idUtlstr = idUtlstr;
    }
    public String getNomUtlstr() {
        return nomUtlstr;
    }
    public void setNomUtlstr(String nomUtlstr) {
        this.nomUtlstr = nomUtlstr;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getNumTel() {
        return numTel;
    }
    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public void createUserInstance(ResultSet userInfoResult) throws SQLException {
        setIdUtlstr(userInfoResult.getInt(1));
        setNomUtlstr(userInfoResult.getString(2));
        setMotDePasse(userInfoResult.getString(3));
        setNom(userInfoResult.getString(4));
        setPrenom(userInfoResult.getString(5));
        setAddress(userInfoResult.getString(6));
        setNumTel(userInfoResult.getInt(7));
    }


}
