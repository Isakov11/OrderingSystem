package ru.avalon.java.dev.j120.practice.IO;

import java.io.*;
import java.util.*;

public class ConfigIO {
    private static final String CONFIGPATH = "config.config";
    
    private ConfigIO(){}
    
    public static Properties readConfig() throws IOException{                
        Properties prop = new Properties();
        try (FileReader fr = new FileReader(CONFIGPATH);
             BufferedReader br = new BufferedReader(fr))
        {            
            prop.load(br);            
        } 
        catch (IOException ex) {
            throw new IOException("Can not find config file " + CONFIGPATH);
        }
        return prop;
    }
    
    public static boolean writeConfig(Properties prop) throws IOException{
        try (FileWriter fw = new FileWriter(CONFIGPATH);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            prop.store(bw, CONFIGPATH);
            return true;   
        } catch (IOException ex) {
             throw new IOException("Can not write config file " + CONFIGPATH);
        }        
    }
}
