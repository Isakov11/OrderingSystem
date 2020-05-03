package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Config;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class ConfigIO {
    private static final String CONFIGPATH = "config.config";
    
    private ConfigIO(){}
    public static boolean writeConfig(){        
        StringBuilder sb = new StringBuilder();
        sb.append("filePath = ");
        sb.append(Config.getInstance().getFilePath());
        sb.append("\nmaxDiscount = ");
        sb.append(Config.getInstance().getMaxDiscount());
        
        try (FileWriter fw = new FileWriter(CONFIGPATH);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(sb.toString());
            return true;   
        } catch (IOException ex) {
            Logger.getLogger(ConfigIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean readConfig(){
        StringBuilder sb = new StringBuilder();
        StringTokenizer tokenizer;        
        HashMap<String,String> hm = new HashMap<>();
        
        try (FileReader fr = new FileReader(CONFIGPATH);
             BufferedReader br = new BufferedReader(fr))
        {
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        tokenizer = new StringTokenizer(sb.toString()," = \n");
        
        while (tokenizer.hasMoreTokens()){
            hm.putIfAbsent(tokenizer.nextToken(), tokenizer.nextToken());
        }
        Config.getInstance(hm.get("filePath"), Integer.parseInt(hm.get("maxDiscount")));        
        return true;
    }
}
