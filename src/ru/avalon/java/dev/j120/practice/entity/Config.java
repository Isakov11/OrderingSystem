
package ru.avalon.java.dev.j120.practice.entity;

import java.util.Properties;

public class Config {
    private static Config instance;
    private Properties prop;

    private Config(Properties prop) {
        this.prop = prop;
    }

    public static Config get(Properties prop) {
        if (instance == null) {
            if (prop.isEmpty()){
                instance = new Config(new Properties());
            }
            instance = new Config(prop);
        }
        return instance;
    }
    
    public static Config get() {                
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

    @Override
    public String toString() {
        return prop.toString() ;
    }
}
