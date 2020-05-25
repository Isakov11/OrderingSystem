/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Hino
 */
public class ErrorFrame{

    private ErrorFrame() {
    }
    
    public static void create(Exception ex) {
        JFrame frame =  new JFrame(ex.getClass().getSimpleName());
        if (ex.getMessage() != null){
            frame.add(new JLabel(ex.getMessage()));
        }
        else{
            frame.add(new JLabel(ex.getClass().getSimpleName()));
        }
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
