package Exam;

import Exam.Utils.Calendario;
import Exam.Utils.DBManager;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.*;
import com.mindfusion.scheduling.standardforms.AppointmentForm;
import com.mindfusion.scheduling.standardforms.DialogResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        //calendar.getTimetableSettings().setItemOffset(30);
        //calendar.getTimetableSettings().setShowItemSpans(false);
        //calendar.getTimetableSettings().setSnapInterval(Duration.fromMinutes(1));
        calendar.getTimetableSettings().setStartTime(510);
        calendar.getTimetableSettings().setCellTime(Duration.fromHours(1));
        //calendar.getTimetableSettings().setEndTime(2850);
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
                System.out.println(rs.getString("nome_struttura"));
                System.out.println(rs.getString("inizio_prenotazione"));
                System.out.println(rs.getString("fine_prenotazione"));
                System.out.println(rs.getString("info_prenotazione"));

                new Swap(rs.getString("inizio_prenotazione"));
                System.out.println(calen_i.get(java.util.Calendar.YEAR));
                System.out.println(calen_i.get(java.util.Calendar.MONTH));
                System.out.println(calen_i.get(java.util.Calendar.DAY_OF_MONTH));
                new Swap(rs.getString("fine_prenotazione"));

                Cal = new Calendario(rs.getString("nome_struttura"),
                        rs.getString("info_prenotazione"),
                        rs.getString("descrizione_prenotazione"),
                        new DateTime( calen_i.get(java.util.Calendar.YEAR),
                                calen_i.get(java.util.Calendar.MONTH) + 1,
                                calen_i.get(java.util.Calendar.DAY_OF_MONTH),
                                calen_i.get(java.util.Calendar.HOUR_OF_DAY),
                                calen_i.get(java.util.Calendar.MINUTE),
                                calen_i.get(java.util.Calendar.SECOND)),
                        new DateTime(calen_f.get(java.util.Calendar.YEAR),
                                calen_f.get(java.util.Calendar.MONTH) + 1,
                                calen_f.get(java.util.Calendar.DAY_OF_MONTH),
                                calen_f.get(java.util.Calendar.HOUR_OF_DAY),
                                calen_f.get(java.util.Calendar.MINUTE),
                                calen_f.get(java.util.Calendar.SECOND)),
                        rs.getInt("numero_ricorsioni"));


                Appointment a = new Appointment();
                a.setId(Cal.getNome_struttura());
                a.setHeaderText(Cal.getInfo_struttura());
                a.setDescriptionText(Cal.getDescrizione_prenotazione());
                a.setStartTime(Cal.getInizio_prenotazione());
                a.setEndTime(Cal.getFine_prenotazione());
                //a.setRecurrence(new Recurrence().setInterval(new Duration()));
                a.setAllowChangeEnd(true);
                a.setAllowChangeStart(true);
                if(a.getEndTime().isLessThan(DateTime.now()))
                    a.getStyle().setBorderLeftColor(new Color(0xB81104));
                else
                    a.getStyle().setBorderLeftColor(new Color(0x2E8402));
                /*
                if (Cal.getNumero_ricursioni() > 1){
                    int DayIndex = calen_i.get(java.util.Calendar.DAY_OF_WEEK);
                    Recurrence recurrence = new Recurrence();
                    //recurrence.setPattern(RecurrencePattern.Weekly);
                    recurrence.setDay(getDayOfWeek(DayIndex));
                    recurrence.setStartDate(Cal.getInizio_prenotazione());
                    recurrence.setNumOccurrences(Cal.getNumero_ricursioni());
                    recurrence.setRecurrenceEnd(RecurrenceEnd.NumOccurrences);
                    a.setRecurrence(recurrence);
                } */


                calen_f = null;
                calen_i = null;
                calendar.getSchedule().getItems().add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DayOfWeekType getDayOfWeek(int i) {
        return switch (i) {
            case 1 -> DayOfWeekType.Monday;
            case 2 -> DayOfWeekType.Tuesday;
            case 3 -> DayOfWeekType.Wednesday;
            case 4 -> DayOfWeekType.Thursday;
            case 5 -> DayOfWeekType.Friday;
            case 6 -> DayOfWeekType.Saturday;
            default -> DayOfWeekType.Sunday;
        };

    }


    public static class Swap extends java.util.Calendar {
        public Swap(String date) {
            try {
                if (calen_i == null) {
                    calen_i = new GregorianCalendar();

                    calen_i.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));

                } else {
                    calen_f = new GregorianCalendar();

                    calen_f.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void computeTime() {

        }

        @Override
        protected void computeFields() {

        }

        @Override
        public void add(int field, int amount) {

        }

        @Override
        public void roll(int field, boolean up) {

        }

        @Override
        public int getMinimum(int field) {
            return 0;
        }

        @Override
        public int getMaximum(int field) {
            return 0;
        }

        @Override
        public int getGreatestMinimum(int field) {
            return 0;
        }

        @Override
        public int getLeastMaximum(int field) {
            return 0;
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

                }

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
                    if(e.getClicks() == 2)
                        showForm(e.getItem());
                }

                @Override
                public void itemCreated(ItemEvent e) {
                    calendar.getSelection().reset();
                    calendar.getSelection().add(e.getItem().getStartTime(),
                            e.getItem().getEndTime());
                    showForm(e.getItem());
                }
            });
        }
    }
}

