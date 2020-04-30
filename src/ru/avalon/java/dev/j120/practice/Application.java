package ru.avalon.java.dev.j120.practice;


import java.io.IOException;
import ru.avalon.java.dev.j120.practice.entity.Goods;
import ru.avalon.java.dev.j120.practice.entity.PriceList;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.IO.ConfigIO;
import ru.avalon.java.dev.j120.practice.IO.GoodsIO;
import ru.avalon.java.dev.j120.practice.entity.Config;

public class Application {
    
    public static void main(String[] args) {
       // Goods(String variety, Currency price, long instock)       
       init();
       
       try{           
           PriceList pl = new PriceList();
           
           /*pl.addNew("AAA",new BigDecimal(1.67),14);
           pl.addNew("BBB",new BigDecimal(5),45);
           pl.addNew("CCC",new BigDecimal(8),1);
           pl.addNew("DDD",new BigDecimal(2),7);
           pl.addExst(new Goods(new Long(10),"EEE",new BigDecimal(1),10));
           pl.addNew("FFF",new BigDecimal(5),67);*/
           //System.out.println(pl.toString());           
           GoodsIO gio = new GoodsIO(Config.getInstance().getFilePath());
           
           while (gio.size()>0){
               pl.addExst(gio.pop());
           }
           System.out.println(pl.toString());       
       }
       catch (IllegalArgumentException e){
           System.out.println(e);
       }
    }
    
    private static void init(){
        
        ConfigIO.readConfig();
    }
}