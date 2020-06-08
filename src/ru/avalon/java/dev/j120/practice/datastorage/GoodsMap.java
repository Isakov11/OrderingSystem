package ru.avalon.java.dev.j120.practice.datastorage;

import java.util.Arrays;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.entity.Good;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;


public class GoodsMap {
    private HashMap<Long, Good> goodsMap;
            
    public GoodsMap() {
        goodsMap = new HashMap<>();
    }
    
    public GoodsMap(HashMap<Long, Good> goodsMap) {                
        if (goodsMap != null){
            this.goodsMap = goodsMap;
        }
        else{
            this.goodsMap = new HashMap<>();
        }
    }
    
    /** Добавляет в goodsMap товар уже имеющий артикул    
     * @param good
     * @throws IllegalArgumentException 
     * @throws ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException 
     */
    public void add(Good good) throws IllegalArgumentException, IllegalStatusException{        
        if (good.getArticle() != 0){
            if(goodsMap.putIfAbsent(good.getArticle(), good) != null){
                throw new IllegalStatusException("Article " + good.getArticle() + " already in the list." );
            }
            //fireDataChanged("update");
        }
        else{
            throw new IllegalArgumentException("Good does not have article");
        }
    }
    
    public HashMap<Long, Good> getGoodsMap() {
        return new HashMap<> (goodsMap);
    }
    
    public Good getGood(Long article) throws IllegalArgumentException{
        if (goodsMap.containsKey(article)){
            return new Good(goodsMap.get(article));
        }
        else{
            throw new IllegalArgumentException("Article " + article +" not exist");
        }
    }
    
    public void removeGood(long article) {
        goodsMap.remove(article);
        //fireDataChanged("update");
    }
    
    public void replaceGood(Good good) {
        goodsMap.replace(good.getArticle(), good);
        //fireDataChanged("update");
    }
    
    /**Возвращает наименьший неиспользованный артикул
     * @return long*/
    public final long getFreeArticle(){        
        if (goodsMap.isEmpty()) {
            return 1;
        }
        
        Long[] keyArray = new Long[goodsMap.keySet().size()];
        keyArray = goodsMap.keySet().toArray(keyArray);
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
        sb.append(goodsMap.size());
        sb.append("\nCurrent free article: ");
        sb.append(getFreeArticle());
        sb.append("\nMap:\n");
        goodsMap.forEach((k,v) -> {sb.append("Article: ");
                                    sb.append(k);
                                    sb.append(" : ");
                                    sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
