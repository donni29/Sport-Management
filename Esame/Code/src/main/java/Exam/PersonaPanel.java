package Exam;

/**
 *The Class implements a JPanel to store and manage the Clus's Members' Dates.
 *
 * @authors Rossi Nicolò Delsante Laura
 */

import Exam.Utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersonaPanel extends JPanel implements ActionListener, KeyListener {

    @Serial
    private static final long serialVersionUID = 1L;
    public static String[] options = {"Atleta","Allenatore","Dirigente"};

    private final JButton btnRemove;
    private final JButton btnInsert;
    private final JButton btnUpdate;
    private final JTextField tfNome;
    private final JTextField tfcognome;
    private final JTextField tfdatanascita;
    private final JTextField tfluogonascita;
    private final JTextField tfcittadiresidenza;
    private final JTextField tfCF;
    private final JTextField tfsquadra;
    private final JTextField tftelefono;
    private final JComboBox<String> cbtipo;
    private final JComboBox<String> cbsport;
    private JTable table;
    private PersonaTableModel tableModel;

    JPanel p3= new JPanel(new BorderLayout());

    private List<Persona> listPersona;
    String query;


    /**
     *The Constructor receives a String query representing what type of people's group it's requested by the users
     *@param query - type of people's group it's requested by the users
     */
    public PersonaPanel(String query) throws SQLException {

        super();
        setLayout(new BorderLayout(1,1));
        this.query =query;
        btnRemove = new JButton("Delete");
        btnRemove.addActionListener(this);
        btnInsert = new JButton("Insert");
        btnInsert.addActionListener(this);
        JButton btnSelezione = new JButton("Filter");
        btnSelezione.addActionListener(this);
        btnUpdate =new JButton("Update");
        btnUpdate.addActionListener(this);


        listPersona= getListPersona(query);

        cbtipo =new JComboBox<>(options);
        cbtipo.addActionListener(this);
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
        cbsport =new JComboBox(Utils.options.toArray());
        cbsport.addActionListener(this);
        tfsquadra = new JTextField();
        tfsquadra.addActionListener(this);
        tftelefono = new JTextField();
        tftelefono.addActionListener(this);


        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(10, 2));
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
        p1.add(new JLabel("Residenza"));
        p1.add(tfcittadiresidenza);
        p1.add(new JLabel("CF"));
        p1.add(tfCF);
        p1.add(new JLabel("Sport"));
        p1.add(cbsport);
        p1.add(new JLabel("Squadra"));
        p1.add(tfsquadra);
        p1.add(new JLabel("Telefono"));
        p1.add(tftelefono);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        p2.add(btnInsert);
        p2.add(btnRemove);
        p2.add(btnUpdate);
        p2.add(btnSelezione);

        ShowItem();

        add(p1, BorderLayout.PAGE_START);
        add(p2, BorderLayout.PAGE_END);
        add(p3,BorderLayout.CENTER);

        update();

    }

    /**
     * Method to Create a List of a People's Requested Type from a SQLite Database.
     * @param query -Type people needed
     * @return the List created
     * @throws SQLException - if there is no Table or no entries in it
     */
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
                                rs.getString("residenza"),
                                rs.getString("CF"),
                                rs.getString("sport"),
                                rs.getString("squadra"),
                                rs.getString("telefono")
                        )
                );
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return personas;

    }

    /**
     *Method to test our JDBC Connection
     * @throws SQLException - if there is no Table Persona or no Entry in it
     */

    private void testconnection() throws SQLException {
        DBManager.setConnection(Utils.JDBC_Driver, Utils.JDBC_URL);
        Statement statement = DBManager.getConnection().createStatement();

        try {
            statement.executeQuery("SELECT * FROM Persona");
        } catch (SQLException e1) {
            System.out.println("SQL Exception");

        }
    }

    /**
     * Update our List created in method getListPersona every time it's called
     */
    private void update() {
        try{
            listPersona.clear();
            listPersona.addAll(getListPersona(query));

        } catch (IndexOutOfBoundsException| SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void SelectRow(){
        int selectedPersonaIndex = table.getSelectedRow();
        Persona person =listPersona.get(selectedPersonaIndex);

        tfNome.setText(person.getNome());
        tfcognome.setText(person.getCognome());
        cbtipo.setSelectedItem(person.getTipo());
        tfdatanascita.setText(person.getData_nascita());
        tfluogonascita.setText(person.getLuogo_nascita());
        tfcittadiresidenza.setText(person.getresidenza());
        tfCF.setText(person.getCF());
        cbsport.setSelectedItem(person.getSport());
        tfsquadra.setText(person.getSquadra());
        tftelefono.setText(person.getTelefono());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == this.btnRemove) {
                DeletePersona();
                JOptionPane.showMessageDialog(this,"Eliminazione di atleta avvenuta con successo");

            }
            else if (e.getSource() == this.btnInsert){
                InsertPersona();
                JOptionPane.showMessageDialog(this,"Inserimento avvenuto con successo");


            }
            else if (e.getSource() == this.btnUpdate){
                UpdatePersona();
                JOptionPane.showMessageDialog(this,"Update avvenuto con successo");
            }
            /* eventuale implementazione successiva
            else if (e.getSource() == this.btnSelezione){
            }*/

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to show the selection's results into the corresponding JPanel's JTextFields related of every column
     */

    public void ShowItem(){
        try {
            testconnection();
            table = new JTable();
            tableModel = new PersonaTableModel(listPersona);
            table.setModel(tableModel);
            table.setAutoCreateRowSorter(true);

            p3.add(new JScrollPane(table));
            JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER);

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SelectRow();
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error");
            e.printStackTrace();
        }

    }

    /**
     * to Insert a new Entry in our DB
     * @throws SQLException - if query has a problem to be executed (es: no table)
     */

    public void InsertPersona() throws SQLException {

        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format("INSERT INTO Persona (nome,cognome,tipo,luogo_nascita,data_nascita,residenza,CF,sport,squadra,telefono) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                tfNome.getText(),
                tfcognome.getText(),
                cbtipo.getSelectedItem(),
                tfluogonascita.getText(),
                tfdatanascita.getText(),
                tfcittadiresidenza.getText(),
                tfCF.getText().toUpperCase(),
                cbsport.getSelectedItem(),
                tfsquadra.getText(),
                tftelefono.getText());
        String query1 =String.format("INSERT INTO Rimborsi  VALUES ('%s',0,0)",
                tfCF.getText().toUpperCase(Locale.ROOT));
        statement.executeUpdate(query);
        statement.executeUpdate(query1);
        statement.close();
        Svuotare();
        update();
        tableModel.fireTableDataChanged();

    }
    /**
     * to Delete an Entry in our DB from table Persona and Table Rimborsi
     * @throws SQLException - if queries have problem to be executed
     */

    public void DeletePersona() throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format("DELETE FROM Persona WHERE CF like '%s'",
                tfCF.getText());
        String query1= String.format("DELETE FROM Rimborsi WHERE CF like '%s'",
                tfCF.getText());
        statement.executeUpdate(query);
        statement.executeUpdate(query1);
        statement.close();
        Svuotare();
        update();
        tableModel.fireTableDataChanged();

    }

    /*parte da implementare successivamente se ritengono necessaria
     public void FilterPersona(String query1) throws SQLException{
        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format(query1 + " WHERE sport like '%s'",// AND squadra like '%s'",
                cbsport.getSelectedItem());
                //tfsquadra.getText());
        statement.executeUpdate(query);
        statement.close();
        Svuotare();
        listPersona = getListPersona(query);
        ShowItem();
        tableModel.fireTableDataChanged();

    }*/
    /**
     * to Update dates of an Entry in our DB
     * @throws SQLException - if the query has a problem in its execution
     */
    public  void UpdatePersona() throws SQLException{
            Statement statement = DBManager.getConnection().createStatement();
            String query =String.format("UPDATE Persona SET nome = '%s',cognome = '%s',tipo ='%s',luogo_nascita = '%s',data_nascita = '%s',residenza = '%s',sport ='%s',squadra ='%s',telefono= '%s' WHERE CF like '%s'",
                    tfNome.getText(),
                    tfcognome.getText(),
                    cbtipo.getSelectedItem(),
                    tfluogonascita.getText(),
                    tfdatanascita.getText(),
                    tfcittadiresidenza.getText(),
                    cbsport.getSelectedItem(),
                    tfsquadra.getText(),
                    tftelefono.getText(),
                    tfCF.getText());
            statement.executeUpdate(query);
            statement.close();
            Svuotare();
            update();
            tableModel.fireTableDataChanged();
    }

    /**
     * to Clear all the JTextField box of our JPanel
     */

    public void Svuotare() {
        cbtipo.setSelectedItem(options);
        tfNome.setText("");
        tfcognome.setText("");
        tfdatanascita.setText("");
        tfluogonascita.setText("");
        cbsport.setSelectedItem(Utils.options.toArray());
        tfcittadiresidenza.setText("");
        tfsquadra.setText("");
        tftelefono.setText("");
        tfCF.setText("");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    public  void keyReleased(KeyEvent e) {
        int pos = tfCF.getCaretPosition();
        tfCF.setText(tfCF.getText().toUpperCase());
        tfCF.setCaretPosition(pos);
    }

}

