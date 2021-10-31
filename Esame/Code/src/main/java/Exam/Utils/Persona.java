package Exam.Utils;

/**
 * Class to describe a single Member of the Club
 *<p>
 *The Constructor Persona - Setting nome, cognome, tipo, luogo_nascita, data_nascita, residenza, CF, sport, squadra, telefono
 *<p>
 *The class have also to implement the following methods:
 *getters for nome, cognome, tipo, luogo_nascita, data_nascita, residenza, CF, sport, squadra, telefono
 *toString() and toString2() for printing nome, cognome, tipo, luogo_nascita, data_nascita, residenza, CF, sport, squadra, telefono in two different version
 *<p>
 * @authors Rossi Nicolò Delsante Laura
 */

public class Persona {

    String nome;
    String cognome;
    String tipo;
    String luogo_nascita;
    String data_nascita;
    String residenza;
    String CF;
    String sport;
    String squadra;
    String telefono;

    public Persona(String nome, String cognome, String tipo,  String luogo_nascita, String data_nascita,String residenza, String CF, String sport, String squadra, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.tipo = tipo;
        this.luogo_nascita = luogo_nascita;
        this.data_nascita =data_nascita;
        this.residenza =residenza;
        this.CF = CF;
        this.sport =sport;
        this.squadra=squadra;
        this.telefono=telefono;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public String getData_nascita() {
        return data_nascita;
    }

    public String getresidenza() {
        return residenza;
    }

    public String getCF() {
        return CF;
    }

    public String getSport() { return sport; }

    public String getSquadra() {
        return squadra;
    }

    public String getTelefono() { return telefono; }

    @Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", data_nascita=" + data_nascita +
                ", residenza='" + residenza + '\'' +
                ", CF='" + CF + '\'' +
                ", sport='" + sport + '\'' +
                ", squadra='" + squadra + '\'' +
                ", telefono='" + telefono +'\''+
                '}';
    }

    public String toString2(){
        return  cognome + " " + nome + "\n" +"NATO A: "+ luogo_nascita + "\n" +"IL: " +
                data_nascita + "\n" + "RESIDENTE A: " + residenza + "\n" +"CF: "+ CF;
    }
}
