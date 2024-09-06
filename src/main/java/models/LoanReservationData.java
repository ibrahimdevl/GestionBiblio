package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class LoanReservationData {
    private int id;
    private int documentId;
    private int memberId;
    private final StringProperty title;
    private final StringProperty name;
    private final ObjectProperty<LocalDate> date;

    public LoanReservationData(String title, String name, LocalDate date) {
        this.title = new SimpleStringProperty(title);
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
    }

    public LoanReservationData(int id, int idDoc, int memberId, String title, String name, LocalDate date) {
        this.id = id;
        this.documentId =idDoc;
        this.memberId = memberId;
        this.title = new SimpleStringProperty(title);
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty nameProperty() {
        return name;
    }


    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public int idProperty(){
        return id;
    }
    public int documentIdProperty(){
        return documentId;
    }
    public int memberIdProperty(){
        return memberId;
    }
}

