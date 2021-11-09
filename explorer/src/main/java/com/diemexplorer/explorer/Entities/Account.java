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
@Table(name="account")
public class Account implements Serializable{
    
    @Id
    private String address;
    
    private String authentication_key;
    
    private int sequence_number;
    
    private boolean is_frozen;

    private String human_name;
    
    public Account(){
        
    }

    public Account(String address, String authentication_key, int sequence_number, boolean is_frozen, String human_name) {
        this.address = address;
        this.authentication_key = authentication_key;
        this.sequence_number = sequence_number;
        this.is_frozen = is_frozen;
        this.human_name= human_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getHuman_name() {
        return human_name;
    }

    public void setHuman_name(String human_name) {
        this.human_name = human_name;
    }
}
