package com.snc.sncuserservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class UserProfile {
    @Id
    private String userId;
    private BigDecimal balance;
    private String email;
    private String docId;
    private String nickName;
    private String login;
    private String passWord;

    public String getUserId() {
        return userId;
    }

    public UserProfile setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfile setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDocId() {
        return docId;
    }

    public UserProfile setDocId(String docId) {
        this.docId = docId;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public UserProfile setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public UserProfile setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public UserProfile setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public UserProfile setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}
