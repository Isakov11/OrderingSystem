
package ru.avalon.java.dev.j120.practice.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.IO.ConfigIO;
import ru.avalon.java.dev.j120.practice.IO.GoodsIO;
import ru.avalon.java.dev.j120.practice.IO.OrderIO;
import ru.avalon.java.dev.j120.practice.datastorage.OrderList;
import ru.avalon.java.dev.j120.practice.datastorage.PriceList;
import ru.avalon.java.dev.j120.practice.entity.Config;

public class Controller {
    PriceList pricelist;
    OrderList orderList;

    public Controller() {
        ConfigIO.readConfig();
        try {           
            pricelist = new PriceList(GoodsIO.read(Config.get().getPricePath()));
            orderList = new OrderList(OrderIO.read(Config.get().getOrderPath()));
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }    
    
    public void main(){        
        System.out.println(pricelist.toString());
        System.out.println(orderList.toString());
    }
    
    

}
    
    