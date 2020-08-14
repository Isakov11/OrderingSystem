/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.utils;

public interface MyEventSource {
    
    public void addListener(MyEventListener listener);
    
    public void removeListener(MyEventListener listener);
    
    public void removeAllListeners();
    
    public MyEventListener[] getListeners();
    
    public void fireDataChanged(String message);
}
