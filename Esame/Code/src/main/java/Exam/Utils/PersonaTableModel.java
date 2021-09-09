package Exam.Utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonaTableModel extends AbstractTableModel {

    private final String[] ColumnNames ={"Nome","Cognome","Tipo","Luogo di Nascita"," Data di Nascita","<html>Citt\u00E0 di Residenza","CF","Sport","squadra"};
    private final List<Persona> personaList;

    public PersonaTableModel(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int getRowCount() {
        return personaList.size();
    }

    @Override
    public int getColumnCount() {
        return ColumnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 ->personaList.get(rowIndex).getNome();
            case 1 -> personaList.get(rowIndex).getCognome();
            case 2 -> personaList.get(rowIndex).getTipo();
            case 3 -> personaList.get(rowIndex).getLuogo_nascita();
            case 4 -> personaList.get(rowIndex).getData_nascita();
            case 5 -> personaList.get(rowIndex).getCitta_residenza();
            case 6 -> personaList.get(rowIndex).getCF();
            case 7 -> personaList.get(rowIndex).getSport();
            case 8 -> personaList.get(rowIndex).getSquadra();
            default -> "-";
        };
    }

    public String getColumnName(int column){
        return  ColumnNames[column];
    }

}

