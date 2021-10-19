package Exam.Utils;


import com.mindfusion.common.DateTime;

import java.sql.Date;

public class Calendario {
    String nome_struttura;
    String info_struttura;
    DateTime inizio_prenotazione;
    DateTime fine_prenotazione;

    public Calendario(String nome_struttura, String info_prenotazione, DateTime inizio_prenotazione, DateTime fine_prenotazione) {
        this.nome_struttura = nome_struttura;
        this.info_struttura = info_prenotazione;
        this.inizio_prenotazione = inizio_prenotazione;
        this.fine_prenotazione = fine_prenotazione;
    }

    public String getNome_struttura() {
        return nome_struttura;
    }

    public void setNome_struttura(String nome_struttura) {
        this.nome_struttura = nome_struttura;
    }

    public String getInfo_struttura() {
        return info_struttura;
    }

    public void setInfo_struttura(String info_struttura) {
        this.info_struttura = info_struttura;
    }

    public DateTime getInizio_prenotazione() {
        return inizio_prenotazione;
    }

    public void setInizio_prenotazione(DateTime inizio_prenotazione) {
        this.inizio_prenotazione = inizio_prenotazione;
    }

    public DateTime getFine_prenotazione() {
        return fine_prenotazione;
    }

    public void setFine_prenotazione(DateTime fine_prenotazione) {
        this.fine_prenotazione = fine_prenotazione;
    }

    @Override
    public String toString() {
        return "Calendario{" +
                "nome_struttura='" + nome_struttura + '\'' +
                ", info_struttura='" + info_struttura + '\'' +
                ", inizio_prenotazione=" + inizio_prenotazione +
                ", fine_prenotazione=" + fine_prenotazione +
                '}';
    }
}
