package com.MeriseGUI.rules;

import com.MeriseGUI.MTableModel;

public class RulesTableModel extends MTableModel {

    public RulesTableModel(String[] title) {
        super(title);

//        this.data = new Object[][]{
//                {"1", "Chaque professeur est caractérisé par son code, son nom, son prénom, son adresse, son grade et son nombre d’heures d’enseignement selon son grade."},
//                {"2", "Un professeur peut enseigner plusieurs modules"}
//        };
    }
    @Override
    public boolean isCellEditable(int row, int col) {
         return col != 0;
    }

}
