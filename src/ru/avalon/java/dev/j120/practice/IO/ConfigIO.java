package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Config;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class ConfigIO {
    private static final String CONFIGPATH = "config.config";
    
    private ConfigIO(){}
    
    public static void readConfig(){                
        Properties prop = new Properties();
        try (FileReader fr = new FileReader(CONFIGPATH);
             BufferedReader br = new BufferedReader(fr))
        {            
            prop.load(br);            
        } 
        catch (IOException ex) {
            Logger.getLogger(ConfigIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Config.get(prop);
    }
    
    public static boolean writeConfig(Properties prop){
        try (FileWriter fw = new FileWriter(CONFIGPATH);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            prop.store(bw, CONFIGPATH);
            return true;   
        } catch (IOException ex) {
            Logger.getLogger(ConfigIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
