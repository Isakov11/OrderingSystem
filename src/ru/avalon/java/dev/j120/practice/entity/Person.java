package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable{
    private long id;
    private final String name;
    private final String surname;
    private final String deliveryAddress;
    private final String phoneNumber;

    public Person(String name, String surname, String deliveryAddress, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
    }

    public Person(long id, String name,String surname, String deliveryAddress, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
    }

    public Person(Person person) {
        if (person != null){
            if (person.name != null && person.surname !=null && 
                    person.deliveryAddress !=null && person.phoneNumber !=null){
                this.id =  person.id;
                this.name =  person.name;
                this.surname =  person.surname;
                //this.contactPerson = person.contactPerson;
                this.deliveryAddress = person.deliveryAddress;
                this.phoneNumber = person.phoneNumber;
            }
            else{
                throw new IllegalArgumentException("Argument is null"); 
            }
        }
        else{
            throw new IllegalArgumentException("Argument is null"); 
        }
    }

    public long getId() {
        return id;
    }
       
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    
    public String getContactPerson() {
        return name + " " + surname;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.surname);
        hash = 59 * hash + Objects.hashCode(this.deliveryAddress);
        hash = 59 * hash + Objects.hashCode(this.phoneNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.deliveryAddress, other.deliveryAddress)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return true;
    }

    
}
