/*
 * Created: Oct 18, 2013
 * 
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
import com.iontrading.isf.committer.spi.PNEntityAttributes;
import com.iontrading.isf.committer.spi.PNEntityAttributesRetriever;
import com.iontrading.isf.committer.spi.TransactionCommitStrategy;
import com.iontrading.isf.commons.async.AsyncResultPromise;
import com.iontrading.isf.persistence_notification_samples.account_manager.configurator.Account;
import com.iontrading.isf.trace.ITracer;
import com.iontrading.talk.api.annotation.TalkFunction;
import com.iontrading.talk.api.annotation.TalkParam;
import com.iontrading.talk.api.exception.TalkFunctionException;

/**
 */
public class DeleteAccountService {
    private final ITracer logger;
    private final CommitterTransactionFactory committer;
    private final Fetcher fetcher;
    private final PNEntityAttributesRetriever attributesRetriever;

    @Inject
    public DeleteAccountService(ITracer logger, CommitterTransactionFactory committer, Fetcher fetcher,
            PNEntityAttributesRetriever attributesRetriever) {
        this.logger = logger;
        this.committer = committer;
        this.fetcher = fetcher;
        this.attributesRetriever = attributesRetriever;
    }

    @TalkFunction
    public void deleteAccount(@TalkParam(name = "Id") String id, final AsyncResultPromise<Void> result) {
        // fetch the previous image of the account
        final Account previous = fetcher.findById(Account.class, id);
        if (previous == null) {
            result.failure(new TalkFunctionException(-1, ":account not found"));
            return;
        }

        try {
            // Start a new transaction
            CommitterTransaction transaction = committer.create();

            // Delete the account by incrementing the update number
            // to the CommitterTransaction
            transaction.delete(previous);

            // commit the change
            transaction.commit(new CommitterListener() {

                @Override
                public void onPending(Collection<? extends DomainEntity> entities) {
                }

                @Override
                public void onRollback(Collection<? extends DomainEntity> entities, Throwable t) {
                    // A problem in committing the transaction: report it to
                    // the caller.
                    result.failure(t);
                }

                @Override
                public void onCommit(Collection<? extends DomainEntity> entities) {
                    for (DomainEntity entity : entities) {
                        PNEntityAttributes attrs = attributesRetriever.getAttributes(entity);
                        if (attrs.isDeleted())
                            logger.INFO().key(entity.getIdentifier().toString()).action("Deleted").end();
                    }
                    // Reply to the function
                    result.success(null);
                }

            }, TransactionCommitStrategy.SINGLE_TRANSACTION);
        } catch (Exception e) {
            // A problem in managing the transaction: report it to the caller
            result.failure(e);
        }
    }
}