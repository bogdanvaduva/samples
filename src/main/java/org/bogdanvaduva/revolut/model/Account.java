/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.model;

/**
 *
 * @author bogdan vaduva
 * 
 * Customer accounts 
 */
public class Account {

    private int id; // ID of the account
    private int customer; // ID of the customer
    private String account; // Account number - unique 
    private int type; // Account type - can be used to differentiate fees - 0 normal fees
    private int currency; // Currency of the account - 1 for GBP, 2 for EUR, 3 for US 
    private float balance; // Account balance

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true   
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Customer or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Account)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members  
        Account a = (Account) o;

        // Compare the data members and return accordingly  
        return (account != null ? account.equals(a.getAccount()) : false);
    }

    @Override
    public String toString() {
        return id + "::" + customer + "::" + currency;
    }

}
