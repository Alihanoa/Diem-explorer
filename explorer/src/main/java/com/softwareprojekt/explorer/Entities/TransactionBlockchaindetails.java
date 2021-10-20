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
@Table(name="transactionblockchaindetails")
public class TransactionBlockchaindetails implements Serializable{
    
    @Id
    @OneToOne
    private Long version;
    
    private String signature_scheme;
    
    private String secondary_signers;
    
    private String secondary_signatures_schemes;
    
    private String secondary_public_keys;
    
    private long chain_id;
    
    private String script_hash;
    
    private String script;
    
    private String hash;
    
    private String vm_status;
   
    public TransactionBlockchaindetails(){
        
    }

    public TransactionBlockchaindetails(Long version, String signature_scheme, String secondary_signers, String secondary_signatures_schemes, String secondary_public_keys, long chain_id, String script_hash, String script, String hash, String vm_status) {
        this.version = version;
        this.signature_scheme = signature_scheme;
        this.secondary_signers = secondary_signers;
        this.secondary_signatures_schemes = secondary_signatures_schemes;
        this.secondary_public_keys = secondary_public_keys;
        this.chain_id = chain_id;
        this.script_hash = script_hash;
        this.script = script;
        this.hash = hash;
        this.vm_status = vm_status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getSignature_scheme() {
        return signature_scheme;
    }

    public void setSignature_scheme(String signature_scheme) {
        this.signature_scheme = signature_scheme;
    }

    public String getSecondary_signers() {
        return secondary_signers;
    }

    public void setSecondary_signers(String secondary_signers) {
        this.secondary_signers = secondary_signers;
    }

    public String getSecondary_signatures_schemes() {
        return secondary_signatures_schemes;
    }

    public void setSecondary_signatures_schemes(String secondary_signatures_schemes) {
        this.secondary_signatures_schemes = secondary_signatures_schemes;
    }

    public String getSecondary_public_keys() {
        return secondary_public_keys;
    }

    public void setSecondary_public_keys(String secondary_public_keys) {
        this.secondary_public_keys = secondary_public_keys;
    }

    public long getChain_id() {
        return chain_id;
    }

    public void setChain_id(long chain_id) {
        this.chain_id = chain_id;
    }

    public String getScript_hash() {
        return script_hash;
    }

    public void setScript_hash(String script_hash) {
        this.script_hash = script_hash;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getVm_status() {
        return vm_status;
    }

    public void setVm_status(String vm_status) {
        this.vm_status = vm_status;
    }
    
}
