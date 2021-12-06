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
public class TransactionController {

    private TransactionsRepository transactionsRepository;

    private TransactiondetailsRepository transactiondetailsRepository;


    @Autowired
    public TransactionController(TransactionsRepository transactionsRepository,
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

    @GetMapping("/rest/lastten")
    public List<Transactions> getLastTenTransactions(){
        return this.transactionsRepository.findTransactionsLimitByTen();
    }

    @GetMapping("/rest/lasttenreal")
    public List<Transactions> getLastTenRealTransactions(){
        return this.transactionsRepository.findRealTransactionsLimitByTen();
    }

    @GetMapping("/rest/lasttensmartcontracts")
    public List<Transactions> getLastTenSmartContracts(){
        List<Transactions> lastTenSmartContracts =new ArrayList<>();

        List<Transactiondetails> contracts = this.transactiondetailsRepository.FindLastTenSmartContracts();

        for( Transactiondetails td : contracts){
            lastTenSmartContracts.add(this.transactionsRepository.findTransactionsByVersion(td.getVersion()));
        }

        return lastTenSmartContracts;
    }
    
    @GetMapping("/rest/lasttenBlock")
    public List<Transactions> getLastTenBlockMetaData(){
        return this.transactionsRepository.findBlockMetaDataLimitTen();
    }
    
        @GetMapping("/rest/AverageGasUnitPriceFromTo")
    public float getAverageGasUnitPrice(@RequestParam long versionFrom, long versionTo){
        return this.transactiondetailsRepository.getAverageGasUnitPriceFromTo(versionFrom, versionTo);
    }
    
    @GetMapping("/rest/AverageGasUnitPriceToday")
    public float getAverageGasUnitPrice(){
        Date date = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        String todaysDate = dateformat.format(date);

       return this.transactiondetailsRepository.getAverageGasUnitPriceFromTo(this.transactionsRepository.firstTransactionOfDay(todaysDate).getVersion(), this.transactionsRepository.getLastTransaction().getVersion()) ;
    }
    
        @GetMapping("/rest/firstTransactionOfDay")
    public Transactions getFirstTransactionOfDay(@RequestParam String date){
        return this.transactionsRepository.firstTransactionOfDay(date);
    }
    
    @GetMapping("/rest/lastTransactionOfDay")
    public Transactions getLastTransactionOfDay(@RequestParam String date){
        return this.transactionsRepository.lastTransactionOfDay(date);
    }
    
    @GetMapping("/rest/lastTransaction")
    public Transactions getLastTransaction(){
        return this.transactionsRepository.getLastTransaction();
    }


    @GetMapping("/rest/smallestversion")
    public Long getFirstTransactionOfDate(@RequestParam String starting){
        return this.transactionsRepository.findFirstTransactionOfDate(starting);
    }

    @GetMapping("/rest/alltransactionsinbetween")
    public List<Transactions> getAllTransactionsBetweenToDates(@RequestParam String starting, @RequestParam String ending){

        long minversion = this.transactionsRepository.findFirstTransactionOfDate(starting);
        long maxversion = this.transactionsRepository.findLastTransactionOfDate((ending));

        return this.transactionsRepository.findAllTransactionsBetweenTwoDates(minversion, maxversion);
    }
}

