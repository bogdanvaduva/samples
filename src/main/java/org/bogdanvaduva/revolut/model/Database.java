/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.model;

import java.util.LinkedHashMap;

/**
 *
 * @author bogdan vaduva
 * 
 * This database model it's not necessary, but for our example we need it
 * 
 */
public class Database {
    
    private LinkedHashMap<Integer, Bank> banks;
    private LinkedHashMap<Integer, Customer> customers;
    private LinkedHashMap<Integer, Account> accounts;
    private LinkedHashMap<Integer, Transaction> transactions;    

    public LinkedHashMap<Integer, Bank> getBanks() {
        return banks;
    }

    public void setBanks(LinkedHashMap<Integer, Bank> banks) {
        this.banks = banks;
    }

    public LinkedHashMap<Integer, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(LinkedHashMap<Integer, Customer> customers) {
        this.customers = customers;
    }

    public LinkedHashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(LinkedHashMap<Integer, Account> accounts) {
        this.accounts = accounts;
    }

    public LinkedHashMap<Integer, Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedHashMap<Integer, Transaction> transactions) {
        this.transactions = transactions;
    }

}
