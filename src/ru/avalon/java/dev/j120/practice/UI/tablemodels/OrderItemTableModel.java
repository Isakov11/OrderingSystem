/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI.tablemodels;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderedItem;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;


public class OrderItemTableModel extends AbstractTableModel implements MyEventListener {
    
    /*private final long article;
    private final BigDecimal fixedPrice;
    private long orderedQuantity;
    private BigDecimal totalPrice;*/
    private final Mediator mediator;        
    private ArrayList<OrderedItem> arrayOrderedItem;
    private final String[] columnHeader = {"Article","Variety","Color","Price","Ordered Quantity","Total Price"};
    private final Class[] columnClasses = new Class[]{
        Long.class, String.class, String.class, BigDecimal.class, Long.class, BigDecimal.class};

    public OrderItemTableModel(Mediator mediator, HashMap<Long, OrderedItem> orderedItemsList) {
        this.mediator = mediator;
        this.arrayOrderedItem = new ArrayList<> (orderedItemsList.values());
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnHeader[column]; 
    }
        
    @Override
    public int getRowCount() {
        return arrayOrderedItem.size();
    }

    @Override
    public int getColumnCount() {
        return columnHeader.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrderedItem OrderedItem = arrayOrderedItem.get(rowIndex);
        switch(columnIndex) { 
            case 0: return OrderedItem.getItem().getArticle(); 
            case 1: return OrderedItem.getItem().getVariety();
            case 2: return OrderedItem.getItem().getColor();            
            case 3: return OrderedItem.getFixedPrice(); 
            case 4: return OrderedItem.getOrderedQuantity();
            case 5: return OrderedItem.getTotalPrice(); 
            default: return null; 
        }
        
    }

    @Override
    public void update(String eventType) {
        /*this.arrayOrderedItem = new ArrayList<>(mediator.getOrderList().getOrder().getOrderList().values());
        arrayOrderedItem.sort((OrderedItem o1, OrderedItem o2) -> o1.getArticle()>o2.getArticle()? 1: -1);
        
        this.fireTableDataChanged();*/
    }
    
}
