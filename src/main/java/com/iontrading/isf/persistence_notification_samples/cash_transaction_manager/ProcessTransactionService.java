/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.iontrading.isf.committer.spi.CommitterListener;
import com.iontrading.isf.committer.spi.CommitterTransaction;
import com.iontrading.isf.committer.spi.CommitterTransactionFactory;
import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.Fetcher;
import com.iontrading.isf.commons.async.AsyncResultPromise;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashMovement;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashTransaction;
import com.iontrading.proguard.annotation.KeepAll;
import com.iontrading.talk.api.annotation.TalkFunction;
import com.iontrading.talk.api.annotation.TalkParam;

/**
 * Provides the entities ({@link CashTransaction} and {@link CashMovement})
 * creation service.
 */

@KeepAll
public class ProcessTransactionService {
    private final CommitterTransactionFactory committer;
    private final Fetcher fetcher;

    @Inject
    public ProcessTransactionService(CommitterTransactionFactory committer, Fetcher fetcher) {
        this.committer = committer;
        this.fetcher = fetcher;
    }

    /**
     * Insert a new {@link CashTransaction}.
     * 
     * @param transId
     *            The user defined Id of the {@link CashTransaction} (used as
     *            external id by P&N)
     */
    @TalkFunction
    public void createTransaction(@TalkParam(name = "TransId") String transId, AsyncResultPromise<Void> result) {
        CashTransaction transaction = new CashTransaction(transId);

        commitAndReply(result, transaction);
    }

    /**
     * Insert a new {@link CashMovement}.
     * 
     * @param movId
     *            The user defined Id of the {@link CashMovement} (used as
     *            external id by P&N)
     */
    @TalkFunction
    public void createMovement(@TalkParam(name = "MovId") String movId, AsyncResultPromise<Void> result) {
        CashMovement movement = new CashMovement(movId);

        commitAndReply(result, movement);
    }

    /**
     * Amend an existing {@link CashTransaction} (or insert a new one if no
     * transaction with the provided Id is found) setting its collection of
     * {@link CashMovement} to be equal to to the provided list of
     * {@link CashMovement} entities (identified by their Ids). If a
     * {@link CashMovement} in the list already exists, it will not be amended;
     * if it does not exist it will be created.
     * 
     * @param transId
     *            The user defined Id of the {@link CashTransaction} to be
     *            amended (or to be inserted)
     * 
     * @param movements
     *            The list of user defined Ids of the {@link CashMovement}
     *            entities to be added to the CashTransaction
     */
    @TalkFunction
    public void bindMovementsToTransaction(@TalkParam(name = "TransId") String transId,
            @TalkParam(name = "MovementsIds") List<String> movements, AsyncResultPromise<Void> result) {

        CashTransaction transaction = fetcher.byExternalId(CashTransaction.class, "EXT_ID")
            .idField("transId", transId)
            .find();

        CashTransaction newTransaction;

        if (transaction != null) {
            newTransaction = new CashTransaction(transaction);
        } else {
            newTransaction = new CashTransaction(transId);
        }
        populateTransaction(newTransaction, movements);
        commitAndReply(result, newTransaction);
    }

    private void populateTransaction(CashTransaction transaction, List<String> movements) {
        for (String movId : movements) {
            CashMovement movement = fetcher.byExternalId(CashMovement.class, "EXT_ID").idField("movId", movId).find();
            if (movement != null) {
                transaction.getCashMovements().add(movement);
            } else {
                transaction.getCashMovements().add(new CashMovement(movId));
            }
        }
    }

    private void commitAndReply(final AsyncResultPromise<Void> result, DomainEntity... entities) {
        // Create a P&N transaction
        CommitterTransaction pnTransaction = committer.create();

        for (DomainEntity entity : entities) {
            pnTransaction.addOrUpdate(entity);
        }

        // Commit the P&N transaction and notify to the caller the success or
        // the failure of the commit
        pnTransaction.commit(new CommitterListener() {

            @Override
            public void onPending(Collection<? extends DomainEntity> entities) {
            }

            @Override
            public void onCommit(Collection<? extends DomainEntity> entities) {
                result.success(null);
            }

            @Override
            public void onRollback(Collection<? extends DomainEntity> entities, Throwable t) {
                // A problem was found while committing the transaction: report
                // it to the caller.
                result.failure(t);
            }
        });
    }
}
