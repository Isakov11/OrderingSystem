package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.math.BigDecimal;


public class OrderedItem implements Serializable{
    private final Good Item;
    private final BigDecimal fixedPrice;
    private long orderedQuantity;
    private BigDecimal totalPrice;

    public OrderedItem(Good Item, BigDecimal fixedPrice, long orderedQuantity) {
        this.Item = Item;
        this.fixedPrice = fixedPrice;
        this.orderedQuantity = orderedQuantity;
        totalPrice = fixedPrice.multiply(new BigDecimal(orderedQuantity));
    }
    
    public Good getItem() {
        return Item;
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

    public void setOrderedQuantity(long orderedQuantity) {
        if (orderedQuantity >= 0){
            this.orderedQuantity = orderedQuantity;
            totalPrice = fixedPrice.multiply(new BigDecimal(this.orderedQuantity));
        }        
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
        sb.append(Item.getArticle());
        sb.append(", Variety: ");
        sb.append(Item.getVariety());
        sb.append(", Color: ");
        sb.append(Item.getColor());
        sb.append(", fixedPrice: ");
        sb.append(fixedPrice);
        sb.append(", ordered quantity: ");
        sb.append(orderedQuantity);
        sb.append(", total price: ");
        sb.append(totalPrice);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { 
            return true; 
        }
        if (!(obj instanceof OrderedItem)) { 
            return false; 
        }
        OrderedItem orderedItem = (OrderedItem) obj;
        return this.Item == orderedItem.Item;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.Item.getArticle() ^ (this.Item.getArticle() >>> 32));        
        return hash;
    }
}
