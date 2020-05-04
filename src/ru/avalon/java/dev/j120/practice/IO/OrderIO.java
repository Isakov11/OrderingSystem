
package ru.avalon.java.dev.j120.practice.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.avalon.java.dev.j120.practice.entity.Order;


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
                Logger.getLogger(OrderIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;    
    }
    
    public static void write(String filePath, HashMap<Long, Order> orderList) throws IOException{
        try( ObjectOutputStream objStream = 
                new ObjectOutputStream(new FileOutputStream(filePath )) )
        {
            objStream.writeObject(orderList);
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(OrderIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
