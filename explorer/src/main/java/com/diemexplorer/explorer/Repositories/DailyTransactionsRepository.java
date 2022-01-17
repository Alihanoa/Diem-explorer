package com.diemexplorer.explorer.Repositories;

import com.diemexplorer.explorer.Entities.DailyTransactions;
import org.springframework.data.repository.CrudRepository;

public interface DailyTransactionsRepository extends CrudRepository<DailyTransactions, String> {
}