
package ru.avalon.java.dev.j120.practice.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import ru.avalon.java.dev.j120.practice.IO.*;
import ru.avalon.java.dev.j120.practice.datastorage.*;
import ru.avalon.java.dev.j120.practice.entity.*;
import java.io.IOException;
import java.math.BigDecimal;


import javax.swing.*;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;

public class Controller {
    PriceList pricelist;
    OrderList orderlist;
    JFrame mainf;
    public Controller() {
    }    
    
    public void main(){        
        try {
            pricelist = new PriceList(GoodsIO.read(Config.get().getPricePath()));
            orderlist = new OrderList(OrderIO.read(Config.get().getOrderPath()));
            System.out.println(pricelist.toString());
            System.out.println(orderlist.toString());
            
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainf =  new MainFrame(pricelist, orderlist);
            });
            //--------------------------------------------------------------------- 
            
            
        } catch (IOException | ClassNotFoundException | IllegalArgumentException ex) {            
            JFrame frame =  new JFrame(ex.getClass().getSimpleName());
            frame.add(new JLabel(ex.getMessage()));            
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }  
    }
    
    /*private class SwingDemo {
        public SwingDemo() {
            
            JFrame frame =  new JFrame("Simple");
            JTabbedPane pane = new JTabbedPane();
            pane.add("Goods", new GoodsPanel());
            pane.add("Orders", new OrdersPanel());
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(pane);
            frame.setVisible(true);
        }
    }
    
    class GoodsPanel extends JPanel{
        public GoodsPanel() {            
            GoodsTableModel gtm = new GoodsTableModel(pricelist);
            
            JTable table = new JTable(gtm){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            setLayout(new BorderLayout());
            add(new JScrollPane(table),BorderLayout.NORTH);      
        }
    }
    class OrdersPanel extends JPanel{
        public OrdersPanel() {
            OrderTableModel otm = new OrderTableModel(orderlist);
            
            JTable table = new JTable(otm);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.setDefaultRenderer(Object.class, new MyTableRenderer());
            
            RowSorter<TableModel> sorter = new TableRowSorter<>(otm);
            table.setRowSorter(sorter);
            
            setLayout(new BorderLayout());  
            add(new JScrollPane(table),BorderLayout.NORTH);                        
        }        
    }
    
    public class MyTableRenderer extends DefaultTableCellRenderer{
        
        
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                Object value,
                                boolean isSelected,
                                boolean hasFocus,
                                int row,
                                int column){
        setText(value.toString());
	
        if ((table.getValueAt(row, 4)).equals(String.valueOf(OrderStatusEnum.CANCELED)))
         {
            setBackground(Color.GRAY);
            setForeground(Color.WHITE);
         }         
        else
        {
            if (isSelected)
            {
                setBackground(Color.GREEN);
                setForeground(Color.BLACK);
            }	
            else
            {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
        }        
        if (column == 0 || column == 3 || column == 5|| column == 6)
            setHorizontalAlignment(SwingConstants.RIGHT);
        else 
            setHorizontalAlignment(SwingConstants.LEFT);
        /*super.setHorizontalAlignment(SwingConstants.CENTER);        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);	*/
 /*        return this;									   
      }
   }*/
}
    
    