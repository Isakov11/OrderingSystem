package ru.avalon.java.dev.j120.practice.datastorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.utils.Indexable;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;


public class GenericList <T extends Indexable> {
    private HashMap<Long, T> itemMap;
    private transient ArrayList<MyEventListener> listeners = new ArrayList<>(); 
            
    public GenericList() {
        itemMap = new HashMap<>();
    }
    
    public GenericList(HashMap<Long, T> itemMap) {                
        this.itemMap = itemMap;
    }
  
    /** Вставляет в itemMap товар уже имеющий артикул
     * @param var
     * @throws IllegalArgumentException 
     */
    public void add(T var) throws IllegalArgumentException{        
        if(itemMap.putIfAbsent(var.getIndex(), var) != null){
            throw new IllegalArgumentException("Item " + var.getIndex() + " already in the map." );
        }
        fireDataChanged("update");
    }
    
    public HashMap<Long, T> getMap() {
        return new HashMap<> (itemMap);
    }
    
    public T getItem(long article) throws IllegalArgumentException{
        if (itemMap.containsKey(article)){
            T temp = itemMap.get(article);
            return temp;
        }
        throw new IllegalArgumentException("Item " + article +" not exist");
    }
    
    public void remove(long article) {
        itemMap.remove(article);
        
        fireDataChanged("update");
    }
    
    public void replace(T var) {
        itemMap.replace(var.getIndex(), var);
        fireDataChanged("update");
    }
    
       public void addListener(MyEventListener listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    } 
    
    public void removeListener(MyEventListener listener){
        if (listeners.contains(listener)){
            listeners.remove(listener);
        }
    }
    
    public MyEventListener[] getListeners(){
        return listeners.toArray(new MyEventListener[listeners.size()]);
    }
    
    public void fireDataChanged(String message){                
        listeners.forEach((listener) -> {
            listener.update(message);
        });
    }
    
    /**Возвращает наименьший неиспользованный индекс
     * @return long*/
    public final long getFreeIndex(){        
        if (itemMap.isEmpty()) {
            return 1;
        }
        
        Long[] keyArray = new Long[itemMap.keySet().size()];
        keyArray = itemMap.keySet().toArray(keyArray);
        Arrays.sort(keyArray);
        for (int i=0; i < (keyArray.length - 1); i++){
            //Если разница между соседними значениями > 1, то между ними содержится свободный артикул
            if ( (keyArray[i+1] - keyArray[i]) > 1){                
                return keyArray[i] + 1;
            }
        }
        //Свободный артикул следующий после максимального имеющегося        
        return (keyArray[keyArray.length-1]) + 1;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generic Map\nSize: ");
        sb.append(itemMap.size());
        sb.append("\nMap:\n");
        itemMap.forEach((k,v) -> {sb.append("Article: ");
                                    sb.append(k);
                                    sb.append(" : ");
                                    sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
