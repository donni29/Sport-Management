package Exam;


import Exam.Utils.DBManager;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    JTextField days;
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
        pbutton.add(new JButton("Crea Tabella"));
        pbutton.add(new JButton("Genera il pdf"));

        p4.add(pbutton,BorderLayout.EAST);

        JPanel paneltable =new JPanel();
        paneltable.add(new JScrollPane());
        paneltable.add(new JButton("prova"));


        add(p3, BorderLayout.PAGE_START);
        add(p4, BorderLayout.CENTER);
        add(paneltable,BorderLayout.PAGE_END);

    }

    /*public void Generate_PDF() {
        try {
            String file_name = "C:\\Users\\Utente\\Desktop\\Sport-Management\\Rimborso\\pdf_prova.pdf";
            com.itextpdf.text.Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));

            document.open();
            Paragraph para = new Paragraph("Questa è una prova del file pdf_prova");
            document.add(para);
            document.close();

            System.out.println("La creazione del pdf è avvenuta con successo");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.rim) {
            //Generate_PDF();
        }
    }


    public void checkCF() throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();

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

