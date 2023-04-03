package com.snc.sncuserservice.mapper;

import com.snc.sncuserservice.models.Transaction;
import com.snc.sncuserservice.models.UserProfile;
import org.springframework.stereotype.Component;
import snc.sncmodels.services.betting.rq.BetRq;
import snc.sncmodels.services.betting.rq.CreateUserProfileRq;
import snc.sncmodels.services.betting.rq.TopUpRq;
import snc.sncmodels.services.betting.rs.BetRs;
import snc.sncmodels.services.betting.rs.CreateUserProfileRs;
import snc.sncmodels.services.betting.rs.TopUpRs;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

@Component
public class Mapper {

    public BetRs mapProfileAndTransactionToBetResponse(UserProfile profile, Transaction transaction) {
        return new BetRs()
                .setTransactionTime(transaction.getDateTime())
                .setUserId(transaction.getUserId())
                .setWinAmount(transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "0" : transaction.getAmount().toString())
                .setResultingBalance(profile.getBalance().toString());
    }
    public Transaction fromBetRqToTransaction(BetRq betRq) {
        return new Transaction()
                .setAmount(new BigDecimal(betRq.getAmount()))
                .setUserId(betRq.getUserId())
                .setDateTime(Instant.now(Clock.systemUTC()));
    }
    public UserProfile updateUserProfile(UserProfile profile, BigDecimal amount) {
        return profile
                .setBalance(profile.getBalance().add(amount));
    }

    public Transaction fromTopUpRqToTransaction(TopUpRq topUpRq) {
        return new Transaction()
                .setAmount(new BigDecimal(topUpRq.getTopUpAmount()))
                .setUserId(topUpRq.getUserId())
                .setDateTime(Instant.now(Clock.systemUTC()));
    }

    public TopUpRs mapProfileAndTransactionToToUpResponse(UserProfile userProfile, Transaction transaction) {
        return new TopUpRs()
                .setTransactionTime(transaction.getDateTime())
                .setUserId(transaction.getUserId())
                .setResultingBalance(userProfile.getBalance().toString());
    }

    public UserProfile mapNewProfile(CreateUserProfileRq rq) {
        return new UserProfile()
                .setBalance(BigDecimal.ZERO)
                .setNickName(rq.getNickName())
                .setLogin(rq.getLogin())
                .setEmail(rq.getEmail())
                .setDocId(rq.getDocId())
                .setPassWord(rq.getPassWord());
    }

    public CreateUserProfileRs mapProfileToCreateUserProfileRs(UserProfile profile) {
        return new CreateUserProfileRs()
                .setBalance(profile.getBalance().toString())
                .setUserId(profile.getUserId())
                .setNickName(profile.getNickName());
    }
}
