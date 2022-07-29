package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import java.util.ArrayList;
import java.util.Collection;

import com.iontrading.isf.committer.annotation.Link;

/**
 * The domain entity representing a very simple CashTransaction. We defined a
 * one-to-many relation between a CashTransaction and the collection of its
 * CashMovements. A publishable is provided for the P&N Link between
 * CashTransaction and CashMovements: it can be used to obtain a recoverable
 * stream of notifications of joined data of each couple of linked entities
 * (CashTransaction - CashMovement)
 * 
 */
public class CashTransaction extends AbstractDomainEntity {

    private final String transId;

    private Collection<CashMovement> cashMovements = new ArrayList<CashMovement>();

    public CashTransaction(String transId) {
        this.transId = transId;
    }

    public CashTransaction(CashTransaction previous) {
        super(previous);
        this.transId = previous.getTransId();
    }

    public String getTransId() {
        return transId;
    }

    /**
     * Defines a P&N Link and provides a publishable for the joined notification
     * of CashTransaction and CashMovements
     */
    @Link(publishedBy = PublishableCashTransactionMovement.class)
    public Collection<CashMovement> getCashMovements() {
        return cashMovements;
    }

    public void setCashMovements(Collection<CashMovement> cashMovements) {
        this.cashMovements = cashMovements;
    }
}
