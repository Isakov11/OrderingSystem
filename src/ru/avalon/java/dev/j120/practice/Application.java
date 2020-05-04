package ru.avalon.java.dev.j120.practice;

import ru.avalon.java.dev.j120.practice.entity.*;
import ru.avalon.java.dev.j120.practice.datastorage.*;
import ru.avalon.java.dev.j120.practice.IO.ConfigIO;
import ru.avalon.java.dev.j120.practice.IO.GoodsIO;

import java.math.BigDecimal;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;
import ru.avalon.java.dev.j120.practice.IO.OrderIO;

public class Application {
    static Properties prop = new Properties();
    
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        prop = ConfigIO.readConfig();        
        try{           
            init();
           //PriceList pl = new PriceList();           
           /*pl.addNew("AAA",new BigDecimal(1.67),14);
           pl.addNew("BBB",new BigDecimal(5),45);
           pl.addNew("CCC",new BigDecimal(8),1);
           pl.addNew("DDD",new BigDecimal(2),7);
           pl.addExist(new Goods(new Long(10),"EEE",new BigDecimal(1),10));
           pl.addNew("FFF",new BigDecimal(5),67);*/
           //System.out.println(pl.toString());           

            //PriceList pl = new PriceList(GoodsIO.read(Config.getInstance().getFilePath()));
            PriceList pl = new PriceList(GoodsIO.read(prop.getProperty("PricePath")));
            OrderList orderList = new OrderList(OrderIO.read(prop.getProperty("OrderPath")));
            
            System.out.println(pl.toString());
            System.out.println(orderList.toString());            
        }
        catch (IllegalArgumentException | IOException e){
           System.out.println(e);
        }
    }
    
    private static void init(){        
        ConfigIO.readConfig();
    }
}