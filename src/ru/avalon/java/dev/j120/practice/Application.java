package ru.avalon.java.dev.j120.practice;

import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.controller.MediatorSQLImpl;


public class Application {    
    
    public static void main(String[] args) {
      
        //Mediator mediator  = new MediatorFile();
        Mediator mediator  = new MediatorSQLImpl();

        //Mediator mediator  = new Mediator();
    }
}