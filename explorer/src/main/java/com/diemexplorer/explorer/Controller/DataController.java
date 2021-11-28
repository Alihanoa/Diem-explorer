package com.diemexplorer.explorer.Controller;

import com.diemexplorer.explorer.Entities.Transactiondetails;
import com.diemexplorer.explorer.Entities.Transactions;
import com.diemexplorer.explorer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class DataController {

    private TransactionsRepository transactionsRepository;

    private TransactiondetailsRepository transactiondetailsRepository;


    @Autowired
    public DataController(TransactionsRepository transactionsRepository,
                          TransactiondetailsRepository transactiondetailsRepository){

        this.transactionsRepository=transactionsRepository;
        this.transactiondetailsRepository=transactiondetailsRepository;
    }


    @GetMapping("/rest/transactions")
    public List<Transactions> getTransactions(){
        return this.transactionsRepository.findAll();
    }

    @GetMapping("/rest/transaction")
    public List<Object> getTransactionInformation(@RequestParam long version){
        List<Object> allTransactionInformation = new ArrayList<Object>();
        allTransactionInformation.add(this.transactionsRepository.findTransactionsByVersion(version));
        allTransactionInformation.add(this.transactiondetailsRepository.findTransactiondetailsByVersion(version));
        return allTransactionInformation;
    }

    @GetMapping("/rest/transactionstodate")
    public int getNumberOfTransactionsToday(@RequestParam String date){
        return this.transactionsRepository.transactionsToday(date);
    }
    @GetMapping("/rest/transactionstoday")
    public int getNumberOfTransactionsToday(){
        Date date = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        String todaysDate = dateformat.format(date);

        return this.transactionsRepository.transactionsToday(todaysDate);
    }


    @GetMapping("/rest/transactionswithcertainamount")
    public List<Transactions> getTransactionsWithEqualOrGreaterAmount(@RequestParam String amount){
        return this.transactionsRepository.findTransactionsWithAmountGreaterOrEqualToParam(Double.parseDouble(amount));
    }

    @GetMapping("/rest/tradingvolume")
    public double getTradingVolumeOfDate(@RequestParam String date){
        return this.transactionsRepository.getTradingVolumeToday((date));
    }
}
