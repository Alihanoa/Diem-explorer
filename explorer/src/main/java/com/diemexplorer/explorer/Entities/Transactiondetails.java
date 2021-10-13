/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author Msi
 */
@Entity
@Table(name="transactiondetails")
public class Transactiondetails implements Serializable{
    
    @Id
    @OneToOne
    private  Long version;
    
    private  String sender_id;
    
    private String public_key;
    
    private long amount;
    
    private String currency;
    
    private String gas_currency;
    
    private int gas_used;
    
    private int expiration_timestamp_seconds;
    
    public Transactiondetails(){
        
    }

    public Transactiondetails(Long version, String sender_id, String public_key, long amount, String currency, String gas_currency, int gas_used, int expiration_timestamp_seconds) {
        this.version = version;
        this.sender_id = sender_id;
        this.public_key = public_key;
        this.amount = amount;
        this.currency = currency;
        this.gas_currency = gas_currency;
        this.gas_used = gas_used;
        this.expiration_timestamp_seconds = expiration_timestamp_seconds;
    }
    
    

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public int getExpiration_timestamp_seconds() {
        return expiration_timestamp_seconds;
    }

    public void setExpiration_timestamp_seconds(int expiration_timestamp_seconds) {
        this.expiration_timestamp_seconds = expiration_timestamp_seconds;
    }
    

    
}
