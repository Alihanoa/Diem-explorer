/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Entities;

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
@Table(name="AccoutBalancesXUS")
public class AccountBalanceXUS implements Serializable{
    @Id
    @OneToOne
    private String adress;
    
    private double amount;
    
    public AccountBalanceXUS(){
        
    }
    
    public AccountBalanceXUS(String adress, double amount){
        this.adress=adress;
        this.amount=amount;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
    
    
}
