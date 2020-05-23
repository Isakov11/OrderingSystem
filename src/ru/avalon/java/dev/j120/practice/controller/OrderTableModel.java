package ru.avalon.java.dev.j120.practice.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import ru.avalon.java.dev.j120.practice.datastorage.OrderList;
import ru.avalon.java.dev.j120.practice.entity.Order;


public class OrderTableModel extends AbstractTableModel {
    private ArrayList<Order> orderlist;
    private String[] columnHeader = {"Number","Date","Contact Person","Discount","Status","Total Price","Discount Price"};
    private Class[] columnClasses = new Class[]{
        Long.class, LocalDate.class, String.class, Long.class, String.class, Double.class, Double.class};
    
    public OrderTableModel(OrderList orderlist) {
        this.orderlist = new ArrayList<>(orderlist.getOrderList().values());
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    @Override
    public int getRowCount() {
        return orderlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnHeader.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnHeader[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orderlist.get(rowIndex);
        switch(columnIndex) { 
            case 0: return order.getOrderNumber(); 
            case 1: return order.getOrderDate(); 
            case 2: return order.getContactPerson().getContactPerson(); 
            case 3: return order.getDiscount();
            case 4: return order.getOrderStatus();
            case 5: return order.getTotalPrice();
            case 6: return order.getDiscountPrice();
            default: return null; 
        } 
    }
}
