package Exam;


import Exam.Utils.DBManager;
import Exam.Utils.Persona;
import Exam.Utils.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class Rimborso extends JPanel implements ActionListener, KeyListener {
    public static String[] options = {"Ciclismo", "Podismo", "Calcio"};
    private String[] data;


    JButton rim;
    private final JTextField days;
    private final JButton bPdf;
    private final JButton bcreate;
    JTextField tcf;
    String datee;

    JComboBox<String> jc;
    int numAll=8;
    private JDatePickerImpl checkInDatePicker;
    ArrayList<Date> dateList;


    public Rimborso() {

        days = new JTextField("");
        tcf = new JTextField("");
        tcf.addKeyListener(this);


        setLayout(new BorderLayout());
        jc = new JComboBox(options);
        JPanel p1 = new JPanel(new GridLayout(1, 2));
        p1.add(new JLabel("CF"));
        p1.add(tcf);
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        p2.add(jc);
        p2.add(new JLabel("Giorno Allenamento:"));
        p2.add(days);


        JPanel p3 = new JPanel(new BorderLayout());
        p3.add(p1, BorderLayout.PAGE_START);
        p3.add(p2, BorderLayout.PAGE_END);

        JPanel p4 = new JPanel(new BorderLayout());
        Font font = new Font("Helvetica", Font.BOLD, 30);
        JLabel titolo = new JLabel("Selezionare le date degli allenamenti:");
        titolo.setFont(font);
        p4.add(titolo, BorderLayout.NORTH);


        Container pane = new Container();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        for(int i =0; i< numAll; i++) {
            UtilDateModel model = new UtilDateModel();
            Properties properties = new Properties();
            properties.put("text.today", "Today");
            properties.put("text.month", "Month");
            properties.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
            checkInDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

            Date selectedDate = (Date) checkInDatePicker.getModel().getValue();
            datee = selectedDate + "";
            dateList = new ArrayList<>();
            dateList.add(selectedDate);
            pane.add(checkInDatePicker);
        }
        p4.add(pane,BorderLayout.CENTER);

        JPanel pbutton =new JPanel();
        bcreate= new JButton ("Crea Tabella");
        bcreate.addActionListener(this);
        pbutton.add(bcreate);
        bPdf =new JButton("Genera Pdf");
        bPdf.addActionListener(this);
        pbutton.add(bPdf);



        p4.add(pbutton,BorderLayout.EAST);




        add(p3, BorderLayout.PAGE_START);
        add(p4, BorderLayout.CENTER);


    }

    public void Generate_PDF(Persona person) {
        try {
            String file_name = "C:\\Users\\Utente\\Desktop\\Sport-Management\\"+ person.getCognome()+".pdf";
            com.itextpdf.text.Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.setPageSize(PageSize.A4);


            document.open();
            Paragraph para = new Paragraph(person.toString2());
            Paragraph p = new Paragraph(Utils.Intestazione);
            p.setAlignment(Element.ALIGN_RIGHT);

            document.add(p);
            document.add(para);

            document.close();


            JOptionPane.showMessageDialog(this,"Creazione PDF avvenuta con successo!");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.rim) {
            //Generate_PDF();
        }else if (e.getSource()==this.bPdf){
            try {
                Persona atleta = checkCF();
                Generate_PDF(atleta);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public Persona checkCF() throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        Persona person = null;
        try{
            String query1 = String.format("SELECT * FROM Persona WHERE CF like '%s'",
                    tcf.getText());
            ResultSet rs =statement.executeQuery(query1);

            while (rs.next()){
                person =new Persona(rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("tipo"),
                        rs.getString("luogo_nascita"),
                        rs.getString("data_nascita"),
                        rs.getString("città_residenza"),
                        rs.getString("CF"),
                        rs.getString("sport"),
                        rs.getString("squadra")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  person;

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

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd/MM/yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}

