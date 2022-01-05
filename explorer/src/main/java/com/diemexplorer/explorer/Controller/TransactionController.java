package com.diemexplorer.explorer.Controller;

import com.diemexplorer.explorer.Entities.Transactiondetails;
import com.diemexplorer.explorer.Entities.Transactions;
import com.diemexplorer.explorer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
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

 @GetMapping("/rest/datalast365days")
    public String[][] statisticForLast365Days(){

        Date date = new Date();

        Long timestampnow = date.getTime();
        Long timestampOneYearBack = timestampnow - 31536000000L;
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        String datum = dateformat.format(timestampOneYearBack);

        long mintimestamp = timestampOneYearBack;
        long maxtimestamp= timestampOneYearBack +43200000;
        String[][] alldates = new String[732][2];
        int counter = 0;
        while(timestampnow> maxtimestamp && counter < 732){
            date.setTime(mintimestamp);
            // alldates[counter][0] = date.toString();
            alldates[counter][0] = dateformat.format(date);



            date.setTime(timestampOneYearBack);
            datum = dateformat.format(date);


            long numberoftransactions = this.transactionsRepository.getNumberOfTransactionsBetweenTwoVersions(mintimestamp, maxtimestamp);

            // alldates[counter][1] = String.valueOf(numberoftransactions) +  " " + String.valueOf(mintimestamp) + " " + String.valueOf(maxtimestamp);
            alldates[counter][1] = String.valueOf(numberoftransactions);
            mintimestamp = mintimestamp + 43200000;
            maxtimestamp = maxtimestamp + 43200000;
            counter ++;

        }
        return alldates;
    }

@GetMapping("/rest/datalastMonth")
    public String[][] statisticForLastMonth(){

        Date date = new Date();

        Long timestampnow = date.getTime();
        Long timestampOneYearBack = timestampnow - 2629800000L;
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        String datum = dateformat.format(timestampOneYearBack);

        long mintimestamp = timestampOneYearBack;
        long maxtimestamp= timestampOneYearBack +3600000;
        String[][] alldates = new String[722][2];
        int counter = 0;
        while(timestampnow> maxtimestamp && counter < 722){
            date.setTime(mintimestamp);
            // alldates[counter][0] = date.toString();
            alldates[counter][0] = dateformat.format(date);




            date.setTime(timestampOneYearBack);
            datum = dateformat.format(date);


            long numberoftransactions = this.transactionsRepository.getNumberOfTransactionsBetweenTwoVersions(mintimestamp, maxtimestamp);

            // alldates[counter][1] = String.valueOf(numberoftransactions) +  " " + String.valueOf(mintimestamp) + " " + String.valueOf(maxtimestamp);
            alldates[counter][1] = String.valueOf(numberoftransactions);
            mintimestamp = mintimestamp + 3600000;
            maxtimestamp = maxtimestamp + 3600000;
            counter ++;

        }
        return alldates;
    }
    
