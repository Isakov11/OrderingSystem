
package ru.avalon.java.dev.j120.practice.controller;

import dao.ConnectionManager;
import dao.GoodsDAO;
import dao.OrdersDAO;
import dao.PersonsDAO;
import ru.avalon.java.dev.j120.practice.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.SwingUtilities;

import ru.avalon.java.dev.j120.practice.UI.ErrorFrame;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;

import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.*;

import ru.avalon.java.dev.j120.practice.utils.MyEventListener;
import ru.avalon.java.dev.j120.practice.entity.Good;

public class MediatorSQLImpl implements Mediator{
    private GoodsDAO  goodsDAO;
    private OrdersDAO ordersDAO;
    private PersonsDAO personsDAO;

    ArrayList<MyEventListener> listeners = new ArrayList<>();
    long start, end;
    
    public MediatorSQLImpl() {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            String url = Config.get().getURL();            
            String username = Config.get().getUserName();
            String password = Config.get().getPassword();
            
            start = System.currentTimeMillis();
            ConnectionManager manager = new ConnectionManager(url,username,password);
            end = System.currentTimeMillis();
            System.out.println("manager time "  + (end-start)+" ms");
            
            goodsDAO = new GoodsDAO(manager);
            ordersDAO = new OrdersDAO(manager);
            personsDAO = new PersonsDAO(manager);
            
            start = System.currentTimeMillis();    
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainframe = new MainFrame(this);
            });
            //--------------------------------------------------------------------- 
            end = System.currentTimeMillis();
            System.out.println("GUI start time "  + (end-start)+" ms");
            
        } catch (IllegalArgumentException| SecurityException ex) {
            ErrorFrame.create(ex, JFrame.EXIT_ON_CLOSE);
        }
        /*} catch (ClassNotFoundException | IllegalArgumentException| 
                NoSuchMethodException | SecurityException | InstantiationException | 
                IllegalAccessException | InvocationTargetException | SQLException ex) {
            ErrorFrame.create(ex, JFrame.EXIT_ON_CLOSE);
        }*/
        
    }    
    
    public final void main(){        
        
    }

    @Override
    public ArrayList<Good> getGoodsArray(){
        return goodsDAO.findAll();
    }
    
    @Override
    public Good getGood(long article){
        try {
            Good tempGood = goodsDAO.findId(article);
            if (tempGood != null){
                return tempGood;
            }
            else{
                throw new NullPointerException("Товар не найден");
            }
        } catch (SQLException | NullPointerException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
        }
        return null;
    }
    
    @Override
    public ArrayList<Order> getOrdersArray(){
        return ordersDAO.findAll();
    }
    
    @Override
    public Order getOrder(long orderNumber){
        start = System.currentTimeMillis();       
        Order order =  ordersDAO.findId(orderNumber);
        end = System.currentTimeMillis();
        System.out.println("getOrder time "  + (end-start)+" ms");
        return order;
    }
    
    @Override
    public final Good addGood(Good good){
        try {
            Good newGood = goodsDAO.create(good);
            fireDataChanged("GoodUpdate");
            return newGood;
        } catch (IllegalArgumentException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
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
             ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
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
    
    public ArrayList<Person> getPersonsArray(){
        return personsDAO.findAll();
    }
    
 /**Проверить достаточно ли на складе товара, и обработать заказ
     * @param order
     */
    private void checkAndProcess(Order order){
        start = System.currentTimeMillis();       
        boolean enoughFlag = true;
        long article;
        HashMap<Long, Good> goodMap = new HashMap<>();
        
        Order ExistOrder = ordersDAO.findId(order.getOrderNumber());
    
        if (ExistOrder.getOrderStatus().equals(PREPARING)){                        
            //Проверить: достаточно ли товара на складе
           
            for (OrderedItem Item : order.getOrderList().values()){
                article = Item.getItem().getArticle();
                goodMap.put(article,getGood(article));
                
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
                goodsDAO.update(goodMap.values());
                
                end = System.currentTimeMillis();
                System.out.println("checkAndProcess  goodsDAO.update time "  + (end-start)+" ms");
                ordersDAO.update(order);
                
                end = System.currentTimeMillis();
                System.out.println("checkAndProcess  ordersDAO.update time "  + (end-start)+" ms");
                fireDataChanged("OrderSHIPPED");
                
            }
            else{
                fireDataChanged("NotEnoughGoods");
            }
        }
        end = System.currentTimeMillis();
        System.out.println("checkAndProcess time "  + (end-start)+" ms");
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
    
    