
package ru.avalon.java.dev.j120.practice.datastorage;

import java.util.ArrayList;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;

import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;


public class OrderList {
    //private Long currentFreeNumber;
    private HashMap<Long, Order> orderList;
    private ArrayList<MyEventListener> listeners = new ArrayList<>(); 
    
    public OrderList() {
        //currentFreeNumber = new Long(1);
        orderList = new HashMap<>();
    }
    
    public OrderList(HashMap<Long, Order> orderList) {        
        this.orderList = orderList;
        //currentFreeNumber = getFreeNumber();
    }

    /*public Long getCurrentFreeNumber() {
        return currentFreeNumber;
    }*/

    public HashMap<Long, Order> getOrderList() {
        return new HashMap<>(orderList);
    }

    /**Добавляет новый заказ в orderList
     * @param order 
     * @throws IllegalArgumentException 
     */
    public void addNew(Order order) throws IllegalArgumentException {
        //Если не удалось вставить заказ по текущему номеру, то найти наименьший свободный номер и вставить        
        long currentFreeNumber = this.getFreeNumber();
        if(orderList.putIfAbsent(currentFreeNumber, 
                new Order (currentFreeNumber, order.getOrderDate(),order.getContactPerson(),
                        order.getDiscount(),order.getOrderStatus(),order.getOrderList())) != null){
            currentFreeNumber = this.getFreeNumber();
            orderList.put(currentFreeNumber, new Order (currentFreeNumber, order.getOrderDate(),order.getContactPerson(),
                        order.getDiscount(),order.getOrderStatus(),order.getOrderList()));        
        }
        fireDataChanged("update");      
    }    

    
    /** Вставляет в orderList существующий заказ
     * @param order
     * @throws ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException
     * @throws IllegalArgumentException 
     */
    public void addExist(Order order) throws IllegalStatusException{        
        if(orderList.putIfAbsent(order.getOrderNumber(), order) != null){
            throw new IllegalStatusException("Number " + order.getOrderNumber() + " already in the list." );            
        }
        fireDataChanged("update"); 
    }
    
    public Order getOrder(long number) throws IllegalArgumentException{
        return new Order(orderList.get(number));
    }
    
    public void cancelOrder(long number) throws IllegalStatusException{
        if (orderList.get(number).getOrderStatus() == OrderStatusEnum.PREPARING){
            orderList.get(number).setOrderStatus(OrderStatusEnum.CANCELED);
            fireDataChanged("update");      
        }
        else{
            throw new IllegalStatusException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
    
    public void removeOrder(long number) throws IllegalStatusException {
        if (orderList.get(number).getOrderStatus() == OrderStatusEnum.PREPARING){
            orderList.remove(number);
            fireDataChanged("update");      
        }
        else{
            throw new IllegalStatusException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
        
    public void replaceOrder(Order order) throws IllegalStatusException {
        if (orderList.get(order.getOrderNumber()).getOrderStatus() == OrderStatusEnum.PREPARING){
            orderList.replace(order.getOrderNumber(), order);
            fireDataChanged("update");      
        }
        else{
            throw new IllegalStatusException("Order status is " + orderList.get(order.getOrderNumber()).getOrderStatus());
        }
    }
    /**Возвращает наименьший неиспользованный номер заказа
    * @return long*/
    public final long getFreeNumber(){        
        if (orderList.isEmpty()) {
            return 1;
        }
        
        Long[] keyArray = new Long[orderList.keySet().size()];
        keyArray = orderList.keySet().toArray(keyArray);
        
        for (int i=0; i < (keyArray.length - 1); i++){
            //Если разница между соседними значениями > 1, то между ними содержится свободный номер
            if ( (keyArray[i+1] - keyArray[i]) > 1){                
                return keyArray[i] + 1;
            }
        }
        //Свободный номер следующий после максимального имеющегося
        return (keyArray[keyArray.length-1]) + 1;
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
        sb.append("OrderList:\nCurrentFreeNumber: ");
        sb.append(getFreeNumber());
        sb.append("\nOrderList:\n");
        orderList.forEach((k,v) -> {sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
