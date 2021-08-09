package Exam;

import Exam.Utils.DBManager;

import Exam.Utils.Utils;

import javax.swing.*;

import javax.swing.table.*;
import java.awt.*;

import java.io.Serial;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Dimension;

public class ArchivioPanel extends JPanel  {

    @Serial
    private static final long serialVersionUID = 1L;


    JButton btInsert;
    JButton btDelete;



    public  ArchivioPanel () {
        JPanel archivio = new JPanel();

        try {
            testconnection();
            archivio.add(new JScrollPane( getTable("Select * from Persona")));



        }catch (SQLException e){
            JOptionPane.showMessageDialog(this,"Database Error");
            System.out.println(e);
        }

        add(archivio);
        setVisible(true);
    }


    public  void testconnection() throws SQLException{
        DBManager.setConnection(Utils.JDBC_Driver,Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Persona");
        } catch (SQLException e1) {
            System.out.println("SQL Exception");

        }

    }

    public JTable getTable(String query) throws SQLException {

        DefaultTableModel dm =new DefaultTableModel();
        JTable t = new JTable(new MyTableModel());
        t.setFillsViewportHeight(true);
        t.setPreferredScrollableViewportSize(new Dimension(1000,1000));

        ResultSet rs = DBManager.getConnection().createStatement().executeQuery(query);
        ResultSetMetaData rsMetaData = rs.getMetaData();

        // get columns metadata
        int cols =t.getColumnCount();
        String[] c = new String[cols];
        for (int i = 0; i < cols; i++) {
            c[i] = rsMetaData.getColumnName(i + 1);
            dm.addColumn(c[i]);
        }

        // Get rows
        Object[] row = new Object[cols];
        while (rs.next()) {
            for (int i = 0; i < cols; i++) {
                row[i] = rs.getString(i + 1);
            }
            dm.addRow(row);
        }
        t.setModel(dm);
        JPanel p2 =new JPanel(new GridLayout(1,2));
        btInsert = new JButton("Insert...");
        //btInsert.addActionListener(this);
        btDelete = new JButton("Remove");
        //btDelete.addActionListener(this);


        p2.add(btInsert);
        p2.add(btDelete);


        //add(Archivio, BorderLayout.SOUTH);
        //add(new JScrollPane(tResults), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(p2, BorderLayout.PAGE_END);
        setVisible(true);
        //t.setAutoResizeMode(t.AUTO_RESIZE_ALL_COLUMNS);
        t.setGridColor(Color.BLACK);
        return t;
    }


    static class MyTableModel extends AbstractTableModel{
        private final String[] columnNames ={
                "Nome",
                "Cognome",
                "Tipo",
                "Luogo di nascita",
                "Data di nascita",
                "Città di Residenza",
                "CF",
                "Sport",
                "Squadra"
        };

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }
    }


}




