package ru.avalon.java.dev.j120.practice.commons;

import java.math.BigDecimal;

public class Goods {
    private long article;
    private String variety;
    private Color color;
    private BigDecimal price;
    private long instock;
    
    public static Goods create(long article, String variety, BigDecimal price, long instock) throws IllegalArgumentException{
        if (price.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new IllegalArgumentException("Object Goods not created. Price must be positive");
        }
        if (instock < 0 ){
            throw new IllegalArgumentException("Object Goods not created. Instock must not be negative");
        }        
        return new Goods(article, variety, price, instock);               
    }
    
    public static Goods create(long article, String variety, Color color, BigDecimal price, long instock) throws IllegalArgumentException{
        if (price.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new IllegalArgumentException("Object Goods not created. Price must be positive");
        }
        if (instock < 0 ){
            throw new IllegalArgumentException("Object Goods not created. Instock must not be negative");
        }        
        return new Goods(article, variety, price, instock);               
    }      
    
    private Goods(){}
    
    private Goods(long article, String variety, BigDecimal price, long instock) {        
        this.article = article;
        this.variety = variety;        
        this.price = price;
        this.instock = instock;
    }

    private Goods(long article, String variety, Color color, BigDecimal price, long instock) {
        this.article = article;
        this.variety = variety;
        this.color = color;
        this.price = price;
        this.instock = instock;
    }    
    
    public long getArticle() {
        return article;
    }

    public String getVariety() {
        return variety;
    }

    public Color getColor() {
        return color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getInstock() {
        return instock;
    }
     
    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPrice(BigDecimal price) throws IllegalArgumentException{
        if (price.compareTo(BigDecimal.ZERO) > 0 ){
            this.price = price;
        }
        else{
            throw new IllegalArgumentException("Price must be positive"); 
        }
    }

    public void addInstock(long stack) {
        if (stack > 0){
            this.instock += stack;
        }
    }
    
    public long reduceInstock(long stack) throws IllegalArgumentException{
        if (stack > 0){
            if ( (this.instock - stack) >= 0){
                this.instock -= stack;
                return stack;
            }
            else{
                throw new IllegalArgumentException("Only " + this.instock + " item(s) left");
            }        
        }
        else{ 
            throw new IllegalArgumentException("Value must be positive"); 
        }
    }    
    
    @Override
    public String toString() {
        return "Goods{" + "article=" + article + ", variety=" + variety + ", color=" + color + ", price=" + price + ", instock=" + instock + '}';
    }
    
}
