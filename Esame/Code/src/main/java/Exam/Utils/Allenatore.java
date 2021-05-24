package Exam.Utils;

import org.joda.time.DateTime;

import java.time.LocalTime;

public class Allenatore extends Persona{
    String allena_Squadra;
    DateTime scadenza_contratto;

    public Allenatore(String nome_cognome, String luogo_nascita, String città_resistenza, String CF, LocalTime data_nascita, String allena_Squadra, DateTime scadenza_contratto) {
        super(nome_cognome, luogo_nascita, città_resistenza, CF, data_nascita);
        this.allena_Squadra = allena_Squadra;
        this.scadenza_contratto = scadenza_contratto;
    }

    public String getAllena_Squadra() {
        return allena_Squadra;
    }

    public DateTime getScadenza_contratto() {
        return scadenza_contratto;
    }

    public void setAllena_Squadra(String allena_Squadra) {
        this.allena_Squadra = allena_Squadra;
    }

    public void setScadenza_contratto(DateTime scadenza_contratto) {
        this.scadenza_contratto = scadenza_contratto;
    }

    @Override
    public String toString() {
        return "Allenatore{" +
                "allena_Squadra='" + allena_Squadra + '\'' +
                ", scadenza_contratto=" + scadenza_contratto +
                '}';
    }
}
