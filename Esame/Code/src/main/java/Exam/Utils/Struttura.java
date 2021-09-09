package Exam.Utils;

public class Struttura {

    String nome;
    String via ;
    String num_telefono;
    String orario_mattina;
    String orario_pomeriggio;

    public Struttura(String nome, String via, String num_telefono, String orario_mattina, String orario_pomeriggio) {
        this.nome = nome;
        this.via = via;
        this.num_telefono = num_telefono;
        this.orario_mattina = orario_mattina;
        this.orario_pomeriggio = orario_pomeriggio;
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

    public String getOrario_mattina() {
        return orario_mattina;
    }

    public void setOrario_mattina(String orario_mattina) {
        this.orario_mattina = orario_mattina;
    }

    public String getOrario_pomeriggio() {
        return orario_pomeriggio;
    }

    public void setOrario_pomeriggio(String orario_pomeriggio) {
        this.orario_pomeriggio = orario_pomeriggio;
    }

    @Override
    public String toString() {
        return "Struttura{" +
                "nome='" + nome + '\'' +
                ", via='" + via + '\'' +
                ", num_telefono='" + num_telefono + '\'' +
                ", orario_mattina='" + orario_mattina + '\'' +
                ", orario_pomeriggio='" + orario_pomeriggio + '\'' +
                '}';
    }
}
