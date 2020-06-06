package ru.avalon.java.dev.j120.practice.datastorage;

import java.util.Arrays;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.entity.Good;


public class GoodsMap {
    private HashMap<Long, Good> priceList;
    //private ArrayList<MyEventListener> listeners = new ArrayList<>(); 
            
    public GoodsMap() {
        priceList = new HashMap<>();
    }
    
    public GoodsMap(HashMap<Long, Good> priceList) {                
        
        if (priceList != null){
            this.priceList = priceList;
        }
        else{
            this.priceList = new HashMap<>();
        }
    }
    
    /**Добавляет новый элемент в priceList
     * @param variety
     * @param color
     * @param price
     * @param instock
     * @return 
     * @throws IllegalArgumentException 
     */    
    
    /*public long addNew(String variety, String color, BigDecimal price, long instock) {
        //Если не удалось вставить товар по текущему артикулу, то найти наименьший свободный артикул и вставить
        if(priceList.putIfAbsent(currentFreeArticle, new Good(currentFreeArticle,variety,color,price,instock)) != null){
            currentFreeArticle = this.getFreeArticle();
            priceList.put(currentFreeArticle, new Good(currentFreeArticle,variety,color,price,instock));            
        }
        long usedArticle = currentFreeArticle;
        currentFreeArticle = this.getFreeArticle();
        fireDataChanged("update");       
        return usedArticle;
    }*/
    
    /** Вставляет в priceList товар уже имеющий артикул    
     * @param good
     * @throws IllegalArgumentException 
     */
    public void add(Good good) throws IllegalArgumentException{        
        if(priceList.putIfAbsent(good.getArticle(), good) != null){
            throw new IllegalArgumentException("Article " + good.getArticle() + " already in the list." );
        }
        //fireDataChanged("update");
    }
    
    public HashMap<Long, Good> getPriceList() {
        return new HashMap<> (priceList);
    }
    
    public Good getGood(Long article) throws IllegalArgumentException{
        if (priceList.containsKey(article)){            
            return new Good(priceList.get(article));
        }
        else{
            throw new IllegalArgumentException("Article " + article +" not exist");
        }
    }
    
    public void removeGood(long article) {
        priceList.remove(article);
        //fireDataChanged("update");
    }
    
    public void replaceGood(Good good) {
        priceList.replace(good.getArticle(), good);
        //fireDataChanged("update");
    }
    
   /*public void addListener(MyEventListener listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    } 
    
    public void removeListener(MyEventListener listener){
        if (listeners.contains(listener)){
            listeners.remove(listener);
        }
    }
    
    public MyEventListener[] getListeners(){
        return listeners.toArray(new MyEventListener[listeners.size()]);
    }
    
    public void fireDataChanged(String message){                
        listeners.forEach((listener) -> {
            listener.update(message);
        });
    }*/
    
    /**Возвращает наименьший неиспользованный артикул
     * @return long*/
    public final long getFreeArticle(){        
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
        sb.append(getFreeArticle());
        sb.append("\nMap:\n");
        priceList.forEach((k,v) -> {sb.append("Article: ");
                                    sb.append(k);
                                    sb.append(" : ");
                                    sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
