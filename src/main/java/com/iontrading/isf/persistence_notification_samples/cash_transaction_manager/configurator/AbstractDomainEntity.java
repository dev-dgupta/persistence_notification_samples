package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.RevisionIdentifier;

public class AbstractDomainEntity implements DomainEntity {

    private DomainEntity previous;
    private RevisionIdentifier id;

    public AbstractDomainEntity() {
    }

    public AbstractDomainEntity(DomainEntity previous) {
        this.previous = previous;
    }

    @Override
    public RevisionIdentifier getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(RevisionIdentifier newId) {
        this.id = newId;
    }

    @Override
    public RevisionIdentifier getPreviousIdentifier() {
        return previous != null ? previous.getIdentifier() : null;
    }

}
