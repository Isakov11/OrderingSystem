package ru.avalon.java.dev.j120.practice.IO;

import ru.avalon.java.dev.j120.practice.entity.Good;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoodsIO {
    //Паттерны для регулярных выражений:
        //1-й поиск целого числа,
        //2-й поиск натурального числа
    private final Pattern digitPattern = Pattern.compile("\\d+");
    private final Pattern pricePattern = Pattern.compile("\\d+[.,]{1}\\d+");
    private final String filePath;
    
    public GoodsIO(String filePath){
        this.filePath = filePath;
    }
    
    public HashMap<Long, Good> read() throws IOException{        
                
        File file = new File(filePath);
        HashMap<Long, Good> goodsMap = new HashMap<>();
        Good good;
        
        
        if (file.isFile()){
            try (FileReader fr = new FileReader(filePath);
                 BufferedReader br = new BufferedReader(fr))
            {
                String line;
                while ((line = br.readLine()) != null){                
                    good = createGood(line);
                    if (good != null){
                        goodsMap.putIfAbsent(good.getArticle(), good);
                    }
                    else {
                        throw new IOException("Error in line: " + line);
                    }    
                }
            } catch (IOException ex) {
                throw new IOException(ex);
            }
        }
        else{
            return new HashMap<>();
        }
        return goodsMap;
    }
    
    private Good createGood(String string) {   
        long article;
        String variety;
        String color;
        BigDecimal price;
        long instock;        
        
        String[] subStringArray = string.split(";");
        Matcher matcher;
        if (subStringArray.length == 5){
            if (!subStringArray[0].isEmpty()){
               matcher = digitPattern.matcher(subStringArray[0].trim());
                if (matcher.find()){
                    article = Long.parseLong(matcher.group());
                }
                else {return null;}
            } 
            else {return null;}

            if (!subStringArray[1].isEmpty()){
                variety = subStringArray[1].trim();
            }
            else {return null;}

            if (!subStringArray[2].isEmpty()){
                color = subStringArray[2].trim();
            }
            else {color="";}

            if (!subStringArray[3].isEmpty()){            
                matcher = pricePattern.matcher(subStringArray[3].trim());
                if (matcher.find()){
                    price = BigDecimal.valueOf(Double.valueOf ( matcher.group() ));
                }
                else {return null;}
            }
            else {return null;}

            if (!subStringArray[4].isEmpty()){
                matcher = digitPattern.matcher(subStringArray[4].trim());
                if (matcher.find()){
                    instock = Long.parseLong(matcher.group());
                }
                else {return null;}
            }
            else {return null;}        
            return new Good(article, variety, color, price, instock);
        }
        else {return null;}
    }
    
    public void write(HashMap<Long, Good> map) throws IOException{        
        StringBuilder sb = new StringBuilder();

        map.forEach((k,v) -> {  sb.append(k);
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
