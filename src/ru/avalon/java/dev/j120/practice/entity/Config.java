
package ru.avalon.java.dev.j120.practice.entity;


public class Config {
    private static Config instance;
    private String filePath;
    private int maxDiscount;

    private Config(String filePath, int maxDiscount) {
        this.filePath = filePath;
        this.maxDiscount = maxDiscount;        
    }

    public static Config getInstance(String filePath, int maxDiscount) {
        if (instance == null) {
            instance = new Config(filePath, maxDiscount);
        }
        return instance;
    }
    public static Config getInstance() {                
        return instance;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean setMaxDiscount(int maxDiscount) {
        if (maxDiscount >= 0 && maxDiscount <= 100){
            this.maxDiscount = maxDiscount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "filePath: " + filePath + " maxDiscount: " + maxDiscount;
    }
    
    
}
