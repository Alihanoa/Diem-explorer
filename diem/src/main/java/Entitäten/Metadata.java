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
public class Metadata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long metadataID;

    public Long getId() {
        return metadataID;
    }

    public void setId(Long id) {
        this.metadataID = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (metadataID != null ? metadataID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Metadata)) {
            return false;
        }
        Metadata other = (Metadata) object;
        if ((this.metadataID == null && other.metadataID != null) || (this.metadataID != null && !this.metadataID.equals(other.metadataID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entit\u00e4ten.Metadata[ id=" + metadataID + " ]";
    }
    
}
