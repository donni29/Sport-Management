package Exam.Utils;

import org.joda.time.DateTime;

import java.time.LocalTime;

public class Atleta extends  Persona{
    String sport ;
    String squadra_appartenenza;
    String p_iva;

    public Atleta(String Utente, String luogo_nascita, String citta_residenza, String CF, LocalTime data_nascita, String sport, String squadra) {
        super(Utente, luogo_nascita, citta_residenza, CF, data_nascita);
        this.sport = sport;
        this.squadra_appartenenza = squadra_appartenenza;
        this.p_iva = p_iva;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setSquadra(String squadra) {
        this.squadra_appartenenza = squadra;
    }


    public String getSport() {
        return sport;
    }

    public String getSquadra() {
        return squadra_appartenenza;
    }

    public String getP_iva() {
        return p_iva;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "sport='" + sport + '\'' +
                ", squadra='" + squadra_appartenenza + '\'' +
                ", p_iva='" + p_iva + '\'' +
                '}';
    }
}
