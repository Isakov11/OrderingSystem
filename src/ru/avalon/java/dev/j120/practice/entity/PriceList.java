package ru.avalon.java.dev.j120.practice.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

public class PriceList {
    private Long currentArticle;
    private HashMap<Long, Goods> priceList;

    public PriceList() {
        currentArticle = new Long(0);
        priceList = new HashMap<>();
    }    

    public Long getCurrentArticle() {
        return currentArticle;
    }

    public void setCurrentArticle(Long currentArticle) {
        this.currentArticle = currentArticle;
    }

    public void addNew(String variety, BigDecimal price, long instock) throws IllegalArgumentException {        
        currentArticle++;
        if(priceList.putIfAbsent(currentArticle, new Goods (currentArticle, variety, price, instock)) != null){
            this.addNew(variety, price, instock);            
        }
    }
    
    public void addNew(String variety, String color, BigDecimal price, long instock) throws IllegalArgumentException {        
        currentArticle++;
        if(priceList.putIfAbsent(currentArticle, new Goods (currentArticle, variety,color, price, instock)) != null){
            this.addNew(variety, color, price, instock);            
        }
    }
    
    public void addExst(Goods goods) throws IllegalArgumentException{        
        if(priceList.putIfAbsent(goods.getArticle(), goods) != null){
            throw new IllegalArgumentException("Article " + goods.getArticle() + " already in the list." );
        }
        else{
            currentArticle = Math.max(currentArticle, goods.getArticle());
        }
    }    
    public Set<Long> keySet() {
        return priceList.keySet();
    }
    
    public Goods find(Long article){
        Goods tempo = priceList.get(article);        
        return new Goods(tempo.getArticle(),tempo.getVariety(),
                         tempo.getColor(),tempo.getPrice(),tempo.getInstock());
    }
    
    public void replace(Goods goods) {
        priceList.replace(goods.getArticle(), goods);
    }
    
    public void remove(Long article){
        priceList.remove(article);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PriceList:\ncurrentArticle: ");
        sb.append(currentArticle);
        sb.append("\nMap:\n");
        priceList.forEach((k,v) -> {sb.append("Article: ");
                                    sb.append(k);
                                    sb.append(" : ");
                                    sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
