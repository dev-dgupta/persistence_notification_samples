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
import com.iontrading.isf.committer.spi.Fetcher;
import com.iontrading.isf.committer.spi.TransactionCommitStrategy;
import com.iontrading.isf.commons.async.AsyncResultPromise;
import com.iontrading.isf.persistence_notification_samples.account_manager.configurator.Account;
import com.iontrading.talk.api.annotation.TalkFunction;
import com.iontrading.talk.api.annotation.TalkParam;
import com.iontrading.talk.api.annotation.TalkResultStrategy;
import com.iontrading.talk.ionbus.spi.SimpleResultStrategy;

/**
 * Provides the account amendment service. When the bus function is invoked: -
 * it fetches the previous image of the account through the <code>Fetcher</code> - it creates a new <code>Account</code>
 * by providing the previous account
 * image - it adds the new created account to the CommitterTransaction - it
 * commits the change - it replies to the function
 * 
 */
public class ModifyAccountService {
    private final CommitterTransactionFactory committer;
    private final Fetcher fetcher;

    @Inject
    public ModifyAccountService(CommitterTransactionFactory committer, Fetcher fetcher) {
        this.committer = committer;
        this.fetcher = fetcher;
    }

    @TalkFunction
    @TalkResultStrategy(SimpleResultStrategy.class)
    public void modifyAccount(@TalkParam(name = "Id") String id, @TalkParam(name = "Name") String name,
            @TalkParam(name = "ShortName") String shortName, @TalkParam(name = "Description") String description,
            final AsyncResultPromise<String> result) {

        // fetch the previous image of the trade
        final Account previous = fetcher.findById(Account.class, id);
        if (previous == null) {
            result.success("-1:account not found");
            return;
        }

        // creates a new Account by providing the previous account image
        final Account account = new Account(previous);
        account.setName(name);
        account.setShortName(shortName);
        account.setDescription(description);

        try {
            // setFunctionResults() in case of errors
            CommitterTransaction transaction = committer.create();

            // Update the account by incrementing the update number
            // to the CommitterTransaction
            transaction.addOrUpdate(account);

            // commit the change
            transaction.commit(new CommitterListener() {

                @Override
                public void onPending(Collection<? extends DomainEntity> entities) {
                }

                @Override
                public void onRollback(Collection<? extends DomainEntity> entities, Throwable t) {
                    // A problem in committing the transaction: report
                    // it to
                    // the caller.
                    result.failure(t);
                }

                @Override
                public void onCommit(Collection<? extends DomainEntity> entities) {
                    // Reply to the function
                    result.success("0:" + account.getIdentifier().getId());
                }

            }, TransactionCommitStrategy.SINGLE_TRANSACTION);
        } catch (Exception e) {
            // A problem in managing the transaction: report it to the
            // caller
            result.failure(e);
        }
    }
}
