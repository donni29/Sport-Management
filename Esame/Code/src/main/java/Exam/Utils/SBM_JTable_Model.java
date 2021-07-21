package Exam.Utils;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SBM_JTable_Model extends AbstractTableModel {

private static final long serialVersionUID = 1L;
private final String[] columnNames = new String[]{"utente","nome", "cognome","citta_di_residenza", "CF","data_di_nascita","sport","squadra"};
private final Class<?>[] columnClass = new Class<?>[]{String.class, String.class,String.class,String.class,String.class,Double.class,String.class,String.class};
private ResultSet rs;

    public SBM_JTable_Model() throws SQLException {
        DBManager.setConnection(
                Utils.JDBC_Driver,
                Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        try {
            rs = statement.executeQuery("SELECT * FROM atleta");
            rs.first();
        } catch (SQLException e) {
            System.out.print("non Funziona");
        }
    }

        public void insertRow(Object[] data) {
            try {
                rs.moveToInsertRow();
                rs.updateString("utente", java.util.UUID.randomUUID().toString());
                rs.updateDouble("nome", Double.parseDouble(java.util.UUID.randomUUID().toString()));
                rs.updateDouble("cognome", Double.parseDouble((String) data[1]));
                rs.updateDouble("citta_di_residenza", Double.parseDouble((String) data[2]));
                rs.updateString("CF", (String) data[3]);
                rs.updateString("data_di_nascita", String.valueOf(Double.parseDouble((String) data[4])));
                rs.updateString("sport", String.valueOf(Double.parseDouble((String) data[5])));
                rs.updateString("squadra", String.valueOf(Double.parseDouble((String) data[4])));
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
