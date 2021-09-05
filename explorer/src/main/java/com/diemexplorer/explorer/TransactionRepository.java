/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Msi
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    
    
    
    ArrayList<Transaction> findAll();
    
}
