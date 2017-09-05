/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vezba;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ms
 */
class CellHighlighterRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public CellHighlighterRenderer() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row == 0 && column == 0) {
            setBackground(Color.YELLOW);
            setOpaque(true);
        } else {
            setBackground(table.getBackground());
            setOpaque(isSelected);
        }
        return this;
    }
}