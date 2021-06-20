package Exam.Utils;

import org.joda.time.DateTime;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalTime;
import java.util.UUID;

public class Persona {

    String Utente;
    String luogo_nascita;
    String citta_residenza;
    String CF;
    LocalTime data_nascita;

    public Persona(String Utente, String luogo_nascita, String citta_residenza, String CF, LocalTime data_nascita) {
        this.Utente = Utente;
        this.luogo_nascita = luogo_nascita;
        this.citta_residenza = citta_residenza;
        this.CF = CF;
        this.data_nascita = data_nascita;
    }

    public Persona(String utente, String luogo_nascita, Date data_nascita, String p_iva, String citta_residenza, String cf, String sport) {
    }

    public void setUtente(String Utente) {
        this.Utente = Utente;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public void setCitta_residenza(String citta_residenza) {
        this.citta_residenza = citta_residenza;
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

    public String getCitta_residenza() {
        return citta_residenza;
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
                ", citta_residenza='" + citta_residenza + '\'' +
                ", CF='" + CF + '\'' +
                ", data_nascita=" + data_nascita +
                '}';
    }
}
