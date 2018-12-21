/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.model;

import java.sql.Date;

/**
 *
 * @author bogdan vaduva
 * 
 * Transactions
 */
public class Transaction {
    
    private int id; // Transaction ID - for any new record id is zero
    private int account; // Account to whom the transaction is releated
    private String identification; // Identification - optional
    private String date; // Transaction date and time - it's string because JSON does not specify any particular format for dates/times 
    private float amount; // Amount - can be negative. Positive amounts means we get money, negative amounts means we send money
    private int currency; // currency
    private int state; // Transaction state - 0 initiated, 1 in progress, 2 declined insufficient fonds, 3 declined other, 4 declined wrong currency, 9999 finished
    private String partner_account; // Partner account

    // For reporting purposes 
    private float balance_before;   // If we save into the database, the balance before transaction start 
                                    //  we can do customer reports / per day / per month / per year 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPartner_account() {
        return partner_account;
    }

    public void setPartner_account(String partner_account) {
        this.partner_account = partner_account;
    }

    public float getBalance_before() {
        return balance_before;
    }

    public void setBalance_before(float balance_before) {
        this.balance_before = balance_before;
    }

    @Override
    public String toString() {
        return id + "::" + account + "::" + date + "::" + amount;
    }

}
