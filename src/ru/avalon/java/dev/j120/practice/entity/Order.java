package ru.avalon.java.dev.j120.practice.entity;

import java.time.LocalDate;
import java.util.HashMap;



public class Order {    
    private long orderNumber;
    private LocalDate orderDate;
    
    private Person contactPerson;
    
    private byte discount;
    private OrderStatus orderStatus;

    private HashMap<Long, Goods> orderList;

    public Order(long orderNumber, LocalDate orderDate, Person contactPerson, byte discount, OrderStatus orderStatus, HashMap<Long, Goods> orderList) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        this.discount = discount;
        this.orderStatus = orderStatus;
        orderList = new HashMap<>();
    }
    
}
