/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.List;
import org.bogdanvaduva.revolut.model.Bank;

/**
 *
 * @author bogdan vaduva
 */

public interface BankService {

	public ResponseMessage addBank(Bank b);
	
	public ResponseMessage deleteBank(int id);
	
	public Bank getBank(int id);
	
	public List<Bank> getAllBanks();

}