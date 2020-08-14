package ru.avalon.java.dev.j120.practice.datastorage;

import java.math.BigDecimal;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;

import java.util.HashMap;
import ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException;

public class OrdersMap {
    private HashMap<Long, Order> ordersMap;
    
    public OrdersMap() {
        ordersMap = new HashMap<>();
    }
    
    public OrdersMap(HashMap<Long, Order> ordersMap) {        
        if (ordersMap != null){
            this.ordersMap = ordersMap;
        }
        else{
            this.ordersMap = new HashMap<>();
        }
    }

    public HashMap<Long, Order> getOrdersMap() {
        return new HashMap<>(ordersMap);
    }

    /** Вставляет в ordersMap существующий заказ
     * @param order
     * @throws ru.avalon.java.dev.j120.practice.exceptions.IllegalStatusException
     * @throws IllegalArgumentException 
     */
    
    public void add(Order order) throws IllegalStatusException{        
        if (order.getOrderNumber() != 0){
            if(ordersMap.putIfAbsent(order.getOrderNumber(), order) != null){
                throw new IllegalStatusException("Number " + order.getOrderNumber() + " already in the list." );            
            }
            //fireDataChanged("update"); 
        }
        else{
            throw new IllegalStatusException("Order does not have article");      
        }
    }
    
    public Order getOrder(long number) throws IllegalArgumentException{
        if (ordersMap.containsKey(number)){
            return new Order(ordersMap.get(number));
        }
        else{
            throw new IllegalArgumentException("Article " + number +" not exist");
        }
    }
    
    public void cancelOrder(Order order) throws IllegalStatusException{
         Long OrderNumber = order.getOrderNumber();
        if (ordersMap.containsKey(OrderNumber)){
            if (ordersMap.get(OrderNumber).getOrderStatus() == OrderStatusEnum.PREPARING){
                ordersMap.get(OrderNumber).setOrderStatus(OrderStatusEnum.CANCELED);
                //fireDataChanged("update");      
            }
            else{
                throw new IllegalStatusException("Order status is " + ordersMap.get(OrderNumber).getOrderStatus());
            }
        }
    }
    
    public void removeOrder(long orderNumber) throws IllegalStatusException, IllegalArgumentException {
        if (ordersMap.containsKey(orderNumber)){
            if (ordersMap.get(orderNumber).getOrderStatus() == OrderStatusEnum.PREPARING){
                ordersMap.remove(orderNumber);
                //fireDataChanged("update");      
            }
            else{
                throw new IllegalStatusException("Order status is " + ordersMap.get(orderNumber).getOrderStatus());
            }
        }
        else{
            throw new IllegalArgumentException("Order " + orderNumber + " not found");
        }
    }
        
    public void replaceOrder(Order order) throws IllegalStatusException, IllegalArgumentException {
        Long OrderNumber = order.getOrderNumber();
        if (ordersMap.containsKey(OrderNumber)){
            if (ordersMap.get(order.getOrderNumber()).getOrderStatus() == OrderStatusEnum.PREPARING){
                ordersMap.replace(order.getOrderNumber(), order);
                //fireDataChanged("update");      
            }
            else{
                throw new IllegalStatusException("Order status is " + ordersMap.get(order.getOrderNumber()).getOrderStatus());
            }
        }
        else{
            throw new IllegalArgumentException("Order " + OrderNumber + " not found");
        }
    }
    
    public BigDecimal getTotalOrderListPrice(){
        BigDecimal subTotal = new BigDecimal(0);
        
        for (Order order:ordersMap.values()){
            subTotal = subTotal.add(order.getDiscountPrice());
        }
        return subTotal;
    }
    
    /**Возвращает наименьший неиспользованный номер заказа
    * @return long*/
    public final long getFreeNumber(){        
        if (ordersMap.isEmpty()) {
            return 1;
        }
        
        Long[] keyArray = new Long[ordersMap.keySet().size()];
        keyArray = ordersMap.keySet().toArray(keyArray);
        
        for (int i=0; i < (keyArray.length - 1); i++){
            //Если разница между соседними значениями > 1, то между ними содержится свободный номер
            if ( (keyArray[i+1] - keyArray[i]) > 1){                
                return keyArray[i] + 1;
            }
        }
        //Свободный номер следующий после максимального имеющегося
        return (keyArray[keyArray.length-1]) + 1;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderList:\nCurrentFreeNumber: ");
        sb.append(getFreeNumber());
        sb.append("\nOrderList:\n");
        ordersMap.forEach((k,v) -> {sb.append(v);
                                    sb.append("\n");});
        return sb.toString();
    }
}
