package Exam.Utils;

import org.joda.time.DateTime;

import java.sql.Time;
import java.time.LocalTime;

public class Atleta extends Persona {
    String sport;
    String squadra_appartenenza;

    public Atleta(String anagrafe, String tipo, String luogo_nascita, String citta_residenza, String CF, Time data_nascita, String sport, String squadra_appartenenza) {
        super(anagrafe, tipo, luogo_nascita, citta_residenza, CF, data_nascita);
        this.sport = sport;
        this.squadra_appartenenza = squadra_appartenenza;

    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSquadra_appartenenza() {
        return squadra_appartenenza;
    }

    public void setSquadra_appartenenza(String squadra_appartenenza) {
        this.squadra_appartenenza = squadra_appartenenza;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "sport='" + sport + '\'' +
                ", squadra_appartenenza='" + squadra_appartenenza + '\'' +
                ", anagrafe='" + anagrafe + '\'' +
                ", tipo='" + tipo + '\'' +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", citta_residenza='" + citta_residenza + '\'' +
                ", CF='" + CF + '\'' +
                ", data_nascita=" + data_nascita +
                '}';
    }
}
