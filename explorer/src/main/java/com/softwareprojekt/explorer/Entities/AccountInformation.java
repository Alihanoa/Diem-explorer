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
@Table(name="accountinformation")
public class AccountInformation implements Serializable{
    
    @Id
    @OneToOne
    private String address;
            
    private String sent_events_key;
    
    private String receive_events_key;
    
    private String rtype;
    
    private String parent_vasp_name;
    
    private String base_url;
    
    private String expiration_time;
    
    private String compliance_key;
    
    private String compliance_key_rotation_events_key;
    
    private String base_url_rotation_events_key;
    
    private String preburn_balances;
    
    private long blockchain_version;

    public AccountInformation() {
    }

    public AccountInformation(String address, String sent_events_key, String receive_events_key, String rtype, String parent_vasp_name, String base_url, String expiration_time, String compliance_key, String compliance_key_rotation_events_key, String base_url_rotation_events_key, String preburn_balances, long blockchain_version) {
        this.address = address;
        this.sent_events_key = sent_events_key;
        this.receive_events_key = receive_events_key;
        this.rtype = rtype;
        this.parent_vasp_name = parent_vasp_name;
        this.base_url = base_url;
        this.expiration_time = expiration_time;
        this.compliance_key = compliance_key;
        this.compliance_key_rotation_events_key = compliance_key_rotation_events_key;
        this.base_url_rotation_events_key = base_url_rotation_events_key;
        this.preburn_balances = preburn_balances;
        this.blockchain_version = blockchain_version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getSent_events_key() {
        return sent_events_key;
    }

    public void setSent_events_key(String sent_events_key) {
        this.sent_events_key = sent_events_key;
    }

    public String getReceive_events_key() {
        return receive_events_key;
    }

    public void setReceive_events_key(String receive_events_key) {
        this.receive_events_key = receive_events_key;
    }


    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getParent_vasp_name() {
        return parent_vasp_name;
    }

    public void setParent_vasp_name(String parent_vasp_name) {
        this.parent_vasp_name = parent_vasp_name;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(String expiration_time) {
        this.expiration_time = expiration_time;
    }

    public String getCompliance_key() {
        return compliance_key;
    }

    public void setCompliance_key(String compliance_key) {
        this.compliance_key = compliance_key;
    }

    public String getCompliance_key_rotation_events_key() {
        return compliance_key_rotation_events_key;
    }

    public void setCompliance_key_rotation_events_key(String compliance_key_rotation_events_key) {
        this.compliance_key_rotation_events_key = compliance_key_rotation_events_key;
    }

    public String getBase_url_rotation_events_key() {
        return base_url_rotation_events_key;
    }

    public void setBase_url_rotation_events_key(String base_url_rotation_events_key) {
        this.base_url_rotation_events_key = base_url_rotation_events_key;
    }

    public String getPreburn_balances() {
        return preburn_balances;
    }

    public void setPreburn_balances(String preburn_balances) {
        this.preburn_balances = preburn_balances;
    }

    public long getBlockchain_version() {
        return blockchain_version;
    }

    public void setBlockchain_version(long blockchain_version) {
        this.blockchain_version = blockchain_version;
    }
    
    
            
    
}
