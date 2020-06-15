package ru.avalon.java.dev.j120.practice.datastorage;

import java.util.ArrayList;
import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.entity.Person;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;


public class PersonsMap {
    //HashMap<телефонный номер, Person> 
    private HashMap<String, Person> map;
    private transient ArrayList<MyEventListener> listeners = new ArrayList<>(); 
            
    public PersonsMap() {
        map = new HashMap<>();
    }
    
    public PersonsMap(HashMap<String, Person> map) {                
        
        if (map != null){
            this.map = map;
        }
        else{
            this.map = new HashMap<>();
        }
    }
    
    /** Вставляет в map товар уже имеющий артикул
     * @param person
     * @throws IllegalArgumentException 
     */
    public void add(Person person) throws IllegalArgumentException{        
        if(map.putIfAbsent(person.getPhoneNumber(), person) != null){
            throw new IllegalArgumentException("Person " + person.getContactPerson() + " already in the list." );
        }
        fireDataChanged("update");
    }
    
    public HashMap<String, Person> getMap() {
        return new HashMap<> (map);
    }
    
    public Person get(String key) throws IllegalArgumentException{
        if (map.containsKey(key)){            
            //return new Person(map.get(key));
            return (map.get(key));
        }
        else{
            throw new IllegalArgumentException("Person " + key +" not exist");
        }
    }
    
    public void remove(String key) {
        map.remove(key);
        fireDataChanged("update");
    }
    
    public void replace(Person person) {
        map.replace(person.getContactPerson(), person);
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person map\nSize: ");
        sb.append(map.size());
        sb.append("\nMap:\n");
        map.forEach((k,v) -> {sb.append("Person: ");
                                    sb.append(k);
                                    sb.append(" : ");
                                    sb.append(v.getDeliveryAddress());
                                    sb.append(" : ");
                                    sb.append(v.getPhoneNumber());
                                    sb.append("\n");
        
                            });
        return sb.toString();
    }
}
