/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Repositories;

import com.diemexplorer.explorer.Entities.Transactiondetails;
import java.util.List;

import com.diemexplorer.explorer.Entities.Transactions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Msi
 */
@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, Long>{
    
    List<Transactions> findAll();
    
    Transactions findTransactionsByVersion(long version);


    @Query("SELECT count(t) FROM Transactions t WHERE t.date like %:date%")
    int transactionsToday(String date);

    @Query("SELECT t from Transactions t WHERE t.amount >=:parameter")
    List<Transactions>  findTransactionsWithAmountGreaterOrEqualToParam(double parameter);

    @Query("SELECT SUM(t.amount) FROM Transactions t WHERE t.date LIKE %:date%")
    double getTradingVolumeToday(String date);

    @Query(value = "SELECT * from Transactions t ORDER BY t.version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> findTransactionsLimitByTen();

    @Query(value = "SELECT * FROM Transactions t WHERE t.type='user' ORDER BY t.version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> findRealTransactionsLimitByTen();

}
