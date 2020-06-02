package ru.avalon.java.dev.j120.practice;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.IO.GoodsIO;
import ru.avalon.java.dev.j120.practice.IO.OrdersIO;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.datastorage.GoodsMap;
import ru.avalon.java.dev.j120.practice.datastorage.OrdersMap;
import ru.avalon.java.dev.j120.practice.entity.*;


public class Application {    
    
    public static void main(String[] args) {
        /*GoodsMap pl = new GoodsMap();                     
           pl.addNew("AAA",new BigDecimal(1.67),14);
           pl.addNew("BBB",new BigDecimal(5),45);
           pl.addNew("CCC",new BigDecimal(8),1);
           pl.addNew("DDD",new BigDecimal(2),7);
           pl.addExist(new Goods(new Long(6),"D6",new BigDecimal(2.0),7));
           pl.addExist(new Goods(new Long(10),"EEE",new BigDecimal(1),10));
           pl.addExist(new Goods(new Long(11),"FFF",new BigDecimal(1),10));
           pl.addNew("F10",new BigDecimal(5),67);
           System.out.println(pl.toString());
           
           OrdersMap orderList = new OrdersMap();
            
            Person person = new Person("Ash Apple", "Mapple st., 5", "+7(961)126-54-98");
            Person Alice  = new Person("Alice Wind", "1-st line st., 10", "+7(961)587-97-13");
            Person Bob    = new Person("Bob Flower", "Outbound st., 4", "+7(961)368-73-02");
            Order order = new Order(1, LocalDate.now(), person, 5, OrderStatusEnum.PREPARING);
            
            Goods goods = new Goods(pl.getGoods(3));            
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),5));
            
            goods = new Goods(pl.getGoods(11));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),3));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),9));
            order.reduce(11, 2);
            
            orderList.addNew(order);
            
            order = new Order(1, LocalDate.now(), Alice, 10, OrderStatusEnum.PREPARING);
            goods = new Goods(pl.getGoods(4));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),16));            
            orderList.addNew(order);
            
            order = new Order(1, LocalDate.now(), Bob, 0, OrderStatusEnum.PREPARING);
            goods = new Goods(pl.getGoods(10));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),4));
            goods = new Goods(pl.getGoods(6));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),18));
            orderList.addNew(order);
            
            goods = new Goods(pl.getGoods(3));
            goods.setPrice(new BigDecimal(5));
            pl.removeGoods(3);
            pl.addExist(goods);            
            
            orderList.cancelOrder(1);
            
            order = new Order(1, LocalDate.now(), person, 5, OrderStatusEnum.PREPARING);
            
            goods = new Goods(pl.getGoods(3));            
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),5));
            
            goods = new Goods(pl.getGoods(11));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),3));
            order.add(new OrderedItem(goods.getArticle(),goods.getPrice(),9));
            order.reduce(11, 2);
            
            orderList.addNew(order);
        try {
            GoodsIO.write(Config.get().getOrderPath(), pl.getPriceList());
            OrdersIO.write(Config.get().getOrderPath(), orderList.getOrderList());
        } catch (IOException ex) {
            
        }*/

        
        Mediator mediator  = new Mediator();
        mediator.main();
    }
           
}