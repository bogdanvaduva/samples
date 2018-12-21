/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service.data;

import java.util.LinkedHashMap;
import org.bogdanvaduva.revolut.model.Account;
import org.bogdanvaduva.revolut.model.Bank;
import org.bogdanvaduva.revolut.model.Customer;
import org.bogdanvaduva.revolut.model.Database;
import org.bogdanvaduva.revolut.model.Transaction;

/**
 *
 * @author bogdan vaduva
 * 
 * Usually this is a service
 */
public class RevolutData {

    private Database database;

    public Database getDatabase() {
        return database;
    }

    public RevolutData() {
        database = new Database();

        LinkedHashMap<Integer, Bank> b = new LinkedHashMap();
        
        Bank b1 = new Bank();
        b1.setId(1);
        b1.setName("REVOLUT");
        b1.setRemarks("A new way of thinking");
        b1.setSwift("REVOUK01");
        b.put(b1.getId(), b1);
        
        database.setBanks(b);
        
        LinkedHashMap<Integer, Customer> c = new LinkedHashMap();
        
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setBank(1);
        c1.setName("Bogdan Vaduva");
        c1.setType(1);
        c.put(c1.getId(),c1);
        
        Customer c2 = new Customer();
        c2.setId(2);
        c2.setBank(1);
        c2.setName("John Smith");
        c2.setType(1);
        c.put(c2.getId(),c2);

        Customer c3 = new Customer();
        c3.setId(3);
        c3.setBank(1);
        c3.setName("Company Inc.");
        c3.setType(2);
        c.put(c3.getId(),c3);

        Customer c4 = new Customer();
        c4.setId(4);
        c4.setBank(1);
        c4.setName("John Doe");
        c4.setType(1);
        c.put(c4.getId(),c4);
        
        database.setCustomers(c);
        
        LinkedHashMap<Integer, Account> a = new LinkedHashMap();
        
        Account a11 = new Account();
        a11.setId(1);
        a11.setCustomer(1);
        a11.setAccount("00000011");
        a11.setCurrency(1); 
        a11.setType(0); 
        a11.setBalance(100);
        a.put(a11.getId(),a11);
        
        Account a12 = new Account();
        a12.setId(2);
        a12.setCustomer(1);
        a12.setAccount("00000012");
        a12.setCurrency(2); 
        a12.setType(0); 
        a12.setBalance(0);
        a.put(a12.getId(),a12);
        
        Account a13 = new Account();
        a13.setId(3);
        a13.setCustomer(1);
        a13.setAccount("00000013");
        a13.setCurrency(3); 
        a13.setType(0); 
        a13.setBalance(0);
        a.put(a13.getId(),a13);

        Account a21 = new Account();
        a21.setId(4);
        a21.setCustomer(2);
        a21.setAccount("00000021");
        a21.setCurrency(1); 
        a21.setType(0); 
        a21.setBalance(1000);
        a.put(a21.getId(),a21);
        
        database.setAccounts(a);
       
        LinkedHashMap<Integer, Transaction> t = new LinkedHashMap();
        
        Transaction t1 = new Transaction();

        t1.setId(1);
        t1.setAccount(1);
        t1.setAmount(100);
        t1.setCurrency(1);
        t1.setDate("2018-12-01 00:00:00");
        t1.setIdentification("1");
        t1.setPartner_account("00000021");
        t.put(t1.getId(), t1);
        
        database.setTransactions(t);
    }
    
}
