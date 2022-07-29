package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager;

import java.util.Arrays;

import com.iontrading.isf.committer.spi.AbstractPNConfiguration;
import com.iontrading.isf.committer.spi.PNConfigurator;
import com.iontrading.isf.mapping.guice.MappingModule;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashMovement;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashMovementMapper;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashTransaction;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashTransactionMapper;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.PersistableCashMovement;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.PersistableCashTransaction;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.PublishableCashTransactionMovement;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.PublishableCashTransactionMovementMapper;

/**
 * Register with P&nN the CashTransaction and {@link CashMovement} domain
 * entities and their persistable representations.
 * 
 * Also, a mapper ({@link PublishableCashTransactionMovementMapper}) for the
 * publishable ({@link PublishableCashTransactionMovement}) representing the
 * join of the two linked entities ({@link CashTransaction} and
 * {@link CashMovement}) is registered in the mapping service
 * 
 * Note: the external id is defined only to simplify the fetching of the
 * persisted entities
 */
public class CashTransactionApplicationPNConfiguration extends AbstractPNConfiguration {

    @Override
    public void onConfigure(PNConfigurator config) {
        config.registerPersistable(PersistableCashTransaction.class)
            .withExternalId("EXT_ID", Arrays.asList("transId"))
            .register();

        config.registerPersistable(PersistableCashMovement.class)
            .withExternalId("EXT_ID", Arrays.asList("movId"))
            .register();

        config.registerDomain(CashTransaction.class)
            .withPersistable(PersistableCashTransaction.class)
            .fromDomain(CashTransactionMapper.class)
            .toDomain(CashTransactionMapper.class)
            .register();

        config.registerDomain(CashMovement.class)
            .withPersistable(PersistableCashMovement.class)
            .fromDomain(CashMovementMapper.class)
            .toDomain(CashMovementMapper.class)
            .register();
    }

    @Override
    protected void configure() {
        super.configure();

        MappingModule.registerMapper(binder(), PublishableCashTransactionMovementMapper.class);
    }
}
