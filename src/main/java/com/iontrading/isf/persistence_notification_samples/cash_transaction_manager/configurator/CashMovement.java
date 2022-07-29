package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

/**
 * The domain entity representing a very simple CashMovement.
 * 
 * @see CashTransaction
 */
public class CashMovement extends AbstractDomainEntity {

    private final String movId;

    public CashMovement(String movId) {
        this.movId = movId;
    }

    public CashMovement(CashMovement previous) {
        super(previous);
        this.movId = previous.getMovId();
    }

    public String getMovId() {
        return movId;
    }

}
