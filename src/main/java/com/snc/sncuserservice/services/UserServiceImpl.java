package com.snc.sncuserservice.services;

import com.snc.sncuserservice.exceptions.InsufficientBalanceException;
import com.snc.sncuserservice.exceptions.NickNameAlreadyExistsException;
import com.snc.sncuserservice.exceptions.UserNotFoundException;
import com.snc.sncuserservice.mapper.Mapper;
import com.snc.sncuserservice.models.UserProfile;
import com.snc.sncuserservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import snc.sncmodels.services.betting.rq.BetRq;
import snc.sncmodels.services.betting.rq.CreateUserProfileRq;
import snc.sncmodels.services.betting.rq.TopUpRq;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public Mono<UserProfile> applyChangesToBalance(BetRq rq) {
        return userRepository.findUserProfileByUserId(rq.getUserId())
                .map(profile -> {
                    if (Objects.isNull(profile)) {
                        throw new UserNotFoundException();
                    }
                    BigDecimal projectedBalance = profile.getBalance().add(new BigDecimal(rq.getAmount()));
                    if (projectedBalance.doubleValue() < 0) throw new InsufficientBalanceException();
                    return profile;
                })
                .zipWhen(profile -> userRepository.save(mapper.updateUserProfile(profile, new BigDecimal(rq.getAmount()))))
                .map(Tuple2::getT2);
    }

    @Override
    public Mono<UserProfile> applyChangesToBalance(TopUpRq rq) {
        return userRepository.findUserProfileByUserId(rq.getUserId())
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .map(profile -> mapper.updateUserProfile(profile, new BigDecimal(rq.getTopUpAmount())))
                .zipWhen(userRepository::save)
                .map(Tuple2::getT2);



    }

    @Override
    public Mono<UserProfile> createUserProfile(CreateUserProfileRq rq) {
        return userRepository.findUserProfileByNickName(rq.getNickName())
                .switchIfEmpty(userRepository.save(mapper.mapNewProfile(rq)));
    }
}
