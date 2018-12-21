/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bogdanvaduva.revolut.service;

/**
 *
 * @author bogdan vaduva
 */
public class ResponseMessage {

    private boolean status; // true for ok, false for error
    private String message; // message
    private Object data; // we can put whatever we want

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
