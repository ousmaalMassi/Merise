package com.models.ddd;

import com.MeriseGUI.MTableModel;
public class DDTableModel extends MTableModel {

    public DDTableModel(String[] title) {
        super(title);
//        this.data = new Object[][]{
//                {"id client", "id_client", Property.Types.NUMERIC, "11", "", false, ""},
//                {"nom", "nom", Property.Types.ALPHABETICAL, "50", "", false, ""},
//                {"prénom", "prénom", Property.Types.ALPHABETICAL, "50", "", false, ""},
//                {"adresse", "adresse", Property.Types.ALPHANUMERIC, "256", "", false, ""},
//                {"id article", "id_article", Property.Types.NUMERIC, "11", "", false, ""},
//                {"prix achat", "prix_achat", Property.Types.NUMERIC, "10", "", false, ""},
//                {"prix vente", "prix_vente", Property.Types.NUMERIC, "10", "", false, ""},
//                {"designation", "designation", Property.Types.ALPHANUMERIC, "256", "", false, ""},
//                {"quantity", "quantity", Property.Types.NUMERIC, "2", "", false, ""},
//        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return switch (col) {
            case 4, 5 -> false;
            default -> true;
        };
    }
}