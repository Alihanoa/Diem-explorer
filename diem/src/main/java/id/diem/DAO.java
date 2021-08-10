/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.diem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Msi
 */
public class DAO {
        @PersistenceUnit
    public EntityManagerFactory emf = Persistence.createEntityManagerFactory("diemPU");
    public EntityManager em;
    public DAO(){
        em = emf.createEntityManager();
    }
    
    public void closeEm(){
        em.close();
    }
}
