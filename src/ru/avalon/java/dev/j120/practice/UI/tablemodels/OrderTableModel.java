package ru.avalon.java.dev.j120.practice.UI.tablemodels;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.Person;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;


public class OrderTableModel extends AbstractTableModel  implements MyEventListener{
    private final Mediator mediator;
    private ArrayList<Order> arrayOrderList;
    private final String[] columnHeader = {"Number","Date","Contact Person","Discount","Status","Total Price","Discount Price"};
    private final Class[] columnClasses = new Class[]{
        Long.class, LocalDate.class, Person.class, Long.class, String.class, Double.class, Double.class};

    public OrderTableModel(Mediator mediator) {
        mediator.getOrderList().addListener((MyEventListener) this);
        this.mediator = mediator;
        this.arrayOrderList = new ArrayList<>(mediator.getOrderList().getOrderList().values());
        arrayOrderList.sort((Order o1, Order o2) -> o1.getOrderNumber()>o2.getOrderNumber()? 1: -1);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    @Override
    public int getRowCount() {
        return arrayOrderList.size();
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
        Order order = arrayOrderList.get(rowIndex);
        switch(columnIndex) { 
            case 0: return order.getOrderNumber(); 
            case 1: return order.getOrderDate();
            case 2: return order.getContactPerson(); 
            case 3: return order.getDiscount();
            case 4: return order.getOrderStatus();
            case 5: return order.getTotalPrice();
            case 6: return order.getDiscountPrice();
            default: return null; 
        } 
    }

    @Override
    public void update(String eventType) {
         this.arrayOrderList = new ArrayList<>(mediator.getOrderList().getOrderList().values());
        arrayOrderList.sort((Order o1, Order o2) -> o1.getOrderNumber()>o2.getOrderNumber()? 1: -1);
        
        this.fireTableDataChanged();
    }
}
