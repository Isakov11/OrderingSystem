package ru.avalon.java.dev.j120.practice.datastorage;

import java.math.BigDecimal;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.entity.Goods;

public class PriceList {
    private Long currentFreeArticle;
    private HashMap<Long, Goods> priceList;

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
     * @param price
     * @param instock
     * @throws IllegalArgumentException 
     */
    public long addNew(String variety, BigDecimal price, long instock) throws IllegalArgumentException {
        //Если не удалось вставить товар по текущему артикулу, то найти наименьший свободный артикул и вставить
        if(priceList.putIfAbsent(currentFreeArticle, new Goods (currentFreeArticle, variety, price, instock)) != null){
            currentFreeArticle = this.getFreeArticle();
            priceList.put(currentFreeArticle, new Goods (currentFreeArticle, variety, price, instock));        
        }        
        return currentFreeArticle++;
    }
    /**Добавляет новый элемент в priceList
     * @param variety
     * @param color
     * @param price
     * @param instock
     * @throws IllegalArgumentException 
     */    
    public long addNew(String variety, String color, BigDecimal price, long instock) throws IllegalArgumentException {
        //Если не удалось вставить товар по текущему артикулу, то найти наименьший свободный артикул и вставить
        if(priceList.putIfAbsent(currentFreeArticle, new Goods (currentFreeArticle, variety,color, price, instock)) != null){
            currentFreeArticle = this.getFreeArticle();
            priceList.put(currentFreeArticle, new Goods (currentFreeArticle, variety,color, price, instock));            
        }
        
        //currentFreeArticle++;
        return currentFreeArticle++;
    }
    
    /** Вставляет в priceList товар уже имеющий артикул    
     * @param goods
     * @throws IllegalArgumentException 
     */
    public void addExist(Goods goods) throws IllegalArgumentException{        
        if(priceList.putIfAbsent(goods.getArticle(), goods) != null){
            throw new IllegalArgumentException("Article " + goods.getArticle() + " already in the list." );
        }        
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
    }
    
    public void replaceGoods(long article, Goods goods) {
        priceList.replace(article, goods);
    }
    
    /**Возвращает наименьший неиспользованный артикул*/
    private long getFreeArticle(){        
        if (priceList.isEmpty()) {
            return 1;
        }
        
        Long[] keyArray = new Long[priceList.keySet().size()];
        keyArray = priceList.keySet().toArray(keyArray);
        
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
        sb.append("PriceList size:" + priceList.size());
        sb.append("PriceList:\ncurrentFreeArticle: ");
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
