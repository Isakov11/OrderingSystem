package ru.avalon.java.dev.j120.practice.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.avalon.java.dev.j120.practice.entity.Goods;

public class GoodsIO {    
    private Deque<Goods> goodsDeque;
    
    public GoodsIO(String filePath){
        goodsDeque = new LinkedList<>();  
        init(filePath);
    }
    private void init(String filePath){        
        long article = 0;
        String variety = "";
        String color;
        BigDecimal price;
        long instock;
        
        StringBuilder sb = new StringBuilder();
        StringTokenizer tokenizer;        
        HashMap<String,String> hm = new HashMap<>();
        
        try (FileReader fr = new FileReader(filePath);
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
        tokenizer = new StringTokenizer(sb.toString(),";\n");
        
        while (tokenizer.hasMoreTokens()){
            article = Long.parseLong(tokenizer.nextToken().trim());
            if (tokenizer.hasMoreTokens()){variety = tokenizer.nextToken();};
            color = tokenizer.nextToken();
            price = BigDecimal.valueOf(Double.valueOf(tokenizer.nextToken().trim()));
            instock = Long.parseLong(tokenizer.nextToken().trim());
            goodsDeque.add(new Goods(article, variety, color, price, instock));
        }
    };
    public int size(){
        return goodsDeque.size();
    }
    
    public Goods pop(){        
        return goodsDeque.pop();        
    }
    
    
}
