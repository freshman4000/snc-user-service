package com.snc.sncuserservice.services;

import com.snc.sncuserservice.models.Transaction;
import reactor.core.publisher.Mono;
import snc.sncmodels.services.betting.rq.BetRq;
import snc.sncmodels.services.betting.rq.TopUpRq;

public interface TransactionService {
    Mono<Transaction> saveTransaction(BetRq betRq);
    Mono<Transaction> saveTransaction(TopUpRq topUpRq);
}
