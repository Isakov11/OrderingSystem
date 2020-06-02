package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Order;
import java.io.*;
import java.util.HashMap;

public class OrdersIO {
    private final String filePath;
    
    public OrdersIO(String filePath){
        this.filePath = filePath;
    }
    
    public HashMap<Long, Order> read() throws IOException, ClassNotFoundException{
        
        File file = new File(filePath);
        if (file.isFile()){
            try( ObjectInputStream objStream = 
                new ObjectInputStream(new FileInputStream(file)) )
            {                
                return new HashMap<>((HashMap<Long, Order>)objStream.readObject());
            } 
            catch (FileNotFoundException ex) {
            }
            catch (InvalidClassException ex) {
              return new HashMap<>();
            }
        
        }
        return new HashMap<>();    
    }
    
    public void write(HashMap<Long, Order> orderList) throws IOException{
        try( ObjectOutputStream objStream = 
                new ObjectOutputStream(new FileOutputStream(filePath )) )
        {
            objStream.writeObject(orderList);
        } 
        catch (FileNotFoundException ex) {            
        }
    }
}
