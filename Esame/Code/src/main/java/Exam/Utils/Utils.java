package Exam.Utils;

/**
 * Class with Method and Param use in classes all around the package
 *
 * @authors Rossi Nicolò Delsante Laura
 */

import java.io.File;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Utils {

        public static String JDBC_Driver ="org.sqlite.JDBC";
        public static String JDBC_URL =String.format("jdbc:sqlite:%s",
                Paths.get(Utils.examdir(), "exam.sqlite"));
        public static String Intestazione = """
                Societ\u00E0/Associazione
                SPORTINSIEME A.S.D
                Via Don Reverberi,17/B
                42014 Castellarano RE
                P.Iva: 02510550359
                CF: 02510550359""";

        /**per la prima installazione
        public static String User= "insieme";
        public static String Password = "1234";**/

        public static List<String> options;
        public static List<String> places;
    /**
     * Method for the Initialization of Sports and Facilities' List played/used in the club
     */
        public static void List_init() throws SQLException {
            options =ListSport();
            places = ListPlaces();
        }

    /**
     * Create List of Sports in the club
     * @return - List Sports played
     * @throws SQLException - if there is no table Sport or no entry in it
     */

    public static ArrayList<String> ListSport() throws SQLException {
            ArrayList<String> options = new ArrayList<>();
            Statement statement =DBManager.getConnection().createStatement();
            try{
                ResultSet rs = statement.executeQuery("SELECT * FROM Sport");
                while(rs.next()){
                    options.add(
                            rs.getString("Name")
                            );
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return options;
        }

    public static ArrayList<String> ListPlaces() throws SQLException {
        ArrayList<String> options = new ArrayList<>();
        Statement statement =DBManager.getConnection().createStatement();
        try{
            ResultSet rs = statement.executeQuery("SELECT * FROM Struttura");
            while(rs.next()){
                options.add(
                        rs.getString("nome")
                );
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }


    public static String examdir() {
            String path = String.format("%s%s%s%s%s", System.getProperty("user.home"), System.getProperty("file.separator"),
                    "Desktop", System.getProperty("file.separator"), "Exam");
            new File(path).mkdirs();
            return path;
        }
}

