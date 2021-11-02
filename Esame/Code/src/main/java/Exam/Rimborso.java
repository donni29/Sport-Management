package Exam;

/**
 * Class to create Refunds to Club's Members
 *  @authors Rossi Nicolò Delsante Laura
 */

import Exam.Utils.DBManager;
import Exam.Utils.JTableUtilities;
import Exam.Utils.Persona;
import Exam.Utils.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public  class Rimborso extends JPanel implements ActionListener, KeyListener {
    private final String[] columnNames = {"Data", "Localit\u00E0", "Campionato", "Titolo Prestazione", "Compenso"};
    public static Integer[] num = {8, 6};

    public JPanel p4;

    private final JTextField days;
    private final JTextField tfdate;
    private final JButton bPdf;
    private final JButton bcreate;
    private final JButton check;
    private final JButton delete;
    private final JButton srcat;
    private final JComboBox<Integer> cbn;
    public static JTextField tcf;
    private final JComboBox<String> jc;
    public int soldi;
    public int getNumRim;
    private final JDatePickerImpl[] checkInDatePicker = new JDatePickerImpl[8];

    int numAll;
    String[] dateList;
    JTable table;
    Persona person;


    public Rimborso() {

        days = new JTextField("");
        tcf = new javax.swing.JTextField("");
        tcf.addKeyListener(this);
        tfdate = new JTextField("");


        setLayout(new BorderLayout());
        jc = new JComboBox(Utils.options.toArray());
        JPanel p1 = new JPanel(new GridLayout(1, 3, 10, 2));
        p1.add(new JLabel(("CF: "), SwingConstants.CENTER));
        p1.add(tcf);
        srcat = new JButton("Search");
        srcat.setSize(4, 4);
        srcat.addActionListener(this);
        p1.add(srcat);
        check = new JButton("check");
        check.setSize(4, 4);
        check.addActionListener(this);
        p1.add(check);
        JPanel p2 = new JPanel(new GridLayout(1, 3, 5, 2));
        p2.add(jc);
        p2.add(new JLabel("Giorno Allenamento:"));
        p2.add(days);


        JPanel p3 = new JPanel(new BorderLayout(10, 10));
        p3.add(p1, BorderLayout.PAGE_START);
        p3.add(p2, BorderLayout.PAGE_END);

        p4 = new JPanel(new BorderLayout(3, 3));

        JPanel p5 = new JPanel(new FlowLayout());
        Font font = new Font("Helvetica", Font.BOLD, 30);
        JLabel titolo = new JLabel("Selezionare le date degli allenamenti:");
        titolo.setFont(font);
        JLabel titolonum = new JLabel("Selezionare numero allenamenti:", SwingConstants.CENTER);
        titolonum.setFont(new Font("Helvetica", Font.PLAIN, 15));
        cbn = new JComboBox<>(num);
        cbn.addActionListener(this);
        p5.add(titolo);
        p5.add(titolonum);
        p5.add(cbn);

        p4.add(p5, BorderLayout.NORTH);


        Container pane = new Container();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        for (int i = 0; i < 8; i++) {
            UtilDateModel model = new UtilDateModel();
            Properties properties = new Properties();
            properties.put("text.today", "Today");
            properties.put("text.month", "Month");
            properties.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
            checkInDatePicker[i] = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            pane.add(checkInDatePicker[i]);
        }
        p4.add(pane, BorderLayout.WEST);

        JPanel pbutton = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        bcreate = new JButton("Crea Tabella");
        bcreate.setPreferredSize(new Dimension(120, 30));
        bcreate.addActionListener(this);
        pbutton.add(bcreate);
        delete = new JButton("Ripulisci Tabella");
        delete.setPreferredSize(new Dimension(120, 30));
        delete.addActionListener(this);
        pbutton.add(delete);
        JLabel label = new JLabel("Data Pagamento:", SwingConstants.CENTER);
        pbutton.add(label);
        tfdate.setPreferredSize(new Dimension(120, 30));
        pbutton.add(tfdate);
        bPdf = new JButton("Genera Pdf");
        bPdf.setPreferredSize(new Dimension(120, 30));
        bPdf.addActionListener(this);
        pbutton.add(bPdf);

        p4.add(pbutton, BorderLayout.PAGE_END);

        add(p3, BorderLayout.PAGE_START);
        add(p4, BorderLayout.CENTER);


    }

    /**
     * To generate the Refund's PDF of a member
     * @param person - Member who is receiving the Refunds
     */
    public void Generate_PDF(Persona person) {
        try {
            String foldername =  "C:\\Users\\Utente\\Desktop\\Rimborsi";
            File folder = new File(foldername);
            if (!folder.isDirectory()){
                folder.mkdir();
            }
            String foldername1 = foldername + "\\" + person.getCognome() + "_" + person.getNome();
            File folder1 = new File(foldername1);
            if (!folder1.isDirectory()){
                folder1.mkdir();
            }
            String file_name = foldername1 + "\\" + person.getCognome() + "_" + getNumRim + ".pdf";
            com.itextpdf.text.Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.setPageSize(PageSize.A4);


            document.open();

            Chunk glue =new Chunk(new VerticalPositionMark());
            Paragraph pnum = new Paragraph(String.valueOf(getNumRim));
            pnum.add(new Chunk(glue));
            Paragraph p = new Paragraph(Utils.Intestazione);
            p.setAlignment(Element.ALIGN_RIGHT);
            pnum.add(p);
            document.add(pnum);

            Paragraph para = new Paragraph(person.toString2());
            document.add(para);

            for (int i = 0; i < 3; i++) {
                document.add(new Paragraph(" "));
            }

            PdfPTable pdfPTable = new PdfPTable(table.getColumnCount());
            for (int t = 0; t < table.getColumnCount(); t++) {
                pdfPTable.addCell(table.getColumnName(t));
            }
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    pdfPTable.addCell(table.getModel().getValueAt(rows, cols).toString());
                }
            }

            pdfPTable.setTotalWidth(PageSize.A4.getWidth() - 30);
            pdfPTable.setLockedWidth(true);
            JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER); //non funziona secondo me, da controllare
            document.add(pdfPTable);

            document.add(new Paragraph(" "));

            Paragraph p1 = new Paragraph("Autocertificazione COMPENSI ai sensi del D.M. 26.11.99 n.473");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Paragraph(" "));


            Chunk c = new Chunk("Dichiaro di ricevere da SPORTINSIEME A.S.D. per i titoli riportati e relativi al periodo " + dateList[0] + "-" + dateList[numAll - 1] + " e comunque riferentesi " +
                    "ad attivit\u00E0 sportiva dilettantistica (Leggi n.342/2000 e 289/2002) le somme indicate in calce", FontFactory.getFont(FontFactory.HELVETICA, 8));
            Paragraph p2 = new Paragraph(c);
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            document.add(new Paragraph(" "));

            Chunk c1 = new Chunk("""
                    Nel corso dell'anno solare 2021 dichiaro inoltre di:
                    [ ] non aver percepito alla data odierna compensi della stessa natura.
                    [ ] aver percepito alla data odierna compensi della stessa natura, incluso il presente, per la somma di Euro\040"""+ soldi +  """
                    
                    [ ] avere riscosso alla data odierna compensi della stessa natura superiori a Euro 10.000,00, ma inferiori a Euro 28.158,28
                    [ ] avere riscosso alla data odierna compensi della stessa natura superiori a Euro 28.158,28""",
                    FontFactory.getFont(FontFactory.HELVETICA, 9));
            document.add(c1);
            document.add(new Paragraph(" "));

            Chunk date = new Chunk("Pagato il: " + tfdate.getText(), FontFactory.getFont(FontFactory.HELVETICA, 10));
            document.add(date);


            Paragraph firma = new Paragraph("FIRMA");
            firma.setAlignment(Element.ALIGN_RIGHT);
            document.add(firma);

            document.close();


            JOptionPane.showMessageDialog(this, "Creazione PDF avvenuta con successo!");

            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(file_name);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nella creazione del PDF" + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.bPdf) {
            soldi += (30*numAll);
            UpdateSoldi();
            getNumRim +=1;
            UpdateNum();
            Generate_PDF(person);
        } else if (e.getSource() == this.cbn) {
            numAll = (int) cbn.getSelectedItem();
            dateList = new String[numAll];

        } else if (e.getSource() == this.srcat) {
            SearchFrame a = new SearchFrame();
            tcf.setText(SearchFrame.tfcf.getText());
            a.setVisible(true);
        } else if (e.getSource() == this.check) {
            try {
                person = checkCF();
                if (person  == null) {
                    JOptionPane.showMessageDialog(this, "CF errato o atleta non presente");
                } else {
                    SoldiCheck();
                    NumRimb();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == this.bcreate) {
            p4.add(new JScrollPane(crtTable()), BorderLayout.CENTER);
            setVisible(true);
        } else if (e.getSource() == this.delete) {
            (((BorderLayout) p4.getLayout()).getLayoutComponent(BorderLayout.CENTER)).setVisible(false);
            JOptionPane.showMessageDialog(this, "TABELLA ELIMINATA");
        }
    }

    /**
     * To check the being of a selected member, to obtain his sport and his Training's days
     * @return - a Persona
     * @throws SQLException - if there is no value with the inserted CF
     */
    public Persona checkCF() throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        person = null;
        try {
            String query1 = String.format("SELECT * FROM Persona WHERE CF like '%s'",
                    tcf.getText());
            ResultSet rs = statement.executeQuery(query1);

            while (rs.next()) {
                person = new Persona(rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("tipo"),
                        rs.getString("luogo_nascita"),
                        rs.getString("data_nascita"),
                        rs.getString("residenza"),
                        rs.getString("CF"),
                        rs.getString("sport"),
                        rs.getString("squadra"),
                        rs.getString("telefono")
                );
                jc.setSelectedItem(rs.getString("sport"));

                String querysport = String.format("SELECT * FROM Sport WHERE Name like '%s'",
                        jc.getSelectedItem());
                rs = statement.executeQuery(querysport);
                while (rs.next()) {
                    days.setText(rs.getString("Giorni_Allenamento"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        statement.close();
        return person;

    }

    /**
     * To obtain the amount of money the member has received until now
     */
    public void SoldiCheck(){
         soldi = 0;

        try{
            Statement statement = DBManager.getConnection().createStatement();
            String query =String.format("SELECT Soldi_Ricevuti FROM Rimborsi WHERE CF = '%s'",
                    person.getCF()
                    );
            ResultSet set =statement.executeQuery(query);
            soldi= ((Number) set.getObject(1)).intValue();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * To obtain Refunds' number of money the member has received until now
     */
    public void NumRimb(){
        getNumRim =0;

        try{
            Statement statement = DBManager.getConnection().createStatement();
            String query =String.format("SELECT N_Rimborsi FROM Rimborsi WHERE CF = '%s'",
                    person.getCF()
            );
            ResultSet rs =statement.executeQuery(query);
            getNumRim = ((Number) rs.getObject(1)).intValue();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int pos = tcf.getCaretPosition();
        tcf.setText(tcf.getText().toUpperCase());
        tcf.setCaretPosition(pos);
    }



    /**
     * To update the amount of money the member has received
     */
    public void UpdateSoldi(){
        try {
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("UPDATE Rimborsi SET Soldi_Ricevuti = '%s' WHERE CF like '%s'",
                    soldi,
                    person.getCF());
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * To obtain Refunds' number of money the member has received
     */
    public void UpdateNum(){
        try {
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("UPDATE Rimborsi SET N_Rimborsi = '%s' WHERE CF like '%s'",
                    getNumRim,
                    person.getCF());
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * To create the table with the range of Training's days selected and amount of money for that determined Refund
     * @return - a table
     */

    public JTable crtTable(){
        table = new JTable();
        DefaultTableModel model =new DefaultTableModel(columnNames,0);
        dateList =new String[numAll];
        for (int i =0; i< numAll;i++) {
            dateList[i]=checkInDatePicker[i].getJFormattedTextField().getText();

            model.addRow(new Object[]{dateList[i],"Castellarano",jc.getSelectedItem(),"Allenamento","30,00"});
            checkInDatePicker[i].getJFormattedTextField().setText("");
        }
        model.addRow(new Object[]{"","","TOTALE:","euro",(30* numAll) + ",00"});
        table.setModel(model);
        JOptionPane.showMessageDialog(this,"CREATA LA TABELLA");
        JTableUtilities.setCellsAlignment(table, SwingConstants.CENTER);
        setVisible(false);
        return table;
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private final String datePattern = "dd/MM/yyyy";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}