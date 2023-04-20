package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class EmpruntPretData {
    private int id;
    private int idDoc;
    private int idAdh;
    private StringProperty titre;
    private StringProperty nom;
    private ObjectProperty<LocalDate> date;

    public EmpruntPretData(String titre, String nom, LocalDate date) {
        this.titre = new SimpleStringProperty(titre);
        this.nom = new SimpleStringProperty(nom);
        this.date = new SimpleObjectProperty<>(date);
    }

    public EmpruntPretData(int id, int idDoc, int idAdh, String titre, String nom, LocalDate date) {
        this.id = id;
        this.idDoc=idDoc;
        this.idAdh = idAdh;
        this.titre = new SimpleStringProperty(titre);
        this.nom = new SimpleStringProperty(nom);
        this.date = new SimpleObjectProperty<>(date);
    }

    public StringProperty titreProperty() {
        return titre;
    }

    public StringProperty nomProperty() {
        return nom;
    }


    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public int idProperty(){
        return id;
    }
    public int idDocProperty(){
        return idDoc;
    }
    public int idAdhProperty(){
        return idAdh;
    }
}

