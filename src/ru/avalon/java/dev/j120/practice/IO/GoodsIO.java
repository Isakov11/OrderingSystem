package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Goods;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class GoodsIO {
    
    private GoodsIO(){}
    
    public static HashMap<Long, Goods> read(String filePath) throws IOException{        
        
        StringBuilder sb = new StringBuilder();        
        File file = new File(filePath);
        
        if (file.isFile()){
            try (FileReader fr = new FileReader(filePath);
                 BufferedReader br = new BufferedReader(fr))
            {
                String line;
                while ((line = br.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
            } catch (IOException ex) {
                throw new IOException("IO exception");
            }
        }
        else{
            //создатьПустойФайл(filePath);
            throw new FileNotFoundException("File Not Found");
        }
        return splitAndPut(sb.toString());
    }
    
    private static HashMap<Long, Goods> splitAndPut(String builderString) {
        
        HashMap<Long, Goods> goodsMap = new HashMap<>();        
        long article = 0;
        String variety = "";
        String color = "";
        BigDecimal price = new BigDecimal(0);
        long instock = 0;
        StringTokenizer tokenizer = new StringTokenizer(builderString,";\n");
            
        while (tokenizer.hasMoreTokens()){
            article = Long.parseLong(tokenizer.nextToken().trim());
            if (tokenizer.hasMoreTokens()){variety = tokenizer.nextToken();}
            if (tokenizer.hasMoreTokens()){color = tokenizer.nextToken();}
            if (tokenizer.hasMoreTokens()){price = BigDecimal.valueOf( Double.valueOf( tokenizer.nextToken().trim() ) );}
            if (tokenizer.hasMoreTokens()){instock = Long.parseLong(tokenizer.nextToken().trim());}
           
            goodsMap.put(article, new Goods(article, variety, color, price, instock));
        }        
        return goodsMap;
    }
}
