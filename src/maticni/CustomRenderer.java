/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maticni;

import java.awt.Color;
import java.awt.Component;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ms
 */
//class CustomRenderer {
public class CustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 6703872492730589499L;
        int answer = 0;
    public CustomRenderer(int i) {
        this.answer = i;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

//if((x%2)==0)
//   // even
//else
//   // odd
//156,152,237

//        Random rn = new Random();
//        int answer = 0;
//
//        for (int i = 0; i < 100; i++) {
//            answer = rn.nextInt(2) + 1; // 10 umesto 2
//            System.out.println(answer);
//        }

        if (row % 2 == 0) {
            if (answer == 0) {
                cellComponent.setBackground(new Color(255, 174, 185)); // 132 112 255
            } else {
                cellComponent.setBackground(new Color(115,173,232)); // 132 112 255

            }

            cellComponent.setForeground(Color.black);

        } else {
            cellComponent.setBackground(Color.white);
            cellComponent.setForeground(Color.black);
        }

        if (isSelected) {
            cellComponent.setBackground(new Color(139, 95, 101));
//                       cellComponent.setBackground(Color.getHSBColor( 256,262,287));
            cellComponent.setForeground(Color.white);
        }

//                if (hasFocus)
//        {
//                       cellComponent.setBackground(Color.blue);
//                       cellComponent.setForeground(Color.white);  
//        }
        return cellComponent;
    }
}
    
//}
