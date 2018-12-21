/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.List;
import org.bogdanvaduva.revolut.model.Account;

/**
 *
 * @author bogdan vaduva
 */

public interface AccountService {

	public ResponseMessage addAccount(Account a);
	
	public ResponseMessage deleteAccount(int id);
	
	public Account getAccount(int id);
	
	public List<Account> getAllAccounts(int customer);

}