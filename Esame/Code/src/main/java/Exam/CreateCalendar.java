package Exam;

import Exam.Utils.Calendario;
import Exam.Utils.DBManager;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;
import com.mindfusion.scheduling.model.ItemEvent;
import com.mindfusion.scheduling.standardforms.AppointmentForm;
import com.mindfusion.scheduling.standardforms.DialogResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Locale.Builder;

public class  CreateCalendar extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    public Calendar calendar;
    public static java.util.Calendar calen_i = null;
    public static java.util.Calendar calen_f = null;


    public CreateCalendar( Object nome_struttura) throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout(10,10));
        setSize(600,400);

        calendar = new Calendar();

        calendar.beginInit();
        calendar.getTimetableSettings().setTwelveHourFormat(false);
        calendar.getTimetableSettings().setShowAM(false);
        calendar.setCurrentView(CalendarView.Timetable);
        calendar.setDateTimeFormat(new DateTimeInfo(calendar,new Builder().setLanguage("it").setRegion("IT").build()));
        calendar.getTimetableSettings().setShowCurrentTime(true);
        calendar.setTheme(ThemeType.Silver);

        for (int i= 1 ; i < 7; i++){
            calendar.getTimetableSettings().getDates().add(DateTime.today().addDays(i));
        }
        calendar.getTimetableSettings().setStartTime(510);
        calendar.getTimetableSettings().setCellTime(Duration.fromHours(1));
        calendar.getTimetableSettings().setVisibleColumns(8);
        calendar.getTimetableSettings().getStyle().setBorderLeftWidth(5);
        calendar.getTimetableSettings().setShowNavigationButtons(true);
        calendar.getTimetableSettings().setScrollStep(7);
        calendar.getTimetableSettings().setCellSize(35);
        calendar.setEnableDragCreate(true);

        calendar.endInit();

        calendar.addCalendarListener(new Listener(calendar,nome_struttura));
        try {
            establishConnection(nome_struttura);
        } catch (SQLException e) {
            System.out.println(e);
        }

        add(calendar,BorderLayout.CENTER);
        setVisible(true);
    }


    public void establishConnection(Object nome_struttura) throws SQLException {
        Statement statement = DBManager.getConnection().createStatement();
        Calendario Cal;
        try {
            String query = String.format("SELECT * FROM CALENDARIO WHERE NOME_STRUTTURA LIKE '%s' ",nome_struttura);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date inizio = sdf.parse(rs.getString("inizio_prenotazione"));
                Date fine = sdf.parse(rs.getString("fine_prenotazione"));

                Cal = new Calendario(rs.getString("nome_struttura"),
                        rs.getString("info_prenotazione"),
                        rs.getString("descrizione_prenotazione"),
                        new DateTime( inizio),
                        new DateTime(fine),
                        rs.getInt("numero_ricorsioni"));


                Appointment a = new Appointment();
                a.setId(Cal.getNome_struttura());
                a.setHeaderText(Cal.getInfo_struttura());
                a.setDescriptionText(Cal.getDescrizione_prenotazione());
                a.setStartTime(Cal.getInizio_prenotazione());
                a.setEndTime(Cal.getFine_prenotazione());
                a.setAllowChangeEnd(true);
                a.setAllowChangeStart(true);
                if(a.getEndTime().isLessThan(DateTime.now()))
                    a.getStyle().setBorderLeftColor(new Color(0xB81104));
                else
                    a.getStyle().setBorderLeftColor(new Color(0x2E8402));
                calendar.getSchedule().getItems().add(a);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
    public void InsertEvento(Item item, Object nome_struttura) throws SQLException {
        if(item.getRecurrenceState() == RecurrenceState.Master){
            System.out.println(item.getRecurrence().getNumOccurrences());
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,descrizione_prenotazione,inizio_prenotazione,fine_prenotazione,numero_ricorsioni) values ('%s','%s','%s','%s','%s','%d')",
                    nome_struttura,
                    item.getHeaderText(),
                    item.getDescriptionText(),
                    item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getRecurrence().getNumOccurrences());
            statement.executeUpdate(query);
            statement.close();
            int i = 1;
            while (item.getRecurrence().getNumOccurrences() > i){
                Statement statement1 = DBManager.getConnection().createStatement();
                String query1 = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,descrizione_prenotazione,inizio_prenotazione,fine_prenotazione,numero_ricorsioni) values ('%s','%s','%s','%s','%s','%d')",
                        nome_struttura,
                        item.getRecurrence().getOccurrence(i).getHeaderText(),
                        item.getRecurrence().getOccurrence(i).getDescriptionText(),
                        item.getRecurrence().getOccurrence(i ).getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                        item.getRecurrence().getOccurrence(i).getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                        item.getRecurrence().getNumOccurrences() - item.getRecurrence().getOccurrence(i).getOccurrenceIndex());
                statement1.executeUpdate(query1);
                statement1.close();
                i++;
            }
        }else {
            Statement statement = DBManager.getConnection().createStatement();
            String query = String.format("INSERT INTO CALENDARIO (nome_struttura,info_prenotazione,descrizione_prenotazione,inizio_prenotazione,fine_prenotazione) values ('%s','%s','%s','%s','%s')",
                    nome_struttura,
                    item.getHeaderText(),
                    item.getDescriptionText(),
                    item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                    item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"));
            statement.executeUpdate(query);
            statement.close();
        }
    }
    public void DeleteEvento(Item item, Object nome_struttura) throws SQLException{
        Statement statement =  DBManager.getConnection().createStatement();
        String query = String.format("DELETE FROM CALENDARIO WHERE info_prenotazione LIKE '%s' and nome_struttura like '%s' and inizio_prenotazione like '%s'",
                item.getHeaderText(),
                nome_struttura,
                item.getStartTime().toString("yyyy-MM-dd HH:mm:ss")
        );
        statement.executeUpdate(query);
        statement.close();
    }
    public void UpdateEvento(ItemModifiedEvent itemModifiedEvent, Object nome_struttura)  throws  SQLException{
        Statement statement =  DBManager.getConnection().createStatement();
        String query = String.format("UPDATE CALENDARIO SET descrizione_prenotazione = '%s' ,inizio_prenotazione = '%s' ,fine_prenotazione = '%s'  WHERE nome_struttura LIKE '%s' and inizio_prenotazione like '%s'",
                itemModifiedEvent.getItem().getDescriptionText(),
                itemModifiedEvent.getItem().getStartTime().toString("yyyy-MM-dd HH:mm:ss"),
                itemModifiedEvent.getItem().getEndTime().toString("yyyy-MM-dd HH:mm:ss"),
                nome_struttura,
                itemModifiedEvent.getOldStartTime().toString("yyyy-MM-dd HH:mm:ss"));
        statement.executeUpdate(query);
        statement.close();
    }

    public class Listener extends CalendarAdapter {

        public Listener(Calendar calendar, Object nome_struttura) {
            calendar.addCalendarListener(new CalendarAdapter(){
                public void showForm(Item item) throws ParseException {
                    new Form(item,nome_struttura);
                }/*
                public void showForm(Item item){
                    AppointmentForm form = new AppointmentForm(calendar.getSchedule());
                    form.setLocale(new Locale("it","ITALY"));
                    form.setAppointment((Appointment)item );
                    form.setTimeFormat("HH:mm:ss");

                    form.setVisible(true);
                    form.addWindowListener(new WindowAdapter()
                    {
                        @Override
                        public void windowClosed(WindowEvent we)
                        {
                            if (form.getDialogResult() == DialogResult.Remove)
                            {
                                try {
                                    if (item.getRecurrenceState() == RecurrenceState.Occurrence ||
                                            item.getRecurrenceState() == RecurrenceState.Exception) {
                                        item.getRecurrence().markException(item, true);
                                        DeleteEvento(item,nome_struttura);
                                    } else {
                                        calendar.getSchedule().getItems().remove(item);
                                        DeleteEvento(item, nome_struttura);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                try {
                                    InsertEvento(item, nome_struttura);

                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                        }
                    });

                }*/

                @Override
                public void itemModified(ItemModifiedEvent itemModifiedEvent) {
                    try {
                        UpdateEvento(itemModifiedEvent,nome_struttura);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void itemClick(ItemMouseEvent e) {
                    if(e.getClicks() == 2) {
                        try {
                            showForm(e.getItem());
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                }

                @Override
                public void itemCreated(ItemEvent e) {
                    calendar.getSelection().reset();
                    calendar.getSelection().add(e.getItem().getStartTime(),
                            e.getItem().getEndTime());
                    try {
                        showForm(e.getItem());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
            });
        }

        private class Form extends JFrame implements ActionListener {

            JLabel nome;
            JButton insert;
            JButton delete;
            JTextField titolo;
            JTextField descrizione;
            JSpinner inizio;
            JSpinner fine;
            Item item1;
            JTextField ric;
            JCheckBox day;
            JCheckBox week;
            JCheckBox month;
            SimpleDateFormat formatter;

            public Form(Item item, Object nome_struttura) throws ParseException  {
                super("Gestione Evento");
                setLocation(250,150);
                setSize(650,600);
                Font font = new Font("Helvetica", Font.BOLD, 30);
                setFont(font);
                JPanel p1 = new JPanel(new GridLayout(1,2,5,5));
                p1.setAlignmentY(Component.CENTER_ALIGNMENT);
                JLabel l0 = new JLabel("nome_struttura:");
                nome = new JLabel(nome_struttura.toString());
                p1.add(l0);
                p1.add(nome);

                JPanel p2 = new JPanel(new GridLayout(1,2,5,5));

                JLabel l1 = new JLabel("Titolo Evento:");
                titolo = new JTextField(item.getHeaderText());
                titolo.setPreferredSize(new Dimension(1,30));
                p2.add(l1);
                p2.add(titolo);

                JPanel p3 = new JPanel(new GridLayout(1,2,5,5));
                JLabel l2 = new JLabel("Descrizione Evento:");
                descrizione = new JTextField(item.getDescriptionText());
                descrizione.setSize(1,70);
                p3.add(l2);
                p3.add(descrizione);

                JPanel p4 = new JPanel(new GridLayout(1,2,5,5));

                JLabel l3 = new JLabel("Ora Inizio:");
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ITALIAN);
                Date start = formatter.parse(item.getStartTime().toString("yyyy-MM-dd HH:mm:ss"));
                System.out.println(start.getTime());
                SpinnerDateModel sm = new SpinnerDateModel(start,null,null, java.util.Calendar.HOUR_OF_DAY);
                inizio = new JSpinner(sm);
                inizio.setEditor(new JSpinner.DateEditor(inizio,"yyyy-MM-dd HH:mm:ss"));
                JLabel l4 = new JLabel("Ora Fine:");
                Date end = formatter.parse(item.getEndTime().toString("yyyy-MM-dd HH:mm:ss"));
                SpinnerDateModel sf = new SpinnerDateModel(end,null,null, java.util.Calendar.HOUR_OF_DAY);
                fine = new JSpinner(sf);
                fine.setEditor(new JSpinner.DateEditor(fine,"yyyy-MM-dd HH:mm:ss"));

                p4.add(l3);
                p4.add(inizio);
                p4.add(l4);
                p4.add(fine);

                JPanel p5 = new JPanel(new GridLayout(1,2,5,5));
                JLabel l5 = new JLabel("Inserisci  Numero Ricorsioni");
                ric = new JTextField();
                if (item.getOccurrenceIndex() < 0)
                    ric.setText("1");
                else
                    ric.setText(String.valueOf(item.getOccurrenceIndex()));

                p5.add(l5);
                p5.add(ric);

                JPanel p7 = new JPanel((new GridLayout(1,3,5,5)));
                day = new JCheckBox("giornalmente");
                day.setSelected(false);
                week = new JCheckBox("settimanalmente");
                week.setSelected(false);
                month = new JCheckBox("mensilmente");
                month.setSelected(false);

                p7.add(day);
                p7.add(week);
                p7.add(month);
                JPanel p0 = new JPanel(new GridLayout(8,1,10,5));
                p0.add(p1);
                p0.add(p2);
                p0.add(p3);
                p0.add(p4);
                p0.add(p5);
                p0.add(p7);
                add(p0,BorderLayout.NORTH);

                JPanel p6 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
                insert = new JButton("Inserisci Evento");
                insert.setPreferredSize(new Dimension(120,30));
                insert.addActionListener(this);
                delete = new JButton("Elimina Evento");
                delete.setPreferredSize(new Dimension(120,30));
                delete.addActionListener(this);
                p6.add(insert);
                p6.add(delete);

                setVisible(true);
                    item1 = item;


                add(p6,BorderLayout.PAGE_END);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == this.insert){
                        item1.setHeaderText(titolo.getText());
                        item1.setDescriptionText(descrizione.getText());


                        Date str = (Date) inizio.getValue();
                        item1.setStartTime(new DateTime(str));

                        str = (Date) fine.getValue();

                        item1.setEndTime(new DateTime(str));

                        if (Integer.valueOf(ric.getText()) > 1){
                            Recurrence recurrence = new Recurrence();
                            recurrence.setStartDate(DateTime.now());
                            if(this.day.isSelected()){
                                recurrence.setDays(1);
                                recurrence.setDailyRecurrence(DailyRecurrence.ByDayInterval);
                            }else if (this.week.isSelected()){
                                recurrence.setDays(7);
                                recurrence.setDailyRecurrence(DailyRecurrence.ByDayInterval);
                            } else {
                                recurrence.setMonthlyRecurrence(MonthlyRecurrence.ByDayNumber);
                            }
                            recurrence.setNumOccurrences(Integer.valueOf(ric.getText()));
                            item1.setRecurrence(recurrence);
                            dispose();
                        }
                        try {
                            InsertEvento(item1,nome.getText());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                    if (e.getSource() == this.delete){
                        item1.setHeaderText(titolo.getText());
                        item1.setDescriptionText(descrizione.getText());



                        Date  str = (Date) inizio.getValue();
                        item1.setStartTime(new DateTime(str));

                        str = (Date) fine.getValue();

                        item1.setEndTime(new DateTime(str));
                        if (Integer.valueOf(ric.getText()) > 1){
                            Recurrence recurrence = new Recurrence();
                            recurrence.setStartDate(item1.getStartTime());
                            recurrence.setNumOccurrences(Integer.valueOf(ric.getText()));
                            item1.setRecurrence(recurrence);
                            dispose();
                        }
                        try {
                            DeleteEvento(item1,nome.getText());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }

                }
            }
        }
    }

