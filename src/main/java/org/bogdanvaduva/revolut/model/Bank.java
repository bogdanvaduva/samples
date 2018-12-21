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
 * Bank - we need the financial institution that we are entering data for
 */
public class Bank {
    
    private int id;
    private String name; // Name of the bank
    private String swift; // SWIFT code
    private String remarks; // Remarks
    
    // I could have put private Customer[] customers; and in customer private Account[] accounts and so one
    // but the way I am doing and it worked for me it's the way I've implemented this example. 
    // In this way I am replicating the database and it's easier to make the match between the application and the database
    
    // To add the other elements assciated with the concept "bank" / "financial institution"

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks.replaceAll("\"", "");
    }

    @Override
    public String toString() {
        return id + "::" + name;
    }
    
}
