package com.iontrading.isf.persistence_notification_samples.account_manager;

import com.iontrading.isf.committer.spi.AbstractPNConfiguration;
import com.iontrading.isf.committer.spi.PNConfigurator;
import com.iontrading.isf.persistence_notification_samples.account_manager.configurator.Account;

/**
 * Register with PnN the account domain entity and its publishable and persistable representations.
 * 
 * We also specify that the account is not revisionable.
 */
public class ApplicationPNConfiguration extends AbstractPNConfiguration {

    @Override
    public void onConfigure(PNConfigurator config) {
        config.registerPersistable(Account.class).notRevisionable().register();
        config.registerDomain(Account.class).register();
    }
}
