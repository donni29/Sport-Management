package Exam.Utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        /**per la prima installazione in teoria
        public static String User= "insieme";
        public static String Password = "1234";**/

        public static List<String> options;

        public static void List_init() throws SQLException {
            options =ListSport();
        }

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


    public static String examdir() {
            String path = String.format("%s%s%s%s%s", System.getProperty("user.home"), System.getProperty("file.separator"),
                    "Desktop", System.getProperty("file.separator"), "Exam");
            new File(path).mkdirs();
            return path;
        }

        // eliminare tutto questo sotto prima dell'esame perchè non serve a nulla

        public static UUID asUUID(byte[] bytes) {
            ByteBuffer bb = ByteBuffer.wrap(bytes);
            long firstLong = bb.getLong();
            long secondLong = bb.getLong();
            return new UUID(firstLong, secondLong);
        }

        public static byte[] asBytes(UUID uuid) {
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(uuid.getMostSignificantBits());
            bb.putLong(uuid.getLeastSignificantBits());
            return bb.array();
        }
}

