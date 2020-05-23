
package ru.avalon.java.dev.j120.practice.datastorage;

import java.util.ArrayList;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;

import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;

public class OrderList {
    private Long currentFreeNumber;
    private HashMap<Long, Order> orderList;
    private ArrayList<MyEventListener> listeners = new ArrayList<>(); 
    
    public OrderList() {
        currentFreeNumber = new Long(1);
        orderList = new HashMap<>();
    }
    
    public OrderList(HashMap<Long, Order> orderList) {        
        this.orderList = orderList;
        currentFreeNumber = getFreeNumber();
    }

    public Long getCurrentFreeNumber() {
        return currentFreeNumber;
    }

    public HashMap<Long, Order> getOrderList() {
        return new HashMap<>(orderList);
    }

    /**Добавляет новый заказ в orderList
     * @param order
     * @throws IllegalArgumentException 
     */
    public long addNew(Order order) throws IllegalArgumentException {
        //Если не удалось вставить заказ по текущему номеру, то найти наименьший свободный номер и вставить        
        if(orderList.putIfAbsent(currentFreeNumber, 
                new Order (currentFreeNumber, order.getOrderDate(),order.getContactPerson(),
                        order.getDiscount(),order.getOrderStatus(),order.getOrderList())) != null){
            currentFreeNumber = this.getFreeNumber();
            orderList.put(currentFreeNumber, new Order (currentFreeNumber, order.getOrderDate(),order.getContactPerson(),
                        order.getDiscount(),order.getOrderStatus(),order.getOrderList()));        
        }        
        long usedFreeNumber = currentFreeNumber;
        currentFreeNumber = this.getFreeNumber();
        //fireDataChanged("upd");       
        return usedFreeNumber;
    }    

    
    /** Вставляет в orderList существующий заказ
     * @param order
     * @throws IllegalArgumentException 
     */
    public void addExist(Order order) throws IllegalArgumentException{        
        if(orderList.putIfAbsent(order.getOrderNumber(), order) != null){
            throw new IllegalArgumentException("Number " + order.getOrderNumber() + " already in the list." );
        }
        currentFreeNumber = this.getFreeNumber();
    }
    
    public Order getOrder(long number) throws IllegalArgumentException{
        return new Order(orderList.get(number));
    }
    
    public void cancelOrder(long number) throws IllegalStatusException{
        if (orderList.get(number).getOrderStatus() == OrderStatusEnum.PREPARING){
            orderList.get(number).setOrderStatus(OrderStatusEnum.CANCELED);
        }
        else{
            throw new IllegalArgumentException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
    
    public void removeOrder(long number) throws IllegalArgumentException {
        if (orderList.get(number).getOrderStatus() == OrderStatusEnum.PREPARING){
            orderList.remove(number);
        }
        else{
            throw new IllegalArgumentException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
        
    public void replaceOrder(long number,Order order) throws IllegalArgumentException {
        if (orderList.get(number).getOrderStatus() == OrderStatusEnum.PREPARING){
            orderList.replace(number, order);
        }
        else{
            throw new IllegalArgumentException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
    /**Возвращает наименьший неиспользованный номер заказа
    * @return long*/
    public long getFreeNumber(){        
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
        listeners.add(listener);
    } 
    
    public void removeListener(MyEventListener listener){
        listeners.remove(listener);
    }
    
    public MyEventListener[] getListeners(){
        return listeners.toArray(new MyEventListener[listeners.size()]);
    }
    
    protected void fireDataChanged(String message){                
        listeners.forEach((listener) -> {
            listener.update(message);
        });
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderList:\nCurrentFreeNumber: ");
        sb.append(currentFreeNumber);
        sb.append("\nOrderList:\n");
        orderList.forEach((k,v) -> {sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
