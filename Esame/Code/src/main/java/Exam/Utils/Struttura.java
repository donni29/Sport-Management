package Exam.Utils;

public class Struttura {

    String nome;
    String via ;
    String num_telefono;
    String orario_apertura;
    String orario_chiusura;

    public Struttura(String nome, String via, String num_telefono, String orario_apertura, String orario_chiusura) {
        this.nome = nome;
        this.via = via;
        this.num_telefono = num_telefono;
        this.orario_apertura = orario_apertura;
        this.orario_chiusura = orario_chiusura;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNum_telefono() {
        return num_telefono;
    }

    public void setNum_telefono(String num_telefono) {
        this.num_telefono = num_telefono;
    }

    public String getOrario_apertura() {
        return orario_apertura;
    }

    public void setOrario_apertura(String orario_apertura) {
        this.orario_apertura = orario_apertura;
    }

    public String getOrario_chiusura() {
        return orario_chiusura;
    }

    public void setOrario_chiusura(String orario_chiusura) {
        this.orario_chiusura = orario_chiusura;
    }

    @Override
    public String toString() {
        return "Struttura{" +
                "nome='" + nome + '\'' +
                ", via='" + via + '\'' +
                ", num_telefono='" + num_telefono + '\'' +
                ", orario_apertura='" + orario_apertura + '\'' +
                ", orario_chiusura='" + orario_chiusura + '\'' +
                '}';
    }
}
