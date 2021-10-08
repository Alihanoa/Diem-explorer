/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Repositories;

import com.diemexplorer.explorer.Entities.AccountBalanceXUS;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Msi
 */
@Repository
public interface AccountBalanceXUSRepository extends CrudRepository<AccountBalanceXUS, String>{
    
    List<AccountBalanceXUS> findAll();
    
    AccountBalanceXUS findAccountBalanceXUSByAdress(); 
}