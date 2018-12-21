/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut;

import com.pyknic.servicekit.HttpServer;
import com.pyknic.servicekit.Path;
import com.pyknic.servicekit.Service;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import org.bogdanvaduva.revolut.model.Account;
import org.bogdanvaduva.revolut.model.Bank;
import org.bogdanvaduva.revolut.model.Customer;
import org.bogdanvaduva.revolut.model.Transaction;
import org.bogdanvaduva.revolut.service.AccountServiceImpl;
import org.bogdanvaduva.revolut.service.ResponseMessage;
import org.bogdanvaduva.revolut.service.BankServiceImpl;
import org.bogdanvaduva.revolut.service.CustomerServiceImpl;
import org.bogdanvaduva.revolut.service.TransactionServiceImpl;

/**
 *
 * @author bogdan vaduva
 */
public class Revolut extends HttpServer {
    
    private BankServiceImpl bsi = new BankServiceImpl();
    private CustomerServiceImpl csi = new CustomerServiceImpl();
    private AccountServiceImpl asi = new AccountServiceImpl();
    private TransactionServiceImpl tsi = new TransactionServiceImpl();
    
    public static void main(String[] args) {
        HttpServer.run(Revolut.class);
    }
    
    public Revolut() {
        super(8888);
    }
    
    // 
    // Depending on performance we can use for our services
    // CompletableFuture which will allow us asyncronous calls -> more user friendly when the task is complex 
    // For this example I didn't used.
    
    //define hooks for /bank
    @Path("bank")
    @Service({"bank"})
    public ResponseMessage addBank(Bank bank) {
        return bsi.addBank(bank);
    }

    @Path("bank")
    @Service({"id"})
    public ResponseMessage deleteBank(int id) {
        return bsi.deleteBank(id);
    }

    @Path("bank")
    @Service({"id"})
    public Bank getBank(int id) {
        return bsi.getBank(id);
    }
    
    @Path("bank")
    @Service({"id"})
    public Bank getDummyBank(int id) {
        return bsi.getDummyBank(id);
    }
    
    @Path("bank")
    @Service()
    public List<Bank> getAllBanks() {
        return bsi.getAllBanks();
    }
    //end bank

    //define hooks for /customer
    @Path("customer")
    @Service({"customer"})
    public ResponseMessage addCustomer(Customer customer) {
        return csi.addCustomer(customer);
    }

    @Path("customer")
    @Service({"id"})
    public ResponseMessage deleteCustomer(int id) {
        return csi.deleteCustomer(id);
    }

    @Path("customer")
    @Service({"id"})
    public Customer getCustomer(int id) {
        return csi.getCustomer(id);
    }
    
    @Path("customer")
    @Service({"id"})
    public Customer getDummyCustomer(int id) {
        return csi.getDummyCustomer(id);
    }
    
    @Path("customer")
    @Service({"bank"})
    public List<Customer> getAllCustomers(int bank) {
        return csi.getAllCustomers(bank);
    }
    //end customer

    //define hooks for /account
    @Path("account")
    @Service({"account"})
    public ResponseMessage addAccount(Account account) {
        return asi.addAccount(account);
    }

    @Path("account")
    @Service({"id"})
    public ResponseMessage deleteAccount(int id) {
        return asi.deleteAccount(id);
    }

    @Path("account")
    @Service({"id"})
    public Account getAccount(int id) {
        return asi.getAccount(id);
    }
    
    @Path("account")
    @Service({"id"})
    public Account getDummyAccount(int id) {
        return asi.getDummyAccount(id);
    }
    
    @Path("account")
    @Service({"customer"})
    public List<Account> getAllAccounts(int customer) {
        return asi.getAllAccounts(customer);
    }
    //end account
    
    //define hooks for /transaction
    @Path("transaction")
    @Service({"transaction"})
    public ResponseMessage processTransfer(Transaction t) {
        return tsi.processTransfer(t);
    }

    @Path("transaction")
    @Service({"id"})
    public Transaction getTransaction(int id) {
        return tsi.getTransaction(id);
    }
    
    @Path("transaction")
    @Service({"id"})
    public Transaction getDummyTransaction(int id) {
        return tsi.getDummyTransaction(id);
    }
    
    @Path("transaction")
    @Service({"account"})
    public List<Transaction> getAllTransactions(int account) {
        return tsi.getAllTransactions(account);
    }
    //end transaction
}
