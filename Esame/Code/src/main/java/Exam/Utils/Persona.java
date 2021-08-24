package Exam.Utils;


public class Persona {

    String nome;
    String cognome;
    String tipo;
    String luogo_nascita;
    String data_nascita;
    String citta_residenza;
    String CF;
    String sport;
    String squadra;

    public Persona(String nome, String cognome, String tipo,  String luogo_nascita, String data_nascita,String citta_residenza, String CF, String sport, String squadra) {
        this.nome = nome;
        this.cognome = cognome;
        this.tipo = tipo;
        this.luogo_nascita = luogo_nascita;
        this.data_nascita =data_nascita;
        this.citta_residenza = citta_residenza;
        this.CF = CF;
        this.sport =sport;
        this.squadra=squadra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
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

    public String getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(String data_nascita) {
        this.data_nascita = data_nascita;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", data_nascita=" + data_nascita +
                ", citta_residenza='" + citta_residenza + '\'' +
                ", CF='" + CF + '\'' +
                ", sport='" + sport + '\'' +
                ", squadra='" + squadra + '\'' +
                '}';
    }
}
