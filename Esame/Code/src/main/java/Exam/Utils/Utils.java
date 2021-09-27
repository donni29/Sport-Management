package Exam.Utils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.UUID;

public class Utils {

        public static String JDBC_Driver ="org.sqlite.JDBC";
        public static String JDBC_URL =String.format("jdbc:sqlite:%s",
                Paths.get(Utils.examdir(), "exam.sqlite").toString());
        static Connection connection;
        public static String Intestazione = "Societ\u00E0/Associazione" + "\n" + "SPORTINSIEME A.S.D" + "\n" + "Via Don Reverberi,17/B" + "\n"+ "42014 Castellarano RE" +
            "\n" + "P.Iva: 02510550359" + "\n" + "CF: 02510550359";

        public static String User = "Elisa";
        public static String Password = "insieme";

    public static String examdir() {
            String path = String.format("%s%s%s%s%s", System.getProperty("user.home"), System.getProperty("file.separator"),
                    "Desktop", System.getProperty("file.separator"), "Exam");
            new File(path).mkdirs();
            return path;
        }

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

