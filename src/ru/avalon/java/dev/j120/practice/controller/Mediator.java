
package ru.avalon.java.dev.j120.practice.controller;

import java.util.ArrayList;
import ru.avalon.java.dev.j120.practice.entity.Good;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.utils.MyEventSource;

public interface Mediator extends MyEventSource{

    public ArrayList<Good> getGoodsArray();
    
    public Good getGood(long article);    
   
    public ArrayList<Order> getOrdersArray();   
    
    public Order getOrder(long orderNumber);
    
    public Good addGood(Good good);
    
    public void updateGood(Good good);
      
    public void removeOrder(long orderNumber);
    
    public Order addOrder(Order order);

    public void updateOrder(Order order);
}