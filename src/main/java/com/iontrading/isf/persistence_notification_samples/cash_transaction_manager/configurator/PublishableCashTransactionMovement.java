package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import com.iontrading.isf.committer.spi.AbstractPublishableEntity;
import com.iontrading.proguard.annotation.KeepAll;
import com.iontrading.talk.api.annotation.TalkProperty;
import com.iontrading.talk.api.annotation.TalkType;

/**
 * A publishable entity defining the set of joined data to be notified through
 * the P&N MessageQueue notification protocol for each pair of linked
 * {@link CashTransaction} - {@link CashMovement} entities.
 */
@TalkType
@KeepAll
public class PublishableCashTransactionMovement extends AbstractPublishableEntity {
    private String transactionId;
    private Integer transactionRev;
    private String movementId;
    private Integer movementRev;

    @TalkProperty(nullable = true)
    public String getCashTransactionId() {
        return transactionId;
    }

    public void setCashTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @TalkProperty(nullable = true)
    public Integer getCashTransactionRev() {
        return transactionRev;
    }

    public void setCashTransactionRev(Integer transactionRev) {
        this.transactionRev = transactionRev;
    }

    @TalkProperty(nullable = true)
    public String getCashMovementId() {
        return movementId;
    }

    public void setCashMovementId(String movementId) {
        this.movementId = movementId;
    }

    @TalkProperty(nullable = true)
    public Integer getCashMovementRev() {
        return movementRev;
    }

    public void setCashMovementRev(Integer movementRev) {
        this.movementRev = movementRev;
    }

}
