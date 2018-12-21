/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bogdanvaduva.revolut.model.Account;
import org.bogdanvaduva.revolut.model.Transaction;
import org.bogdanvaduva.revolut.service.data.RevolutData;

/**
 *
 * @author bogdan vaduva
 */
public class TransactionServiceImpl implements TransactionService {

    private RevolutData rd = new RevolutData();
    private Map<Integer, Transaction> transactions = rd.getDatabase().getTransactions();
    private Map<Integer, Account> accounts = rd.getDatabase().getAccounts();

    @Override
    public ResponseMessage processTransfer(Transaction t) {
        ResponseMessage response = new ResponseMessage();
        // Transfer logic - any transfer involves two transactions - one for current account and one for coresponding account
        //
        // lock the account in terms of writing transactions
        //
        // we add the transaction and get the id of the new transaction - the state will be initiated - 0
        // we put in balance_before the current balance
        // 
        //
        // process the record for changing amount and state
        // we change state in processing
        // if amount is negative then
        //          if balance is more than amount
        //          
        //                  we substract the amount
        //                  we send confirmation to partner 
        //                  change state to sucess - 9999
        //
        //          else we send insufficient fonds to partner_account
        //                  change state in declined
        //  
        // else we are in that situation when we are trying to initiate a transaction for adding money from receiver part and not the sender part
        //         we send answer that we have a problem
        //        
        // unlock account in terms of wrinting transactions
        
        // Generate id similiar with inserting data into a database
        // Presume that account (int) in transaction is a valid value;
        t.setId(Math.round((float)Math.random()*100));
        t.setState(0);
        
        // Start processing the transaction
        t.setState(1); // processing
        // get the sending/receiving account from transaction
        Account ta = null;
        for (Account a: accounts.values()) {
            if (a.getId() == t.getAccount()) {
                ta = a;
                break;
            }
        }
        if (ta != null) {
            t.setBalance_before(ta.getBalance());
        } else {
            t.setState(3);
            transactions.put(t.getId(), t);
            response.setStatus(false);
            response.setMessage("Transaction completed unsuccessfully - account wrong");
            response.setData(rd);
            return response;
        }
        if (ta.getCurrency()!=t.getCurrency()) {
            t.setState(4);
            transactions.put(t.getId(), t);
            // here instead of sending error we can try a conversion of funds
            // but for our sample we do not
            response.setStatus(false);
            response.setMessage("Transaction completed unsuccessfully - wrong currency");
            response.setData(rd);
            return response;
        }
        // get the partner 
        Account partner_account = null;
        for (Account a : accounts.values()) {
            if (a.getAccount().equals(t.getPartner_account())) {
                partner_account = a;
            }
        }
        if (partner_account == null) {
            t.setState(3);
            transactions.put(t.getId(), t);
            response.setStatus(false);
            response.setMessage("Transaction completed unsuccessfully - partner account wrong");
            response.setData(rd);
            return response;
        }
        // Implement logic
        if (t.getAmount() < 0) {
            if (t.getBalance_before() >= -1*t.getAmount()) {
                ta.setBalance(ta.getBalance() + t.getAmount());
                t.setState(9999); // success
                // check if coresponding account it's locked for transaction and if yes wait till it unlock or timeout
                // if error rollback
                // if not
                Transaction tmp = new Transaction();
                tmp.setId(Math.round((float)Math.random()*100));
                tmp.setAccount(partner_account.getId());
                tmp.setAmount(-1*t.getAmount());
                tmp.setCurrency(t.getCurrency());
                tmp.setDate(t.getDate());
                tmp.setPartner_account(ta.getAccount());
                tmp.setBalance_before(partner_account.getBalance());
                tmp.setState(9999);
                partner_account.setBalance(partner_account.getBalance()+ tmp.getAmount());
                transactions.put(tmp.getId(), tmp);
            } else {
                t.setState(2); //insufficient fonds
                transactions.put(t.getId(), t);
                response.setStatus(false);
                response.setMessage("Transaction completed unsuccessfully - insufficient fonds");
                response.setData(rd);
                return response;
            } 
        } else {
            response.setStatus(false);
            response.setMessage("Transaction completed unsuccessfully - you can't add fonds from nothing");
            response.setData(rd);
            return response;
        }
        
        transactions.put(t.getId(), t);
        response.setStatus(true);
        response.setMessage("Transaction completed successfully");
        response.setData(rd);
        return response;
    }

    @Override
    public Transaction getTransaction(int id) {
        return transactions.get(id);
    }

    public Transaction getDummyTransaction(int id) {
        Transaction p = new Transaction();
        p.setId(id);
        p.setAccount(9999);
        p.setAmount(100);
        p.setDate("2018-12-01 00:00:00");
        p.setState(9999);
        p.setIdentification("Dummy");
        p.setPartner_account("DUMMY0000000");
        return p;
    }

    @Override
    public List<Transaction> getAllTransactions(int account) {
        List<Transaction> p = new ArrayList();
        transactions.entrySet().stream().forEach(transaction -> {
            if (transaction.getValue().getAccount() == account) {
                p.add(transaction.getValue());
            }
        });
        return p;
    }
}
