/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import static java.lang.Thread.sleep;
import javax.swing.SwingWorker;

class ConnectionWaitTask extends SwingWorker<Integer,Integer> {
    int checkoutTimeout;
    int currentTimeout;
    
    ConnectionWaitTask(int checkoutTimeout) {
        currentTimeout = 0; 
        this.checkoutTimeout = checkoutTimeout;
    }

    @Override
    public Integer doInBackground() {
        while (currentTimeout < checkoutTimeout){
            try {
                sleep(500);
                setProgress(100 * currentTimeout / checkoutTimeout);
            } catch (InterruptedException ex) {
                
            }
            
        }
        return null;
    }     
}

