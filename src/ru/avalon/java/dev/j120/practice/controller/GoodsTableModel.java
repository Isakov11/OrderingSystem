package ru.avalon.java.dev.j120.practice.controller;


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ru.avalon.java.dev.j120.practice.datastorage.PriceList;
import ru.avalon.java.dev.j120.practice.entity.Goods;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;

public class GoodsTableModel extends AbstractTableModel implements MyEventListener {
    PriceList pricelist;
    private ArrayList<Goods> arrayPriceList;
    private final String[] columnHeader = {"Article","Variety","Color","Price","Instock"};
    private final Class[] columnClasses = new Class[]{
        Long.class, String.class, String.class, Double.class, Long.class};
    
    public GoodsTableModel(PriceList pricelist) {
        pricelist.addListener(this);
        this.pricelist = pricelist;
        this.arrayPriceList = new ArrayList<>(pricelist.getPriceList().values());
        arrayPriceList.sort((Goods o1, Goods o2) -> o1.getArticle()>o2.getArticle()? 1: -1);
    }
    
    @Override
    public void update(String eventType) {
        this.arrayPriceList = new ArrayList<>(pricelist.getPriceList().values());
        arrayPriceList.sort((Goods o1, Goods o2) -> o1.getArticle()>o2.getArticle()? 1: -1);
        
        this.fireTableDataChanged();
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
        return columnHeader[column]; 
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
