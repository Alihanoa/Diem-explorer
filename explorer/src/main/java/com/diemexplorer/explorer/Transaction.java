/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


/**
 *
 * @author Msi
 */
@Entity
@Table(name="transaktion")
public class Transaction{
    
    @Id
    private  Long transactionID;
    
    private  String sender_id;
    
    private String public_key;
    
    private int gas_unit_price;
    
    private String gas_currency;
    
    private int gas_used;
    
    private long amount;
    
    private String currency;
    
    public Transaction(){
        
    }

    public Long getTransaktionID() {
        return transactionID;
    }

    public void setTransaktionID(Long transaktionID) {
        this.transactionID = transaktionID;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public int getGas_unit_price() {
        return gas_unit_price;
    }

    public void setGas_unit_price(int gas_unit_price) {
        this.gas_unit_price = gas_unit_price;
    }

    public String getGas_currency() {
        return gas_currency;
    }

    public void setGas_currency(String gas_currency) {
        this.gas_currency = gas_currency;
    }

    public int getGas_used() {
        return gas_used;
    }

    public void setGas_used(int gas_used) {
        this.gas_used = gas_used;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }
    
    
}
