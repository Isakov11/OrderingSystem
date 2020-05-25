package ru.avalon.java.dev.j120.practice.entity;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable{
    private String contactPerson;
    private String deliveryAddress;
    private String phoneNumber;

    public Person(String contactPerson, String deliveryAddress, String phoneNumber) {
        this.contactPerson = contactPerson;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
    }

    public Person(Person person) {
        if (person !=null){
            this.contactPerson = person.contactPerson;
            this.deliveryAddress = person.deliveryAddress;
            this.phoneNumber = person.phoneNumber;
        }
    }
    
    public String getContactPerson() {
        return contactPerson;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return contactPerson;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.contactPerson);
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
        if (!Objects.equals(this.contactPerson, other.contactPerson)) {
            return false;
        }
        return true;
    }
}
