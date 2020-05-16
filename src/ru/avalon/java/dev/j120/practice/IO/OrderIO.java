package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Order;
import java.io.*;
import java.util.HashMap;
import java.util.logging.*;

public class OrderIO {

    private OrderIO() {}
    
    public static HashMap<Long, Order> read(String filePath) throws IOException, ClassNotFoundException{
        
        File file = new File(filePath);
        if (file.isFile()){
            try( ObjectInputStream objStream = 
                new ObjectInputStream(new FileInputStream(file)) )
            {                
                return new HashMap<>((HashMap<Long, Order>)objStream.readObject());
            } 
            catch (FileNotFoundException ex) {                
            }
        }
        return new HashMap<>();    
    }
    
    public static void write(String filePath, HashMap<Long, Order> orderList) throws IOException{
        try( ObjectOutputStream objStream = 
                new ObjectOutputStream(new FileOutputStream(filePath )) )
        {
            objStream.writeObject(orderList);
        } 
        catch (FileNotFoundException ex) {            
        }
    }
}
