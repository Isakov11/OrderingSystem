
package ru.avalon.java.dev.j120.practice.controller;

import dao.GoodsDAO;
import dao.OrdersDAO;
import ru.avalon.java.dev.j120.practice.entity.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.SwingUtilities;

import ru.avalon.java.dev.j120.practice.UI.ErrorFrame;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;

import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.*;

import ru.avalon.java.dev.j120.practice.utils.MyEventListener;
import ru.avalon.java.dev.j120.practice.entity.Good;

public class MediatorSQL implements Mediator{
    private GoodsDAO  goodsDAO;
    private OrdersDAO ordersDAO ;
    ArrayList<MyEventListener> listeners = new ArrayList<>();
    
    public MediatorSQL() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            String url = Config.get().getURL();            
            String username = Config.get().getUserName();
            String password = Config.get().getPassword();
            
            
            goodsDAO = new GoodsDAO(url,username,password);
            ordersDAO = new OrdersDAO(url,username,password);
            
            System.out.println(goodsDAO.findAll());
            System.out.println("----------------------");
            System.out.println(ordersDAO.findAll());
            
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainframe = new MainFrame(this);
            });
            //--------------------------------------------------------------------- 
            
            
        } catch (ClassNotFoundException | IllegalArgumentException| 
                NoSuchMethodException | SecurityException | InstantiationException | 
                IllegalAccessException | InvocationTargetException ex) {
            ErrorFrame.create(ex);
        }
        
    }    
    
    public final void main(){        
        
    }

    @Override
    public ArrayList<Good> getGoodsArray(){
        return goodsDAO.findAll();   
    }
    
    @Override
    public Good getGood(long article){
        return goodsDAO.findId(article);    
    }
    
    @Override
    public ArrayList<Order> getOrdersArray(){
        return ordersDAO.findAll();   
    }
    
    @Override
    public Order getOrder(long orderNumber){
        return ordersDAO.findId(orderNumber);
    }
    
    @Override
    public final Good addGood(Good good){
        try {
            Good newGood = goodsDAO.create(good);
            fireDataChanged("GoodUpdate");
            return newGood;
        } catch (IllegalArgumentException ex) {
            ErrorFrame.create(ex);
        }
        return null;
    }    
    
    @Override
    public final void updateGood(Good good){
        goodsDAO.update(good);
        fireDataChanged("GoodUpdate");
    }
      
    @Override
    public final void removeOrder(long orderNumber){
        try {
            ordersDAO.delete(orderNumber);
            fireDataChanged("OrdersMapChanged");
        } catch (IllegalArgumentException ex) {
             ErrorFrame.create(ex);
        }
    }
    
    @Override
    public Order addOrder(Order order) {
        Order temp = ordersDAO.create(order);
        fireDataChanged("OrdersMapChanged");
        return temp;
    }

    
    @Override
    public final void updateOrder(Order order){
        
        switch (order.getOrderStatus() ){
            case PREPARING:            
                ordersDAO.update(order);
                fireDataChanged("OrdersMapChanged");
                break;
            case SHIPPED:                    
                checkAndProcess(order);
                break;
            case CANCELED:
                ordersDAO.update(order);
                fireDataChanged("OrdersMapChanged");
                break;
        }
    }
 /**Проверить достаточно ли на складе товара, и обработать заказ
     * @param order
     */
    private void checkAndProcess(Order order){
        boolean enoughFlag = true;
        long article;
        HashMap<Long, Good> goodMap = new HashMap<>();
        
        Order ExistOrder = ordersDAO.findId(order.getOrderNumber());
    
        if (ExistOrder.getOrderStatus().equals(PREPARING)){                        
            //Проверить: достаточно ли товара на складе
           
            for (OrderedItem Item : order.getOrderList().values()){
                article = Item.getItem().getArticle();
                goodMap.put(article,goodsDAO.findId(article));
                
                if (goodMap.get(article).getInstock() < Item.getOrderedQuantity()){
                    //Если товара недостаточно, то опустить флаг достаточности товара
                    enoughFlag = false;
                    break;
                }
                goodMap.get(article).reduceInstock(Item.getOrderedQuantity());
            }
            
            if (enoughFlag == true){
                /*Good tempGood;
                for (OrderedItem Item : order.getOrderList().values()){
                    article = Item.getItem().getArticle();
                    //Получить товар
                    tempGood = goodMap.get(article);
                    
                    //и вычесть заказанное количество
                    tempGood.reduceInstock(Item.getOrderedQuantity());
                    goodsDAO.update(tempGood);
                }*/
                goodsDAO.update (goodMap.values());
                ordersDAO.update(order);
                fireDataChanged("OrderSHIPPED");
                
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
    
    