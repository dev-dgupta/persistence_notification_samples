package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.iontrading.isf.committer.spi.AbstractPersistableEntity;
import com.iontrading.proguard.annotation.KeepAll;

@Entity
@Table(name = "CASH_TRANSACTION")
@KeepAll
public class PersistableCashTransaction extends AbstractPersistableEntity {
    private static final long serialVersionUID = -3353519378448459512L;

    private String transId;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

}
