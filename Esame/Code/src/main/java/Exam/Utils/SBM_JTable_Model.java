package Exam.Utils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class SBM_JTable_Model extends DefaultTableModel {


private static final long serialVersionUID = 1L;
private final String[] columnNames = new String[]{"nome", "cognome","tipo","luogo di nascita","data_di_nascita","citta_di_residenza", "CF","sport","squadra"};
private final Class<?>[] columnClass = new Class<?>[]{String.class, String.class,String.class,String.class,String.class, Time.class,String.class,String.class};
private ResultSet rs;

    public SBM_JTable_Model() throws SQLException {
        DBManager.setConnection(
                Utils.JDBC_Driver,
                Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        try {
            statement.executeUpdate("SELECT * FROM Persona");
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.print(e);
        }
    }

        public void insertRow(Object[] data) {
            try {
                rs.moveToInsertRow();

                rs.updateDouble("nome", Double.parseDouble(java.util.UUID.randomUUID().toString()));
                rs.updateDouble("cognome", Double.parseDouble((String) data[1]));
                rs.updateString("tipo", java.util.UUID.randomUUID().toString());
                rs.updateDouble("luogo_nascita ", Double.parseDouble((String) data[2]));
                rs.updateString("data_di_nascita", String.valueOf(Time.valueOf((String) data[3])));
                rs.updateDouble("citta_di_residenza", Double.parseDouble(String.copyValueOf((char[]) data[4])));
                rs.updateString("CF", (String) data[5]);
                rs.updateString("sport", String.valueOf(String.valueOf(data[6])));
                rs.updateString("squadra", String.valueOf(Double.parseDouble((String) data[7])));
                rs.insertRow();
                fireTableRowsInserted(rs.getRow(), rs.getRow());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void removeRow(int firstRow, int lastRow) {
            try {
                rs.absolute(firstRow);
                for (int i = firstRow; i <= lastRow; i++) {
                    rs.deleteRow();
                }
                fireTableRowsDeleted(firstRow, lastRow);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
