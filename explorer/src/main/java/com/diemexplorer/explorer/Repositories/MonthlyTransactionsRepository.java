package com.diemexplorer.explorer.Repositories;

import com.diemexplorer.explorer.Entities.MonthlyTransactions;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyTransactionsRepository extends CrudRepository<MonthlyTransactions, String> {
}