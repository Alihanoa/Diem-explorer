package com.diemexplorer.explorer.Controller;

import com.diemexplorer.explorer.Entities.AccountBalanceXUS;
import com.diemexplorer.explorer.Entities.AccountInformation;
import com.diemexplorer.explorer.Repositories.AccountBalanceXDXRepository;
import com.diemexplorer.explorer.Repositories.AccountBalanceXUSRepository;
import com.diemexplorer.explorer.Repositories.AccountInformationRepository;
import com.diemexplorer.explorer.Repositories.AccountRepository;
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



    private AccountInformationRepository accountInformationRepository;
    private AccountRepository accountRepository;
    private AccountBalanceXDXRepository accountBalanceXDXRepository;
    private AccountBalanceXUSRepository accountBalanceXUSRepository;

    public AccountController(){

    }
    @Autowired
    public AccountController(AccountBalanceXDXRepository accountBalanceXDXRepository,
                                 AccountBalanceXUSRepository accountBalanceXUSRepository,
                                 AccountRepository accountRepository,
                                 AccountInformationRepository accountInformationRepository) {

            this.accountBalanceXDXRepository=accountBalanceXDXRepository;
            this.accountRepository= accountRepository;
            this.accountInformationRepository= accountInformationRepository;
            this.accountBalanceXUSRepository=accountBalanceXUSRepository;

    }

    @GetMapping("/rest/accounts")
    public List<Account> getAccounts(){
            return this.accountRepository.findAll();
    }

    @GetMapping ("/rest/account")
    public List<Object> getAccountInformation(@RequestParam String address){
            List<Object> allAccountInfos = new ArrayList<Object>();
                    allAccountInfos.add(this.accountRepository.findAccountByAddress(address));
                    allAccountInfos.add(this.accountInformationRepository.findAccountInformationByAddress(address));
                    allAccountInfos.add(this.accountBalanceXUSRepository.findAccountBalanceXUSByAddress((address)));
            return allAccountInfos;
    }


}
