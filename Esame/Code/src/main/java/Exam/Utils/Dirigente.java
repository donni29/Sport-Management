package Exam.Utils;

import java.time.LocalTime;

public class Dirigente extends Persona {
    String ruolo;
    String p_iva;

    public Dirigente(String nome_cognome, String luogo_nascita, String città_resistenza, String CF, LocalTime data_nascita, String ruolo, String p_iva) {
        super(nome_cognome, luogo_nascita, città_resistenza, CF, data_nascita);
        ruolo = ruolo;
        this.p_iva = p_iva;
    }

    public String getRuolo() {
        return ruolo;
    }

    public String getP_iva() {
        return p_iva;
    }

    public void setRuolo(String ruolo) {
        ruolo = ruolo;
    }

    public void setP_iva(String p_iva) {
        this.p_iva = p_iva;
    }

    @Override
    public String toString() {
        return "Dirigente{" +
                "Ruolo='" + ruolo + '\'' +
                ", p_iva='" + p_iva + '\'' +
                '}';
    }
}
