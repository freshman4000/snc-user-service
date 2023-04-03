package com.snc.sncuserservice.repositories;

import com.snc.sncuserservice.models.UserProfile;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveSortingRepository<UserProfile, String> {
    Mono<UserProfile> findUserProfileByUserId(String userId);

    Mono<UserProfile> findUserProfileByNickName(String nickName);
}
