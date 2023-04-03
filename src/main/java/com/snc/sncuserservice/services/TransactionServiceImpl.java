package com.snc.sncuserservice.services;

import com.snc.sncuserservice.mapper.Mapper;
import com.snc.sncuserservice.models.Transaction;
import com.snc.sncuserservice.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import snc.sncmodels.services.betting.rq.BetRq;
import snc.sncmodels.services.betting.rq.TopUpRq;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final Mapper mapper;
    @Override
    public Mono<Transaction> saveTransaction(BetRq betRq) {
        return transactionRepository.save(mapper.fromBetRqToTransaction(betRq));
    }

    @Override
    public Mono<Transaction> saveTransaction(TopUpRq topUpRq) {
        return transactionRepository.save(mapper.fromTopUpRqToTransaction(topUpRq));
    }
}
