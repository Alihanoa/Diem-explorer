/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Msi
 */
@Entity
@Table(name="AccountBalanceXDX")
public class AccountBalanceXDX implements Serializable{
    
    @Id
    private String adress;
    
    private double amount;

    public AccountBalanceXDX(String adress, double amount) {
        this.adress = adress;
        this.amount = amount;
    }
    
    public AccountBalanceXDX(){
        
    }
}
