/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.junit.revolut;

import com.google.gson.Gson;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bogdanvaduva.revolut.Revolut;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author bogdan vaduva
 */
public class TestRevolut extends Revolut {

    private static PipedOutputStream stdIn;

    private static Thread serverStartThread;

    @BeforeClass
    public static void setUp() throws Exception {
        stdIn = new PipedOutputStream();
        System.setIn(new PipedInputStream(stdIn));
        serverStartThread = new Thread(new Runnable() {

            @Override
            public void run() {
                String[] args = {
                    "--host",
                    "localhost",
                    "--port",
                    "8888"
                };
                Revolut.main(args);
            }
        });
        serverStartThread.start();
        // give the server some tine to start.
        Thread.sleep(100);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        stdIn.write("\n\n".getBytes());
        serverStartThread.join(2000);
        Assert.assertFalse(serverStartThread.isAlive());
    }

    @Test
    public void doTestAddBank() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/bank/addbank?bank=" + URLEncoder.encode("{\"id\":\"2\",\"name\":\"REVOLUT US\"}", "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Bank created successfully", jsonJavaRootObject.get("message"));
        //try to add the bank again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Bank Already Exists", jsonJavaRootObject.get("message"));        
        response.close();
    }

    @Test
    public void doTestDeleteBank() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/bank/deletebank?id=2");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Bank deleted successfully", jsonJavaRootObject.get("message"));
        //try to delete the bank again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Bank Doesn't Exists", jsonJavaRootObject.get("message"));        
        response.close();
    }

    @Test
    public void doTestGetBank() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/bank/getbank?id=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(1.0, jsonJavaRootObject.get("id"));
        response.close();
    }

    @Test
    public void doTestGetDummyBank() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/bank/getdummybank?id=100");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals("Dummy", jsonJavaRootObject.get("name"));
        response.close();
    }

    @Test
    public void doTestGetAllBanks() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/bank/getallbanks");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        List jsonJavaRootObject = gson.fromJson(string, List.class);
        Assert.assertEquals(2, jsonJavaRootObject.size());
        response.close();
    }

    @Test
    public void doTestAddCustomer() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/customer/addcustomer?customer=" + URLEncoder.encode("{\"id\":\"5\",\"bank\":\"1\",\"name\":\"Jane Doe\"}", "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Customer created successfully", jsonJavaRootObject.get("message"));
        //try to add the customer again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Customer Already Exists", jsonJavaRootObject.get("message"));        
        response.close();
    }

    @Test
    public void doTestDeleteCustomer() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/customer/deletecustomer?id=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Customer deleted successfully", jsonJavaRootObject.get("message"));
        //try to delete the customer again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Customer Doesn't Exists", jsonJavaRootObject.get("message"));        
        response.close();
    }

    @Test
    public void doTestGetCustomer() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/customer/getcustomer?id=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(1.0, jsonJavaRootObject.get("id"));
        response.close();
    }

    @Test
    public void doTestGetDummyCustomer() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/customer/getdummycustomer?id=100");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals("Dummy", jsonJavaRootObject.get("name"));
        response.close();
    }

    @Test
    public void doTestGetAllCustomers() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/customer/getallcustomers?bank=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        List jsonJavaRootObject = gson.fromJson(string, List.class);
        Assert.assertEquals(4, jsonJavaRootObject.size());
        response.close();
    }

    @Test
    public void doTestAddAccount() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/account/addaccount?account=" + URLEncoder.encode("{\"customer\":\"1\",\"account\":\"00000100\",\"currency\":\"1\",\"type\":\"0\"}", "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Account created successfully", jsonJavaRootObject.get("message"));
        //try to add the customer again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Account Already Exists", jsonJavaRootObject.get("message"));        
        response.close();
    }

    @Test
    public void doTestDeleteAccount() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/account/deleteaccount?id=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Account deleted successfully", jsonJavaRootObject.get("message"));
        //try to delete the customer again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Account Doesn't Exists", jsonJavaRootObject.get("message"));        
        response.close();
    }
    
    @Test
    public void doTestGetAccount() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/account/getaccount?id=2");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(2.0, jsonJavaRootObject.get("id"));
        response.close();
    }

    @Test
    public void doTestGetDummyAccount() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/account/getdummyaccount?id=100");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals("Dummy", jsonJavaRootObject.get("account"));
        response.close();
    }

    @Test
    public void doTestGetAllAccounts() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/account/getallaccounts?customer=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        List jsonJavaRootObject = gson.fromJson(string, List.class);
        Assert.assertEquals(3, jsonJavaRootObject.size());
        response.close();
    }

    @Test
    public void doTestProcessTransfer() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        // first we get all transactions for account 1
        HttpGet httpget = new HttpGet("http://localhost:8888/transaction/getalltransactions?account=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println("Initial state of transactions");
        System.out.println(string);
        
        // initiate a transfer
        httpget.setURI(new URI("http://localhost:8888/transaction/processtransfer?transaction=" + URLEncoder.encode("{\"account\":\"1\",\"amount\":\"-100\",\"currency\":\"1\",\"date\":\"2018-01-01 00:00:00\",\"partner_account\":\"00000021\",\"identification\":\"1\"}", "UTF-8")));
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(true, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Transaction completed successfully", jsonJavaRootObject.get("message"));
        
        //try to execute transaction once again, we should get false as status
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(false, (boolean)jsonJavaRootObject.get("status"));
        Assert.assertEquals("Transaction completed unsuccessfully - insufficient fonds", jsonJavaRootObject.get("message"));

        System.out.println("Database state after transactions");
        System.out.println(jsonJavaRootObject.get("data"));

        // get transactions for account 1
        httpget.setURI(new URI("http://localhost:8888/transaction/getalltransactions?account=1"));
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(string);

        // get transactions for account 4 - partner account
        httpget.setURI(new URI("http://localhost:8888/transaction/getalltransactions?account=4"));
        response = httpclient.execute(httpget);
        string = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(string);
        response.close();
    }
    
    @Test
    public void doTestGetTransaction() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/transaction/gettransaction?id=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals(1.0, jsonJavaRootObject.get("id"));
        response.close();
    }

    @Test
    public void doTestGetDummyTransaction() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/transaction/getdummytransaction?id=100");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        Map jsonJavaRootObject = gson.fromJson(string, Map.class);
        Assert.assertEquals("Dummy", jsonJavaRootObject.get("identification"));
        response.close();
    }

    @Test
    public void doTestGetAllTransactions() throws Exception {
        Gson gson = new Gson();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8888/transaction/getalltransactions?account=1");
        CloseableHttpResponse response = httpclient.execute(httpget);
        String string = EntityUtils.toString(response.getEntity(), "UTF-8");
        List jsonJavaRootObject = gson.fromJson(string, List.class);
        Assert.assertEquals(1, jsonJavaRootObject.size());
        response.close();
    }

    
}
