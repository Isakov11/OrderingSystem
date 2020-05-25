package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Good implements Serializable{
    private long article;
    private String variety;
    private String color;
    private BigDecimal price;
    private long instock;

    public Good(long article, String variety, String color, BigDecimal price, long instock) {
        this.article = article;
        this.variety = variety;
        this.color = color;
        setPrice(price);
        addInstock(instock);
    }

    public Good(long article, String variety, BigDecimal price, long instock) {        
        this(article, variety, "n/a", price, instock);
    }
     
    public Good(Good good){
        this.article = good.article;
        this.variety = good.variety;
        this.color = good.color;
        setPrice(good.price);
        addInstock(good.instock);
    }
    
    public long getArticle() {
        return article;
    }

    public String getVariety() {
        return variety;
    }

    public String getColor() {
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

    public void setColor(String color) {
        this.color = color;
    }

    public final void setPrice(BigDecimal price) throws IllegalArgumentException{
        if (price.compareTo(BigDecimal.ZERO) > 0 ){           
            this.price = price;
        }
        else{
            throw new IllegalArgumentException("Price must be positive"); 
        }
    }

    public final void addInstock(long stack) throws IllegalArgumentException {
        if (stack >= 0){
            this.instock += stack;
        }
        else {
            throw new IllegalArgumentException("Instock must be positive article " + this.article);
        }
    }
    
    public long reduceInstock(long stack) throws IllegalArgumentException{
        if (stack > 0){
            if ( (this.instock - stack) >= 0){
                this.instock -= stack;
                return stack;
            }
            else{
                //this.instock = 0;
                return (this.instock - stack);
                //throw new IllegalStatusException("Only " + this.instock + " item(s) left");
            }        
        }
        else{ 
            throw new IllegalArgumentException("Value must be positive"); 
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Goods{article: ");
        sb.append(article);
        sb.append(", variety: ");
        sb.append(variety);
        sb.append(", color: ");
        sb.append(color);
        sb.append(", price: ");
        sb.append(price.doubleValue());
        sb.append(", instock: ");
        sb.append(instock);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {        
        if (obj == this) { 
            return true; 
        }
        if (!(obj instanceof Good)) { 
            return false; 
        }
        Good good = (Good) obj;
        return this.article == good.article;        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.article ^ (this.article >>> 32));        
        return hash;
    }
}
