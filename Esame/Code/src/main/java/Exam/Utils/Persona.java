package Exam.Utils;


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

    public String getresidenza() {
        return residenza;
    }

    public void setresidenza(String residenza) {
        this.residenza = residenza;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public String getSport() { return sport; }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }


    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

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
