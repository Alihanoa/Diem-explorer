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
@Table(name="transactionblockchaindetails")
public class TransactionBlockchaindetails implements Serializable{
    
    @Id
    private Long version;
    
    private long chain_id;

    private String hash;


    // The Metadata variables are part of the script
    private String metadata;

    private String metadata_signature;



    private String script_hash;

    //All the variables below are part of the VM Status. Type is always set while the others aren't
    
    private int abort_code;

    private String category;

    private String category_description;

    private String reason;

    private String reason_description;

    private String location;

    private String type;

    private String expiration_date;


   
    public TransactionBlockchaindetails(){
        
    }

    public TransactionBlockchaindetails(Long version, long chain_id, String hash, String metadata, String metadata_signature, String script_hash, int abort_code, String category, String category_description, String reason, String reason_description, String location, String type, String expiration_date) {
        this.version = version;
        this.chain_id = chain_id;
        this.hash = hash;
        this.metadata = metadata;
        this.metadata_signature = metadata_signature;
        this.script_hash = script_hash;
        this.abort_code = abort_code;
        this.category = category;
        this.category_description = category_description;
        this.reason = reason;
        this.reason_description = reason_description;
        this.location = location;
        this.type = type;
        this.expiration_date=expiration_date;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public long getChain_id() {
        return chain_id;
    }

    public void setChain_id(long chain_id) {
        this.chain_id = chain_id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMetadata_signature() {
        return metadata_signature;
    }

    public void setMetadata_signature(String metadata_signature) {
        this.metadata_signature = metadata_signature;
    }

    public String getScript_hash() {
        return script_hash;
    }

    public void setScript_hash(String script_hash) {
        this.script_hash = script_hash;
    }

    public int getAbort_code() {
        return abort_code;
    }

    public void setAbort_code(int abort_code) {
        this.abort_code = abort_code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason_description() {
        return reason_description;
    }

    public void setReason_description(String reason_description) {
        this.reason_description = reason_description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
}
