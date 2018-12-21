/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.List;
import org.bogdanvaduva.revolut.model.Customer;

/**
 *
 * @author bogdan vaduva
 */

public interface CustomerService {

	public ResponseMessage addCustomer(Customer c);
	
	public ResponseMessage deleteCustomer(int id);
	
	public Customer getCustomer(int id);
	
	public List<Customer> getAllCustomers(int bank);

}