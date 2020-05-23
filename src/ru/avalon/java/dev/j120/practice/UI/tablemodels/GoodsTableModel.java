package ru.avalon.java.dev.j120.practice.UI.tablemodels;


import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Good;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;

public class GoodsTableModel extends AbstractTableModel implements MyEventListener {
    private Mediator mediator;    
    private ArrayList<Good> arrayPriceList;
    private final String[] columnHeader = {"Article","Variety","Color","Price","Instock"};
    private final Class[] columnClasses = new Class[]{
        Long.class, String.class, String.class, BigDecimal.class, Long.class};
    
    public GoodsTableModel(Mediator mediator) {
        mediator.getPriceList().addListener(this);
        this.mediator = mediator;
        
        this.arrayPriceList = new ArrayList<>(mediator.getPriceList().getPriceList().values());
        arrayPriceList.sort((Good o1, Good o2) -> o1.getArticle()>o2.getArticle()? 1: -1);
    }
    
    @Override
    /**
     * Обновление данных модели и таблицы по событию от источника
     */
    public void update(String eventType) {
        
        this.arrayPriceList = new ArrayList<>(mediator.getPriceList().getPriceList().values());
        //Сортировка по возрастанию артикула
        arrayPriceList.sort((Good o1, Good o2) -> o1.getArticle()>o2.getArticle()? 1: -1);
        
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
        Good goods = arrayPriceList.get(rowIndex);
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
