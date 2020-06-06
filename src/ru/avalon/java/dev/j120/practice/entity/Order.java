package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;


public class Order implements Serializable {    
    private long orderNumber;
    private LocalDate orderDate;    
    private Person contactPerson;    
    private int discount;
    private OrderStatusEnum orderStatus;
    private BigDecimal totalPrice;    
    private HashMap<Long, OrderedItem> orderList; //HashMap <артикул, товар> 
    private transient ArrayList<MyEventListener> listeners = new ArrayList<>();

    public Order(LocalDate orderDate, Person contactPerson, int discount, OrderStatusEnum orderStatus) {
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        setDiscount(discount);
        this.orderStatus = orderStatus;
        this.orderList = new HashMap<>();
        totalPrice = new BigDecimal(0);        
    }
    
    public Order(long orderNumber, LocalDate orderDate, Person contactPerson, int discount, OrderStatusEnum orderStatus) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        setDiscount(discount);
        this.orderStatus = orderStatus;
        this.orderList = new HashMap<>();
        totalPrice = new BigDecimal(0);        
    }
    
    public Order(long orderNumber, LocalDate orderDate, Person contactPerson, int discount, OrderStatusEnum orderStatus, HashMap<Long, OrderedItem> orderList) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        setDiscount(discount);
        this.orderStatus = orderStatus;
        this.orderList = orderList;
        calcTotalPrice();
    }
    
    public Order(Order order) {
        if (order != null){
            this.orderNumber = order.orderNumber;
            this.orderDate = order.orderDate;
            this.contactPerson = order.contactPerson;
            setDiscount(order.discount);
            this.orderStatus = order.orderStatus;
            if (order.orderList != null){
                this.orderList = order.orderList;
            }
            else{
                this.orderList = new HashMap<>();
            }
            calcTotalPrice();
        }
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
    
    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    
    public void setContactPerson(Person contactPerson)  {
        this.contactPerson = contactPerson;
        fireDataChanged("personUpdate");
    }

    public final void setDiscount(int discount) {
        if ( (discount > 0) && (discount <= Config.get().getMaxDiscount()) ){
            this.discount = discount;
        }       
        if ( discount > Config.get().getMaxDiscount() ){
            this.discount = Config.get().getMaxDiscount();
        }
    }
    
    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void add(OrderedItem orderedItem) {
        OrderedItem tempItem = this.orderList.putIfAbsent(orderedItem.getItem().getArticle(), orderedItem);
        if (tempItem != null){
            //Если товар уже присутствует в списке, то добавить
            tempItem.addQuantity(orderedItem.getOrderedQuantity());
            this.orderList.replace(orderedItem.getItem().getArticle(), tempItem);
        }
        calcTotalPrice();
        fireDataChanged("update");
    }
    
    public void replace(OrderedItem orderedItem) {
        this.orderList.replace(orderedItem.getItem().getArticle(), orderedItem);            
        calcTotalPrice();
        fireDataChanged("update");
    }
    
    public void reduce(long article,long quantity) {
        if (this.orderList.containsKey(article)){
            this.orderList.get(article).reduceQuantity(quantity);
            calcTotalPrice();
            fireDataChanged("update");
        }            
    }
    
    public void removeItem(long article){
        this.orderList.remove(article);
        calcTotalPrice();
        fireDataChanged("update");
    }
    
    //TODO PRIVATE
    private void calcTotalPrice(){       
        BigDecimal temp = new BigDecimal(0);
        for (OrderedItem value : this.orderList.values()) {
           temp = temp.add(value.getTotalPrice());
        }
        this.totalPrice = temp;
    }
    
    //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------
    
    public void addListener(MyEventListener listener){
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
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order №: ");
        sb.append(orderNumber);
        sb.append("\nOrder Date: ");
        sb.append(orderDate);
        sb.append("\nContact Person: ");
        sb.append(contactPerson.getContactPerson());
        sb.append("\nDelivery Address: ");
        sb.append(contactPerson.getDeliveryAddress());
        sb.append("\nPhone Number: ");
        sb.append(contactPerson.getPhoneNumber());
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
