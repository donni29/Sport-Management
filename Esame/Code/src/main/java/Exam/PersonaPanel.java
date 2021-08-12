package Exam;

import Exam.Utils.DBManager;
import Exam.Utils.Persona;
import Exam.Utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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


    JTable t;
    DefaultTableModel dm;

    private List<Persona> listPersona;
    private int selectedPersonaIndex = 0;
    String query;

    public PersonaPanel(String query) throws SQLException {

        this.query =query;
        btnRemove = new JButton("Delete");
        btnRemove.addActionListener(this);
        btnInsert = new JButton("Insert");
        btnInsert.addActionListener(this);

        //listPersona= getListPersona(query);

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

        //update();
        t.addMouseListener(new MouseAdapter(){
             public void mouse( MouseEvent evt){
                 selectRow(evt);
                 System.out.println("ciaoooo");
             }
        });

    }



    /*public List<Persona> getListPersona(String query) throws SQLException{
            ArrayList<Persona> personas =new ArrayList<>();

            Statement statement =DBManager.getConnection().createStatement();
            ResultSet rs =statement.executeQuery(query);
            System.out.println("prova2");
            while (rs.next()){
                personas.add(
                        new Persona(rs.getString("nome"),
                                                        rs.getString("cognome"),
                                                        rs.getString("tipo"),
                                                        rs.getString("luogo_nascita"),
                                                        rs.getDate("data_nascita"),
                                                        rs.getString("città_residenza"),
                                                        rs.getString("CF"),
                                                        rs.getString("sport"),
                                                        rs.getString("squadra")
                                                        )
                );
            }
        statement.close();
        return personas;



    }*/

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

        dm = new DefaultTableModel();
        t = new JTable(new ArchivioPanel.MyTableModel());
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

        return t;

    }

    /*private void update() {
        try{
            listPersona.clear();
            listPersona.addAll(getListPersona(query));
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


        } catch (IndexOutOfBoundsException| SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
*/

    public void selectRow(MouseEvent evt){
        int i=t.getSelectedRow();
        TableModel model =t.getModel();
        System.out.println(model);
        System.out.println(i);

        tfNome.setText(model.getValueAt(i,0).toString());
        tfcognome.setText(model.getValueAt(i,1).toString());
        tftipo.setText(model.getValueAt(i,2).toString());
        tfdatanascita.setText(model.getValueAt(i,4).toString());
        tfluogonascita.setText(model.getValueAt(i,3).toString());
        tfcittadiresidenza.setText(model.getValueAt(i,5).toString());
        tfCF.setText(model.getValueAt(i,6).toString());
        tfsport.setText(model.getValueAt(i,7).toString());
        tfsquadra.setText(model.getValueAt(i,8).toString());

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource() == this.btnRemove) {


            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }




        public void insertPersona() throws SQLException {

        Statement statement = DBManager.getConnection().createStatement();
        String query =String.format("INSERT INTO Persona (nome,cognome,tipo,luogo_nascita,data_nascita,città_residenza,CF,sport,squadra) VALUES () ");

        statement.executeUpdate(query);
        statement.close();
        }
}
