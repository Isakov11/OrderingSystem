package ru.avalon.java.dev.j120.practice.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Goods {
    private long article;
    private String variety;
    private String color;
    private BigDecimal price;
    private long instock;

    public Goods(long article, String variety, String color, BigDecimal price, long instock) {
        this.article = article;
        this.variety = variety;
        this.color = color;
        setPrice(price);
        addInstock(instock);
    }

    public Goods(long article, String variety, BigDecimal price, long instock) {        
        this(article, variety, "n/a", price, instock);
    }
     
    public Goods(Goods goods){
        this.article = goods.article;
        this.variety = goods.variety;
        this.color = goods.color;
        setPrice(goods.price);
        addInstock(goods.instock);
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
        if (stack > 0){
            this.instock += stack;
        }
        else {
            throw new IllegalArgumentException("Instock must be positive");
        }
    }
    
    public long reduceInstock(long stack) throws IllegalArgumentException{
        if (stack > 0){
            if ( (this.instock - stack) >= 0){
                this.instock -= stack;
                return stack;
            }
            else{
                this.instock = 0;
                return (this.instock - stack);
                //throw new IllegalArgumentException("Only " + this.instock + " item(s) left");
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
        if (!(obj instanceof Goods)) { 
            return false; 
        }
        Goods good = (Goods) obj;
        return this.article == good.article;        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.article ^ (this.article >>> 32));
        hash = 97 * hash + Objects.hashCode(this.variety);
        hash = 97 * hash + Objects.hashCode(this.color);
        hash = 97 * hash + Objects.hashCode(this.price);
        hash = 97 * hash + (int) (this.instock ^ (this.instock >>> 32));
        return hash;
    }
}
