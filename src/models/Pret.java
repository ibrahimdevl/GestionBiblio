package models;

import java.util.Date;

public class Pret {
    private Document document;
    private Adherent adherent;
    private Date datePret;
    private boolean validee;

    public Pret() {}

    public Pret(Document document, Adherent adherent, Date datePret, boolean validee) {
        this.document = document;
        this.adherent = adherent;
        this.datePret = datePret;
        this.validee = validee;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "document=" + document +
                ", adherent=" + adherent +
                ", datePret=" + datePret +
                ", validee=" + validee +
                '}';
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public Date getDatePret() {
        return datePret;
    }

    public void setDatePret(Date datePret) {
        this.datePret = datePret;
    }

    public boolean isValidee() {
        return validee;
    }

    public void setValidee(boolean validee) {
        this.validee = validee;
    }
}
