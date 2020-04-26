package ru.avalon.java.dev.j120.practice;


import java.math.BigDecimal;
import ru.avalon.java.dev.j120.practice.commons.*;

public class Application {
    
    public static void main(String[] args) {
       // Goods(String variety, Currency price, long instock)
       try{
            Goods good = Goods.create(514561651,"name",new BigDecimal(5),45);
            System.out.println(good);
            System.out.println("New price");
            good.setPrice(new BigDecimal(1.79));
            System.out.println(good);
            long l = good.reduceInstock(-50);
            System.out.println(good);            
       }
       catch (IllegalArgumentException e){
           System.out.println(e);
       }      
      
    }    
}
