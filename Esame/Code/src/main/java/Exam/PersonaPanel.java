package Exam;

import Exam.Utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonaPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final JButton btnRemove;
    private final JButton btnInsert;
    private final JTextField tfNome;
    private final JTextField tfcognome;
    private final JTextField tftipo;
    private final JTextField tfdatanascita;
    private final JTextField tfluogonascita;
    private final JTextField tfcittadiresidenza;
    private final JTextField tfCF;
    private final JTextField tfsport;
    private final JTextField tfsquadra;


    private List<Persona> listPersona;
    private int selectedStudentIndex = 0;

    public PersonaPanel(String query) throws SQLException {
        super();

        btnRemove = new JButton("Delete");
        btnRemove.addActionListener(this);
        btnInsert = new JButton("Insert");
        btnInsert.addActionListener(this);


        tftipo = new JTextField();
        tftipo.addActionListener(this);
        tfNome = new JTextField();
        tfNome.addActionListener(this);
        tfcognome = new JTextField();
        tfcognome.addActionListener(this);
        tfdatanascita = new JTextField();
        tfdatanascita.addActionListener(this);
        tfluogonascita = new JTextField();
        tfluogonascita.addActionListener(this);
        tfcittadiresidenza = new JTextField();
        tfcittadiresidenza.addActionListener(this);
        tfCF = new JTextField();
        tfCF.addActionListener(this);
        tfsport = new JTextField();
        tfsport.addActionListener(this);
        tfsquadra = new JTextField();
        tfsquadra.addActionListener(this);



        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(9, 2));
        p1.add(new JLabel("Tipo"));
        p1.add(tftipo);
        p1.add(new JLabel("Nome"));
        p1.add(tfNome);
        p1.add(new JLabel("Cognome"));
        p1.add(tfcognome);
        p1.add(new JLabel("Data di Nascita"));
        p1.add(tfdatanascita);
        p1.add(new JLabel("Luogo di Nascita"));
        p1.add(tfluogonascita);
        p1.add(new JLabel("Città di Residenza"));
        p1.add(tfcittadiresidenza);
        p1.add(new JLabel("CF"));
        p1.add(tfCF);
        p1.add(new JLabel("Sport"));
        p1.add(tfsport);
        p1.add(new JLabel("Squadra"));
        p1.add(tfsquadra);


        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 4));
        p2.add(btnInsert);
        p2.add(btnRemove);

        JPanel p3 = new JPanel(new BorderLayout());
        try {
            testconnection();
            p3.add(new JScrollPane(getTable(query)));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error");
            System.out.println(e);
        }

        setLayout(new BorderLayout());
        add(p1, BorderLayout.PAGE_START);
        add(p2, BorderLayout.PAGE_END);
        add(p3, BorderLayout.CENTER);

        update();


    }


    private void testconnection() throws SQLException {
        DBManager.setConnection(Utils.JDBC_Driver, Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Persona");
        } catch (SQLException e1) {
            System.out.println("SQL Exception");

        }
    }

    public JTable getTable(String query) throws SQLException {

        DefaultTableModel dm = new DefaultTableModel();
        JTable t = new JTable(new ArchivioPanel.MyTableModel());
        t.setFillsViewportHeight(true);
        t.setPreferredScrollableViewportSize(new Dimension(1000, 1000));

        ResultSet rs = DBManager.getConnection().createStatement().executeQuery(query);
        ResultSetMetaData rsMetaData = rs.getMetaData();

        // get columns metadata
        int cols = t.getColumnCount();
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
        setVisible(true);
        t.setGridColor(Color.BLACK);
        return  t;

    }
        private void update () {
        }


        @Override
        public void actionPerformed (ActionEvent e){

        }
}
