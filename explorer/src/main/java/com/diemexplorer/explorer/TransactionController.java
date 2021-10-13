/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer;

import com.diemexplorer.explorer.Repositories.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Msi
 */
@Controller
public class TransactionController {
    
    private TransactionRepository transactionrepository;
    
    public TransactionController( TransactionRepository tr){
        this.transactionrepository=tr;
    }
    
    @GetMapping
    public String getTrasactions(Model model){
         model.addAttribute("daten",transactionrepository.findAll());
         
         return "index";
    }
    
    
}
