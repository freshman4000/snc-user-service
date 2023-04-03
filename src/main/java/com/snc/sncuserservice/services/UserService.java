package com.snc.sncuserservice.services;

import com.snc.sncuserservice.models.UserProfile;
import reactor.core.publisher.Mono;
import snc.sncmodels.services.betting.rq.BetRq;
import snc.sncmodels.services.betting.rq.CreateUserProfileRq;
import snc.sncmodels.services.betting.rq.TopUpRq;

public interface UserService {
    Mono<UserProfile> applyChangesToBalance(BetRq rq);
    Mono<UserProfile> applyChangesToBalance(TopUpRq rq);

    Mono<UserProfile> createUserProfile(CreateUserProfileRq rq);
}
