/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vezba;

/**
 *
 * @author ms
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.*;

public class RollOverTable extends JTable {

    private Color rollOverBackground = new Color(233,239,248);
    private Color rollOverForeground = UIManager.getDefaults().getColor("windowText");

    private int rollOverRowIndex = -1;
    private boolean dispatchedEvent = false;
    private RollOverListener lst = null;
    private JScrollPane scp = null;

    public RollOverTable(TableModel model) {
        super(model);
        lst = new RollOverListener();
        addMouseMotionListener(lst);
        addMouseListener(lst);
        addMouseWheelListener(lst);
    }

    public Color getRollOverBackground() {
        return rollOverBackground;
    }

    public void setRollOverBackground(Color value) {
        rollOverBackground = value;
    }

    public Color getRollOverForeground() {
        return rollOverForeground;
    }

    public void setRollOverForeground(Color value) {
        rollOverForeground = value;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        for (Component c = this; c != null; c = c.getParent())
            if (c instanceof JScrollPane) {
                scp = (JScrollPane) c;
                scp.addMouseWheelListener(new RollOverMouseWheelListener(this));
                break;    
            }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (isRowSelected(row)) {
            c.setForeground(getSelectionForeground());
            c.setBackground(getSelectionBackground());
        } else if (row == rollOverRowIndex) {
            c.setForeground(getRollOverForeground());
            c.setBackground(getRollOverBackground());
        } else {
            c.setForeground(getForeground());
            c.setBackground(getBackground());
        }
        return c;
    }

    protected void repaintRow(int row) {
        if (row == -1) return;
        int modelRow = convertRowIndexToModel(row);
        ((AbstractTableModel)getModel()).fireTableRowsUpdated(modelRow, modelRow);
    }

    private class RollOverListener extends MouseInputAdapter {

        @Override
        public void mouseExited(MouseEvent e) {
            if (rollOverRowIndex != -1) {
                repaintRow(rollOverRowIndex);
                rollOverRowIndex = -1;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int row = rowAtPoint(e.getPoint());
            if (row != rollOverRowIndex) {
                repaintRow(rollOverRowIndex);
                rollOverRowIndex = row;
                repaintRow(rollOverRowIndex);
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (rollOverRowIndex != -1) {
                repaintRow(rollOverRowIndex);
                rollOverRowIndex = -1;
                dispatchedEvent = true;
                scp.dispatchEvent(SwingUtilities.convertMouseEvent(
                        e.getComponent(), e, scp));
            }
        }
    }

    private class RollOverMouseWheelListener extends MouseInputAdapter {

        private final RollOverTable table;

        public RollOverMouseWheelListener(RollOverTable table) {
            this.table = table;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (dispatchedEvent) {
                dispatchedEvent = false;
                Point point = SwingUtilities.convertPoint(
                        e.getComponent(), e.getPoint(), table);
                int row = table.rowAtPoint(point);
                if (rollOverRowIndex != row) {
                    rollOverRowIndex = row;
                    table.repaintRow(rollOverRowIndex);
                }
            }
        }
    }

}
