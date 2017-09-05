/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vezba;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author ms
 */
public class Main extends JFrame {

public Main() {
    super("Table Demo");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(300, 300));
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    DefaultTableModel model = new DefaultTableModel();
    model.setColumnCount(5);
    model.setRowCount(5);

    JTable table = new JTable();
    table.setModel(model);

    //Get an instance of the column and the style to apply and hold a default style instance
    final TableColumn column = table.getColumnModel().getColumn(1);
    final CellHighlighterRenderer cellRenderer = new CellHighlighterRenderer();
    final TableCellRenderer defaultRenderer = column.getCellRenderer();

    //Now in your button listener you can toggle between the styles 
    JButton button = new JButton("Click!");
    button.addActionListener(new ActionListener() {
        private boolean clicked = false;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (clicked) {
                column.setCellRenderer(cellRenderer);
                clicked = false;
            } else {
                column.setCellRenderer(defaultRenderer);
                clicked = true;
            }
            repaint(); //edit
        }

//        @Override
//        public void actionPerformed(ActionEvent e) {
////            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
    });

    getContentPane().add(table, BorderLayout.CENTER);
    getContentPane().add(button, BorderLayout.NORTH);
    pack();
    setVisible(true);
}

public static void main(String[] args) {
     new Main();
}

}