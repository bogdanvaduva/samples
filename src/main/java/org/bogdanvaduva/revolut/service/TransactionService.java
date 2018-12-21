/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

import java.util.List;
import org.bogdanvaduva.revolut.model.Transaction;

/**
 *
 * @author bogdan vaduva
 */

public interface TransactionService {

	public ResponseMessage processTransfer(Transaction t);
	
	public Transaction getTransaction(int id);

        public List<Transaction> getAllTransactions(int account);

}