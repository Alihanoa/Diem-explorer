/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.diem;

import Entitäten.DBAccount;
import java.util.Collection;
import javax.persistence.Query;

/**
 *
 * @author Msi
 */
public class dbtests {
    public static void main(String[] args){
        DAO dao = new DAO();
        DBAccount dbaa = new DBAccount();
        dao.em.getTransaction().begin();
        String adress = "bf2d55d9a15983e44f18a799a0bebca5";
        Collection<DBAccount> dba  = dao.em.createQuery("SELECT a FROM DBAccount a").getResultList();
//        dba = (DBAccount) query.
        for( DBAccount account : dba){
            System.out.println(account.getAccountId());
            System.out.println(account.getAdress());
            System.out.println(account.getAuthenticationKey() + "\n");

        }
        
        
    }
}
