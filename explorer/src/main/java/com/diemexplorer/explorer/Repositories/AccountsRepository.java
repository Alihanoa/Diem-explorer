/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Repositories;

import java.util.List;

import com.diemexplorer.explorer.Entities.Accounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Msi
 */
@Repository
public interface AccountsRepository extends CrudRepository<Accounts, String>{
    
    List<Accounts> findAll();
    

    Accounts findAccountsByAddress(String address);
}
