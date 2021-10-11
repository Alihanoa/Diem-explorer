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
@Table(name="Account")
public class Account implements Serializable{
    
    @Id
    private String adress;
    
    private String authentication_key;
    
    private int sequence_number;
    
    private boolean is_frozen;
    
    public Account(){
        
    }

    public Account(String adress, String authentication_key, int sequence_number, boolean is_frozen) {
        this.adress = adress;
        this.authentication_key = authentication_key;
        this.sequence_number = sequence_number;
        this.is_frozen = is_frozen;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAuthentication_key() {
        return authentication_key;
    }

    public void setAuthentication_key(String authentication_key) {
        this.authentication_key = authentication_key;
    }

    public int getSequence_number() {
        return sequence_number;
    }

    public void setSequence_number(int sequence_number) {
        this.sequence_number = sequence_number;
    }

    public boolean isIs_frozen() {
        return is_frozen;
    }

    public void setIs_frozen(boolean is_frozen) {
        this.is_frozen = is_frozen;
    }
    
    
    
}
