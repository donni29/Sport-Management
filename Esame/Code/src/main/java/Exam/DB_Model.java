package Exam;

import Exam.Utils.*;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;

public class DB_Model {
    ResultSet rs;
    Statement statement;

    public DB_Model() throws SQLException {
        DBManager.setConnection(
                 Utils.JDBC_Driver,
                 Utils.JDBC_URL);
        statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Persona");
        } catch (SQLException e) {
            /* TOGLIERE ALLA FINE DEL PROGETTO */
            statement.executeUpdate("DROP TABLE IF EXISTS Persona");
            statement.executeUpdate("CREATE TABLE Persona (" + " nome VARCHAR(50)," +"cognome VARCHAR(50)," + " tipo varchar(11) check( tipo like 'Atleta' or tipo like 'Dirigente' or tipo like 'Allenatore'), " + " luogo_nascita VARCHAR(50)," + "data_nascita DATE," + "città_residenza VARCHAR(50)," + "CF VARCHAR(50)PRIMARY KEY," + " sport VARCHAR(50)," + "squadra VARCHAR(50))");
            statement.executeUpdate("INSERT INTO Persona (nome,cognome, tipo, luogo_nascita, data_nascita, città_residenza, CF, sport, squadra) VALUES ('Piero', 'Giovanni','Atleta','Ladispoli','17/12/1950','Modena','0764352056C','Tennis','Circolo Modena')");
            statement.executeUpdate("INSERT INTO Persona (nome,cognome,tipo, luogo_nascita,data_nascita, città_residenza, CF, sport,squadra) VALUES ('Cavoli', 'Racho','Dirigente','Rodi','17/11/1991','Sassuolo','0764357052C','Calcio','Valsa Calcio')");
            statement.executeUpdate("INSERT INTO Persona (nome,cognome,tipo, luogo_nascita,data_nascita, città_residenza,CF, sport,squadra) VALUES ('Giovanni', 'Sforza','Allenatore','CentoCelle','14/02/2001','Reggio Emilia','0764352086F','Nuoto','Piscina Reggio Emilia')");

        }
    }

    public Persona getSelectedItem() {
        try {
            return new Persona(rs.getString("nome"),rs.getString("cognome"),rs.getString("tipo"), rs.getString("luogo_nascita"),rs.getDate("data_nascita"),
                    rs.getString("città_residenza"), rs.getString("CF"),rs.getString("sport"),rs.getString("squadra"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSelectedIndex() {
        try {
            return rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void first() {
        try {
            rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void last() {
        try {
            rs.last();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void next() {
        try {
            if (!rs.isLast())
                rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void previous() {
        try {
            if (!rs.isFirst())
                rs.previous();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String nome,String cognome,String tipo, String luogo_nascita, String citta_residenza, String CF, LocalTime data_nascita, String sport, String squadra) {
        try {
            rs.moveToInsertRow();
            rs.updateString("nome", nome);
            rs.updateString("cognome", cognome);
            rs.updateString("tipo", tipo);
            rs.updateString("luogo_nascita", luogo_nascita);
            rs.updateString("citta_residenza", citta_residenza);
            rs.updateString("CF", CF);
            rs.updateTime("data_nascita", Time.valueOf(data_nascita));
            rs.updateString("sport", sport);
            rs.updateString("squadra", squadra);
            rs.insertRow();
            rs.last();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

