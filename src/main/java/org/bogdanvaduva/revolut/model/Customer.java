/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.model;

import java.util.Objects;

/**
 *
 * @author bogdan vaduva
 *
 * Bank customers - we need to know the account number for each customer in
 * order to make a money transfer
 */
public class Customer {

    private int id; // ID of the customer
    private int bank; // ID of the bank
    private String name; // Name of the customer
    private int type; // Type of customer : 1 - persons, 2- companies

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; // we should escape characters 
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true   
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Customer or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Customer)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members  
        Customer c = (Customer) o;

        // Compare the data members and return accordingly  
        return (name != null ? name.equals(c.getName()) : false);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return id + "::" + name + "::" + type;
    }

}
