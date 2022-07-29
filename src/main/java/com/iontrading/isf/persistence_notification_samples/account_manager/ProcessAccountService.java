/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.account_manager;

import java.util.Collection;

import javax.inject.Inject;

import com.iontrading.isf.committer.spi.CommitterListener;
import com.iontrading.isf.committer.spi.CommitterTransaction;
import com.iontrading.isf.committer.spi.CommitterTransactionFactory;
import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.TransactionCommitStrategy;
import com.iontrading.isf.commons.async.AsyncResultPromise;
import com.iontrading.isf.persistence_notification_samples.account_manager.configurator.Account;
import com.iontrading.talk.api.annotation.TalkFunction;
import com.iontrading.talk.api.annotation.TalkParam;
import com.iontrading.talk.api.annotation.TalkResultStrategy;
import com.iontrading.talk.ionbus.spi.SimpleResultStrategy;

/**
 * Provides the account creation service. When the bus function is invoked: - it
 * creates a new Account - it adds the new created account to the
 * CommitterTransaction - it commits the transaction with the SINGLE_TRANSACTION
 * strategy - it replies to the function with the identifier assigned by the
 * persistence_notification engine.
 * 
 */

public class ProcessAccountService {
    private final CommitterTransactionFactory committer;

    @Inject
    public ProcessAccountService(CommitterTransactionFactory committer) {
        this.committer = committer;
    }

    @TalkFunction
    @TalkResultStrategy(SimpleResultStrategy.class)
    public void createAccount(@TalkParam(name = "Name") String name, @TalkParam(name = "ShortName") String shortName,
            @TalkParam(name = "Description") String description, final AsyncResultPromise<String> result) {
        final Account account = new Account();
        account.setName(name);
        account.setShortName(shortName);
        account.setDescription(description);

        // Create a transaction
        CommitterTransaction transaction = committer.create();

        // adds the new trade to the CommitterTransaction
        transaction.addOrUpdate(account);

        // We are now ready to commit the transaction
        transaction.commit(new CommitterListener() {

            @Override
            public void onPending(Collection<? extends DomainEntity> entities) {
            }

            @Override
            public void onCommit(Collection<? extends DomainEntity> entities) {
                // Reply to the function with the id assigned by the
                // persistence_notification engine
                result.success("0:" + account.getIdentifier().getId());
            }

            @Override
            public void onRollback(Collection<? extends DomainEntity> entities, Throwable t) {
                // A problem in committing the transaction: report it to the
                // caller.
                result.success("-1:" + t.getMessage());
            }

        }, TransactionCommitStrategy.SINGLE_TRANSACTION);
    }
}
