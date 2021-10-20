package com.diemexplorer.explorer.Controller;

import com.diemexplorer.explorer.Entities.Transactiondetails;
import com.diemexplorer.explorer.Repositories.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class DataController {

    private TransactiondetailsRepository transactiondetailsRepository;

    private TransactionBlockchaindetailsRepository transactionBlockchaindetailsRepository;

    private AccountRepository accountRepository;

    private AccountInformationRepository accountInformationRepository;

    private AccountBalanceXUSRepository accountBalanceXUSRepository;

    private AccountBalanceXDXRepository accountBalanceXDXRepository;


    public DataController(TransactiondetailsRepository transactiondetailsRepository,
                          TransactionBlockchaindetailsRepository transactionBlockchaindetailsRepository,
                          AccountRepository accountRepository,
                          AccountInformationRepository accountInformationRepository,
                          AccountBalanceXUSRepository accountBalanceXUSRepository,
                          AccountBalanceXDXRepository accountBalanceXDXRepository){


        this.transactiondetailsRepository=transactiondetailsRepository;
        this.transactionBlockchaindetailsRepository=transactionBlockchaindetailsRepository;
        this.accountRepository=accountRepository;
        this.accountInformationRepository=accountInformationRepository;
        this.accountBalanceXUSRepository=accountBalanceXUSRepository;
        this.accountBalanceXDXRepository=accountBalanceXDXRepository;
    }

    @GetMapping("/rest/transactions")
    public List<Transactiondetails> getTransactiondetails(){
        return this.transactiondetailsRepository.findAll();
    }
}
