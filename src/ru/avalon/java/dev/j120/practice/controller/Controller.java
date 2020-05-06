
package ru.avalon.java.dev.j120.practice.controller;

import ru.avalon.java.dev.j120.practice.IO.*;
import ru.avalon.java.dev.j120.practice.datastorage.*;
import ru.avalon.java.dev.j120.practice.entity.*;
import java.io.IOException;
import java.util.logging.*;

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
    
    