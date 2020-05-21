package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Goods;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoodsIO {
    
    public GoodsIO(){}
    
    public HashMap<Long, Goods> read(String filePath) throws IOException{        
                
        File file = new File(filePath);
        HashMap<Long, Goods> goodsMap = new HashMap<>();
        Goods goods;
        //Паттерны для регулярных выражений:
        //1-й поиск целого числа,
        //2-й поиск натурального числа
        Pattern[] pattern = {Pattern.compile("\\d+"), Pattern.compile("\\d+[.,]{1}\\d+")};
        String[] subs;
        if (file.isFile()){
            try (FileReader fr = new FileReader(filePath);
                 BufferedReader br = new BufferedReader(fr))
            {
                String line;
                while ((line = br.readLine()) != null){                
                    goods = CreateGoods(line, pattern);
                    if (goods != null){
                        goodsMap.putIfAbsent(goods.getArticle(), goods);
                    }
                    else {
                        throw new IllegalArgumentException("Error in line: " + line);
                    }    
                }
            } catch (IOException ex) {
                throw new IOException("IO exception");
            }
        }
        else{
            return new HashMap<>();
        }
        return goodsMap;
    }
    
    private Goods CreateGoods(String string, Pattern[] pattern) {   
        long article;
        String variety;
        String color;
        BigDecimal price;
        long instock;        
        
        String[] SubStringArray = string.split(";");
        Pattern digitPattern = pattern[0];
        Pattern pricePattern = pattern[1];
        Matcher matcher;
        
        if (!SubStringArray[0].isEmpty()){
           matcher = digitPattern.matcher(SubStringArray[0].trim());
            if (matcher.find()){
                article = Long.parseLong(matcher.group());
            }
            else {return null;}
        } 
        else {return null;}
        
        if (!SubStringArray[1].isEmpty()){
            variety = SubStringArray[1].trim();
        }
        else {return null;}
        
        if (!SubStringArray[2].isEmpty()){
            color = SubStringArray[2].trim();
        }
        else {color="";}
        
        if (!SubStringArray[3].isEmpty()){            
            matcher = pricePattern.matcher(SubStringArray[3].trim());
            if (matcher.find()){
                price = BigDecimal.valueOf(Double.valueOf ( matcher.group() ));
            }
            else {return null;}
        }
        else {return null;}
        
        if (!SubStringArray[4].isEmpty()){
            matcher = digitPattern.matcher(SubStringArray[4].trim());
            if (matcher.find()){
                instock = Long.parseLong(matcher.group());
            }
            else {return null;}
        }
        else {return null;}
        
        return new Goods(article, variety, color, price, instock);
    }
    
    public void write(String filePath, HashMap<Long, Goods> map) throws IOException{
        
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
