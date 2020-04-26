package ru.avalon.java.dev.j120.practice.commons;

import java.time.LocalDate;
import java.util.List;


public class Order {
    private LocalDate orderDate;
    
    private Person contactPerson;    
    
    private byte discount;
    private OrderStatus orderStatus;
    //не годится
    private List<Goods> orderList;
    
}
