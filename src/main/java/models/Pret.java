package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Pret {
    private int idPret;
    private int idDocument;
    private int idAdherent;
    private Date datePret;
    private boolean validee;

    public Pret() {}

    public Pret(int idPret, int idDocument, int idAdherent, Date datePret, boolean validee) {
        this.idPret = idPret;
        this.idDocument = idDocument;
        this.idAdherent = idAdherent;
        this.datePret = datePret;
        this.validee = validee;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "idPret=" + idPret +
                ", document=" + idDocument +
                ", adherent=" + idAdherent +
                ", datePret=" + datePret +
                ", validee=" + validee +
                '}';
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public int getIdAdherent() {
        return idAdherent;
    }

    public void setIdAdherent(int idAdherent) {
        this.idAdherent = idAdherent;
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

    public int getIdPret() {
        return idPret;
    }

    public void setIdPret(int idPret) {
        this.idPret = idPret;
    }

    public void createPretInstance(ResultSet PretInfoResult) throws SQLException {
        setIdPret(PretInfoResult.getInt(1));
        setIdDocument(PretInfoResult.getInt(2));
        setIdAdherent(PretInfoResult.getInt(3));
        setDatePret(PretInfoResult.getDate(4));
        setValidee(false);
    }
}
