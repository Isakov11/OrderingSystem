package ru.avalon.java.dev.j120.practice.controller;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ru.avalon.java.dev.j120.practice.datastorage.PriceList;
import ru.avalon.java.dev.j120.practice.entity.Goods;

public class GoodsTableModel extends AbstractTableModel {
    private ArrayList<Goods> arrayPriceList;
    private String[] columnHeader = {"Article","Variety","Color","Price","Instock"};
    private Class[] columnClasses = new Class[]{
        Long.class, String.class, String.class, Double.class, Long.class};
    
    public GoodsTableModel(PriceList pricelist) {
        this.arrayPriceList = new ArrayList<>(pricelist.getPriceList().values());
    }
    
    public void update(PriceList pricelist){
        this.arrayPriceList = new ArrayList<>(pricelist.getPriceList().values());
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    @Override
    public int getRowCount() {
        return arrayPriceList.size();
    }

    @Override
    public int getColumnCount() {
        return columnHeader.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnHeader[column]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Goods goods = arrayPriceList.get(rowIndex);
        switch(columnIndex) { 
            case 0: return goods.getArticle(); 
            case 1: return goods.getVariety(); 
            case 2: return goods.getColor(); 
            case 3: return goods.getPrice();
            case 4: return goods.getInstock();
            default: return null; 
        } 
    }
}
