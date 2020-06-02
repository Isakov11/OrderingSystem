
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

public class Mediator {
    private GoodsIO goodsIO;
    private OrdersIO orderIO;
    private GoodsMap pricelist;
    private OrdersMap orderlist;    
    ArrayList<MyEventListener> listeners = new ArrayList<>();
    
    public Mediator() {
        try {
            
            //goodsIO = new GoodsIO(Config.get().getPricePath());            
            orderIO = new OrdersIO(Config.get().getOrderPath());
            
            String url = Config.get().getURL();            
            String username = Config.get().getUserName();
            String password = Config.get().getPassword();
            
            SQLIO universalIO = new SQLIO(url, username, password);
            HashMap<Long, Order> ordersMap = universalIO.readOrders();
            System.out.println(ordersMap.toString());   
            orderlist = new OrdersMap(orderIO.read());            
            pricelist = new GoodsMap(universalIO.readGoods());
            
            System.out.println(pricelist);
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainframe = new MainFrame(this);
            });
            //--------------------------------------------------------------------- 
            
        } catch (IOException | ClassNotFoundException | IllegalArgumentException | SQLException ex) {
            ErrorFrame.create(ex);
        }  
    }    
    
    public final void main(){        
        
    }

    public GoodsMap getPriceList() {
        return pricelist;
    }

    public OrdersMap getOrderList() {
        return orderlist;
    }
        
    public final void updateGood(StateEnum state, Good good){
        if (state.equals(StateEnum.NEW)){pricelist.addExist(good);}
        if (state.equals(StateEnum.EXIST)){pricelist.replaceGood(good);}
        try {
            goodsIO.write(pricelist.getPriceList());
        } catch (IOException ex) {
           ErrorFrame.create(ex);
        }
    }
      
    public final void removeOrder(long orderNumber){
        try {
            orderlist.removeOrder(orderNumber);
        } catch (IllegalStatusException | IllegalArgumentException ex) {
             ErrorFrame.create(ex);
        }
    }
    
    public final void updateOrder(StateEnum state,Order order){
        if (state.equals(NEW)){
            try {
                orderlist.addExist(order);
                orderIO.write(orderlist.getOrderList());
                
                fireDataChanged("DataChanged");
            } catch (IllegalStatusException | IOException ex) {
                ErrorFrame.create(ex);
            }
        }
        if (state.equals(EXIST) && orderlist.getOrderList().containsKey(order.getOrderNumber())){
            switch (order.getOrderStatus() ){
                case PREPARING:            
                    try {
                        orderlist.replaceOrder(order);
                        fireDataChanged("DataChanged");
                        orderIO.write(orderlist.getOrderList());
                        
                    } catch (IllegalStatusException | IllegalArgumentException | IOException ex) {
                        ErrorFrame.create(ex);
                    }
                    break;
                    
                case SHIPPED:                    
                    checkAndProcess(order);
                    break;
                    
                case CANCELED:
                    try {
                        orderlist.replaceOrder(order);
                        fireDataChanged("DataChanged");
                        orderIO.write(orderlist.getOrderList());                            
                        
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
        Order ExistOrder = orderlist.getOrder(order.getOrderNumber());
    
        if (ExistOrder.getOrderStatus().equals(PREPARING)){                        
            //Проверить: достаточно ли товара на складе
           
            for (OrderedItem Item : order.getOrderList().values()){
                article = Item.getItem().getArticle();
                if (pricelist.getGood( article ).getInstock() < Item.getOrderedQuantity()){
                    //Если товара недостаточно, то опустить флаг достаточности товара
                    enoughFlag = false;
                }
            }
            if (enoughFlag == true){
                for (OrderedItem Item : order.getOrderList().values()){
                    article = Item.getItem().getArticle();
                    
                    //Получить товар
                    Good temp = pricelist.getGood( article );
                    
                    //и вычесть заказанное количество
                    temp.reduceInstock(Item.getOrderedQuantity());
                    pricelist.replaceGood(temp);
                    fireDataChanged("OrderSHIPPED");
                }
                try {       
                    orderlist.replaceOrder(order);
                    orderIO.write(orderlist.getOrderList());
                    goodsIO.write(pricelist.getPriceList());
                    
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
//------------------------------------------------------------------------------
}
    
    