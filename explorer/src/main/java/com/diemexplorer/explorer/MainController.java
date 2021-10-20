/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer;

import com.diemexplorer.explorer.Entities.Account;
import com.diemexplorer.explorer.Repositories.AccountRepository;
import com.diemexplorer.explorer.Repositories.TransactiondetailsRepository;
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
public class AccountController {
    @Autowired
    private TransactiondetailsRepository transactiondetailsrepository;
    @Autowired
    private AccountRepository accountRepository;
    
    public AccountController( TransactiondetailsRepository tr,
                                  AccountRepository accountRepository){
        this.transactiondetailsrepository=tr;
        this.accountRepository=accountRepository;
    }
    
    @GetMapping
    public String getTransactiondetails(Model model){
         model.addAttribute("daten",transactiondetailsrepository.findAll());
         
         return "index";
    }
    
    @GetMapping()
    public String getAccount(Model model){
        return "index";
    }
    
    
}
