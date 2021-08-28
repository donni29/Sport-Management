package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Persona;
import Exam.Utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonaPanel extends JPanel implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    public static String[] options = {"Atleta","Allenatore","Dirigente"};
    private final JButton btnRemove;
    private final JButton btnInsert;
    private final JButton btnSelezione;
    private final JTextField tfNome;
    private final JTextField tfcognome;
    private final JTextField tftipo;
    private final JTextField tfdatanascita;
    private final JTextField tfluogonascita;
    private final JTextField tfcittadiresidenza;
    private final JTextField tfCF;
    private final JTextField tfsport;
    private final JTextField tfsquadra;
    private final JComboBox<String> cbtipo;


    JTable t;
    DefaultTableModel dm;
    ResultSet rs;
    ResultSetMetaData rsMetaData;
    JPanel p3= new JPanel(new BorderLayout());

    private List<Persona> listPersona;
    private int selectedPersonaIndex = 0;
    String query;

    public PersonaPanel(String query) throws SQLException {

        this.query =query;
        btnRemove = new JButton("Delete");
        btnRemove.addActionListener(this);
        btnInsert = new JButton("Insert");
        btnInsert.addActionListener(this);
        btnSelezione =new JButton("Filter");
        btnSelezione.addActionListener(this);


        listPersona= getListPersona(query);

        cbtipo =new JComboBox<>(options);
        cbtipo.addActionListener(this);
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
        tfCF.addKeyListener(this);
        tfsport = new JTextField();
        tfsport.addActionListener(this);
        tfsquadra = new JTextField();
        tfsquadra.addActionListener(this);


        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(9, 2));
        p1.add(new JLabel("Tipo"));
        p1.add(cbtipo);
        p1.add(new JLabel("Nome"));
        p1.add(tfNome);
        p1.add(new JLabel("Cognome"));
        p1.add(tfcognome);
        p1.add(new JLabel("Luogo di Nascita"));
        p1.add(tfluogonascita);
        p1.add(new JLabel("Data di Nascita"));
        p1.add(tfdatanascita);
        p1.add(new JLabel("Città di Residenza"));
        p1.add(tfcittadiresidenza);
        p1.add(new JLabel("CF"));
        p1.add(tfCF);
        p1.add(new JLabel("Sport"));
        p1.add(tfsport);
        p1.add(new JLabel("Squadra"));
        p1.add(tfsquadra);




        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 4));
        p2.add(btnInsert);
        p2.add(btnSelezione);
        p2.add(btnRemove);

        ShowItem(query,p3);

        setLayout(new BorderLayout());
        add(p1, BorderLayout.PAGE_START);
        add(p2, BorderLayout.PAGE_END);
        add(p3, BorderLayout.CENTER);

        update();
    }



    public List<Persona> getListPersona(String query) throws SQLException {
        ArrayList<Persona> personas = new ArrayList<>();
        Statement statement = DBManager.getConnection().createStatement();
        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                personas.add(
                        new Persona(rs.getString("nome"),
                                rs.getString("cognome"),
                                rs.getString("tipo"),
                                rs.getString("luogo_nascita"),
                                rs.getString("data_nascita"),
                                rs.getString("città_residenza"),
                                rs.getString("CF"),
                                rs.getString("sport"),
                                rs.getString("squadra")
                        )
                );
            }
            statement.close();
        }catch (SQLException e){
            System.out.println(e);
        }
        return personas;

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

    public JTable GetTable(String query) throws SQLException {

        dm = new DefaultTableModel();
        t = new JTable();
        t.setFillsViewportHeight(true);
        t.setPreferredScrollableViewportSize(new Dimension(1000, 1000));

        rs = DBManager.getConnection().createStatement().executeQuery(query);
        rsMetaData = rs.getMetaData();

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

        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SelectRow(e);
            }
        });

        t.setModel(dm);
        setVisible(true);
        t.setGridColor(Color.BLACK);

        return t;

    }

    private void update() {
        try{
            listPersona.clear();
            listPersona.addAll(getListPersona(query));
            Persona person =listPersona.get(selectedPersonaIndex);

        } catch (IndexOutOfBoundsException| SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }


    public void SelectRow(MouseEvent evt){
        selectedPersonaIndex =t.getSelectedRow();
        Persona person =listPersona.get(selectedPersonaIndex);

        tfNome.setText(person.getNome());
        tfcognome.setText(person.getCognome());
        tftipo.setText(person.getTipo());
        tfdatanascita.setText(person.getData_nascita().toString());
        tfluogonascita.setText(person.getLuogo_nascita());
        tfcittadiresidenza.setText(person.getCitta_residenza());
        tfCF.setText(person.getCF());
        tfsport.setText(person.getSport());
        tfsquadra.setText(person.getSquadra());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == this.btnRemove) {
                DeletePersona();

            }
            else if (e.getSource() == this.btnInsert){
                InsertPersona();

            }
            else if (e.getSource() == this.btnSelezione) {
                FilterPersona(query);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    public void ShowItem(String query, JPanel p3) throws SQLException{
        try {
            testconnection();
            p3.add(new JScrollPane(GetTable(query)));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error");
            System.out.println(e);
        }
    }



    public void InsertPersona() throws SQLException {

        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format("INSERT INTO Persona (nome,cognome,tipo,luogo_nascita,data_nascita,città_residenza,CF,sport,squadra) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                tfNome.getText(),
                tfcognome.getText(),
                cbtipo.getSelectedItem(),
                tfluogonascita.getText(),
                tfdatanascita.getText(),
                tfcittadiresidenza.getText(),
                tfCF.getText().toUpperCase(),
                tfsport.getText(),
                tfsquadra.getText());
        statement.executeUpdate(query);
        statement.close();
        Svuotare();
        update();


    }

    public void DeletePersona() throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format("DELETE FROM Persona WHERE cf like '%s'",
                tfCF.getText());
        statement.executeUpdate(query);
        statement.close();
        Svuotare();

    }

    public void FilterPersona(String query1) throws SQLException{
        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format(query1 + "AND WHERE sport like '%s' AND squadra like '%s'",
                tfsport.getText(),
                tfsquadra.getText());
        statement.executeUpdate(query);
        statement.close();
    }
    public void Svuotare() {
        tfNome.setText("");
        tfcognome.setText("");
        tfdatanascita.setText("");
        tfluogonascita.setText("");
        tfsport.setText("");
        tfcittadiresidenza.setText("");
        tfsquadra.setText("");
        tfCF.setText("");
        cbtipo.setSelectedItem(options);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        int pos = tfCF.getCaretPosition();
        tfCF.setText(tfCF.getText().toUpperCase());
        tfCF.setCaretPosition(pos);
    }

}

