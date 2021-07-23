package Exam.Utils;

import org.joda.time.DateTime;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class Persona {

    String anagrafe;
    String tipo;
    String luogo_nascita;
    String citta_residenza;
    String CF;
    Time data_nascita;

    public Persona(String anagrafe, String tipo, String luogo_nascita, String citta_residenza, String CF, Time data_nascita) {
        this.anagrafe = anagrafe;
        this.tipo = tipo;
        this.luogo_nascita = luogo_nascita;
        this.citta_residenza = citta_residenza;
        this.CF = CF;
        this.data_nascita = data_nascita;
    }

    public String getAnagrafe() {
        return anagrafe;
    }

    public void setAnagrafe(String anagrafe) {
        this.anagrafe = anagrafe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public String getCitta_residenza() {
        return citta_residenza;
    }

    public void setCitta_residenza(String citta_residenza) {
        this.citta_residenza = citta_residenza;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public Time getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Time data_nascita) {
        this.data_nascita = data_nascita;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "anagrafe='" + anagrafe + '\'' +
                ", tipo='" + tipo + '\'' +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", citta_residenza='" + citta_residenza + '\'' +
                ", CF='" + CF + '\'' +
                ", data_nascita=" + data_nascita +
                '}';
    }
}
