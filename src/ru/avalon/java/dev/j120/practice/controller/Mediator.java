
package ru.avalon.java.dev.j120.practice.controller;

import ru.avalon.java.dev.j120.practice.IO.*;
import ru.avalon.java.dev.j120.practice.datastorage.*;
import ru.avalon.java.dev.j120.practice.entity.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.SwingUtilities;
import ru.avalon.java.dev.j120.practice.UI.MainFrame;
import ru.avalon.java.dev.j120.practice.utils.StateEnum;


public class Mediator {
    private GoodsIO goodsIO;
    private OrderIO orderIO;
    private PriceList pricelist;
    private OrderList orderlist;    
    private JFrame mainframe;
    
    public Mediator() {
        try {
            goodsIO = new GoodsIO();
            orderIO = new OrderIO();
            pricelist = new PriceList(goodsIO.read(Config.get().getPricePath()));
            orderlist = new OrderList(orderIO.read(Config.get().getOrderPath()));
            System.out.println(pricelist.toString());
            System.out.println(orderlist.toString());
            
            //---------------------------------------------------------------------
            SwingUtilities.invokeLater(() -> {
              JFrame mainframe = new MainFrame(this);
            });
            //--------------------------------------------------------------------- 
            
            
        } catch (IOException | ClassNotFoundException | IllegalArgumentException ex) {            
            JFrame frame =  new JFrame(ex.getClass().getSimpleName());
            frame.add(new JLabel(ex.getMessage()));            
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            System.out.println(ex);
        }  
    }    
    
    public final void main(){        
        
    }

    public PriceList getPriceList() {
        return pricelist;
    }

    public OrderList getOrderList() {
        return orderlist;
    }
    
    public final void updateGood(StateEnum state, Good good){
        if (state.equals(StateEnum.NEW)){pricelist.addExist(good);}
        if (state.equals(StateEnum.EXIST)){pricelist.replaceGood(good);}
        /*try {
            goodsIO.write(Config.get().getPricePath(), pricelist.getPriceList());
        } catch (IOException ex) {
            JFrame frame =  new JFrame(ex.getClass().getSimpleName());
            frame.add(new JLabel(ex.getMessage()));            
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }*/
    }
    
    public final void updateOrder(StateEnum state, Order order){
        if (state.equals(StateEnum.NEW)){orderlist.addNew(order);}
    }
}
    
    