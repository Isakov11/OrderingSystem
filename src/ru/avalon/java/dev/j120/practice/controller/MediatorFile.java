
package ru.avalon.java.dev.j120.practice.controller;

import ru.avalon.java.dev.j120.practice.IO.*;
import ru.avalon.java.dev.j120.practice.datastorage.*;
import ru.avalon.java.dev.j120.practice.entity.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.SwingUtilities;

import ru.avalon.java.dev.j120.practice.UI.ErrorFrame;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;

import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.*;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;

import ru.avalon.java.dev.j120.practice.utils.MyEventListener;
import ru.avalon.java.dev.j120.practice.utils.StateEnum;
import static ru.avalon.java.dev.j120.practice.utils.StateEnum.*;
import ru.avalon.java.dev.j120.practice.entity.Good;

public class MediatorFile implements Mediator{
    private GoodsIO goodsIO;
    private OrdersIO orderIO;
    private GoodsMap goodsMap;
    private OrdersMap ordersMap;    
    ArrayList<MyEventListener> listeners = new ArrayList<>();
    
    public MediatorFile() {
        try {
            goodsIO = new GoodsIO(Config.get().getPricePath());            
            orderIO = new OrdersIO(Config.get().getOrderPath());
            
            ordersMap = new OrdersMap(orderIO.read());
            goodsMap = new GoodsMap(goodsIO.read());
            
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainframe = new MainFrame(this);
            });
            //--------------------------------------------------------------------- 
            
        } catch (IOException | ClassNotFoundException | IllegalArgumentException ex) {
            ErrorFrame.create(ex);
        }  
    }    
    
    public final void main(){        
        
    }

    @Override
    public ArrayList<Good> getGoodsArray(){
        return new ArrayList<> (goodsMap.getGoodsMap().values());    
    }
    
    @Override
    public Good getGood(long article){
        return goodsMap.getGood(article);    
    }
    
    @Override
    public ArrayList<Order> getOrdersArray(){
        return new ArrayList<>(ordersMap.getOrdersMap().values());   
    }
    
    @Override
    public Order getOrder(long orderNumber){
        return ordersMap.getOrder(orderNumber);
    }
    
    @Override
    public final Good addGood(Good good){
        try {
            long article = goodsMap.getFreeArticle();
            goodsMap.add(new Good(article,
                                good.getVariety(),good.getColor(),
                                good.getPrice(),good.getInstock()) );
            
            goodsIO.write(goodsMap.getGoodsMap());
            fireDataChanged("GoodUpdate");
            return goodsMap.getGood(article);
        } catch (IllegalArgumentException | IllegalStatusException | IOException ex) {
            ErrorFrame.create(ex);
        }
        return null;
    }    
    
    @Override
    public final void updateGood(Good good){
        try {
            goodsMap.replaceGood(good);
            goodsIO.write(goodsMap.getGoodsMap());
            fireDataChanged("GoodUpdate");
        } catch (IOException ex) {
           ErrorFrame.create(ex);
        }
    }
      
    @Override
    public final void removeOrder(long orderNumber){
        try {
            ordersMap.removeOrder(orderNumber);
            fireDataChanged("OrdersMapChanged");
        } catch (IllegalStatusException | IllegalArgumentException ex) {
             ErrorFrame.create(ex);
        }
    }
    
    @Override
    public Order addOrder(Order order) {
        try {
            Order temp = new Order(ordersMap.getFreeNumber(), order);
            ordersMap.add(temp);
            orderIO.write(ordersMap.getOrdersMap());
            fireDataChanged("OrdersMapChanged");
            return temp;
        } catch (IllegalStatusException | IOException ex) {
            ErrorFrame.create(ex);
        }
        return null;
    }

    
    @Override
    public final void updateOrder(Order order){
        
        if (ordersMap.getOrdersMap().containsKey(order.getOrderNumber())){
            switch (order.getOrderStatus() ){
                case PREPARING:            
                    try {
                        ordersMap.replaceOrder(order);
                        fireDataChanged("OrdersMapChanged");
                        orderIO.write(ordersMap.getOrdersMap());
                        
                    } catch (IllegalStatusException | IllegalArgumentException | IOException ex) {
                        ErrorFrame.create(ex);
                    }
                    break;
                    
                case SHIPPED:                    
                    checkAndProcess(order);
                    break;
                    
                case CANCELED:
                    try {
                        ordersMap.replaceOrder(order);
                        fireDataChanged("OrdersMapChanged");
                        orderIO.write(ordersMap.getOrdersMap());                            
                        
                    } catch (IllegalStatusException | IOException ex) {
                        ErrorFrame.create(ex);
                    }
                    break;
            }           
        }
    }
    
    /**Проверить достаточно ли на складе товара, и обработать заказ
     * @param order
     */
    private void checkAndProcess(Order order){
        boolean enoughFlag = true;
        long article;
        Order ExistOrder = ordersMap.getOrder(order.getOrderNumber());
    
        if (ExistOrder.getOrderStatus().equals(PREPARING)){                        
            //Проверить: достаточно ли товара на складе
           
            for (OrderedItem Item : order.getOrderList().values()){
                article = Item.getItem().getArticle();
                if (goodsMap.getGood( article ).getInstock() < Item.getOrderedQuantity()){
                    //Если товара недостаточно, то опустить флаг достаточности товара
                    enoughFlag = false;
                }
            }
            if (enoughFlag == true){
                for (OrderedItem Item : order.getOrderList().values()){
                    article = Item.getItem().getArticle();
                    
                    //Получить товар
                    Good temp = goodsMap.getGood( article );
                    
                    //и вычесть заказанное количество
                    temp.reduceInstock(Item.getOrderedQuantity());
                    goodsMap.replaceGood(temp);
                }
                try {       
                    ordersMap.replaceOrder(order);
                    fireDataChanged("OrderSHIPPED");
                    orderIO.write(ordersMap.getOrdersMap());
                    goodsIO.write(goodsMap.getGoodsMap());
                    
                } catch (IllegalStatusException | IOException ex) {
                   ErrorFrame.create(ex);
                }
                
            }
            else{
                fireDataChanged("NotEnoughGoods");
            }
        }
    }
    
//------------------------------------------------------------------------------    
    @Override
    public void addListener(MyEventListener listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    } 
    
    @Override
    public void removeListener(MyEventListener listener){
        if (listeners.contains(listener)){
            listeners.remove(listener);
        }
    }
    
    @Override
    public void removeAllListeners() {
        listeners.clear();
    }
    @Override
    public MyEventListener[] getListeners(){
        return listeners.toArray(new MyEventListener[listeners.size()]);
    }
    
    @Override
    public void fireDataChanged(String message){                
        listeners.forEach((listener) -> {
            listener.update(message);
        });
    }
//------------------------------------------------------------------------------

    
}
    
    