package com.diemexplorer.explorer.Controller;

import com.diemexplorer.explorer.Repositories.*;
import com.diemexplorer.explorer.Repositories.AccountsRepository;
import com.diemexplorer.explorer.Repositories.AccountdetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.diemexplorer.explorer.Entities.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
public class AccountController {



    private AccountdetailsRepository accountdetailsRepository;
    private AccountsRepository accountsRepository;
    private AccountBalanceXDXRepository accountBalanceXDXRepository;
    private AccountBalanceXUSRepository accountBalanceXUSRepository;

    public AccountController(){

    }

    @Autowired
    public AccountController(AccountBalanceXDXRepository accountBalanceXDXRepository,
                                 AccountBalanceXUSRepository accountBalanceXUSRepository,
                                 AccountsRepository accountsRepository,
                                 AccountdetailsRepository accountdetailsRepository) {

            this.accountBalanceXDXRepository=accountBalanceXDXRepository;
            this.accountsRepository= accountsRepository;
            this.accountdetailsRepository= accountdetailsRepository;
            this.accountBalanceXUSRepository=accountBalanceXUSRepository;

    }

    @GetMapping("/rest/accounts")
    public List<Accounts> getAccounts(){
            return this.accountsRepository.findAll();
    }

    @GetMapping ("/rest/account")
    public List<Object> getAccountdetails(@RequestParam String address){
            List<Object> allAccountInfos = new ArrayList<Object>();
                    allAccountInfos.add(this.accountsRepository.findAccountsByAddress(address));
                    allAccountInfos.add(this.accountdetailsRepository.findAccountdetailsByAddress(address));
                    allAccountInfos.add(this.accountBalanceXUSRepository.findAccountBalanceXUSByAddress((address)));
            return allAccountInfos;
    }

    @GetMapping("/rest/accountdetails")
    public List<Accountdetails> getAllAccountdetails(){
        return this.accountdetailsRepository.findAll();
    }


}
