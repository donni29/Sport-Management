package Exam.Utils;

import org.joda.time.DateTime;

import java.sql.Date;
import java.time.LocalTime;
import java.util.UUID;

public class Persona {

    String Utente;
    String luogo_nascita;
    String città_residenza;
    String CF;
    LocalTime data_nascita;

    public Persona(String Utente, String luogo_nascita, String città_residenza, String CF, LocalTime data_nascita) {
        this.Utente = Utente;
        this.luogo_nascita = luogo_nascita;
        this.città_residenza = città_residenza ;
        this.CF = CF;
        this.data_nascita = data_nascita;
    }

    public Persona(String utente, String luogo_nascita, Date data_nascita, String p_iva, String città_residenza, String cf, String sport) {
    }

    public void setUtente(String Utente) {
        this.Utente = Utente;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public void setCittà_residenza(String città_residenza) {
        this.città_residenza = città_residenza;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public void setData_nascita(LocalTime data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getUtente() {
        return Utente;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public String getCittà_residenza() {
        return città_residenza;
    }

    public String getCF() {
        return CF;
    }

    public LocalTime getData_nascita() {
        return data_nascita;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "Utente='" + Utente + '\'' +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", città_residenza='" + città_residenza + '\'' +
                ", CF='" + CF + '\'' +
                ", data_nascita=" + data_nascita +
                '}';
    }
}