@GetMapping("/rest/datalastWeek")
    public String[][] statisticForLastWeek(){

        Date date = new Date();

        Long timestampnow = date.getTime();
        Long timestampOneYearBack = timestampnow - 604800017;
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        String datum = dateformat.format(timestampOneYearBack);

        long mintimestamp = timestampOneYearBack;
        long maxtimestamp= timestampOneYearBack + 900000;
        String[][] alldates = new String[674][2];
        int counter = 0;
        while(timestampnow> maxtimestamp && counter < 674){
            date.setTime(mintimestamp);
//            alldates[counter][0] = date.toString();
            alldates[counter][0] = dateformat.format(date);



            date.setTime(timestampOneYearBack);
            datum = dateformat.format(date);


            long numberoftransactions = this.transactionsRepository.getNumberOfTransactionsBetweenTwoVersions(mintimestamp, maxtimestamp);

            // alldates[counter][1] = String.valueOf(numberoftransactions) +  " " + String.valueOf(mintimestamp) + " " + String.valueOf(maxtimestamp);
            alldates[counter][1] = String.valueOf(numberoftransactions);
            mintimestamp = mintimestamp + 900000;
            maxtimestamp = maxtimestamp + 900000;
            counter ++;

        }
        return alldates;
    }
    
    @GetMapping("/rest/datalastDay")
    public String[][] statisticForLastDay(){

        Date date = new Date();

        Long timestampnow = date.getTime();
        Long timestampOneYearBack = timestampnow - 86400000;
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        String datum = dateformat.format(timestampOneYearBack);

        long mintimestamp = timestampOneYearBack;
        long maxtimestamp= timestampOneYearBack + 120000;
        String[][] alldates = new String[674][2];
        int counter = 0;
        while(timestampnow> maxtimestamp && counter < 674){
            date.setTime(mintimestamp);
            alldates[counter][0] = date.toString();




            date.setTime(timestampOneYearBack);
            datum = dateformat.format(date);


            long numberoftransactions = this.transactionsRepository.getNumberOfTransactionsBetweenTwoVersions(mintimestamp, maxtimestamp);

            // alldates[counter][1] = String.valueOf(numberoftransactions) +  " " + String.valueOf(mintimestamp) + " " + String.valueOf(maxtimestamp);
            alldates[counter][1] = String.valueOf(numberoftransactions);
            mintimestamp = mintimestamp + 120000;
            maxtimestamp = maxtimestamp + 120000;
            counter ++;

        }
        return alldates;
    }
    
        @GetMapping("/rest/datalastHour")
    public String[][] statisticForLastHour(){

        Date date = new Date();

        Long timestampnow = date.getTime();
        Long timestampOneYearBack = timestampnow - 3600000;
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


        String datum = dateformat.format(timestampOneYearBack);

        long mintimestamp = timestampOneYearBack;
        long maxtimestamp= timestampOneYearBack + 5000;
        String[][] alldates = new String[674][2];
        int counter = 0;
        while(timestampnow> maxtimestamp && counter < 674){
            date.setTime(mintimestamp);
            alldates[counter][0] = date.toString();




            date.setTime(timestampOneYearBack);
            datum = dateformat.format(date);


            long numberoftransactions = this.transactionsRepository.getNumberOfTransactionsBetweenTwoVersions(mintimestamp, maxtimestamp);

            // alldates[counter][1] = String.valueOf(numberoftransactions) +  " " + String.valueOf(mintimestamp) + " " + String.valueOf(maxtimestamp);
            alldates[counter][1] = String.valueOf(numberoftransactions);
            mintimestamp = mintimestamp + 5000;
            maxtimestamp = maxtimestamp + 5000;
            counter ++;

        }
        return alldates;
    }

    @GetMapping("/rest/getnextten")
    public List<Transactions> getNextTenTransactions(@RequestParam String lastVersionNumber){
        return this.transactionsRepository.getNextTen(Long.parseLong(lastVersionNumber));
    }

    @GetMapping("/rest/getlast50")
    public List<Transactions> getLast50Transactions(){
        return this.transactionsRepository.getLastFiftyTransactions();
    }


    @GetMapping("/rest/doughnutchart")
    public int[] getDataForDoughnutChart(){
        /* In order : Blockmetadata, Transactions, Smart Contracts */
        int[] data = new int[]{this.transactionsRepository.getNumberOfBlockmetadata(),
                               this.transactionsRepository.getNumberOfRealTransactions(),
                               this.transactionsRepository.getNumberOfSmartContracts()};

        return data;
    }

    @GetMapping("/rest/lasttentransactionsofaccount")
    public List<Transactions> getLastTenTransactionsOfAccount(@RequestParam String address){
        List<Transactions> result = new ArrayList<>();
        List<Transactions> sender = this.transactionsRepository.getlasttentransactionsbyAsSender(address);
        List<Transactions> receiver = this.transactionsRepository.getLastTenTransactionsAsReceiver(address);

        int senderCounter =0;
        int receiverCounter =0;
        for ( int i=0; i<10;i++){

            if(sender.get(senderCounter).getVersion() > receiver.get(receiverCounter).getVersion()){
                result.add(sender.get(senderCounter));
                senderCounter ++;
            }
            else{
                result.add(receiver.get(receiverCounter));
                receiverCounter ++;
            }

        }
        return result;
    }
}

