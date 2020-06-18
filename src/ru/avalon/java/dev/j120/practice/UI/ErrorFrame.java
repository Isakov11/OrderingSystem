/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ErrorFrame{

    private ErrorFrame() {
    }
    
    public static void create(Exception ex, int closeOperation) {
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        
        JFrame frame =  new JFrame(ex.getClass().getSimpleName());
        if (ex.getMessage() != null){
            JLabel label = new JLabel();
            
            label.setText("<html>"+ex.getMessage());
            frame.add(label);
        }
        else{
            frame.add(new JLabel(ex.getClass().getSimpleName()));
        }
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(closeOperation);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setLocation((int) sSize.width/2-200, (int) sSize.height/2-75);
        frame.setVisible(true);
    }
}
