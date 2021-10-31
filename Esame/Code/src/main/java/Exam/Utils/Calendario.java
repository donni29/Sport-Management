package Exam.Utils;

/**
 * Class to describe a Calendar for every Facility of the Club
 *<p>
 *The Constructor Calendario - Setting nome_struttura, info_struttura, inizio_prenotazione, fine_prenotazione, numero_ricursioni
 *<p>
 *The class have also to implement the following methods:
 *getters for Setting nome_struttura, info_struttura, inizio_prenotazione, fine_prenotazione, numero_ricursioni
 *toString() for printing Setting nome_struttura, info_struttura, inizio_prenotazione, fine_prenotazione, numero_ricursioni
 *<p>
 * @authors Rossi Nicolò Delsante Laura
 */

import com.mindfusion.common.DateTime;

public class Calendario {
    String nome_struttura;
    String info_struttura;
    String descrizione_prenotazione;
    DateTime inizio_prenotazione;
    DateTime fine_prenotazione;
    int numero_ricursioni;

    public Calendario(String nome_struttura, String info_struttura, String descrizione_prenotazione, DateTime inizio_prenotazione, DateTime fine_prenotazione, int numero_ricursioni) {
        this.nome_struttura = nome_struttura;
        this.info_struttura = info_struttura;
        this.descrizione_prenotazione = descrizione_prenotazione;
        this.inizio_prenotazione = inizio_prenotazione;
        this.fine_prenotazione = fine_prenotazione;
        this.numero_ricursioni = numero_ricursioni;
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

    public String getDescrizione_prenotazione() {
        return descrizione_prenotazione;
    }

    public void setDescrizione_prenotazione(String descrizione_prenotazione) {
        this.descrizione_prenotazione = descrizione_prenotazione;
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

    public int getNumero_ricursioni() {
        return numero_ricursioni;
    }

    public void setNumero_ricursioni(int numero_ricursioni) {
        this.numero_ricursioni = numero_ricursioni;
    }

    @Override
    public String toString() {
        return "Calendario{" +
                "nome_struttura='" + nome_struttura + '\'' +
                ", info_struttura='" + info_struttura + '\'' +
                ", descrizione_prenotazione='" + descrizione_prenotazione + '\'' +
                ", inizio_prenotazione=" + inizio_prenotazione +
                ", fine_prenotazione=" + fine_prenotazione +
                ", numero_ricursioni=" + numero_ricursioni +
                '}';
    }
}
