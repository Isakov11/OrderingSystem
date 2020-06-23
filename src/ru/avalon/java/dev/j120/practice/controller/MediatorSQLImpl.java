
package ru.avalon.java.dev.j120.practice.controller;

import dao.ConnectionManager;
import dao.GoodsDAO;
import dao.OrdersDAO;
import dao.PersonsDAO;
import java.lang.reflect.InvocationTargetException;
import ru.avalon.java.dev.j120.practice.entity.*;
import static ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ru.avalon.java.dev.j120.practice.UI.ErrorFrame;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;

import ru.avalon.java.dev.j120.practice.utils.MyEventListener;
import ru.avalon.java.dev.j120.practice.entity.Good;


public class MediatorSQLImpl implements Mediator{
    private GoodsDAO  goodsDAO;
    private OrdersDAO ordersDAO;
    private PersonsDAO personsDAO;
    private ConnectionManager manager;

    private ArrayList<MyEventListener> listeners = new ArrayList<>();
    private long start, end;
    
    public MediatorSQLImpl() {
        try {
            start = System.currentTimeMillis();
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            String url = Config.get().getURL();            
            String username = Config.get().getUserName();
            String password = Config.get().getPassword();
            
            manager = new ConnectionManager(url,username,password);
           
            goodsDAO = new GoodsDAO(manager.getConnection());
            ordersDAO = new OrdersDAO(manager.getConnection());
            personsDAO = new PersonsDAO();
            
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainframe = new MainFrame(this);
            });
            //--------------------------------------------------------------------- 
            end = System.currentTimeMillis();
            System.out.println("GUI start time "  + (end-start)+" ms");
        
        } catch (ClassNotFoundException | IllegalArgumentException| 
                NoSuchMethodException | SecurityException | InstantiationException | 
                IllegalAccessException | InvocationTargetException | SQLException ex) {
            ErrorFrame.create(ex, JFrame.EXIT_ON_CLOSE);
        }
    }    
    
    public final void main(){        
        
    }

    @Override
    public ArrayList<Good> getGoodsArray(){
        try {
            return goodsDAO.findAll();
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
        }
        return new ArrayList<>();
    }
    
    @Override
    public Good getGood(long article){
        try {
            return goodsDAO.findId(article);
        } catch (SQLException | NullPointerException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
            return null;
        }
    }
    
    @Override
    public ArrayList<Order> getOrdersArray(){
        try {
            return ordersDAO.findAll();
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
        }
        return new ArrayList<>();
    }
    
    @Override
    public Order getOrder(long orderNumber){
        try {
            start = System.currentTimeMillis();
            Order order =  ordersDAO.findId(orderNumber);
            end = System.currentTimeMillis();
            System.out.println("getOrder time "  + (end-start)+" ms");
            return order;
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
            return null;
        }
    }
    
    @Override
    public final Good addGood(Good good){
        try {
            Good newGood = goodsDAO.create(good);
            fireDataChanged("GoodUpdate");
            return newGood;
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
            return null;
        }
    }    
    
    @Override
    public final void updateGood(Good good){
        try {
            goodsDAO.update(good);
            fireDataChanged("GoodUpdate");
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
        }
    }
      
    @Override
    public final void removeOrder(long orderNumber){
        try {
            ordersDAO.delete(orderNumber);
            fireDataChanged("OrdersMapChanged");
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
        }
    }
    
    @Override
    public Order addOrder(Order order) {
        try {
            Order temp = ordersDAO.create(order);
            fireDataChanged("OrdersMapChanged");
            return temp;
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
            return null;
        }
    }

    
    @Override
    public final void updateOrder(Order order){
        try{
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
        } catch (SQLException ex) {
            ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
        }
    }
    
    public ArrayList<Person> getPersonsArray(){
        try {
            return personsDAO.findAll(manager.getConnection());
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }
    
 /**Проверить достаточно ли на складе товара, и обработать заказ
     * @param order
     */
    private void checkAndProcess(Order order){
        start = System.currentTimeMillis();       
        boolean enoughFlag = true;
        long article;
        HashMap<Long, Good> goodMap = new HashMap<>();
        
        Order ExistOrder = this.getOrder(order.getOrderNumber());
    
        if (ExistOrder != null && ExistOrder.getOrderStatus().equals(PREPARING)){                        
            //Проверить: достаточно ли товара на складе
           
            for (OrderedItem Item : order.getOrderList().values()){
                article = Item.getItem().getArticle();
                Good good = getGood(article);
                if (good !=null){
                    goodMap.put(article,good);
                }
                if (goodMap.get(article).getInstock() < Item.getOrderedQuantity()){
                    //Если товара недостаточно, то опустить флаг достаточности товара
                    enoughFlag = false;
                    break;
                }
                goodMap.get(article).reduceInstock(Item.getOrderedQuantity());
            }
            
            if (enoughFlag == true){
                try{
                    goodsDAO.update(goodMap.values());

                    end = System.currentTimeMillis();
                    System.out.println("checkAndProcess  goodsDAO.update time "  + (end-start)+" ms");
                    ordersDAO.update(order);

                    end = System.currentTimeMillis();
                    System.out.println("checkAndProcess  ordersDAO.update time "  + (end-start)+" ms");
                    fireDataChanged("OrderSHIPPED");
                } catch (SQLException ex) {
                    ErrorFrame.create(ex, JFrame.DISPOSE_ON_CLOSE);
                }
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
    
    