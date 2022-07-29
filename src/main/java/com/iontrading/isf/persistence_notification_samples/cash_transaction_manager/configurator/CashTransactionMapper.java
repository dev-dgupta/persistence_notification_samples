package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import com.iontrading.isf.committer.spi.MapperException;
import com.iontrading.isf.committer.spi.PersistableFromDomainMapper;
import com.iontrading.isf.committer.spi.PersistableToDomainMapper;

/**
 * The mapper between the {@link CashTransaction} domain entity and the
 * corresponding persistable entity ({@link PersistableCashTransaction})
 */
public class CashTransactionMapper implements PersistableFromDomainMapper<PersistableCashTransaction, CashTransaction>,
        PersistableToDomainMapper<PersistableCashTransaction, CashTransaction> {

    @Override
    public CashTransaction read(PersistableCashTransaction input) throws MapperException {
        return new CashTransaction(input.getTransId());
    }

    @Override
    public Class<? extends CashTransaction> getDomainEntityClass() {
        return CashTransaction.class;
    }

    @Override
    public PersistableCashTransaction write(CashTransaction input) throws MapperException {
        PersistableCashTransaction cashTransaction = new PersistableCashTransaction();
        cashTransaction.setTransId(input.getTransId());
        return cashTransaction;
    }

    @Override
    public Class<? extends PersistableCashTransaction> getPersistableEntityClass() {
        return PersistableCashTransaction.class;
    }

}
