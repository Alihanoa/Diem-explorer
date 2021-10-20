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
@Table(name="accountbalancexdx")
public class AccountBalanceXDX implements Serializable{
    
    @Id
    private String address;
    
    private double amount;

    public AccountBalanceXDX(String address, double amount) {
        this.address = address;
        this.amount = amount;
    }
    
    public AccountBalanceXDX(){
        
    }




}
