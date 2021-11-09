/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer;

import com.diemexplorer.explorer.Entities.Account;
import com.diemexplorer.explorer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Msi
 */
@Controller
@CrossOrigin
public class MainController {


    private TransactiondetailsRepository transactiondetailsrepository;

    private AccountRepository accountRepository;

    private AccountInformationRepository accountInformationRepository;

    private AccountBalanceXUSRepository accountBalanceXUSRepository;

    private AccountBalanceXDXRepository accountBalanceXDXRepository;
    
    public MainController(TransactiondetailsRepository transactiondetailsrepository,
                          AccountRepository accountRepository,
                          AccountInformationRepository accountInformationRepository,
                          AccountBalanceXUSRepository accountBalanceXUSRepository,
                          AccountBalanceXDXRepository accountBalanceXDXRepository){
        
        this.accountRepository = accountRepository;
        this.accountInformationRepository = accountInformationRepository;
        this.transactiondetailsrepository= transactiondetailsrepository;
        this.accountBalanceXDXRepository= accountBalanceXDXRepository;
        this.accountBalanceXUSRepository = accountBalanceXUSRepository;

    }
    
    @GetMapping("/transactions")
    public String getTransactiondetails(Model model){
         model.addAttribute("daten",transactiondetailsrepository.findAll());
        System.out.println(model.getAttribute("daten"));
         
         return "index";
    }
    
    @GetMapping()
    public String getAccount(Model model){
        return "";
    }
    
    
}
