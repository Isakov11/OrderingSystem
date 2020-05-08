
package ru.avalon.java.dev.j120.practice.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import ru.avalon.java.dev.j120.practice.IO.*;
import ru.avalon.java.dev.j120.practice.datastorage.*;
import ru.avalon.java.dev.j120.practice.entity.*;
import java.io.IOException;
import java.util.logging.*;

import javax.swing.*;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;


public class Controller {
    PriceList pricelist;
    OrderList orderlist;

    public Controller() {
        ConfigIO.readConfig();
        try {           
            pricelist = new PriceList(GoodsIO.read(Config.get().getPricePath()));
            orderlist = new OrderList(OrderIO.read(Config.get().getOrderPath()));
            //---------------------------------------------------------------------
            /*SwingUtilities.invokeLater(new Runnable(){                
                                        public void run(){new SwingDemo();}});*/
            //---------------------------------------------------------------------            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);           
        }      
    }    
    
    public void main(){        
        System.out.println(pricelist.toString());
        System.out.println(orderlist.toString());
    }

    private class SwingDemo {
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
            int len = pricelist.getPriceList().size();
            String[][] array = new String[len][5]; 
            Goods[] goods = pricelist.getPriceList().values().toArray(new Goods[len]);
            String[] columnHeader = {"Article","Variety","Color","Price","Instock"};
            
            for (int i=0; i< len; i++ ){
                array[i][0] = String.valueOf(goods[i].getArticle());
                array[i][1] = String.valueOf(goods[i].getVariety());
                array[i][2] = goods[i].getColor();
                array[i][3] = String.valueOf(goods[i].getPrice());
                array[i][4] = String.valueOf(goods[i].getInstock());
            }
            JTable table = new JTable(array, columnHeader){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table.setDefaultRenderer(table.getColumnClass(1), new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {   
		        super.setHorizontalAlignment(SwingConstants.CENTER);
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				return this;   
			}
		});
            setLayout(new BorderLayout());
            add(new JScrollPane(table),BorderLayout.NORTH);      
        }
    }
    class OrdersPanel extends JPanel{

        public OrdersPanel() {
            int len = orderlist.getOrderList().size();
            String[][] array = new String[len][7]; 
            Order[] orders = orderlist.getOrderList().values().toArray(new Order[len]);
            String[] columnHeader = {"Number","Date","Contact Person","Discount","Status","Total Price","Discount Price"};
            
            for (int i=0; i<len; i++ ){
                array[i][0] = String.valueOf(orders[i].getOrderNumber());
                array[i][1] = String.valueOf(orders[i].getOrderDate());
                array[i][2] = String.valueOf(orders[i].getContactPerson().getContactPerson());
                array[i][3] = String.valueOf(orders[i].getDiscount()+"%");
                array[i][4] = String.valueOf(orders[i].getOrderStatus());
                array[i][5] = String.valueOf(orders[i].getTotalPrice().doubleValue());
                array[i][6] = String.valueOf(orders[i].getDiscountPrice().doubleValue());
            }
            JTable table = new JTable(array, columnHeader){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };            
            
            table.setDefaultRenderer(table.getColumnClass(1), new DefaultTableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {   
		        super.setHorizontalAlignment(SwingConstants.CENTER);
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				return this;   
			}
		});
            
            setLayout(new BorderLayout());  
            add(new JScrollPane(table),BorderLayout.NORTH);      
        }        
    }
}
    
    