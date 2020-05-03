
package ru.avalon.java.dev.j120.practice.datastorage;



import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatus;

import java.util.HashMap;

public class OrderList {
    private Long currentFreeNumber;
    private HashMap<Long, Order> orderList;
    
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
    public void addNew(Order order) throws IllegalArgumentException {
        //Если не удалось вставить заказ по текущему номеру, то найти наименьший свободный номер и вставить        
        if(orderList.putIfAbsent(currentFreeNumber, 
                new Order (currentFreeNumber, order.getOrderDate(),order.getContactPerson(),
                        order.getDiscount(),order.getOrderStatus(),order.getOrderList())) != null){
            currentFreeNumber = this.getFreeNumber();
            orderList.put(currentFreeNumber, new Order (currentFreeNumber, order.getOrderDate(),order.getContactPerson(),
                        order.getDiscount(),order.getOrderStatus(),order.getOrderList()));        
        }        
        currentFreeNumber++;
    }    
    
    /** Вставляет в orderList существующий заказ
     * @param order
     * @throws IllegalArgumentException 
     */
    public void addExist(Order order) throws IllegalArgumentException{        
        if(orderList.putIfAbsent(order.getOrderNumber(), order) != null){
            throw new IllegalArgumentException("Number " + order.getOrderNumber() + " already in the list." );
        }        
    }
    
    public HashMap<Long, Order> getPriceList() {
        return new HashMap<> (orderList);
    }
    
    public Order getOrder(long number) {
        return new Order(orderList.get(number));
    }
    
    public void cancelOrder(long number) throws IllegalArgumentException{
        if (orderList.get(number).getOrderStatus() == OrderStatus.PREPARING){
            orderList.get(number).setOrderStatus(OrderStatus.CANCELED);
        }
        else{
            throw new IllegalArgumentException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
    
    public void removeOrder(long number) throws IllegalArgumentException {
        if (orderList.get(number).getOrderStatus() == OrderStatus.PREPARING){
            orderList.remove(number);
        }
        else{
            throw new IllegalArgumentException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
        
    public void replaceOrder(long number,Order order) throws IllegalArgumentException {
        if (orderList.get(number).getOrderStatus() == OrderStatus.PREPARING){
            orderList.replace(number, order);
        }
        else{
            throw new IllegalArgumentException("Order status is " + orderList.get(number).getOrderStatus());
        }
    }
        
    private long getFreeNumber(){        
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
