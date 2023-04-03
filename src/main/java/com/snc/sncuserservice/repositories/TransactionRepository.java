package com.snc.sncuserservice.repositories;

import com.snc.sncuserservice.models.Transaction;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface TransactionRepository extends ReactiveSortingRepository<Transaction, String> {
}
