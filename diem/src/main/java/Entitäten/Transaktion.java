/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitäten;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Msi
 */
@Entity
public class Transaktion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String senderAuthenticationKey;
    
    private String receiverAuthenticationKey;
    
    private String currency;
    
    private double amount;
    
    private double burnedGas;
    
    private Long metadataID;
    
    private boolean signed;

    public String getSenderAuthenticationKey() {
        return senderAuthenticationKey;
    }

    public void setSenderAuthenticationKey(String senderAuthenticationKey) {
        this.senderAuthenticationKey = senderAuthenticationKey;
    }

    public String getReceiverAuthenticationKey() {
        return receiverAuthenticationKey;
    }

    public void setReceiverAuthenticationKey(String receiverAuthenticationKey) {
        this.receiverAuthenticationKey = receiverAuthenticationKey;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBurnedGas() {
        return burnedGas;
    }

    public void setBurnedGas(double burnedGas) {
        this.burnedGas = burnedGas;
    }

    public long getMetadataID() {
        return metadataID;
    }

    public void setMetadataID(long metadataID) {
        this.metadataID = metadataID;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaktion)) {
            return false;
        }
        Transaktion other = (Transaktion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entit\u00e4ten.Transaktion[ id=" + id + " ]";
    }
    
}
