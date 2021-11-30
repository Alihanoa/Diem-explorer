/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Repositories;

import com.diemexplorer.explorer.Entities.Transactiondetails;
import java.util.List;

import com.diemexplorer.explorer.Entities.Transactiondetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Msi
 */
@Repository
public interface TransactiondetailsRepository extends CrudRepository<Transactiondetails, Long>{
    
    List<Transactiondetails> findAll();
    
    List<Transactiondetails>findTransactiondetailsByVersion(long version);

    @Query(value = "SELECT * FROM Transactiondetails td WHERE td.type='move_abort' ORDER BY td.version Desc LIMIT 10", nativeQuery = true)
    List<Transactiondetails> FindLastTenSmartContracts();

}
