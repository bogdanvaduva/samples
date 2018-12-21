/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bogdanvaduva.revolut.model.Customer;
import org.bogdanvaduva.revolut.service.data.RevolutData;

/**
 *
 * @author bogdan vaduva
 */
public class CustomerServiceImpl implements CustomerService {

    private Map<Integer, Customer> customers = new RevolutData().getDatabase().getCustomers();

    @Override
    public ResponseMessage addCustomer(Customer p) {
        ResponseMessage response = new ResponseMessage();
        for (Customer c : customers.values()) {
            if (c.equals(p)) {
                response.setStatus(false);
                response.setMessage("Customer Already Exists");
                return response;
            }
        }
        p.setId(Math.round((float)Math.random()*100));
        customers.put(p.getId(),p);
        response.setStatus(true);
        response.setMessage("Customer created successfully");
        return response;
    }

    @Override
    public ResponseMessage deleteCustomer(int id) {
        ResponseMessage response = new ResponseMessage();
        if (customers.get(id) == null) {
            response.setStatus(false);
            response.setMessage("Customer Doesn't Exists");
            return response;
        }
        customers.remove(id); // we cannot delete a customer that has account with balance != 0
                              // we mark customer as being inactive/deleted
                             // for this example I just delete
        response.setStatus(true);
        response.setMessage("Customer deleted successfully");
        return response;
    }

    @Override
    public Customer getCustomer(int id) {
        return customers.get(id);
    }

    public Customer getDummyCustomer(int id) {
        Customer p = new Customer();
        p.setId(id);
        p.setName("Dummy");
        p.setBank(9999);
        p.setType(1);
        return p;
    }

    @Override
    public List<Customer> getAllCustomers(int bank) {
        List<Customer> p = new ArrayList();
        customers.entrySet().stream().forEach(customer -> {
            if (customer.getValue().getBank() == bank) {
                p.add(customer.getValue());
            }
        });
        return p;
    }
}
