package ru.avalon.java.dev.j120.practice;

import ru.avalon.java.dev.j120.practice.controller.Controller;


public class Application {    
    
    public static void main(String[] args) {
        Controller controller  = new Controller();
        controller.main();
    }
           /*PriceList pl = new PriceList();           
           pl.addNew("AAA",new BigDecimal(1.67),14);
           pl.addNew("BBB",new BigDecimal(5),45);
           pl.addNew("CCC",new BigDecimal(8),1);
           pl.addNew("DDD",new BigDecimal(2),7);
           pl.addExist(new Goods(new Long(10),"EEE",new BigDecimal(1),10));
           pl.addNew("FFF",new BigDecimal(5),67);
           System.out.println(pl.toString());    
           OrderList orderList = new OrderList();
            
            Person person = new Person("Ash Apple", "Mapple st., 5", "+7(961)126-54-98");
            Person Alice  = new Person("Alice Wind", "1-st line st., 10", "+7(961)587-97-13");
            Person Bob    = new Person("Bob Flower", "Outbound st., 4", "+7(961)368-73-02");
            Order order = new Order(1, LocalDate.now(), person, 5, OrderStatus.PREPARING);
            
            Goods goods = new Goods(pl.getGoods(3));            
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),5));
            
            goods = new Goods(pl.getGoods(11));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),3));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),9));
            order.reduce(11, 2);
            
            orderList.addNew(order);
            
            order = new Order(1, LocalDate.now(), Alice, 10, OrderStatus.PREPARING);
            goods = new Goods(pl.getGoods(4));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),16));            
            orderList.addNew(order);
            
            order = new Order(1, LocalDate.now(), Bob, 0, OrderStatus.PREPARING);
            goods = new Goods(pl.getGoods(10));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),4));
            goods = new Goods(pl.getGoods(6));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),18));
            orderList.addNew(order);
            
            goods = new Goods(pl.getGoods(3));
            goods.setPrice(new BigDecimal(5));
            pl.removeGoods(3);
            pl.addExist(goods);            
            
            orderList.cancelOrder(1);
            
            order = new Order(1, LocalDate.now(), person, 5, OrderStatus.PREPARING);
            
            goods = new Goods(pl.getGoods(3));            
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),5));
            
            goods = new Goods(pl.getGoods(11));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),3));
            order.add(new OrderedGoods(goods.getArticle(),goods.getPrice(),9));
            order.reduce(11, 2);
            
            orderList.addNew(order);
            OrderIO.write(Config.get().getOrderPath(), orderList.getOrderList());*/
}