package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class OrderedGoods implements Serializable{
    private final long article;
    private final BigDecimal fixedPrice;
    private long orderedQuantity;
    private BigDecimal totalPrice;

    public OrderedGoods(long article, BigDecimal fixedPrice, long orderedQuantity) {
        this.article = article;
        this.fixedPrice = fixedPrice;
        this.orderedQuantity = orderedQuantity;
        totalPrice = fixedPrice.multiply(new BigDecimal(orderedQuantity));
    }
    
    public long getArticle() {
        return article;
    }
    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public long getOrderedQuantity() {
        return orderedQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    
    public void addQuantity(long quantity) {        
        if (quantity >= 0){
            this.orderedQuantity += quantity;
            totalPrice = fixedPrice.multiply(new BigDecimal(orderedQuantity));
        }
    }
    
    public void reduceQuantity(long quantity) {
        if (quantity >= 0){
            if (this.orderedQuantity - quantity >= 0){
                
                this.orderedQuantity -= quantity;
                totalPrice = fixedPrice.multiply(new BigDecimal(orderedQuantity));
            }            
        }
        else {
            throw new IllegalArgumentException("Only " + this.orderedQuantity + " goods in order");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("article: ");
        sb.append(article);       
        sb.append(", fixedPrice: ");
        sb.append(fixedPrice);
        sb.append(", orderedQuantity: ");
        sb.append(orderedQuantity);
        sb.append(", totalPrice: ");
        sb.append(totalPrice);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { 
            return true; 
        }
        if (!(obj instanceof OrderedGoods)) { 
            return false; 
        }
        OrderedGoods orderedGoods = (OrderedGoods) obj;
        return this.article == orderedGoods.article;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.article ^ (this.article >>> 32));
        hash = 59 * hash + Objects.hashCode(this.fixedPrice);
        hash = 59 * hash + (int) (this.orderedQuantity ^ (this.orderedQuantity >>> 32));
        hash = 59 * hash + Objects.hashCode(this.totalPrice);
        return hash;
    }
}
