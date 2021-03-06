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
    
    @Query(value = "SELECT * FROM Transactions t WHERE t.type='blockmetadata' ORDER BY t.version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> findBlockMetaDataLimitTen();
    
    @Query(value = "SELECT * FROM Transactions t WHERE t.date like '%:date%' ORDER BY t.version Asc LIMIT 1", nativeQuery = true)
    Transactions firstTransactionOfDay(String date);
    
    @Query(value ="SELECT * FROM Transactions t WHERE t.date like '%:date%' ORDER BY t.version Desc LIMIT 1", nativeQuery=true)
    Transactions lastTransactionOfDay(String date);
    
    @Query(value="SELECT * From Transactions t ORDER BY t.version Desc LIMIT 1", nativeQuery=true)
    Transactions getLastTransaction();

    @Query(value = "SELECT MIN(t.version) FROM Transactions t WHERE t.date LIKE %:starting% AND t.type='user'", nativeQuery = false)
    Long findFirstTransactionOfDate(String starting);

    @Query("SELECT MAX(t.version) FROM Transactions t WHERE t.date LIKE %:ending% and t.type='user'")
    Long findLastTransactionOfDate(String ending);

    @Query("SELECT t FROM Transactions t WHERE t.version<=:max AND t.version >=:min AND t.type='user'")
    List<Transactions> findAllTransactionsBetweenTwoDates(long min, long max);

    @Query("SELECT count(t) FROM Transactions t WHERE t.version<=:max AND t.version >=:min AND t.type='user'")
    int countAllTransactionsBetweenTwoDates(long min, long max);

    @Query(value = "SELECT version FROM Transactions t WHERE t.date like ':date%' ORDER BY t.version Asc LIMIT 1", nativeQuery = true)
    long firstshit(String date);

   /* @Query("SELECT MIN(t.version) FROM Transactions t WHERE t.timestamp >= :timestampneeded AND t.type='user'")
    Long getFirstVersionOfUnixTimestamp(long timestampneeded);*/

    @Query("SELECT count(t.version) FROM Transactions t WHERE t.timestamp >= :mintimestamp AND t.timestamp <= :maxtimestamp AND t.type='user'")
    int getNumberOfTransactionsBetweenTwoVersions(long mintimestamp, long maxtimestamp);

    @Query("SELECT t FROM Transactions t WHERE t.version < :eingabe AND t.version >= :eingabe-10")
    List<Transactions> getNextTen(long eingabe);

    @Query(value="SELECT * From Transactions t ORDER BY t.version Desc LIMIT 50", nativeQuery=true)
    List<Transactions> getLastFiftyTransactions();

    @Query("SELECT count(t) FROM Transactions t WHERE t.type='blockmetadata'")
    int getNumberOfBlockmetadata();

    @Query("SELECT COUNT(t) FROM Transactions t WHERE t.type='user' AND t.amount>0")
    int getNumberOfRealTransactions();

    @Query ("SELECT COUNT(t) FROM Transactions t WHERE t.type='user' AND t.amount=0")
    int getNumberOfSmartContracts();

    @Query(value = "SELECT * from Transactions  WHERE sender_id=:address ORDER BY version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> getlasttentransactionsbyAsSender(String address);

    @Query(value = "SELECT * FROM Transactions WHERE receiver_id=:address ORDER BY version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> getLastTenTransactionsAsReceiver(String address);

    @Query(value ="SELECT * FROM Transactions WHERE sender_id=:address AND version<:version ORDER BY version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> getNextTenTransactionsAsSender(String address, long version);

    @Query(value ="SELECT * FROM Transactions WHERE receiver_id=:address AND version<:version ORDER BY version Desc LIMIT 10", nativeQuery = true)
    List<Transactions> getNextTenTransactionsAsReceiver(String address, long version);

    @Query("SELECT SUM(t.amount) FROM Transactions t WHERE t.timestamp >= :mintimestamp AND t.timestamp <= :maxtimestamp AND t.type='user' AND t.currency='XUS'")
    Long getHandelsVolBetweenTwoTimeStampsXUS(long mintimestamp, long maxtimestamp);

    @Query("SELECT SUM(t.amount) FROM Transactions t WHERE t.timestamp >= :mintimestamp AND t.timestamp <= :maxtimestamp AND t.type='user' AND t.currency='XDX'")
    long getHandelsVolBetweenTwoTimeStampsXDX(long mintimestamp, long maxtimestamp);

    @Query("SELECT t FROM Transactions t WHERE t.type='user' AND t.version > :eingabe AND t.version <= :eingabe+30")
    List<Transactions> getNext30(long eingabe);

    @Query(value = "SELECT t FROM Transactions t WHERE t.type='blockmetadata' AND t.version > :eingabe AND t.version <= :eingabe+30")
    List<Transactions> findBlockMetaDataLimit30(long eingabe);


}

//@Query (value="SELECT * FROM Transactions WHERE t.version in (SELECT version from transactiondetails td where td.type='blockmetadata' ORDER BY td.version DESC LIMIT 10)", nativeQuery= true)
    //List<Transactions> getBlockMetaDataLast10();
    


