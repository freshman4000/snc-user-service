package com.snc.sncuserservice.handlers;

import com.snc.snckafkastarter.JsonConverter;
import com.snc.snckafkastarter.converters.KafkaMessageCreator;
import com.snc.snckafkastarter.event.annotation.Event;
import com.snc.snckafkastarter.event.annotation.EventHandler;
import com.snc.snckafkastarter.kafka.MessageService;
import com.snc.snckafkastarter.models.KafkaMessage;
import com.snc.sncuserservice.mapper.Mapper;
import com.snc.sncuserservice.models.UserProfile;
import com.snc.sncuserservice.services.TransactionService;
import com.snc.sncuserservice.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import snc.sncmodels.constants.Events;
import snc.sncmodels.services.betting.rq.BetRq;
import snc.sncmodels.services.betting.rq.CreateUserProfileRq;
import snc.sncmodels.services.betting.rq.TopUpRq;

import java.util.Map;

@EventHandler
@AllArgsConstructor
@Slf4j
public class UserProfileHandler {
    private final MessageService messageService;
    private final KafkaMessageCreator kafkaMessageCreator;
    private final UserService userService;
    private final TransactionService transactionService;
    private final Mapper mapper;

    @Event(Events.APPLY_BET_RESULT)
    public void applyBetResult(KafkaMessage message) {
        Map<String, String> headers = message.getHeaders();
        try {
            BetRq betRq = JsonConverter.objectFromJson(message.getMessageBody(), BetRq.class);
            Mono<UserProfile> updatedProfile = userService.applyChangesToBalance(betRq);
            updatedProfile.zipWhen(profile -> transactionService.saveTransaction(betRq))
                    .map(tuple -> mapper.mapProfileAndTransactionToBetResponse(tuple.getT1(), tuple.getT2()))
                    .doOnError(e -> messageService.sendMessage(kafkaMessageCreator.getErrorMessage(e, headers)))
                    .subscribe(response -> messageService.sendMessage(kafkaMessageCreator.getSuccessMessage(response, headers)));
        } catch (Exception exception) {
            messageService.sendMessage(kafkaMessageCreator.getErrorMessage(exception, message.getHeaders()));
        }
    }
    @Event(Events.TOP_UP_BALANCE)
    public void topUpBalance (KafkaMessage message) {
        Map<String, String> headers = message.getHeaders();
        try {
            TopUpRq topUpRq = JsonConverter.objectFromJson(message.getMessageBody(), TopUpRq.class);
            Mono<UserProfile> updatedProfile = userService.applyChangesToBalance(topUpRq);
            updatedProfile.zipWhen(profile -> transactionService.saveTransaction(topUpRq))
                    .map(tuple -> mapper.mapProfileAndTransactionToToUpResponse(tuple.getT1(), tuple.getT2()))
                    .doOnError(e -> messageService.sendMessage(kafkaMessageCreator.getErrorMessage(e, headers)))
                    .subscribe(response -> messageService.sendMessage(kafkaMessageCreator.getSuccessMessage(response, headers)));
        } catch (Exception exception) {
            messageService.sendMessage(kafkaMessageCreator.getErrorMessage(exception, message.getHeaders()));
        }
    }
    @Event(Events.CREATE_USER_PROFILE)
    public void createUserProfile (KafkaMessage message) {
        Map<String, String> headers = message.getHeaders();
        try {
            CreateUserProfileRq rq = JsonConverter.objectFromJson(message.getMessageBody(), CreateUserProfileRq.class);
                     userService.createUserProfile(rq)
                    .map(mapper::mapProfileToCreateUserProfileRs)
                    .doOnError(e -> messageService.sendMessage(kafkaMessageCreator.getErrorMessage(e, headers)))
                    .subscribe(response -> messageService.sendMessage(kafkaMessageCreator.getSuccessMessage(response, headers)));
        } catch (Exception exception) {
            messageService.sendMessage(kafkaMessageCreator.getErrorMessage(exception, message.getHeaders()));
        }
    }
}
