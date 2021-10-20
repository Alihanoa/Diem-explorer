/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareprojekt.explorer.Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Msi
 */
@Entity
@Table(name="accountbalancexus")
public class AccountBalanceXUS implements Serializable{
    @Id
    @OneToOne
    private String address;
    
    private double amount;
    
    public AccountBalanceXUS(){
        
    }
    
    public AccountBalanceXUS(String address, double amount){
        this.address=address;
        this.amount=amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
    
    
}
