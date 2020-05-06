package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Goods;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class GoodsIO {
    
    private GoodsIO(){}
    
    public static HashMap<Long, Goods> read(String filePath) throws IOException{        
                
        File file = new File(filePath);
        HashMap<Long, Goods> goodsMap = new HashMap<>();
        Goods goods;
        
        if (file.isFile()){
            try (FileReader fr = new FileReader(filePath);
                 BufferedReader br = new BufferedReader(fr))
            {
                String line;
                while ((line = br.readLine()) != null){
                goods = split(line);
                if (goods != null){
                    goodsMap.putIfAbsent(goods.getArticle(), goods);
                }
            }
            } catch (IOException ex) {
                throw new IOException("IO exception");
            }
        }
        else{
            write (filePath, new HashMap<>());
        }
        return goodsMap;
    }
    
    private static Goods split(String string) {   
        long article = 0;
        String variety = "";
        String color = "";
        BigDecimal price = new BigDecimal(0);
        long instock = 0;
        StringTokenizer tokenizer = new StringTokenizer(string,";");       
        boolean errorFlag = false;
        
        if (tokenizer.hasMoreTokens()){
            article = Long.parseLong(tokenizer.nextToken().trim());
        } 
        else {errorFlag = true;}
        
        if (tokenizer.hasMoreTokens()){
            variety = tokenizer.nextToken().trim();
        }
        else {errorFlag = true;}
        
        if (tokenizer.hasMoreTokens()){
            color = tokenizer.nextToken().trim();
        }
        else {errorFlag = true;}
        
        if (tokenizer.hasMoreTokens()){
            price = BigDecimal.valueOf(Double.valueOf( tokenizer.nextToken().trim() ) );
        }
        else {errorFlag = true;}
        
        if (tokenizer.hasMoreTokens()){
            instock = Long.parseLong(tokenizer.nextToken().trim());
        }
        else {errorFlag = true;}
        
        if (errorFlag == false) {return new Goods(article, variety, color, price, instock);}
        return null;
    }
    
    public static void write(String filePath, HashMap<Long, Goods> map) throws IOException{
        
        StringBuilder sb = new StringBuilder();

        map.forEach((k,v) -> {  sb.append(v.getArticle());
                                sb.append(";");
                                sb.append(v.getVariety());
                                sb.append(";");
                                sb.append(v.getColor());
                                sb.append(";");
                                sb.append(v.getPrice());
                                sb.append(";");
                                sb.append(v.getInstock());
                                sb.append(";");
                                sb.append("\n");});
        
        File file = new File(filePath);
        try (FileWriter fw = new FileWriter(filePath);
                 BufferedWriter bw = new BufferedWriter(fw))
            {
                bw.write(sb.toString());
            }
    }
}
