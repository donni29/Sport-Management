package Exam;

import Exam.Utils.DBManager;

import Exam.Utils.Utils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ArchivioPanel extends JPanel  {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private JPanel Archivio;
    private JButton Button;
    JTable tResults;
    JButton btInsert;
    JButton btDelete;
    JTextArea text= new JTextArea();


    public  ArchivioPanel () {
        Archivio = new JPanel();
            /*Button = new JButton( "CAVOLI");
            Button.setSize(20,20);
            Archivio.add(Button);
            add(Archivio);*/

            try {
                testconnection();
                JScrollPane scrollPane =new JScrollPane();
                Archivio.add(new JScrollPane( getTable("Select * from Persona")));

            }catch (SQLException e){
                JOptionPane.showMessageDialog(this,"Database Error");
            }


            add(Archivio);
            setVisible(true);
    }


    public  void testconnection() throws SQLException{
        DBManager.setConnection(Utils.JDBC_Driver,Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Persona");
        } catch (SQLException e) {
            System.out.println("non funziona");
        }

    }

    public JTable getTable(String query) throws SQLException {
        JTable t = new JTable();
        DefaultTableModel dm = new DefaultTableModel();

        ResultSet rs = DBManager.getConnection().createStatement().executeQuery(query);
        ResultSetMetaData rsMetaData = rs.getMetaData();


        //((AbstractTableModel) t.getModel()).fireTableCellUpdated(rs.getRow(),0);
        ((AbstractTableModel) t.getModel()).fireTableDataChanged();

        // get columns metadata
        int cols = rsMetaData.getColumnCount();
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

        btInsert = new JButton("Insert...");
        //btInsert.addActionListener(this);
        btDelete = new JButton("Remove");
        //btDelete.addActionListener(this);


        Archivio.add(btInsert);
        Archivio.add(btDelete);

        setLayout(new BorderLayout());
        add(Archivio, BorderLayout.SOUTH);
        add(new JScrollPane(tResults), BorderLayout.CENTER);

        setSize(600, 400);
        add(Archivio);
        setVisible(true);
        t.setModel(dm);
        t.setGridColor(Color.BLACK);
        return t;
    }

        /*try {
            tResults = new JTable();
            tResults.setModel(new SBM_JTable_Model());
            tResults.getModel().addTableModelListener(this);
        } catch (SQLException | NullPointerException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Database Error!");
        }


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btInsert) {
            String[] v = JOptionPane.showInputDialog(this, "Insert sausage (length;diameter;weight;quality)")
                    .split(";");
            ((SBM_JTable_Model) tResults.getModel()).insertRow(v);
        }

        if (e.getSource() == btDelete) {
            ((SBM_JTable_Model) tResults.getModel()).removeRow(tResults.getSelectedRow(),
                    tResults.getSelectedRow());
        }
    }

        if (e.getSource() == btDelete) {
            ((SBM_JTable_Model) tResults.getModel()).removeRow(tResults.getSelectedRow(),
                    tResults.getSelectedRow());
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("The table has been modified!");
    }*/

}




