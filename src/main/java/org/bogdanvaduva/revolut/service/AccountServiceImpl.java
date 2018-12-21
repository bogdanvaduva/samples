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
import org.bogdanvaduva.revolut.service.data.RevolutData;

/**
 *
 * @author bogdan vaduva
 */
public class AccountServiceImpl implements AccountService {

    private Map<Integer, Account> accounts = new RevolutData().getDatabase().getAccounts();

    @Override
    public ResponseMessage addAccount(Account p) {
        ResponseMessage response = new ResponseMessage();
        for (Account a : accounts.values()) {
            if (a.equals(p)) {
                response.setStatus(false);
                response.setMessage("Account Already Exists");
                return response;
            }
        }
        accounts.put(p.getId(), p);
        response.setStatus(true);
        response.setMessage("Account created successfully");
        return response;
    }

    @Override
    public ResponseMessage deleteAccount(int id) {
        ResponseMessage response = new ResponseMessage();
        if (accounts.get(id) == null) {
            response.setStatus(false);
            response.setMessage("Account Doesn't Exists");
            return response;
        }
        accounts.remove(id); // here we should test if there are any money in balance field
                            // and second the account should never ever be acctually deleted because I will
                            // lose informations in transactions, but to be marked as being inactive/deleted
                            // for this example I just delete
        response.setStatus(true);
        response.setMessage("Account deleted successfully");
        return response;
    }

    @Override
    public Account getAccount(int id) {
        return accounts.get(id);
    }

    public Account getDummyAccount(int id) {
        Account p = new Account();
        p.setId(id);
        p.setAccount("Dummy");
        p.setCurrency(1);
        p.setCustomer(9999);
        p.setBalance(0);
        return p;
    }

    @Override
    public List<Account> getAllAccounts(int customer) {
        List<Account> p = new ArrayList();
        accounts.entrySet().stream().forEach(account -> {
            if (account.getValue().getCustomer() == customer) {
                p.add(account.getValue());
            }
        });
        return p;
    }
}
