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
@Table(name="transactions")
public class Transactions implements Serializable {

    @Id
    private Long version;

    private String sender_id;

    private String receiver_id;

    private String public_key;

    private double amount;

    private String currency;

    private String gas_currency;

    private double gas_used;


    private String date;

    private String type;

    private long timestamp;

    private String addressshort;

    private String dateshort;

//    private Float realgasprice;

    public Transactions() {

    }

    public Transactions(Long version, String receiver_id, String sender_id, String public_key, double amount, String currency, String gas_currency, double gas_used, String date, String type, long timestamp) {
        this.version = version;
        this.sender_id = sender_id;
        this.public_key = public_key;
        this.amount = amount;
        this.currency = currency;
        this.gas_currency = gas_currency;
        this.gas_used = gas_used;

        this.date = date;
        this.receiver_id = receiver_id;
        this.type = type;
        this.timestamp = timestamp;

}
//    public void setRealgasprice(){
//        this.realgasprice=   ( (float) this.gas_used)/1000000;
//    }
//
//    public float getRealgasprice(){
//        return this.realgasprice;
//    }

    public String getDateshort(){
        return this.dateshort;
    }

    public void setDateshort(){

        this.dateshort = this.date.substring(0,this.date.length()-4);
    }

    public String getaddressshort(){
    return this.addressshort;
    }

    public void setAddressshort(){
        this.addressshort = this.public_key.substring(0,4)+"..."+this.public_key.substring(public_key.length()-5,this.public_key.length()-1);
        ;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public double getGas_used() {
        return gas_used;
    }

    public void setGas_used(double gas_used) {
        this.gas_used = gas_used;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp =timestamp;
    }


}
