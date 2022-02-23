package com.diemexplorer.explorer;

import com.diem.DiemException;
import com.diemexplorer.explorer.Entities.DailyTransactions;
import org.hibernate.tool.schema.ast.SqlScriptLexer;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PerformanceOptimizer extends Thread{

    private long currentVersion = 1;
    Connection con;
    public PerformanceOptimizer()throws SQLException {
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");;

    }

    @Override
    public void run(){
        while (!interrupted()) {

            try {
                this.ScanDatabaseToCurrentDate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }



    }

    public void ScanDatabaseToCurrentDate() throws SQLException{



        LocalDateTime dateToday = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dateToday.toLocalDate().atStartOfDay());
        long timestampinmillis = timestamp.getTime(); // Bis zu dieser Zeit soll gearbeitet werden

        currentVersion = getLastVersionOfAnalyzedTransaction();


//        System.out.println(timestampOfCurrentVersion);
        long firstDayBeginning = 0;
        long firstDayEnding = 0;

        Date date = null;


        while(timestampinmillis >  firstDayBeginning){

            String query = "SELECT * FROM transactions WHERE version="+currentVersion;
            PreparedStatement stmnt = this.con.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            long timestampOfCurrentVersion =0;
            if(rs.next()){
                timestampOfCurrentVersion = rs.getLong("timestamp");
            }
            else{
                // Wenn er nichts findet, was dann?


            }
             date = new Date(timestampOfCurrentVersion);
//        System.out.println(date);

            Timestamp yeet = Timestamp.valueOf(date.toLocalDate().atStartOfDay());
//        System.out.println("date.toLocalDate().atStartOfDay() : " + date.toLocalDate().atStartOfDay().getDayOfMonth());
//        System.out.println("date.toLocalDate().atStartOfDay() : " + date.toLocalDate().atStartOfDay().getMonth().getValue());
//        System.out.println("date.toLocalDate().atStartOfDay() : " + date.toLocalDate().atStartOfDay().getYear());
            // Das ist der Timestamp vom ersten Tag, in dem die erste Transaktion stattgefunden hat.

            if(firstDayBeginning ==0){
                firstDayBeginning = yeet.getTime();
            }

            firstDayEnding = firstDayBeginning + 86400000;

            DailyTransactions daily = new DailyTransactions(Long.parseLong(String.valueOf(date.toLocalDate().atStartOfDay().getDayOfMonth()) + String.valueOf(date.toLocalDate().atStartOfDay().getMonth().getValue()) + String.valueOf(date.toLocalDate().atStartOfDay().getYear())));
            System.out.println(daily.getDate());
            daily.setDay(date.toLocalDate().atStartOfDay().getDayOfMonth());
            daily.setMonth(date.toLocalDate().getMonthValue());
            daily.setYear(date.toLocalDate().getYear());
            long[] transactionsAndSmartContracts = countTransactionsDuringGivenDay(firstDayBeginning, firstDayEnding);
            daily.setAmountTransactions(transactionsAndSmartContracts[0]);
            daily.setAmountSmartContracts(transactionsAndSmartContracts[1]);
            daily.setFirsTransactionVersion(transactionsAndSmartContracts[2]);
            daily.setLastTransactionVersion(transactionsAndSmartContracts[3]);
            daily.setFirstTimestamp(transactionsAndSmartContracts[4]);
            daily.setLastTimestamp(transactionsAndSmartContracts[5]);
            daily.setTransactionVolume(transactionsAndSmartContracts[6]);

            System.out.println("erster Tag anfang : " + firstDayBeginning);
            System.out.println("erster Tag zu ende : " + firstDayEnding);

            insertDailyTransaction(daily);

            firstDayBeginning = firstDayEnding +1;

            currentVersion = transactionsAndSmartContracts[3] + 1;

        }



    }

    public long getMaxVersionInDaily() throws SQLException{


        String query = " SELECT last_transaction_version FROM dailytransactions ORDER BY last_transaction_version  DESC LIMIT 1";
        PreparedStatement stmnt = con.prepareStatement(query);
        ResultSet rs = stmnt.executeQuery(query);

        stmnt.close();
        rs.close();

        return rs.findColumn("last_transaction_version");
    }

    public void getDayContextOfTransaction() throws SQLException{

        long lastTransactionVersion = getMaxVersionInDaily();
    }

    public long getLastVersionOfAnalyzedTransaction()throws SQLException{

        String query = "SELECT max(last_transaction_version) FROM dailytransactions";
        PreparedStatement stmnt = this.con.prepareStatement(query);


        ResultSet rs = stmnt.executeQuery();
        boolean rsvalue = rs.next();
        System.out.println(rsvalue);
        if(rs.next()){

            stmnt.close();
            rs.close();
            return rs.getLong("last_transaction_version");
        }
        else{
            stmnt.close();
            rs.close();
            return 1;
        }
    }

    public void insertDailyTransaction(DailyTransactions transaction) throws SQLException{
        String insert = "INSERT INTO dailytransactions (date," +
                "                                        day," +
                "                                        month," +
                "                                        year," +
                "                                        amount_transactions," +
                "                                        amount_smart_contracts," +
                "                                        first_transaction_version," +
                "                                        last_transaction_version," +
                "                                        first_timestamp," +
                "                                        last_timestamp," +
                "                                        transaction_volume)" +
                "        VALUES(" + transaction.getDate() + ","
                                  + transaction.getDay() + ","
                                  + transaction.getMonth() + ","
                                  + transaction.getYear() + ","
                                  + transaction.getAmountTransactions() + ","
                                  + transaction.getAmountSmartContracts() + ","
                                  + transaction.getFirsTransactionVersion() + ","
                                  + transaction.getLastTransactionVersion() + ","
                                  + transaction.getFirstTimestamp() + ","
                                  + transaction.getLastTimestamp() + ","
                                  + transaction.getTransactionVolume() + ")";
        PreparedStatement stmnt  = this.con.prepareStatement(insert);
        stmnt.executeUpdate();

        stmnt.close();

    }

    public long[] countTransactionsDuringGivenDay(long dayTimestampBegin, long dayTimestampEnd) throws SQLException{
        long transactionsCounter = 0;
        long smartcontractCounter = 0;
        long firstTransactionVersion = 0;
        long lastTransactionVersion =0;
        long firstTimestamp = 0;
        long lastTimestamp =0;
        long transactionVolume =0;
        long[] data = new long[7];
        boolean transactionInTimestamp = true;

        String query;
        long timestampOfLoadedTransaction =0;
        String typeOfTransaction;
        String amount;

        while(transactionInTimestamp){
             query = "SELECT * FROM transactions t WHERE t.version="+ currentVersion;

             PreparedStatement stmnt = con.prepareStatement(query);

            ResultSet rs = stmnt.executeQuery();
            rs.next();
             timestampOfLoadedTransaction = rs.getLong("timestamp");
             typeOfTransaction = rs.getString("type");
            System.out.println(currentVersion);
//            System.out.println(timestampOfLoadedTransaction);
            // Wenn Transaction blockmetadata ist, mach mit dem nächsten DB Eintrag weiter
            if(!typeOfTransaction.equals("user")){

                currentVersion = currentVersion +1;
                lastTransactionVersion = rs.getLong("version");
                lastTimestamp = Long.parseLong(rs.getString("timestamp"));
                rs.close();
                stmnt.close();

                continue;
            }

            // Wenn Timestamp zwischen gegebenen Tag ist, wird es gezählt.
            if(timestampOfLoadedTransaction >= dayTimestampBegin && timestampOfLoadedTransaction <= dayTimestampEnd){
                if(rs.getString("currency").equals("")){
                    // Wenn Currency leer ist, ist das ein Smart Contract
                    currentVersion = currentVersion +1;
                    smartcontractCounter = smartcontractCounter +1;
                    lastTransactionVersion = rs.getLong("version");
                    lastTimestamp = Long.parseLong(rs.getString("timestamp"));
                }
                else{
                    // Wenn Currency nicht leer ist, ist das eine Währungsüberweisung

                    /* Wenn transactionCounter und smartContractCounter = 0 sind, heißt das, dass die erste Transaktion des Tages nicht
                     * gesetzt wird und dass die nächste echte Transaktion die erste Transaktion des Tages ist
                     */
                    if(transactionsCounter==0 && smartcontractCounter==0){
                       firstTransactionVersion =  rs.getLong("version");
                       firstTimestamp = Long.parseLong(rs.getString("timestamp"));

                    }
                    currentVersion = currentVersion +1;
                    transactionsCounter = transactionsCounter +1;
                     amount = rs.getString("amount");
                    transactionVolume = transactionVolume + ((long) Double.parseDouble(amount));

                    lastTransactionVersion = rs.getLong("version");
                    lastTimestamp = Long.parseLong(rs.getString("timestamp"));
                }
            }
            else{

                // Wenn Transaction nicht in gegebenen timestamp ist, ist das ein neuer Tag
                transactionInTimestamp = false;
                lastTransactionVersion = rs.getLong("version");
                lastTimestamp = Long.parseLong(rs.getString("timestamp"));
                rs.close();
                stmnt.close();
                continue;
            }
            rs.close();
            stmnt.close();
        }

        // Die gezählten Daten werden hier als Array zurückgegeben
        data[0] = transactionsCounter;
        data[1] = smartcontractCounter;
        data[2] = firstTransactionVersion;
        data[3] = lastTransactionVersion;
        data[4] = firstTimestamp;
        data[5] = lastTimestamp;
        data[6] = transactionVolume;


//        System.out.println(transactionsCounter);
//        System.out.println(smartcontractCounter);
//        System.out.println(firstTransactionVersion);
//        System.out.println(lastTransactionVersion);
//        System.out.println(firstTimestamp);
//        System.out.println(lastTimestamp);
//        System.out.println(transactionVolume);
        System.out.println(currentVersion);
        return data;
    }
}