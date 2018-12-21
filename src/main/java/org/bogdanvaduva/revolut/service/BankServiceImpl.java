/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bogdanvaduva.revolut.model.Bank;
import org.bogdanvaduva.revolut.service.data.RevolutData;

/**
 *
 * @author bogdan vaduva
 */
public class BankServiceImpl implements BankService {

    private Map<Integer, Bank> banks = new RevolutData().getDatabase().getBanks();

    @Override
    public ResponseMessage addBank(Bank p) {
        ResponseMessage response = new ResponseMessage();
        if (banks.get(p.getId()) != null) {
            response.setStatus(false);
            response.setMessage("Bank Already Exists");
            return response;
        }
        banks.put(p.getId(), p);
        response.setStatus(true);
        response.setMessage("Bank created successfully");
        return response;
    }

    @Override
    public ResponseMessage deleteBank(int id) {
        ResponseMessage response = new ResponseMessage();
        if (banks.get(id) == null) {
            response.setStatus(false);
            response.setMessage("Bank Doesn't Exists");
            return response;
        }
        banks.remove(id); // we should check if the bank has customers, accounts with balance !=0
                        // for this example I just delete
        response.setStatus(true);
        response.setMessage("Bank deleted successfully");
        return response;
    }

    @Override
    public Bank getBank(int id) {
        return banks.get(id);
    }

    public Bank getDummyBank(int id) {
        Bank p = new Bank();
        p.setName("Dummy");
        p.setId(id);
        return p;
    }

    @Override
    public List<Bank> getAllBanks() {
        List<Bank> p = new ArrayList();
        banks.entrySet().stream().forEach(bank -> {
            p.add(bank.getValue());
        });
        return p;
    }
}
