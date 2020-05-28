
package ru.avalon.java.dev.j120.practice.entity;

import java.util.Properties;
import ru.avalon.java.dev.j120.practice.IO.ConfigIO;

public class Config {
    private static Config instance;
    private Properties prop;

    private Config(Properties prop) {
        this.prop = prop;
    }

    public static Config get() {
        if (instance == null) {            
            Properties temp = ConfigIO.readConfig();
            if (temp.isEmpty()){
                instance = new Config(new Properties());
            }
            instance = new Config(temp);             
        }
        return instance;
    }   

    public String getPricePath() {        
        return prop.getProperty("PricePath");
    }
    
    public String getOrderPath() {
        return prop.getProperty("OrderPath");
    }

    public int getMaxDiscount() {
        return Integer.valueOf(prop.getProperty("maxDiscount","0"));
    }
    
    public String getURL() {
        return String.valueOf(prop.getProperty("url"));
    }
    
    public String getUserName() {
        return String.valueOf(prop.getProperty("username"));
    }
    
    public String getPassword() {
        return String.valueOf(prop.getProperty("password"));
    }

    public void setPricePath(String pricePath) {
        prop.setProperty("PricePath", pricePath);
    }

    public void setOrderPath(String orderPath) {
        prop.setProperty("PricePath", orderPath);
    }
    public void setMaxDiscount(int maxDiscount) {
        if (maxDiscount >= 0 && maxDiscount <= 100){
            prop.setProperty("maxDiscount", String.valueOf(maxDiscount));            
        }        
    }
    
    public void setURL(String url) {
        prop.setProperty("url", url);
    }
    
    public void setUser(String user) {
        prop.setProperty("user", user);
    }
    
    public void setPassword(String password) {
        prop.setProperty("password", password);
    }
    
    @Override
    public String toString() {
        return prop.toString() ;
    }
}
