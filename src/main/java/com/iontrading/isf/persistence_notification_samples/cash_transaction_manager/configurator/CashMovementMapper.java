package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import com.iontrading.isf.committer.spi.MapperException;
import com.iontrading.isf.committer.spi.PersistableFromDomainMapper;
import com.iontrading.isf.committer.spi.PersistableToDomainMapper;

/**
 * The mapper between the {@link CashMovement} domain entity and the
 * corresponding persistable entity ({@link PersistableCashMovement})
 */
public class CashMovementMapper implements PersistableFromDomainMapper<PersistableCashMovement, CashMovement>,
        PersistableToDomainMapper<PersistableCashMovement, CashMovement> {

    @Override
    public CashMovement read(PersistableCashMovement input) throws MapperException {
        return new CashMovement(input.getMovId());
    }

    @Override
    public Class<? extends CashMovement> getDomainEntityClass() {
        return CashMovement.class;
    }

    @Override
    public PersistableCashMovement write(CashMovement input) throws MapperException {
        PersistableCashMovement result = new PersistableCashMovement();
        result.setMovId(input.getMovId());
        return result;
    }

    @Override
    public Class<? extends PersistableCashMovement> getPersistableEntityClass() {
        return PersistableCashMovement.class;
    }
}
