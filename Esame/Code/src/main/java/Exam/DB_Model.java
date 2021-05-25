package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Persona;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;

public class DB_Model {
    ResultSet rs;
    public DB_Model() throws SQLException{
        DBManager.setConnection(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/jdbc_prova?user=Nico&password=10");
        Statement statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        try{
            rs = statement.executeQuery("SELECT * FROM ATLETA");
        } catch (SQLException e){
            /* TOGLIERE ALLA FINE DEL PROGETTO */
            statement.executeQuery("DROP TABLE IF EXISTS atleta");
            statement.executeQuery("CREATE TABLE ATLETA ("+" utente VARCHAR(50),"+" luogo_nascita VARCHAR(50),"+"data_nascita DATE"+"p_iva VARCHAR(50) NOT NULL UNIQUE"+"città_residenza VARCHAR(50),"+"CF VARCHAR(50)PRIMARY KEY,"+" sport VARCHAR(50)"+"squadra VARCHAR(50))");
            statement.executeQuery("INSERT INTO atleta (utente, luogo_nascita, data_nascita, p_iva, città_residenza, CF, sport, squadra) VALUES ('Piero Giovanni','Ladispoli','17/12/1995','0764352056C','Modena','FSGSGP47C22E764C','Tennis','Circolo Modena')");
            statement.executeQuery("INSERT INTO atleta (utente, luogo_nascita, città_residenza,data_nascita, p_iva, CF, sport) VALUES ('Cavoli Racho','Rodi','17/11/1991','0764357052C','Sassuolo','RNXCYH38D29G463W','Calcio','Valsa Calcio')");
            statement.executeQuery("INSERT INTO atleta (utente, luogo_nascita, città_residenza,data_nascita, p_iva, CF, sport) VALUES ('Giovanni Sforza','CentoCelle','14/02/2001','0764352086F','Reggio Emilia','NPBBVZ28P28I700E','Nuoto','Piscina Reggio Emilia')");
        }
    }
    public Persona getSelectedItem(){
        try{
            return new Persona(rs.getString("Utente"),rs.getString("luogo_nascita"),
                    rs.getDate("data_nascita"),rs.getString("p_iva"),
                    rs.getString("città_residenza"),rs.getString("CF"),rs.getString("sport"));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getSelectedIndex(){
        try{
            return rs.getRow();
        }catch (SQLException e){
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
   public void insert(String utente, String luogo_nascita, String citta_residenza, String CF, LocalTime data_nascita, String sport, String squadra, String p_iva){
        try{
            rs.moveToInsertRow();
            rs.updateString("utente",utente);
            rs.updateString("luogo_nascita",luogo_nascita);
            rs.updateString("citta_residenza",citta_residenza);
            rs.updateString("CF",CF);
            rs.updateTime("data_nascita", Time.valueOf(data_nascita));
            rs.updateString("sport",sport);
            rs.updateString("squadra",squadra);
            rs.updateString("p_iva",p_iva);
            rs.insertRow();
            rs.last();
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

}

