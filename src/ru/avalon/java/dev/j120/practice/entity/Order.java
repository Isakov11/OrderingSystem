package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class Order implements Serializable {    
    private final long orderNumber;
    private final LocalDate orderDate;    
    private Person contactPerson;    
    private int discount;
    private OrderStatusEnum orderStatus;
    private BigDecimal totalPrice;
    //HashMap<артикул, товар> orderList;
    private HashMap<Long, OrderedItem> orderList;

    public Order(long orderNumber, LocalDate orderDate, Person contactPerson, int discount, OrderStatusEnum orderStatus) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        setDiscntWtCheck(discount);
        this.orderStatus = orderStatus;
        this.orderList = new HashMap<>();
        totalPrice = new BigDecimal(0);
    }

    public Order(long orderNumber, LocalDate orderDate, Person contactPerson, int discount, OrderStatusEnum orderStatus, HashMap<Long, OrderedItem> orderList) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        setDiscntWtCheck(discount);
        this.orderStatus = orderStatus;
        this.orderList = orderList;
        calcTotalPrice();
    }
    
    public Order(Order order) {
        this.orderNumber = order.orderNumber;
        this.orderDate = order.orderDate;
        this.contactPerson = order.contactPerson;
        setDiscntWtCheck(order.discount);
        this.orderStatus = order.orderStatus;
        this.orderList = order.orderList;
        calcTotalPrice();
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Person getContactPerson() {
        return new Person(contactPerson);
    }

    public int getDiscount() {
        return discount;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public HashMap<Long, OrderedItem> getOrderList() {
        return new HashMap<> (orderList);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public BigDecimal getDiscountPrice() {
        return totalPrice.multiply(new BigDecimal(1 - this.discount*0.01));
    }
    
    public void setContactPerson(Person contactPerson) throws IllegalArgumentException {
        if (this.orderStatus == OrderStatusEnum.PREPARING){
            this.contactPerson = contactPerson;
        }
        else {
            throw new IllegalArgumentException("Order status is " + this.orderStatus);
        }
    }

    public void setDiscount(int discount) throws IllegalArgumentException {
        if (this.orderStatus == OrderStatusEnum.PREPARING){
            setDiscntWtCheck(discount);
        }
        else {
            throw new IllegalArgumentException("Order status is " + this.orderStatus);
        }
    }
    
    private void setDiscntWtCheck(int discount) throws IllegalArgumentException {
        if ( (discount > 0) && (discount <= Config.get().getMaxDiscount()) ){
            this.discount = discount;
        }       
        if ( discount > Config.get().getMaxDiscount() ){
            this.discount = Config.get().getMaxDiscount();
        }
    } 
    
    public void setOrderStatus(OrderStatusEnum orderStatus) throws IllegalArgumentException {
        if (this.orderStatus == OrderStatusEnum.PREPARING){
            this.orderStatus = orderStatus;
        }
        else {
            throw new IllegalArgumentException("Order status is " + this.orderStatus);
        }
    }

    public void add(OrderedItem orderedItem)throws IllegalArgumentException{
        if (this.orderStatus == OrderStatusEnum.PREPARING){
            OrderedItem tempGood = this.orderList.putIfAbsent(orderedItem.getArticle(), orderedItem);
            if (tempGood != null){
                //Если товар уже присутствует в списке, то прибавить и заменить
                tempGood.addQuantity(orderedItem.getOrderedQuantity());
                this.orderList.replace(orderedItem.getArticle(), tempGood);
            }
            calcTotalPrice();
        }
        else {
            throw new IllegalArgumentException("Order status is " + this.orderStatus);
        }
    }
    
    public void reduce(long article,long quantity)throws IllegalArgumentException{
        if (this.orderStatus == OrderStatusEnum.PREPARING){
            if (this.orderList.containsKey(article)){
                this.orderList.get(article).reduceQuantity(quantity);
                calcTotalPrice();
            }            
        }
        else {
            throw new IllegalArgumentException("Order status is " + this.orderStatus);
        }
    }
    
    public void remove(long article){
        if (this.orderStatus == OrderStatusEnum.PREPARING){
            this.orderList.remove(article);
            calcTotalPrice();
        }
    }
    
    private void calcTotalPrice(){       
        BigDecimal temp = new BigDecimal(0);
        for (OrderedItem value : this.orderList.values()) {
           temp = temp.add(value.getTotalPrice());
        }
        this.totalPrice = temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order №: ");
        sb.append(orderNumber);
        sb.append("\nOrder Date: ");
        sb.append(orderDate);
        sb.append("\nContact Person: ");
        sb.append(contactPerson);
        sb.append("\nDiscount: ");
        sb.append(discount);
        sb.append("%");
        sb.append("\nOrder Status: ");
        sb.append(orderStatus);
        sb.append("\nTotal Price: ");
        sb.append(totalPrice);        
        sb.append("\nDiscount Price: ");
        sb.append(getDiscountPrice().doubleValue());       
        sb.append("\nOrder list:\n");
        
        orderList.forEach((k,v) -> {sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
    
    
}
