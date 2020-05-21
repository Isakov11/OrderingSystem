package ru.avalon.java.dev.j120.practice.datastorage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.entity.Goods;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;

public class PriceList {
    private Long currentFreeArticle;
    private HashMap<Long, Goods> priceList;
    private ArrayList<MyEventListener> listeners = new ArrayList<>(); 
            
    public PriceList() {
        currentFreeArticle = new Long(1);
        priceList = new HashMap<>();
    }
    
    public PriceList(HashMap<Long, Goods> priceList) {                
        this.priceList = priceList;
        currentFreeArticle = getFreeArticle();
    }

    public Long getCurrentFreeArticle() {
        return currentFreeArticle;
    }    
    
    /**Добавляет новый элемент в priceList
     * @param variety
     * @param color
     * @param price
     * @param instock
     * @return 
     * @throws IllegalArgumentException 
     */    
    
    public long addNew(String variety, String color, BigDecimal price, long instock) {
        //Если не удалось вставить товар по текущему артикулу, то найти наименьший свободный артикул и вставить
        if(priceList.putIfAbsent(currentFreeArticle, new Goods(currentFreeArticle,variety,color,price,instock)) != null){
            currentFreeArticle = this.getFreeArticle();
            priceList.put(currentFreeArticle, new Goods(currentFreeArticle,variety,color,price,instock));            
        }
        long usedArticle = currentFreeArticle;
        currentFreeArticle = this.getFreeArticle();
        fireDataChanged("update");       
        return usedArticle;
    }
    /** Вставляет в priceList товар уже имеющий артикул    
     * @param goods
     * @throws IllegalArgumentException 
     */
    public void addExist(Goods goods) throws IllegalArgumentException{        
        if(priceList.putIfAbsent(goods.getArticle(), goods) != null){
            throw new IllegalArgumentException("Article " + goods.getArticle() + " already in the list." );
        }
        currentFreeArticle = getFreeArticle();
        fireDataChanged("update");
    }
    
    public HashMap<Long, Goods> getPriceList() {
        return new HashMap<> (priceList);
    }
    
    public Goods getGoods(long article) throws IllegalArgumentException{
        if (priceList.containsKey(article)){            
            return new Goods(priceList.get(article));
        }
        throw new IllegalArgumentException("Article " + article +" not exist");
    }
    
    public void removeGoods(long article) {
        priceList.remove(article);
        currentFreeArticle = getFreeArticle();
        fireDataChanged("update");
    }
    
    public void replaceGoods(Goods goods) {
        priceList.replace(goods.getArticle(), goods);
        fireDataChanged("update");
    }
    
    public void addListener(MyEventListener listener){
        listeners.add(listener);
    } 
    
    public void removeListener(MyEventListener listener){
        listeners.remove(listener);
    }
    
    public MyEventListener[] getListeners(){
        return listeners.toArray(new MyEventListener[listeners.size()]);
    }
    
    protected void fireDataChanged(String message){
        listeners.forEach((listener) -> {
            listener.update(message);
        });
    }
    
    /**Возвращает наименьший неиспользованный артикул
     * @return long*/
    public long getFreeArticle(){        
        if (priceList.isEmpty()) {
            return 1;
        }
        
        Long[] keyArray = new Long[priceList.keySet().size()];
        keyArray = priceList.keySet().toArray(keyArray);
        Arrays.sort(keyArray);
        for (int i=0; i < (keyArray.length - 1); i++){
            //Если разница между соседними значениями > 1, то между ними содержится свободный артикул
            if ( (keyArray[i+1] - keyArray[i]) > 1){                
                return keyArray[i] + 1;
            }
        }
        //Свободный артикул следующий после максимального имеющегося        
        return (keyArray[keyArray.length-1]) + 1;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PriceList\nSize: ");
        sb.append(priceList.size());
        sb.append("\nCurrent free article: ");
        sb.append(currentFreeArticle);
        sb.append("\nMap:\n");
        priceList.forEach((k,v) -> {sb.append("Article: ");
                                    sb.append(k);
                                    sb.append(" : ");
                                    sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
