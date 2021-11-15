/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.Repositories;

import java.util.List;

import com.diemexplorer.explorer.Entities.Accountdetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Msi
 */
@Repository
public interface AccountdetailsRepository extends CrudRepository<Accountdetails, String>{
    
    List<Accountdetails> findAll();
    
    Accountdetails findAccountInformationByAddress(String address);

}
