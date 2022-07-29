package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.iontrading.isf.committer.spi.AbstractPersistableEntity;
import com.iontrading.proguard.annotation.KeepAll;

@Entity
@Table(name = "CASH_MOVEMENT")
@KeepAll
public class PersistableCashMovement extends AbstractPersistableEntity {
    private static final long serialVersionUID = 7698829520280981745L;

    private String movId;

    public String getMovId() {
        return movId;
    }

    public void setMovId(String movId) {
        this.movId = movId;
    }

}
